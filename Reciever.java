
import com.rabbitmq.client.*;
import java.util.*;
import java.io.*;
import com.fasterxml.jackson.databind.*;

public class TestReciever implements Runnable{

    private String message;
    private String node;
    private String environment;

    public void run() {

      LogWriter lw = new LogWriter();

      try{
        /* ***********************************************
         * Properties File
        *********************************************** */
        String environment = "SERVER_NAME";
        Properties configFile = new Properties();
        InputStream input = null;
        input = new FileInputStream("./Rabbit.properties");
        configFile.load(input);
        String User = configFile.getProperty("User");
        String Password = configFile.getProperty("Pass");
        String URL = configFile.getProperty("URL");
        String Queue = configFile.getProperty("QUEUENAME");
        String CommID = configFile.getProperty("COMMID");

        lw.main(" [+] Queue: "+Queue);


        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(User);
        factory.setPassword(Password);
        factory.setHost(URL);
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(Queue, false, false, true, null);
        channel.queueBind(Queue, "EXCHANGE_NAME", "CO-FF-EE");
        System.out.println(" [+] Listening for Messages...");
        
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(Queue, true, consumer);
        
        while (true) {
          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
          message = new String(delivery.getBody());
          System.out.println(" [%] Message : "+message);
          lw.main(message);
            // Strip Name From JSON
            //create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            //read JSON like DOM Parser
            JsonNode rootNode = objectMapper.readTree(message);
            JsonNode headNode = rootNode.path("header");
            JsonNode nameNode = headNode.path("name");
            //System.out.println("Responce: "+nameNode);
            node = nameNode.toString().replaceAll("\"", "").trim();
            lw.main(" [+] Responce Received: "+node);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public String getMessage() {
      //System.out.println(node);
      return node;
    }
}

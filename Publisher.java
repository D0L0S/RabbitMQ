import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;
import java.util.*;
import java.io.*;
    
public class TestPublisher {
    
  public Connection connection;
  public Channel channel;
  public String requestQueueName = "Queue_NAME";
  public String replyQueueName;
  public QueueingConsumer consumer;
  public String CommID;

  public TestPublisher() throws Exception {

    /* ***********************************************
     * Properties File
     *********************************************** */
    Properties configFile = new Properties();
    InputStream input = null;
    input = new FileInputStream("./Rabbit.properties");
    configFile.load(input);
    String User = configFile.getProperty("User");
    String Password = configFile.getProperty("Pass");
    String URL = configFile.getProperty("URL");
    String CommID = configFile.getProperty("COMMID");

    /* ***********************************************
     * Connection String
    *********************************************** */
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername(User);
    factory.setPassword(Password);
    factory.setHost(URL);
    factory.setPort(5672);
    connection = factory.newConnection();
    channel = connection.createChannel();

    /* ***********************************************
     * Rabbit Queue
     *********************************************** */
    replyQueueName = channel.queueDeclare().getQueue();
    consumer = new QueueingConsumer(channel);
    channel.basicConsume(replyQueueName, true, consumer);
  }
  
  public String call(String message) throws Exception {

    /* ***********************************************
     * Rabbit Call
     *********************************************** */
    String response = null;
    String corrId = UUID.randomUUID().toString();
    BasicProperties props = new BasicProperties
                                .Builder()
                                .correlationId(corrId)
                                .replyTo(replyQueueName)
                                .build();
    channel.basicPublish("EXCHANGE_NAME", "CO-FF-EE", null, message.getBytes());
    return " [+] Message Sent";
  }

  public void close() throws Exception {
    connection.close();
  }
  

  public void parser(String argc) {

    /* ***********************************************
     * Body Strings
     *********************************************** */
      String RequestString = "";
      String ReleaseString = "";
      try {
        TestPublisher util = new TestPublisher();
        if (argc.contains("request")){
          System.out.println(" [+] Requesting Resource");
          String Request = util.call(RequestString);
        } else if (argc.contains("release")){
          System.out.println(" [+] Releasing Resource");
          String Release = util.call(ReleaseString);
        } else {
          System.out.println(" [!] Unknown Request");
          System.exit(0);
        }
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
  }

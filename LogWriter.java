import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class LogWriter 
{
    public static void main( String args ){	
    	try{ 
    		File file = new File("~/Rabbit/Rabbit.log");
    		//if file doesnt exists, then create it
    		if(!file.exists()){
    			file.createNewFile();
    		}
            java.util.Date date= new java.util.Date();
            Timestamp currentTimestamp= new Timestamp(date.getTime());
    		//true = append file
    		FileWriter fileWritter = new FileWriter(file.getName(),true);
    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	        bufferWritter.write(currentTimestamp+" "+args+"\n");
    	        bufferWritter.close();
 
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
}

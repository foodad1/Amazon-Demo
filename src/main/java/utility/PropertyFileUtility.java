package utility;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileUtility {

	public static Properties readPropertiesFile(String fileName) {
	      
		FileInputStream fis = null;
	      Properties prop = null;
	      try {
	    	 String filePath = ".\\src\\main\\resources\\configuration\\";
	         fis = new FileInputStream(filePath+fileName);
	         prop = new Properties();
	         prop.load(fis);
	      
	      } catch(FileNotFoundException fnfe) {
	         fnfe.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      } finally {
	         try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      return prop;
	   }
	
}

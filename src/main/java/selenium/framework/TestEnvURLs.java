package selenium.framework;

import java.util.HashMap;
import java.util.Properties;

import utility.PropertyFileUtility;

public class TestEnvURLs {
	
	private static Properties pro;

	/**
	 * @param environment - environment in which need to test 
	 * @return urlID - browser URL to navigate
	 */
	public static String launchURL(String environment) {

		pro =PropertyFileUtility.readPropertiesFile("configuration.properties");
		if(environment.isEmpty() || environment==null ) {
			environment = pro.getProperty("environment_type");
		}
		HashMap<String, String> envType = new HashMap<String, String>();
			envType.put("url","https://www.amazon.in/");
			
		return envType.get(environment);
	}

}

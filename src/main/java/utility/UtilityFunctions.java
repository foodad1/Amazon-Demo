package utility;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

public class UtilityFunctions {
	
	private static final Logger LOGGER = Logger.getLogger(UtilityFunctions.class.getName());
	WebDriver driver;
	ExtentTest testStep;
	WebDriverWait wait;
	
	public UtilityFunctions(WebDriver driver,ExtentTest testStep) {

		this.driver = driver;
		this.testStep=testStep;
		PageFactory.initElements(driver,this);
		wait = new WebDriverWait(this.driver,15);
	}
	
	public UtilityFunctions() {
	}
	
	@SuppressWarnings("deprecation")
	public static void killDriverProcess() {
		
		try {
			if(System.getProperty("os.name").toLowerCase().contains("windows")) {
				WindowsUtils.killByName("chromedriver.exe");
				WindowsUtils.killByName("gekodriver.exe");
			}
		} catch (Exception e) {
			LOGGER.info("kill/cleanup driver process error "+e.getMessage());
		}
	}
	
	public static String injectIDPwdInURL(String url,String uName,String pwd) {
		String newURL=null;
		StringBuilder strBuild = new StringBuilder(url);
		if(strBuild.substring(0, 5).equalsIgnoreCase("https")) {
			newURL=strBuild.insert(7, uName+":"+pwd+"@").toString();
		} else {
			newURL=strBuild.insert(7, uName+":"+pwd+"@").toString();
		}
		//System.out.println(url);
		System.out.println("new URL - "+newURL);
		return newURL;

		
	}
	
	public String generateRandomNum(int maxValue) {
		
		int random = (int) (Math.random() * (maxValue - 1 + 5) * 10);
		return Integer.toString(random);
	    //ThreadLocalRandom.current().nextInt(500);
	}

}

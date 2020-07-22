package selenium.framework;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverBuilder {

	//public static WebDriver driver;

	private final static String DRIVERPATH = "\\src\\main\\resources\\drivers\\";

	/**
	 * @param params - global parameter of testng xml
	 * @param caps - test parameters of xml
	 * @return WebDriver instance to initiate the browser  
	 */
	protected static WebDriver getWebDriver(Map<String,String> params ,Map<String,String> caps) {

		if (params.get("runRemote").equals("true" )) {
			return RemoteWebDriverBuilder( params ,caps);
		} else {
			return LocalWebDriverBuilder(params,caps);
		}

	}

	private static WebDriver RemoteWebDriverBuilder(Map<String, String> params, Map<String, String> caps) {
		//Remote Driver launch or Mobile Device Launch <Appium or IOS>
		WebDriver driver = null;
		//driver = new RemoteWebDriver(executor, desiredCapabilities)
		return  driver;
	}

	private static WebDriver LocalWebDriverBuilder(Map<String,String> params ,Map<String,String> caps) {

		System.out.println("Initiating browser");
		WebDriver driver = null;
		if(caps.get("browserName").equalsIgnoreCase("chrome")) {

			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+DRIVERPATH+"chromedriver.exe");
			driver=new ChromeDriver();

		} else if (caps.get("browserName").equalsIgnoreCase("firefox")) {
			
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+DRIVERPATH+"geckodriver.exe");
			//File pathBinary = new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
			//FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);   
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			//FirefoxProfile firefoxProfile = new FirefoxProfile();
			capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			capabilities.setCapability("firefox_binary","C:\\Program Files\\Mozilla Firefox\\firefox.exe");
			driver = new FirefoxDriver(capabilities);
			//driver = new FirefoxDriver(firefoxBinary,firefoxProfile);

		} else if (caps.get("browserName").equalsIgnoreCase("chrome_headless")) {

			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+DRIVERPATH+"chromedriver.exe");
			ChromeOptions chromeOptions = new ChromeOptions();
			DesiredCapabilities capabilities = new DesiredCapabilities();
			//chromeOptions.setBinary("/usr/bin/chromium-browser");
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("disable-popup-blocking");
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			driver = new ChromeDriver(capabilities);
		} else {
			System.out.println("Browser Name is not valid or null so initiating default IE driver");
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+DRIVERPATH+"IEDriverServer.exe");  
			// Instantiate a IEDriver class.       
			driver=new InternetExplorerDriver();  
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		return driver;
	}
}

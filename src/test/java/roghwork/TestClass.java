package roghwork;

import java.util.LinkedHashMap;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import amazon.pages.HomePage;
import amazon.pages.LoginPage;
import selenium.framework.BaseBeforeMethodTest;
import selenium.framework.TestEnvURLs;
import selenium.framework.TestMemberFactory;
import selenium.reports.ExtentReportsTestClass;
import utility.PropertyFileUtility;

public class TestClass extends BaseBeforeMethodTest {

	private Properties property; 
	private LoginPage login;
	private HomePage home;


	@Parameters({"environmentType"})
	@BeforeMethod (alwaysRun = true)
	public void beforeMethod(String environmentType) {

		try {
			TestMemberFactory.getTest().log(LogStatus.INFO,"Browser initiating-----");
			String url = TestEnvURLs.launchURL(environmentType);
			TestMemberFactory.getDriver().get(url);
			Thread.sleep(1000); 

		} catch (Exception e) { 
			TestMemberFactory.getTest().log(LogStatus.SKIP,ExtentReportsTestClass.formatTestSkippedMsg("Test skipped", e.getMessage()));
			Assert.assertTrue(false); } 
	}

	@Test()
	public void Test() throws InterruptedException {

		System.out.println("loginTest "); 
		WebDriver driver =TestMemberFactory.getDriver(); 
		ExtentTest testStep =TestMemberFactory.getTest();

		property =PropertyFileUtility.readPropertiesFile("configuration2.properties");
		login = new LoginPage(driver, testStep);
		home =  new HomePage(driver, testStep);
		
		login.loginUser(property.getProperty("username"), property.getProperty("password"));
		home.enterSearchValue("oneplus smartPhone");
		home.sortProductPreference("Price: Low to High");//
		home.selectItemFromCatalogue("QTek Full Glass");
		Thread.sleep(500);
		home.clickAddToCartBtn();
		home.clickCartLink();
		Thread.sleep(1000);
		home.verifyCartItems("1");
		login.logout();
		
	}

}

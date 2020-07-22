package selenium.framework;

import java.lang.reflect.Method;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseBeforeMethodTest {

	public static final String BROWSERNAME = "BrowserName";

	/**
	 * @param m : Capture method name
	 * @param testListener - get parameter of currenXML
	 */
	@BeforeMethod(alwaysRun = true)
	public void baseBeforeMethod(Method m, ITestContext testListener) {

		XmlSuite suite = testListener.getSuite().getXmlSuite();
		Map<String, String> params = suite.getParameters();
		XmlTest test = testListener.getCurrentXmlTest();
		Map<String, String> caps = test.getLocalParameters();

		TestMemberFactory.setDriver(params, caps);
		TestMemberFactory.setReports("", testListener.getSuite().getName(), m.getName());
		TestMemberFactory.setTest(m.getName());

		// System Info
		ExtentReports report = TestMemberFactory.getReports();
		WebDriver driver = TestMemberFactory.getDriver();
		ExtentTest testStep = TestMemberFactory.getTest();

		if (driver != null) {
			testStep.log(LogStatus.INFO, caps.get(BROWSERNAME) == null ? "N/A"
					: caps.get(BROWSERNAME).toUpperCase() + " - Browser Launched ");
			System.out.println("Driver launched successfully");
		} else {
			testStep.log(LogStatus.SKIP, "WebDriver not created ,check the error message");

			report.endTest(testStep);
			report.flush();
			report.close();
			Assert.assertTrue(false, "WebDriver not created - Execution aborted ");

		}
	}

	@AfterMethod
	public void afterMethod() {
		
		ExtentReports report = TestMemberFactory.getReports();
		WebDriver driver = TestMemberFactory.getDriver();
		ExtentTest testStep=TestMemberFactory.getTest();

		driver.close();
		report.endTest(testStep);

		// writing everything to document. 
		report.flush();


	}

}

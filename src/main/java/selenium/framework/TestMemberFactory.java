package selenium.framework;

import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import selenium.reports.ExtentReportsTestClass;

public class TestMemberFactory {

	private static ThreadLocal<WebDriver> tmDriver = new ThreadLocal<>();
	private static ThreadLocal<ExtentTest> tmtestStep = new ThreadLocal<>();
	private static ThreadLocal<ExtentReports> tmReport = new ThreadLocal<>();
	private static ThreadLocal<String> tmReportPath = new ThreadLocal<>();

	private TestMemberFactory() {
		throw new IllegalStateException("TestMemberFactory.class");
	}

	public static WebDriver getDriver() {
		return tmDriver.get();

	}
	public static ExtentTest getTest() {
		return tmtestStep.get();

	}
	public static ExtentReports getReports() {
		return tmReport.get();

	}
	public static String getReportsPath() {
		return tmReportPath.get();

	}

	public static void setDriver(Map<String,String> params ,Map<String,String> caps) {
		tmDriver.set(DriverBuilder.getWebDriver(params,caps));
	}

	/*
	 * public static void setReportPath () { ExtentReportsTestClass report = new
	 * ExtentReportsTestClass(); tmReportPath.set(report.getReportPath())
	 */
		
	public static void setTest(String testName) {
		try { tmtestStep.set(getReports().startTest(testName)); 
		} catch(Exception e) {
			Logger.getLogger(e.getMessage());
		}
	}

	public static synchronized void setReports (String detailedDirectory,String suiteName,String testName) {

		ExtentReportsTestClass report = new ExtentReportsTestClass();
		tmReport.set(report.getReport(detailedDirectory, suiteName, testName));
		tmReportPath.set(report.getReportPath());
	}

}

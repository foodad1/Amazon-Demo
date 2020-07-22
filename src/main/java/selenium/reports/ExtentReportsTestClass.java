package selenium.reports;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import selenium.framework.ScreenshotUtility;


public class ExtentReportsTestClass {

	private String ReportFilePath;
	private static ThreadLocal<ExtentReports> reportDeatils= new ThreadLocal<>();
	//private static final Logger LOGGER = Logger.getLogger(ExtentReportsTestClass.class.getName());
	private static String errorMessageTable = "<TABLE style='border=2px solid black; table-layout: fixed'>";
	
	
	public void assertAllReportErr(SoftAssert sAssert) {
		sAssert.assertAll();
	}
	
	public ExtentReports getReportInstance(String detailedDirectory,ITestContext suite,String testTypeName) {
		if (detailedDirectory.isEmpty()) {
			detailedDirectory = new File("").getAbsolutePath()+"\\DetailedReports";
		}
		System.out.println(detailedDirectory);
		ReportFilePath = detailedDirectory+"\\"+suite.getName()+"_"+testTypeName+"_"+getDateTimeSys()+".html";
		System.out.println(ReportFilePath);
		ExtentReports reports = new ExtentReports(ReportFilePath,true);
		reportDeatils.set(reports);
		return reportDeatils.get();
		
	}

	private String getDateTimeSys() {
		return new SimpleDateFormat("MM_dd_yyyy_hh_mm_ss").format(new Date());
		 
	}

	public String getReportPath() {
		return ReportFilePath;
	}
	
	public ExtentReports getReport (String detailedDirectory,String suiteName ,String testTypeName) {
		
		if (detailedDirectory.isEmpty()) {
			detailedDirectory = new File("").getAbsolutePath()+"/DetailedReports";
		}
		System.out.println("Report Path : "+detailedDirectory);
		ReportFilePath = detailedDirectory+"/"+suiteName+"_"+testTypeName+"_"+getDateTimeSys()+".html";
		//System.out.println(detailedDirectory +" *&*"+ReportFilePath);
		return new ExtentReports(ReportFilePath,true);
	}
	
	public static ExtentReports getReportDetails() {
		
		return reportDeatils.get();
	}
	
	public void setReportLink(String reportPath) {
		
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("DETAILED RESULT LOCATED AT --> <a href=\""+reportPath +"\">Click to see Detailed Result </a>");
	}
	
	public static void logFailureTestStep(ExtentTest testStep,String message) {
		
		testStep.log(LogStatus.FAIL, message);
		//testStep.addScreenCapture(ScreenshotUtility.getScreenshot(driver));
	}
	
	/**
	 * Log a failed exception message to display in Detailed Report when throws exception.
	 * @param iResult Test results
	 * @param testStep ExtentTest test step fail
	 */
	public static void formattedFailureExceptionMessage(ITestResult iResult, ExtentTest testStep) {
		
		if(iResult.getThrowable()!= null) {
			StackTraceElement stackTraceElement[]=iResult.getThrowable().getStackTrace();
			if(!(stackTraceElement[0].getClassName().contains("org.testng.asserts"))) {
				String trackStage = iResult.getThrowable().toString()+ " on line "+stackTraceElement[0].getLineNumber();
				ExtentReportsTestClass.logFailureTestStep(testStep," Exception Thrown --"+formatFailMessage("Test Exception Thrown", "Fail Message "));
				
			}
		}
	}
	
	public static String formatFailMessage(String failTitle,String msg) {
		String errorMsg = errorMessageTable+"<TR style='border:2px solid black'><TD style='border:2px solid black;width:50px'>";
		errorMsg = errorMsg+"<TR style='border:2px solid black'><TD style='border:2px solid black'><B>failure Message</B></TD></TR>";
		errorMsg = errorMsg+"<TR style='border:2px solid black'><TD style='border:2px solid black'><B>Test Status</B></TD></TR>";
		return errorMsg;
	}
	
	public static void logPassTestStep(ExtentTest testStep,String message) {
		testStep.log(LogStatus.PASS, message);
		
	}
	
	public static String formatTestSkippedMsg(String skipTitle,String msg) {
		String errorMsg = errorMessageTable+"<TR style='border:2px solid black'><TD style='border:2px solid black;width:200px'>";
		errorMsg = errorMsg+"<TR style='border:2px solid black'><TD style='border:2px solid black'><B>msg</B></TD></TR>";
		errorMsg = errorMsg+"<TR style='border:2px solid black'><TD style='border:2px solid black'><B>Test Status</B></TD></TR>";
		return errorMsg;
	}
	
	
	 public static void logtestNGInfoTestStep(ExtentTest testStep,String message)
	  { testStep.log(LogStatus.INFO, message); }
	 
	public void logTestStepInfo(ExtentTest testStep,String message) {
		testStep.log(LogStatus.INFO, message);
	}
}

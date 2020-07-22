package selenium.reports;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.io.FileUtils;
import org.testng.IExecutionListener;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import utility.UtilityFunctions;


public class TestListenerClass implements ISuiteListener,IExecutionListener,IReporter{

	 private long startTime;
	 private Queue<ISuite> queueSuite = new LinkedList<ISuite>();
	 
	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		Reporter.log("On Start Suite - About to begin Excecution Suite  "+suite.getName(), true);
		String outputFlder = new File("").getAbsolutePath()+"\\test-output" ;
		File testOutputFldr = new File(outputFlder);
		FileUtils.deleteQuietly(testOutputFldr);
		testOutputFldr.mkdir();
	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		Reporter.log("On Finish Suite - About to End Excecution Suite  "+suite.getName(), true);
		UtilityFunctions.killDriverProcess();
		queueSuite.add(suite);
	}

	@Override
	public void onExecutionStart() {
		startTime = System.currentTimeMillis();
        System.out.println("TestNG is going to start....");
	}

	@Override
	public void onExecutionFinish() {
		System.out.println("TestNG has finished, took around " + (System.currentTimeMillis() - startTime));
		Reporter.log("TestNG has finished, took around " + (System.currentTimeMillis() - startTime));
		
		Map<String,Integer> sresults = new HashMap<String, Integer>();
		for(ISuite suite: queueSuite) {
			Map<String,ISuiteResult> suiteResult = suite.getResults();
			for(ISuiteResult sr:suiteResult.values()) {
				ITestContext c = sr.getTestContext();
				if(sresults.size()==0) {
					sresults.put("passed", c.getPassedTests().getAllResults().size());
					sresults.put("failed", c.getPassedTests().getAllResults().size());
					sresults.put("skiped", c.getPassedTests().getAllResults().size());
				} else {
					sresults.put("passed", (sresults.get("passed"))+ c.getPassedTests().getAllResults().size());
					sresults.put("failed", (sresults.get("failed"))+ c.getPassedTests().getAllResults().size());
					sresults.put("skiped", (sresults.get("skiped"))+ c.getPassedTests().getAllResults().size());
				}
			}
		}
	
    }

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		// TODO Auto-generated method stub
		UtilityFunctions.killDriverProcess();
	}	

}

package utility;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.DataProvider;


public class DataProviderClass {

	@DataProvider(name="excel")
	public static Object[][] getData(Method m) throws Exception {

		Object[][] result = null;
		System.out.println("Dataprovider Started------");
		String DataFileName = m.getDeclaringClass().getName().split("\\.")[1];
		String filePath = System.getProperty("user.dir")+
				"\\src\\main\\resources\\TestData\\"+DataFileName+".xlsx";
		String testCaseName = m.getName();
		//result = ExcelFunctions.getExcelData(filePath, DataFileName,testCaseName);
		
		//System.out.println("Dataprovider values------ "+result.length + result.toString());
		return result;
	}
}

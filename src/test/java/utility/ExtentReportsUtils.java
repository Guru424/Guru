package utility;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


	/* ******************
	Created ExtentReportsUtils.java and Modified by Guru on 19/02/2018
	* DriverScript and ActionKeywords classes extends ExtentReportsUtils class for Extent reporting.
	* *******************/
public abstract class ExtentReportsUtils {
    protected static ExtentReports extent;
    protected static ExtentTest test;
    private static ExtentReports extent1;
    public static String screenShotPath1;
	public String filePath ;
  

    @AfterMethod
    protected void afterMethod(ITestResult result) throws IOException {
    	  if (result.getStatus() == ITestResult.FAILURE) {
            test.log(LogStatus.FAIL, result.getThrowable());
        } /*else if (result.getStatus() == ITestResult.SKIP) {
            test.log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
        } else {
            test.log(LogStatus.PASS, "Test passed");
        }*/
		extent.endTest(test);
        extent.flush();
    }
    
    @BeforeSuite
    public void beforeSuite() throws IOException {
    	Date now = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh mm ss");
    	String time = dateFormat.format(now);
    	String resDir = System.getProperty("user.dir")+"\\Extent Reports\\"+time;
    	File resFolder = new File(resDir);
    	resFolder.mkdir();
    	final String filePath = resDir+"./ExtentReport.html";
        extent = ExtentReportsUtils.getReporter(filePath);
       }
    
    /*@AfterSuite
    protected void afterSuite() throws IOException {
       // extent.close();
    	  
    }*/
    
	public synchronized static ExtentReports getReporter(String filePath) {
        if (extent1 == null) {
        	extent1 = new ExtentReports(filePath, true);
            
        	extent1
                .addSystemInfo("Host Name", "Guru")
                .addSystemInfo("Environment", "QA");
        }
        return extent1;
    }
}
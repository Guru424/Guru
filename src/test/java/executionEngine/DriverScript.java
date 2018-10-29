package executionEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import action_Keywords.ActionKeywords;
import config.Constants;

import com.relevantcodes.extentreports.LogStatus;

import utility.ExtentReportsUtils;
import utility.ExcelUtils;
import utility.Log;

	/* ******************
	Created DriverScript.java and Modified by Guru on 19/02/2018
	* It is the main execution test to run.
	* *******************/

public class DriverScript extends ExtentReportsUtils{
	
	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static Constants constants;
	public static String sActionKeyword;
	public static String sPageObject;
	public static String sPageObjectType;
	public static Method method[];
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sTestCaseDescription;
	public static String sData;
	public static String sDescription;
	public static boolean bResult;
	public static WebDriver driver;
		
	//constructor
	public DriverScript() throws Exception{
	
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();	
		ExcelUtils.setExcelFile(Constants.Path_TestData);
		constants= new Constants();
		constants.test_environment();
		        
	}
	
	/*
	public static void main(String args[]) throws Exception
	{
    	startMain();
    }
	*/
	@Test
	static void startMain() throws Exception
	{
		BasicConfigurator.configure();
		
    	ExcelUtils.setExcelFile(Constants.Path_TestData);
    	DOMConfigurator.configure("log4j.xml");
    	String Path_OR = Constants.sPath_OR;
		FileInputStream fs = new FileInputStream(Path_OR);
		OR= new Properties(System.getProperties());
		OR.load(fs);
		DriverScript startEngine = new DriverScript();
		startEngine.execute_TestCase();
		
	}
    void execute_TestCase() throws Exception {
    	int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
    	for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
    		bResult = true;
    		sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
    		sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
    		sTestCaseDescription = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseDescription, Constants.Sheet_TestCases);
			// Start the test using the ExtentTest class object.
    		test = extent.startTest(sTestCaseDescription);
    		if (sRunMode.equalsIgnoreCase("Yes")){
    			Log.startTestCase(sTestCaseID);
    			iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
    			iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
    			if(iTestStep==iTestLastStep)
    			{
    				ExcelUtils.setCellData(Constants.KEYWORD_NO_DATA,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
    				Log.endTestCase(sTestCaseID);
    			}
    			for (;iTestStep<iTestLastStep;iTestStep++){
    				bResult=true;
    				sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,Constants.Sheet_TestSteps);
    				sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
    				sPageObjectType = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObjectType, Constants.Sheet_TestSteps);
    				System.out.println(sPageObjectType);
    				sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
    				execute_Actions();
    				if(bResult==false){
    					ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
    					Log.endTestCase(sTestCaseID);
    					break;
    				}		
    				if(bResult==true){
    					ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
    					//Log.endTestCase(sTestCaseID);
    				}	
    			}
    		}
    		else if(sRunMode.equalsIgnoreCase("NO"))
    		{
    			Log.info("Test Mode is set to NO for " + sTestCaseID);
    			ExcelUtils.setCellData(Constants.KEYWORD_NO_RUN,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
				test.log(LogStatus.WARNING, "Test Mode is set to NO for " + sTestCaseID);
		        extent.endTest(test);        
		        extent.flush();
    			Log.endTestCase(sTestCaseID);
    		}
    		else
    		{
    			Log.info("Test Mode is NOT set for " + sTestCaseID);
    			ExcelUtils.setCellData(Constants.KEYWORD_RUN_NOT_SET,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
				test.log(LogStatus.WARNING, "Test Mode is NOT set for " + sTestCaseID);
		        extent.endTest(test);        
		        extent.flush();
    			Log.endTestCase(sTestCaseID);
    		}
    	}
    }	
     
    private static void execute_Actions() throws Exception {
    	for(int i=0;i<method.length;i++){
    		if(method[i].getName().equals(sActionKeyword)){
    			method[i].invoke(actionKeywords.toString(),OR,sPageObject.toString(),sPageObjectType.toString(),sData.toString(),iTestStep);
    			if(bResult==true){
    				ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
    				break;
    			}else{
    				ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
    				//ActionKeywords.quitBrowser(OR,"","","",0);
    				break;
    			}
    		}
    	}
    }
    
    //method to take Screenshot
    public static String takeScreenShot(String fileName) throws IOException{   
    	Date now = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh mm ss");
    	String time = dateFormat.format(now);
    	String resDir = System.getProperty("user.dir")+"\\Extent Reports\\screenShots\\"+time;
    	File resFolder = new File(resDir);
    	resFolder.mkdir();
    	String Screenshot=resDir+"\\"+fileName+".png";
    	File scrFile = ((TakesScreenshot)ActionKeywords.driver).getScreenshotAs(OutputType.FILE);
    	FileUtils.copyFile(scrFile, new File(Screenshot));
		return Screenshot;
    }

//	Automation Scripting and execution
}
     
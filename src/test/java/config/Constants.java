package config;

import utility.ExcelUtils;


	/* ******************
	Created Constants.java and Modified by Guru on 19/02/2018
	* It is used to define all constant variables.
	* *******************/

public class Constants {
	
	//System Variables
	public static String File_TestData ="HosApp_Registration.xlsx"; //Mandatory ***************************** 

	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";
	public static final String KEYWORD_NO_RUN = "RUN MODE SET TO NO";
	public static final String KEYWORD_NO_DATA = "TEST STEPS NOT FOUND";
	public static final String KEYWORD_RUN_NOT_SET = "RUN MODE IS NOT SET.PLEASE SET TO YES OR NO";
	public static String sURL ;
	public static String sPATH_IE;
	public static String sPATH_Firefox;
	public static String sPATH_Chrome;
	public static String sPath_TestData;
	public static String sPath_OR;
	public static String File_TestOR;
	
	public static String Path_TestData=System.getProperty("user.dir")+"\\src\\test\\resources\\dataEngine\\"+File_TestData;
	public static String Path_OR=System.getProperty("user.dir")+"\\src\\test\\resources\\objectRepository\\";

	public void test_environment() throws Exception
	{
		int iTestcase=1;
		    sURL=ExcelUtils.getCellData(iTestcase, Constants.Col_URL, Constants.Sheet_TestEnvironment);
			sPATH_IE=ExcelUtils.getCellData(iTestcase, Constants.Col_Path_IE, Constants.Sheet_TestEnvironment); 
			sPATH_Firefox=ExcelUtils.getCellData(iTestcase, Constants.Col_Path_Firefox, Constants.Sheet_TestEnvironment); 
			sPATH_Chrome=ExcelUtils.getCellData(iTestcase, Constants.Col_Path_Chrome, Constants.Sheet_TestEnvironment); 
			File_TestOR=ExcelUtils.getCellData(iTestcase, Constants.Col_File_TestOR, Constants.Sheet_TestEnvironment);
			sPath_OR = Path_OR+File_TestOR;
		
	}

	
	/* ***Test Cases Sheet*** */
	public static final int Col_TestCaseID = 0;	
	public static final int Col_TestCaseDescription = 1;	
	public static final int Col_RunMode =2 ;
	public static final int Col_Result =3 ;
	

	/* ***Test Steps Sheet*** */
	public static final int Col_TestCaseId =0 ;
	public static final int Col_TestScenarioID =1 ;
	public static final int Col_TestDescription =2;
	public static final int Col_PageObject =4 ;
	public static final int Col_PageObjectType =5 ;
	public static final int Col_ActionKeyword =6 ;
	public static final int Col_DataSet =7 ;
	public static final int Col_TestStepResult =8 ;
	
	/* ***Test Environment Sheet*** */
	public static final int Col_URL = 0;	
	public static final int Col_Path_IE = 1;	
	public static final int Col_Path_Firefox =2 ;
	public static final int Col_Path_Chrome =3 ;
	public static final int Col_File_TestOR =4 ;
	
	// Data Engine Excel sheets	
	public static final String Sheet_TestSteps = "Test Steps";
	public static final String Sheet_TestCases = "Test Cases";
	public static final String Sheet_TestEnvironment = "Test Environment";
	


}

package action_Keywords;

import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import static org.testng.Assert.assertTrue;
import utility.ExcelUtils;
import utility.ExtentReportsUtils;
import utility.Log;

import com.relevantcodes.extentreports.LogStatus;

import config.Constants;
import executionEngine.DriverScript;

	/* ******************
	Created ActionKeywords.java and Modified by Guru on 19/02/2018
	* It includes all the functions.
	* *******************/

public class ActionKeywords extends ExtentReportsUtils{
	
	public static WebDriver driver;
	static String elementText;
	
	static int invalidImageCount = 0;
	
	
	
	/*
	 * This function is used to open Browser :IE / Firefox / Chrome.
	 */
	
	public static void openBrowser(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{		
		
		Log.info("Opening Browser");
		try{				
			if(data.equalsIgnoreCase("Mozilla")){
				System.out.println("launching firefox browser"); 
				System.setProperty("webdriver.gecko.driver", Constants.sPATH_Firefox);
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				Log.info("Mozilla browser started");
				test.log(LogStatus.PASS, "Mozilla Firefox Browser Launched");
				addScreenCapture();
				DriverScript.bResult=true;
			}
			else if(data.equalsIgnoreCase("IE")){
				System.out.println("launching IE browser"); 
				System.setProperty("webdriver.ie.driver", Constants.sPATH_IE);
				driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				Log.info("IE browser started");
				test.log(LogStatus.PASS, "Internet Explorer browser Launched");
				addScreenCapture();
				DriverScript.bResult=true;
			}
			else if(data.equalsIgnoreCase("Chrome")){
				System.out.println("launching Chrome browser"); 
				ChromeOptions options = new ChromeOptions();
				options.addArguments("use-fake-ui-for-media-stream=1");
				System.setProperty("webdriver.chrome.driver", Constants.sPATH_Chrome);
				driver = new ChromeDriver(options);
				/*String nodeURL="http://172.16.26.171:4444/wd/hub";
				DesiredCapabilities capability= DesiredCapabilities.chrome();
				driver =new RemoteWebDriver(new URL(nodeURL),capability);*/
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				Log.info("Chrome browser started");
				test.log(LogStatus.PASS, "Google Chrome Browser Launched");
				addScreenCapture();
				DriverScript.bResult=true;
			}
			int implicitWaitTime=(10);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		}
		catch (Exception ex){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.info("Not able to open the Browser --- " + ex.getMessage());
			System.out.println(ex.getCause());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to launch browser");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		}
	}
	
	/*
	 * This function is used to open Browser and execution in Grid on Node.
	 */
	
	public static void openBrowserGridExecution(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{		
		
		Log.info("Opening Browser");
		try{				
			if(data.equalsIgnoreCase("Mozilla")){
				System.out.println("launching firefox browser and Execution on Node"); 
				/*System.setProperty("webdriver.gecko.driver", Constants.sPATH_Firefox);
				driver = new FirefoxDriver();*/
				String nodeURL="http://172.16.26.171:4444/wd/hub";
				DesiredCapabilities capability= DesiredCapabilities.firefox();
				driver =new RemoteWebDriver(new URL(nodeURL),capability);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				Log.info("Mozilla browser started");
				test.log(LogStatus.PASS, "Mozilla Firefox Browser Launched");
				addScreenCapture();
				DriverScript.bResult=true;
			}
			else if(data.equalsIgnoreCase("IE")){
				System.out.println("launching IE browser and Execution on Node"); 
				/*System.setProperty("webdriver.ie.driver", Constants.sPATH_IE);
				driver = new InternetExplorerDriver();*/
				String nodeURL="http://172.16.26.171:4444/wd/hub";
				DesiredCapabilities capability= DesiredCapabilities.internetExplorer();
				driver =new RemoteWebDriver(new URL(nodeURL),capability);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				Log.info("IE browser started");
				test.log(LogStatus.PASS, "Internet Explorer browser Launched");
				addScreenCapture();
				DriverScript.bResult=true;
			}
			else if(data.equalsIgnoreCase("Chrome")){
				System.out.println("launching Chrome browser and Execution on Node"); 
				/*ChromeOptions options = new ChromeOptions();
				options.addArguments("use-fake-ui-for-media-stream=1");
				System.setProperty("webdriver.chrome.driver", Constants.sPATH_Chrome);*/
				//driver = new ChromeDriver(options);
				String nodeURL="http://172.16.26.171:4444/wd/hub";
				DesiredCapabilities capability= DesiredCapabilities.chrome();
				driver =new RemoteWebDriver(new URL(nodeURL),capability);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				Log.info("Chrome browser started");
				test.log(LogStatus.PASS, "Google Chrome Browser Launched");
				addScreenCapture();
				DriverScript.bResult=true;
			}
			int implicitWaitTime=(10);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		}
		catch (Exception ex){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.info("Not able to open the Browser since Node is not connected --- " + ex.getMessage());
			System.out.println(ex.getCause());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to launch browser on Node");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		}
	}
	
	/*
	 * This function is used to launch url. 
	 */
	
	public static void launchUrl(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Navigating to URL");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(Constants.sURL);
			test.log(LogStatus.PASS,"Navigated to URL : "+Constants.sURL);
			addScreenCapture();
			DriverScript.bResult=true;
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.info("Not able to launch url  --- " +Constants.sURL+ e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to launch url");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		}
	}
	/*
	 * This function is used to launch url with providing data set externally. 
	 */
	
	public static void launchUrlWithDataSet(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Navigating to URL");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(data);
			test.log(LogStatus.PASS,"Navigated to URL : "+data);
			addScreenCapture();
            DriverScript.bResult=true;
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.info("Not able to launch url  --- " +data+ e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to launch url");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		}
	}

	/*
	 * This function is used to refresh page with accepting alert  . 
	 */
	public static void refreshWithAcceptAlert(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Refresh page");
			driver.navigate().refresh();
			Alert simpleAlert = driver.switchTo().alert();
			Thread.sleep(3000); 
	        simpleAlert.accept();
			test.log(LogStatus.PASS,"Page refreshed and alert accepted");
			addScreenCapture();
            DriverScript.bResult=true;
			}
		        catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to refresh page --- " + e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to refresh page");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
	}
	

/* * This function is used to wait for Webelement to be selected on page . */

	public static void waitForElementToBeSelected(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try {
			Log.info("Waiting for Webelement to be selected on page" +object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeSelected(locator)); 
			test.log(LogStatus.PASS,"Element to be selected - Passed");
			addScreenCapture();
			DriverScript.bResult = true;
		}
		catch (Exception e) {
			String testCase = DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to select --- " + e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Waiting timeout");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		}
	}
	
	
	/*
	 * This function is used to wait for Webelement to be clickable on page . 
	 */
	public static void waitForElementToBeClickable(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try {
			Log.info("Waiting for Webelement to be clickable on page" +object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			test.log(LogStatus.PASS,"Element to be clickable - Passed");
			addScreenCapture();
			DriverScript.bResult = true;
		}
		catch (Exception e) {
			String testCase = DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to click --- " + e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Waiting timeout");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		}
	}
	/*
	 * This function is used to wait for n seconds while page loading. 
	 */

	public static void waitForPageLoad(Properties OR, String object, String objectType,	String data, int stepNumber) throws Exception {
		try {
			Log.info("Wait for n seconds while page load");
			int waitingTime = Integer.parseInt(data);
			driver.manage().timeouts().pageLoadTimeout(waitingTime,TimeUnit.SECONDS);
			test.log(LogStatus.PASS,"Wait for " +waitingTime+" seconds" );
			addScreenCapture();
			DriverScript.bResult=true;
		}
		catch (Exception e) {
			String testCase = DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to Wait --- " + e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Waiting timeout");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		}
	}
	
	/*
	 * This function is used to get current url. 
	 */
	
	public static void getCurrentUrl(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Getting current url");
			String currentUrl = driver.getCurrentUrl();
			System.out.println("Current url is " + currentUrl);
			ExcelUtils.setCellData(currentUrl, stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
			test.log(LogStatus.PASS,"Current url is " + currentUrl );
			addScreenCapture();
			DriverScript.bResult=true;
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to get current url --- " + e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to get current url");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		}
	}
	
	/*
	 * This function is used to refresh page. 
	 */
	public static void refresh(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Refresh page");
			driver.navigate().refresh();
			test.log(LogStatus.PASS,"Page refreshed");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to refresh page --- " + e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to refresh page");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to get the page title. 
	 */
	
	public static void getPageTitle(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Getting page title");
			String pageTitle = driver.getTitle();
			System.out.println("Page Title is " + pageTitle);
			ExcelUtils.setCellData(pageTitle.toString(), stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
			test.log(LogStatus.PASS,"Page Title is " + pageTitle);
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to get the page title  --- " + e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to get the page title");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to get the page source. 
	 */
	
	public static void getPageSource(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Getting page source of the page");
			String pageSource= driver.getPageSource();
			String msg ="Check log for page source";
			Log.info(pageSource);
			System.out.println(pageSource);
			ExcelUtils.setCellData(msg.toString(), stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
			test.log(LogStatus.PASS,"Check log for page source");
			addScreenCapture();	
			DriverScript.bResult=true;
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to get the page source  --- " + e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to get the page source");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to get the element text. 
	 */
	
	public static void getText(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Getting inner text of the element");
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			 elementText = element.getText();
			ExcelUtils.setCellData(elementText.toString(), stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
			test.log(LogStatus.PASS,"Element text is " +elementText);
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to get the element text  --- "+object + e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to get the element text");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to click on Web Element. 
	 */
	
	public static void click(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Clicking on Webelement "+ object);
			System.out.println(OR.getProperty(object));
			By locator;
			locator = locatorValue(OR,object,objectType);
			WebElement element = driver.findElement(locator);
			element.click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS,"Clicked on "+object+" webelement");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to click on--- " + object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to perform click on " +object);
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
         	}
		   }
	
	/*
	 * This function is used to input data in Web Element. 
	 */
	public static void input(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
	
			Log.info("Entering the text in " + object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			
			element.sendKeys(data);
		
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS,"Entered data into "+object+ " element");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to Enter data in " +object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to enter data into "+object);
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
			 
		 	}
		}
	
	/*
	 * This function is used to clear the element data. 
	 */
	
	public static void clear(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Clear field " + object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			element.clear();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS, "Field cleared" +object);
			addScreenCapture();	
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to perform clear on " +object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to perform clear on " +object);
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		 	}
		}
	
	/*
	 * This function is used to navigate to given url. 
	 */
	public static void navigateTo(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Navigating to mentioned url");
			driver.navigate().to(data);
			test.log(LogStatus.PASS, "Navigated to url "+ data);
			addScreenCapture();
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to navigate to --- " +data+ e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to navigate "+data);
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to navigate to back page. 
	 */
	
	public static void navigateBack(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Navigate back ");
			driver.navigate().back();
			test.log(LogStatus.PASS, "Navigated back");
			addScreenCapture();
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to navigate back  --- " + e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to navigate back");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to navigate to next page. 
	 */
	
	public static void navigateForward(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Navigate foward");
			driver.navigate().forward();
			test.log(LogStatus.PASS, "Navigated forward");
			addScreenCapture();
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to navigate forward  --- " + e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to navigate forward");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}

	/*
	 * This function is used to close the current browser. 
	 */

	public static void closeBrowser(Properties OR, String object,String objectType, String data, int stepNumber) throws IOException {
		try {
			Log.info("Closing the browser");
			driver.close();
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe"); 
			test.log(LogStatus.PASS, "Current browser closed");
			} 
		catch (Exception e) {
			String testCase = DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to Close the Browser --- " + e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to close current browser");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
			}
		}

	/*
	 * This function is used to quit all the opened browser. 
	 */
	public static void quitBrowser(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Closing all browser windows");
			driver.quit();
			test.log(LogStatus.PASS, "Closed all browser windows");
			DriverScript.bResult = true;
				
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to Close all Browser windows --- " + e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to close all browser window ");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to validate that element is displayed on page. 
	 */
	public static void isDisplayed(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		
		try{
			Log.info("Validating Webelement is displayed on page or not" +object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			boolean status = element.isDisplayed();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			if(status==true)
			{
				String testResult= "Element displayed";	
				ExcelUtils.setCellData(testResult,stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
				Log.info("is displayed status"+status);
				System.out.println("is displayed status "+status);
				test.log(LogStatus.PASS, "Webelement " +object +" is displayed on page");
				addScreenCapture();
				DriverScript.bResult = true;
			}
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Webelement is not displayed on page --- " + e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL, "Webelement "+object + " is not displayed on page"); 
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 String testResult1= "Element is not displayed";	
			 ExcelUtils.setCellData(testResult1,stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
			 DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to validate that element is not displayed on page. 
	 */
	public static void isNotDisplayed(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		
		try{
			Log.info("Validating Webelement is not displayed on page or not" +object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			boolean status = element.isDisplayed();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			if(status==false)
			{
				String testResult= "Element is not displayed";	
				ExcelUtils.setCellData(testResult,stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
				Log.info("is not displayed status"+status);
				System.out.println("is not displayed status "+status);
				test.log(LogStatus.PASS, "Webelement " +object +" is displayed on page");
				addScreenCapture();
				DriverScript.bResult = true;
				}
				
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Webelement is displayed on page --- " + e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL, "Webelement "+object + " is displayed on page"); 
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 String testResult1= "Element is displayed";	
			 ExcelUtils.setCellData(testResult1,stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
			 DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to validate that element is not enabled on page. 
	 */
	public static void isEnabled(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Validating Webelement is enabled on page or not" +object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			boolean status = element.isEnabled();
			if(status==true)
			{
				String testResult= "Element enabled";	
				ExcelUtils.setCellData(testResult,stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
				test.log(LogStatus.PASS, "Webelement "+object+" is enabled on page"); 
				addScreenCapture();
				DriverScript.bResult = true;
				}
			Log.info("is enabled status "+status);
			System.out.println("is enabled status "+status);
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Webelement is not enabled on page --- " + e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL, "Webelement "+object+" is not enabled on page"); 
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to validate that element is enabled on page . 
	 */
	public static void isNotEnabled(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Validating Webelement is not enabled on page" +object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			boolean status = element.isEnabled();
			if(status==false)
			{

				String testResult= "Element is not enabled";	
				ExcelUtils.setCellData(testResult,stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
 				test.log(LogStatus.PASS, "Webelement "+object+" is not enabled on page"); 
 				addScreenCapture();
 				DriverScript.bResult = true;
				}
			System.out.println("is not enabled status "+status);
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Webelement is enabled on page --- " + e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL, "Webelement "+object+" is enabled on page"); 
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to validate whether element is selected on page or not. 
	 */
	public static void isSelected(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Validating Webelement is selected on page or not" +object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			boolean status = element.isSelected();
			if(status==true)
			{
				String testResult= "Element selected";	
				ExcelUtils.setCellData(testResult,stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
				test.log(LogStatus.PASS, "Webelement "+object+" is selected on page");
				addScreenCapture();
				DriverScript.bResult = true;
				}
			Log.info("is selected status "+status);
			System.out.println("is selected status "+status);
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Webelement is not selected on page --- " + e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL, "Webelement "+object+" is not selected on page");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
         	}
		}

	/*
	 * This function is used to validate that element is not selected on page. 
	 */
	public static void isNotSelected(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Validating Webelement is selected on page or not" +object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			boolean status = element.isSelected();
			if(status==false)
			{
				String testResult= "Element is not selected";	
				ExcelUtils.setCellData(testResult,stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
				test.log(LogStatus.PASS, "Webelement "+object+" is selected on page");
				addScreenCapture();
				}
			
			System.out.println("is not selected status "+status);

			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Webelement is selected on page --- " + e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL, "Webelement "+object+" is selected on page");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function works well/better than the click()on Web Element. 
	 */
	
	public static void submit(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Submitting form on Webelement "+ object);
			System.out.println(OR.getProperty(object));
			By locator;
			locator = locatorValue(OR,object,objectType);
			WebElement element = driver.findElement(locator);
			element.submit();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS, "Submitted form on Webelement "+object);
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to perform submit on--- "+ object + e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL, "Not able to perform submit  on "+object);
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
 			 DriverScript.bResult = false;
         	}
		   }
	
	/*
	 * This function is used to get the element tag name. 
	 */
	
	public static void getTagName(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Getting tag name of the element");
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			String elementTagName = element.getTagName();
			ExcelUtils.setCellData(elementTagName.toString(), stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
			test.log(LogStatus.PASS, "Tag name of element is " + elementTagName);
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to get the element text  --- "+object + e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to get the element text");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function gets the all options belonging to the Select tag. It takes no parameter and returns List<WebElements>. 
	 */
	
	public static void getSelectOptionsCount(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Gets all options of Select tag");
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			Select oSelect = new Select(element);
			List <WebElement> elementCount = oSelect.getOptions();
			System.out.println(elementCount.size());
			int count = elementCount.size();
			ExcelUtils.setIntCellData(count, stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
			test.log(LogStatus.PASS, "Select tag's options count is  " + count);
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to get all options of Select tag  --- "+object + e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to get all options of Select tag");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to select an option given under any dropdowns and multiple selection boxes with selectByVisibleText method. 
	 */
	public static void selectByVisibleText(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Select an option given under any dropdowns and multiple selection boxes with selectByVisibleText method " + object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			Select oSelect = new Select(element);
			oSelect.selectByVisibleText(data);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS, "Selected an option by visible text");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to select data by visible text in " +object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to select data by visible text");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
			 
		 	}
		}
	
	/*
	 * This function is used to select an option given under any dropdown and multiple selection boxes with the index number of the option.
	 */
	public static void selectByIndex(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Select an option given under any dropdowns and multiple selection boxes with the index number of the option " + object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			Select oSelect = new Select(element);
			int indexNumber = Integer.parseInt(data);
			oSelect.selectByIndex(indexNumber);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS, "Selected an option by index");
			addScreenCapture();
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to select data by index in " +object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to select data by index ");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
		 	}
		}
	
	/*
	 * This function is used to select an option given under any dropdown and multiple selection boxes with the value of the option.
	 */
	public static void selectByValue(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Select an option given under any dropdowns and multiple selection boxes with the value of the option " + object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			Select oSelect = new Select(element);
			oSelect.selectByValue(data);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS, "Selected an option by value");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to select data by value in " +object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to select data by value");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
		 	}
		}
	
	/*
	 * This function prints all options belonging to the Select tag. 
	 */
	
	public static void printSelectOptions(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Prints all options of Select tag on Console and log");
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			Select oSelect = new Select(element);
			List <WebElement> elementCount = oSelect.getOptions();
			System.out.println(elementCount.size());
			int count=elementCount.size();
			for(int i =0; i<count ; i++){
				String sValue = elementCount.get(i).getText();
				Log.info(sValue);
				System.out.println(sValue);
				}
			String msg ="Check log for all options of Select tag";
			ExcelUtils.setCellData(msg.toString(), stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
			test.log(LogStatus.PASS, "Check log for all options of Select tag");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to print all options of Select tag  --- "+object + e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to print all optons of Select tag");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to validate whether the SELECT element support multiple selecting options at the same time or not. 
	 */
	public static void isMultiple(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Validating whether the select webelement support multile selecting options on page or not" +object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			Select oSelect = new Select(element);
			boolean status = oSelect.isMultiple();
			if(status==true)
			{
				String testResult= "Select element supports multiple selecting options";	
				ExcelUtils.setCellData(testResult,stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
				test.log(LogStatus.PASS, "Select element supports multile selecting options on page");
				addScreenCapture();
				DriverScript.bResult=true;
			}
		
			Log.info("is Multiple status "+status);
			System.out.println("is Multiple status "+status);
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 String testResult= "Select element does not supports multiple selecting options";	
			 ExcelUtils.setCellData(testResult,stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Select element does not supports multile selecting options on page");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to validate whether the SELECT element does not support multiple selecting options at the same time. 
	 */
	public static void isNotMultiple(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Validating whether the select webelement does not support multile selecting options on page " +object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			Select oSelect = new Select(element);
			boolean status = oSelect.isMultiple();
			if(status==false)
			{
				String testResult= "Select element does not supports multiple selecting options";	
				ExcelUtils.setCellData(testResult,stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
				test.log(LogStatus.PASS, "Select element does not supports multile selecting options on page");
				addScreenCapture();
				DriverScript.bResult=true;
				}
				Log.info("is Not Multiple status "+status);
			System.out.println("is Not Multiple status "+status);
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 String testResult= "Select element supports multiple selecting options";	
			 ExcelUtils.setCellData(testResult,stepNumber,Constants.Col_DataSet, Constants.Sheet_TestSteps);
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Select element supports multile selecting options on page");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to deselect an option given under any dropdowns and multiple selection boxes with selectByVisibleText method. 
	 */
	public static void deselectByVisibleText(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Deselect an option given under any dropdowns and multiple selection boxes with deselectByVisibleText method " + object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			Select oSelect = new Select(element);
			oSelect.deselectByVisibleText(data);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS, "Deselected an option by visible text");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to deselect data by visible text in " +object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to deselect data by visible text");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
		 	}
		}
	
	/*
	 * This function is used to deselect an option given under any dropdown and multiple selection boxes with the index number of the option.
	 */
	public static void deselectByIndex(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Deselect an option given under any dropdowns and multiple selection boxes with the index number of the option " + object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			Select oSelect = new Select(element);
			int indexNumber = Integer.parseInt(data);
			oSelect.deselectByIndex(indexNumber);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS, "Deselected an option by index");
			addScreenCapture();
			DriverScript.bResult=true;
		 	}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to deselect data by index in " +object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to deselect data by index ");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
		 	}
		}
	
	/*
	 * This function is used to deselect an option given under any dropdown and multiple selection boxes with the value of the option.
	 */
	public static void deselectByValue(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Deselects an option given under any dropdowns and multiple selection boxes with the value of the option " + object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			Select oSelect = new Select(element);
			oSelect.deselectByValue(data);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS, "Deselected an option by value");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to deselect data by value in " +object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to deselect data by value");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
		 	}
		}
	
	/*
	 * This function is used to deselect all selected options given under any dropdown and multiple selection boxes.
	 */
	public static void deselectAll(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Deselect all selected options given under any dropdown and multiple selection boxes " + object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			Select oSelect = new Select(element);
			List<WebElement> oSize = oSelect.getOptions();
			int iListSize = oSize.size();
			// Setting up the loop to print all the options
			for(int i =0; i < iListSize ; i++){
				// Storing the value of the option	
				String sValue = oSelect.getOptions().get(i).getText();
				// Printing the stored value
				System.out.println(sValue);
				// Selecting all the elements one by one
				oSelect.selectByIndex(i);
				Thread.sleep(2000);
				}
			oSelect.deselectAll();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS, "Deselected all selected option ");
			addScreenCapture();
			DriverScript.bResult=true;
		 	}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to deselect all options  " +object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to deselect all options");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
		 	}
		}
	
	/*
	 * This function is used to switch window. 
	 */
	public static void switchToNewWindow(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Switch to new window");
			String handle= driver.getWindowHandle();
			System.out.println(handle);
	        driver.switchTo().window(handle);
	        Thread.sleep(5);
	        String title = driver.getTitle();
	        System.out.println("Title " +title );
	        addScreenCapture();
	        DriverScript.bResult=true;
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to deselect all options  " +object +  e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to deselect all options");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	

	/*
	 * 	 * This function is used to upload file. 
	 */
	public static void uploadFile(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Uploading a file");
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			element.clear();
			element.sendKeys(data);
			test.log(LogStatus.PASS, "File uploaded successfuly ");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to upload file --- " +data+ e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to upload file");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to validate actual string extracted from element and compare with passed parameter expected string. 
	 */
	public static void 	assertStringValidation(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("Verification points");
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			String actual = element.getText();
			String expected = data;
			if (actual.equals(expected))
			{
			Assert.assertEquals(actual, expected);
			assertTrue(actual.matches(expected));
			Log.info("Verification points successful");
			System.out.println("Verification points successful");
			test.log(LogStatus.PASS, "Verification points successful");
			addScreenCapture();
			DriverScript.bResult = true;
			}
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Verification points failed " +data);
			System.out.println("Verification points failed");
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL, "Verification points failed");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	
	/*
	 * This function is used to force click on element.
	 */
	public static void forceClick(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("It is used to force click on element");
			System.out.println(OR.getProperty(object));
			By locator;
			locator = locatorValue(OR,object,objectType);
			WebElement element = driver.findElement(locator);
			element.sendKeys(Keys.CONTROL);
			element.sendKeys(Keys.ENTER);
			test.log(LogStatus.PASS, "Forced clicked on "+object+" element");
			addScreenCapture();
			DriverScript.bResult=true;
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Force clicked failed on " +object+ e.getMessage());
			System.out.println("Force clicked failed on " +object);
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL, "Force clicked failed on "+object);
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to scroll the web page.
	 */
	public static void scrollPageDown(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("It is used to scroll the webpage");
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,1000)", "");
			Thread.sleep(5000);
			test.log(LogStatus.PASS, "Scrolled the webpage down");
			addScreenCapture();
			DriverScript.bResult=true;
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Scroll bar failed " + e.getMessage());
			System.out.println("Scroll bar failed" +object);
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Failed to scroll page down - Error Snapshot : "+ test.addScreenCapture(screenShotPath));
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	
	/*
	 * This function is used to move to element and perform action click on it.
	 */
	public static void moveToElementAndClick(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("It is used to action click on element");
			System.out.println(OR.getProperty(object));
			By locator;
			locator = locatorValue(OR,object,objectType);
			WebElement element = driver.findElement(locator);
			Actions action1 = new Actions(driver);
			action1.moveToElement(element).click().build().perform();
			test.log(LogStatus.PASS, "Mouse hover and click on "+object+" element done");
			addScreenCapture();
			DriverScript.bResult=true;
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Action clicked failed on " +object+ e.getMessage());
			System.out.println("Action clicked failed on " +object);
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Mouse hover and click on element "+object);
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	

		/*
	 * This function is used to wait/sleep.
	 */
	public static void threadSleep(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			//int waitingTime = Integer.parseInt();
			Log.info("It is used to wait/sleep");
			Thread.sleep(2000);
			test.log(LogStatus.PASS, "Wait for 2 seconds");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Timeout wait " + e.getMessage());
			System.out.println("Timeout wait" +object);
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Timeout thread wait");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to implicitly wait for 10 seconds.
	 */
	public static void implicitWait(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
	try
	{
		
		Log.info("It is used to implicitly wait");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "Implicit Wait for 10 seconds");
		addScreenCapture();
		DriverScript.bResult=true;
	}
	catch(Exception e){
		String testCase= DriverScript.sTestCaseID;
		DriverScript.takeScreenShot(testCase);
		Log.error("Timeout wait " + e.getMessage());
		System.out.println("Timeout wait" +object);
		String screenShotPath = takeScreenShot("extentReportImage");
		test.log(LogStatus.FAIL,"Timeout implicit wait");
		test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
		DriverScript.bResult = false;
     	}
	}
	
	/*
	 * This function is click OK on simple alert. 
	 */
	public static void acceptSimpleAlert(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Click OK on simple alert");
			Alert simpleAlert = driver.switchTo().alert();
			String alertText = simpleAlert.getText();
			System.out.println("Alert text is " + alertText);
			Log.info("Alert text is " + alertText);
			simpleAlert.accept();
			test.log(LogStatus.PASS, "Clicked on alert");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("No alert is present --- " + e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL," Not alert is present");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
        	}
		}
	 
	/*
	 * This function is used to move horizontal slider bar.
	 */
	public static void moveHorizontalSlider(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("It is used to move horizontal slider");
			System.out.println(OR.getProperty(object));
			By locator;
			locator = locatorValue(OR,object,objectType);
			WebElement slider = driver.findElement(locator);
		    Actions move = new Actions(driver);
		    Action action = (Action) move.dragAndDropBy(slider, 60, 0).build();
		    action.perform();
			test.log(LogStatus.PASS, "Slider movement success on  "+object+" element done");
			addScreenCapture();
			DriverScript.bResult=true;
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Slider movement failed on " +object+ e.getMessage());
			System.out.println("Slider movement failed on " +object);
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Slider movement failed on "+object);
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		}
		}
	/*
	 * This function is used to pick the date from open calender.
	 */
	public static void datePicker(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("It is used to pick the date from open calender");
			By locator;
			locator = locatorValue(OR,object,objectType);
			WebElement element = driver.findElement(locator);
		//	List<WebElement> rows=element.findElements(By.tagName("tr"));
            List<WebElement> columns=element.findElements(By.tagName("td"));
            String dateProvided = data;
            for(WebElement cell : columns)
            {
                   if(cell.getText().equals(dateProvided))
                   {
                         cell.findElement(By.linkText(dateProvided)).click();
                         break;
                   }
            }
            test.log(LogStatus.PASS,"Date Picker clicked "+object+"");
			addScreenCapture();
			DriverScript.bResult=true;
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Date pick from calender failed " + e.getMessage());
			System.out.println("Date pick from calender failed" +object);
			DriverScript.bResult = false;
         	}
		}

	/*
	 * This function is used to verify for element present on page.
	 */
	public static void verifyElementPresent(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("It is used to verify that element is present");
			System.out.println(OR.getProperty(object));
			By locator;
			locator = locatorValue(OR,object,objectType);
			isElementPresent(locator);
			test.log(LogStatus.PASS, "Verify for element "+object+" present");
			addScreenCapture();
			DriverScript.bResult=true;
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Webelement " +object+ "is not present" +e.getMessage());
			System.out.println("Verify element present failed on " +object);
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Verify for element present failed on "+object);
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
		}
		}
	
	/*
	 * This function is used to wait for element present on page.
	 */
	public static void waitForElementPresent(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("It is used to wait untill element present");
			System.out.println(OR.getProperty(object));
			By locator;
			locator = locatorValue(OR,object,objectType);
			for (int second = 0;; second++) {
		    	if (second >= 60) fail("timeout");
		    	try { if (isElementPresent(locator)) break;
		    	} catch (Exception e) {}
		    	Thread.sleep(1000);
		    }
			test.log(LogStatus.PASS, "Waiting for element "+object+" present");
			addScreenCapture();
			DriverScript.bResult=true;
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Webelement " +object+ "is not present" +e.getMessage());
			System.out.println("Wait for element present failed on " +object);
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Wait for element present failed on "+object);
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		}
		}
	
	
	/*
	 * This function is used to scroll into particular view of the web page.
	 */
	public static void scrollIntoView(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("It is used to scroll into view");
			By locator;
			locator = locatorValue(OR,object,objectType);
			WebElement element = driver.findElement(locator);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(2000);
			test.log(LogStatus.PASS, "scroll into view "+object);
			addScreenCapture();
			
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Scroll up failed " + e.getMessage());
			System.out.println("Scroll into view failed" +object);
			
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"scroll ino view failed on "+object);
			test.log(LogStatus.ERROR,"scroll ino view failed on "+object+" failed - Error Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
			
			
         	}
		}
	
	/*
	 * This function is used to select a radio button.
	 */
	public static void radioButtonSelect(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("It is used to select particular radio button");
			By locator;
			locator = locatorValue(OR,object,objectType);
			WebElement element = driver.findElement(locator);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("arguments[0].click()", element);
			Thread.sleep(2000);
			test.log(LogStatus.PASS, "select radio button "+object);
			addScreenCapture();
			
		}
		
		
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Select radio button failed " + e.getMessage());
			System.out.println("Select radio button failed" +object);
			
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Select radio button failed on "+object);
			test.log(LogStatus.ERROR,"Select radio button failed on "+object+" failed - Error Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
			
			
         	}
		}
	
	/*
	 * This function is used to play Video 
	 */

	public static void playVideo(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("It is used to play video "+ object);
			System.out.println(OR.getProperty(object));
			String Video_value = OR.getProperty(object);
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			System.out.println("document.getElementById('"+Video_value+"').play()");
			js .executeScript("document.getElementById('"+Video_value+"').play()");
			Thread.sleep(5000);
			test.log(LogStatus.PASS,"Video Played on "+object+"");
			addScreenCapture();
			DriverScript.bResult=true;
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not playing video--- "+ object + e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to play video "+object);
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		}
	}

	
	/*
	 * This function is used to pause Video 
	 */

	public static void pauseVideo(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("It is used to pause video"+ object);
			System.out.println(OR.getProperty(object));
			String Video_value = OR.getProperty(object);
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			System.out.println("document.getElementById('"+Video_value+"').pause()");
			js .executeScript("document.getElementById('"+Video_value+"').pause()");
			Thread.sleep(5000);
			test.log(LogStatus.PASS,"Video Paused on "+object+"");
			addScreenCapture();
			DriverScript.bResult=true;
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not pause video--- "+ object + e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to pause video "+object);
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		}
	}
	
	/*
	 * This function is used to load Video 
	 */

	public static void loadVideo(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("It is used to load video"+ object);
			System.out.println(OR.getProperty(object));
			String Video_value = OR.getProperty(object);
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			System.out.println("document.getElementById('"+Video_value+"').load()");
			js .executeScript("document.getElementById('"+Video_value+"').load()");
			Thread.sleep(5000);
			test.log(LogStatus.PASS,"Video load on "+object+"");
			addScreenCapture();
			DriverScript.bResult=true;
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not load video--- "+ object + e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to load video "+object);
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		}
	}	
		/*	
		 * 	To take screen shot
		 */
	public static String takeScreenShot(String fileName) throws IOException{
	    
    	Date now = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh mm ss");
    	String time = dateFormat.format(now);
    	String resDir = System.getProperty("user.dir")+"\\Extent Reports\\results\\"+time;
    	File resFolder = new File(resDir);
    	resFolder.mkdir();
    	String screenShotPath = resDir+"\\"+fileName+".png";
    	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    	// Now you can do whatever you need to do with it, for example copy somewhere
    	FileUtils.copyFile(scrFile, new File(screenShotPath));
		return screenShotPath;
}
	/*
	 * This function is used return locatorValue to findElement method.
	 */
	
	public static By locatorValue(Properties OR,String object,String objectType) {
		/*String locatorValue;
		By by;
		switch (objectType) {
		case "id":
			locatorValue = OR.getProperty(object);
			by = By.id(locatorValue.toString());
			System.out.println(By.id(locatorValue.toString()));
			//by = By.id(object);
			break;
		case "name":
			locatorValue = OR.getProperty(object);
			by = By.name(locatorValue.toString());
			System.out.println(By.name(locatorValue.toString()));
			//by = By.name(object);
			break;
		case "xpath":
			locatorValue = OR.getProperty(object);
			by = By.xpath(locatorValue.toString());
			System.out.println(By.xpath(locatorValue.toString()));
			//by = By.xpath(object);
			break;
		case "cssSelector":
			locatorValue = OR.getProperty(object);
			by = By.cssSelector(locatorValue.toString());
			System.out.println(By.cssSelector(locatorValue.toString()));
			//by = By.cssSelector(object);
			break;
		case "linkText":
			locatorValue = OR.getProperty(object);
			by = By.linkText(locatorValue.toString());
			System.out.println(By.linkText(locatorValue.toString()));
			break;
		case "partialLinkText":
			by = By.partialLinkText(object);
			break;
		case "className":
			locatorValue = OR.getProperty(object);
			by = By.className(locatorValue.toString());
			System.out.println(By.className(locatorValue.toString()));
			break;
		case "tagName":
			locatorValue = OR.getProperty(object);
			by = By.tagName(locatorValue.toString());
			System.out.println(By.tagName(locatorValue.toString()));
			break;
		default:
			by = null;
			break;
		}
		return by;*/
		String locatorValue;
		By by = null;
		if (objectType.equalsIgnoreCase("id")) {
		/*case "id":*/
			locatorValue = OR.getProperty(object);
			by = By.id(locatorValue.toString());
			System.out.println(By.id(locatorValue.toString()));
			//by = By.id(object);
			//break;
		}
		/*case "name":*/
		else if (objectType.equalsIgnoreCase("name")) {
			locatorValue = OR.getProperty(object);
			by = By.name(locatorValue.toString());
			System.out.println(By.name(locatorValue.toString()));
			//by = By.name(object);
			//break;
		}
			else if (objectType.equalsIgnoreCase("xpath"))
			{
			locatorValue = OR.getProperty(object);
			by = By.xpath(locatorValue.toString());
			System.out.println(By.xpath(locatorValue.toString()));
			//by = By.xpath(object);
			//break;
			}
		/*case "cssSelector":*/
			else if (objectType.equalsIgnoreCase("cssSelector"))
			{
			locatorValue = OR.getProperty(object);
			by = By.cssSelector(locatorValue.toString());
			System.out.println(By.cssSelector(locatorValue.toString()));
			//by = By.cssSelector(object);
			//break;
		}
		/*case "linkText":*/
			else if (objectType.equalsIgnoreCase("linkText"))
			{
			locatorValue = OR.getProperty(object);
			by = By.linkText(locatorValue.toString());
			System.out.println(By.linkText(locatorValue.toString()));
			//break;
			}
		/*case "partialLinkText":*/
			else if (objectType.equalsIgnoreCase("partialLinkText")){
				locatorValue = OR.getProperty(object);
			by = By.partialLinkText(object);
			//break;
			}
	/*	case "className":*/
			else if (objectType.equalsIgnoreCase("className")){
			locatorValue = OR.getProperty(object);
			by = By.className(locatorValue.toString());
			System.out.println(By.className(locatorValue.toString()));
			//break;
			}
		/*case "tagName":*/
			else if (objectType.equalsIgnoreCase("tagName")){
			locatorValue = OR.getProperty(object);
			by = By.tagName(locatorValue.toString());
			System.out.println(By.tagName(locatorValue.toString()));
			//break;
			}
	/*	default:*/
			else if (objectType.equalsIgnoreCase("")){
			by = null;
			
		//	break;
		}
		return by;
	}
	public static void addScreenCapture() throws IOException{
	screenShotPath1 = DriverScript.takeScreenShot("ScreenShot");
	test.log(LogStatus.INFO,"Screen Shot :"+ test.addScreenCapture(screenShotPath1));
	}
	private static boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    } 
}
	
	/*
	 * This function is used to move to element and perform action on it.
	 */
	public static void moveToElement(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("It is used to action move to on element");
			System.out.println(OR.getProperty(object));
			By locator;
			locator = locatorValue(OR,object,objectType);
			WebElement element = driver.findElement(locator);
			Actions action1 = new Actions(driver);
			action1.moveToElement(element).build().perform();
			test.log(LogStatus.PASS, "Mouse hover on "+object+" element done");
			addScreenCapture();
			DriverScript.bResult=true;
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Action clicked failed on " +object+ e.getMessage());
			System.out.println("Action clicked failed on " +object);
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Mouse hover and click on element "+object);
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}

	

	/*
	 * This function is used to Press Enter in Web Element. 
	 */
	public static void pressEnter(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Press enter button on " + object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			element.sendKeys(Keys.ENTER);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS,"Press enter button on  "+object+ " element");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		    catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to Press Enter in " +object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to pres enter  into "+object);
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
			 
		 	}
		}
	
	
	
	public static void drop1(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Clicking on Webelement "+ object);
			System.out.println(OR.getProperty(object));
			By locator;
			locator = locatorValue(OR,object,objectType);
			WebElement element = driver.findElement(locator);
			element.click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			//element.sendKeys(Keys.ARROW_DOWN);
			element.sendKeys(Keys.ENTER);
			test.log(LogStatus.PASS,"Clicked on "+object+" webelement");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to click on--- " + object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to perform click on " +object);
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
         	}
		   }
	
	public static void drop2(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Clicking on Webelement "+ object);
			System.out.println(OR.getProperty(object));
			By locator;
			locator = locatorValue(OR,object,objectType);
			WebElement element = driver.findElement(locator);
			element.click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			element.sendKeys(Keys.ARROW_DOWN);
			//element.sendKeys(Keys.ARROW_DOWN);
			element.sendKeys(Keys.ENTER);
			test.log(LogStatus.PASS,"Clicked on "+object+" webelement");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to click on--- " + object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to perform click on " +object);
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
         	}
		   }
	
	
	public static void openNewtab(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Open new tab");
		/*	By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			element.sendKeys(Keys.ENTER);*/
			
			 ((JavascriptExecutor)driver).executeScript("window.open()");
			    ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			    driver.switchTo().window(tabs.get(1));
			    
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.get("https://www.mailinator.com/");
			//test.log(LogStatus.PASS,"Press enter button on  "+object+ " element");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		    catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to open new tab "+  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to open new tab ");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
			 
		 	}
		}
	public static void switchToBackFirsttab(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Switch back to first tab ");
		/*	By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			element.sendKeys(Keys.ENTER);*/
			
			 //((JavascriptExecutor)driver).executeScript("window.open()");
			    ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			    driver.switchTo().window(tabs.get(0));
			    
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			//test.log(LogStatus.PASS,"Press enter button on  "+object+ " element");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		    catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to Switch back to first tab "+  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Switch back to first tab ");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
			 
		 	}
		}
	
	/*
	 * This function prints all options belonging to Switch to Second tab. 
	 */
	public static void switchToSecondTab(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Switch back to first tab ");
		/*	By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			element.sendKeys(Keys.ENTER);*/
			
			 //((JavascriptExecutor)driver).executeScript("window.open()");
			    ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			    driver.switchTo().window(tabs.get(1));
			    
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			//test.log(LogStatus.PASS,"Press enter button on  "+object+ " element");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		    catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to Switch back to first tab "+  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Switch back to first tab ");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
			 
		 	}
		}
	
	public static void emailInput(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
	
			Log.info("Entering the text in " + object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			
			element.sendKeys(elementText);
		
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS,"Entered data into "+object+ " element");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to Enter data in " +object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to enter data into "+object);
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
			 
		 	}
		}
	
	/*
	 * This function prints all options belonging to the Enter frame. 
	 */
	public static void frame(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Switch to Frame");
			driver.switchTo().frame(data);
			test.log(LogStatus.PASS, "Switch to Frame "+ data);
			addScreenCapture();
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to Switch to Frame --- " +data+ e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Switch to Frame "+data);
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	
	/*
	 * This function is used to switch window. 
	 */
	public static void switchToWindow(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Switch to new window");
			String parentHandle = driver.getWindowHandle();
			String currentHandle ="";
		    Set<String> win  = driver.getWindowHandles();   

		    Iterator<String> it =  win.iterator();
		    if(win.size() > 1){
		        while(it.hasNext()){
		            String handle = it.next();
		            if (!handle.equalsIgnoreCase(parentHandle)){
		                driver.switchTo().window(handle);
		                currentHandle = handle;
		            }
		        }
		    }
		    else{
		        System.out.println("Unable to switch");
		    }
		   
	        String title = driver.getTitle();
	        System.out.println("Title " +title );
	        addScreenCapture();
	        DriverScript.bResult=true;
			}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to deselect all options  " +object +  e.getMessage());
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL,"Not able to deselect all options");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function prints all options belonging to Switch to Third tab. 
	 */
	public static void switchToThirdTab(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Switch to Third tab ");
	
			 //((JavascriptExecutor)driver).executeScript("window.open()");
			    ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			    driver.switchTo().window(tabs.get(2));
			    
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			//test.log(LogStatus.PASS,"Press enter button on  "+object+ " element");
			addScreenCapture();
			DriverScript.bResult=true;
			}
		    catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to Switch to Third tab "+  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Switch to Third tab ");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
			 
		 	}
		}
           
	/*
	 * This function prints all options belonging to Switch to Third tab. 
	 */
	public static void validateInvalidImages(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Validate invalid Images ");
	
			List<WebElement> imagesList = driver.findElements(By.tagName("img"));
			System.out.println("Total no. of images are " + imagesList.size());
			for (WebElement imgElement : imagesList) {
				if (imgElement != null) {
					verifyimageActive(imgElement);
				}
			}
			Log.info("Total no. of invalid images are "	+ invalidImageCount); 
			    
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
			addScreenCapture();
			DriverScript.bResult=true;
			}
		    catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Invalid images "+  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"invalid images ");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
			 
		 	}
		}
	
	public static void verifyimageActive(WebElement imgElement) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(imgElement.getAttribute("src"));
			HttpResponse response = client.execute(request);
			// verifying response code he HttpStatus should be 200 if not,
			// increment as invalid images count
			if (response.getStatusLine().getStatusCode() != 200)
				invalidImageCount++;
		} catch (Exception e) {
			e.printStackTrace();
		}
}
	/*
	 * This function prints all options belonging to Select text on edit box. 
	 */
	public static void selectText(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Select entire text " + object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			element.sendKeys(data);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS, "Selected entire text" +object);
			addScreenCapture();	
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to Select entire text " +object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to Select entire text " +object);
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		 	}
		}
	

	/*
	 * This function prints all options belonging to Navigate to paginations. 
	 */
	public static void checkPagination(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Check paginations ");
	
			List<WebElement> pagination =driver.findElements(By.xpath("//li[@class='page-item']/a")); 
			// check if pagination link exists 

					if(pagination .size()>1){ 
				System.out.println("pagination exists"); 
			// click on pagination link 
			for(int i=1; i<pagination .size(); i++){ 
				
			if(pagination.get(i).isDisplayed())
			{
				pagination.get(i).click();
			}
			} 
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//li[@class='page-item']/a")).click();
			} else { 
				System.out.println("pagination not exists"); 
			} 
			Log.info("Total no. of Paginations "	+ pagination.size()); 
			    
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
			addScreenCapture();
			DriverScript.bResult=true;
			}
		    catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to get  paginations "+  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"invalid images ");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
			 
		 	}
		}
	/*
	 * This function prints all options belonging to Bread crumb. 
	 */
	public static void checkBreadcrumbs(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try{
			Log.info("Verify breadcrum " + object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			String breadcrumbText=element.getText();
			System.out.println(breadcrumbText);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			String lastbreadcrumb=driver.findElement(By.xpath("//span[@class='program-name']/h3")).getText();
			System.out.println(lastbreadcrumb);
			if(breadcrumbText.equalsIgnoreCase("Home Products "+lastbreadcrumb))
			{
				Log.info("Breadcrurm verifyed succssfully");
			}
			    
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
			addScreenCapture();
			DriverScript.bResult=true;
			}
		    catch(Exception e){
			 String testCase= DriverScript.sTestCaseID;
			 DriverScript.takeScreenShot(testCase);
			 Log.error("Not able to check breadcrumb "+  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"invalid images ");
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			 DriverScript.bResult = false;
			 
		 	}
		}
	
	
	/*
	 * This function prints all options belonging to Select text on edit box. 
	 */
	public static void changeZipcode(Properties OR,String object,String objectType,String data,int stepNumber) throws IOException{
		try{
			Log.info("Change zipcode " + object);
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			element.sendKeys(data);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			test.log(LogStatus.PASS, "Change zipcode" +object);
			addScreenCapture();	
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Not able to Change zipcode " +object +  e.getMessage());
			 String screenShotPath = takeScreenShot("extentReportImage");
			 test.log(LogStatus.FAIL,"Not able to Change zipcode " +object);
			 test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
		 	}
		}
	
	/*
	 * This function is used to validate actual string extracted from element and compare with passed parameter expected string. 
	 */
	public static void stringValidation(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("Verification points");
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			String actual = element.getText();
			String expected = data;
			if (actual.contains(expected))
			{
			//Assert.assertEquals(actual, expected);
			Log.info("Verification points successful");
			System.out.println("Verification points successful");
			test.log(LogStatus.PASS, "Verification points successful");
			addScreenCapture();
			DriverScript.bResult = true;
			}
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Verification points failed " +data);
			System.out.println("Verification points failed");
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL, "Verification points failed");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	/*
	 * This function is used to Count total of items in the web table. 
	 */
	public static void countItemsInTable(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("Count total of items in web table");
			/*By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);*/
			List<WebElement> list=driver.findElements(By.xpath("//div[@class='card-body-box product-list']/div"));
			System.out.println("Count total of items in web table"+list.size());
			
			Log.info("Count total of items in web table"+list.size());
			test.log(LogStatus.PASS, "Counted total of items in web table");
			addScreenCapture();
			DriverScript.bResult = true;
			
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Counting total of items in web table is failed " +data);
			
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL, "Counting total of items in web table is failed");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to Verify the Checkout button is present or not. 
	 */
	public static void verifyCheckoutBtnIsPresent(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("Verify the Checkout button is present or not");
			/*By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);*/
			List<WebElement> list=driver.findElements(By.xpath("//div[@class='mt-2 set-middle']/button[2]"));
			int button=list.size();
			if(button > 0)
			{
				System.out.println("Checkout button is Present");
				Log.info("Checkout button is Present");
			}else
			{
				System.out.println("Checkout button is Absent");
				Log.info("Checkout button is Absent");
			}
			
			
			test.log(LogStatus.PASS, "Verify the Checkout button is present or not");
			addScreenCapture();
			DriverScript.bResult = true;
			
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Verify the Checkout button is present or not " +data);
			
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL, "Verify the Checkout button is present or not");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	

	/*
	 * This function is used to Verify the Recommended Products On Cart Page. 
	 */
	public static void verifyRecommendedProductsOnCart(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("Verify the Recommended Products On Cart Page");
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			String cartTilte=element.getText();
			List<WebElement> list=driver.findElements(By.xpath("//div[@class='ngucarousel-items']/ngu-tile"));
			int products=list.size();
			
			for(int i=1;i<=products;i++)
			{
				String productTilte=driver.findElement(By.xpath("//div[@class='ngucarousel-items']/ngu-tile["+ i +"]/div/hos-product-card/div/div/h4")).getText();
			if(productTilte.equals(cartTilte) )
			{
				System.out.println("Recommended products not displaying properly");
				Log.info("Recommended products not displaying properly");
			}
			}
			
			
			test.log(LogStatus.PASS, "Verify the Recommended Products On Cart Page");
			addScreenCapture();
			DriverScript.bResult = true;
			
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Recommended Products On Cart Page not verified " +data);
			
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL, "Recommended Products On Cart Page not verified");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}
	
	/*
	 * This function is used to Verify the Recommended Products On Cart Page. 
	 */
	public static void verifyMonthlyAndAnnualTotal(Properties OR,String object,String objectType,String data,int stepNumber) throws Exception{
		try
		{
			Log.info("Verify the Monthly And Annual Total");
			By locator;
			locator = locatorValue(OR,object,objectType );
			WebElement element = driver.findElement(locator);
			String MonthlyPrice=element.getText();
			
			List<WebElement> list=driver.findElements(By.xpath("//div[@class='custom-radio']/label"));
			int paymentOptions=list.size();
			
			for(int i=1;i<=paymentOptions;i++)
			{
				driver.findElement(By.xpath("(//div[@class='custom-radio']/label)["+ i +"]")).click();
				
				String TotalAmount=driver.findElement(By.xpath("//div[@class='card-body row no-gutters sub-total-box']/div[2]/div[2]")).getText();
				
				
			if(i==2)
			{
                String amount[]=MonthlyPrice.split("\\$");
                String onePunch = amount[1].toString();
                double monthly=Double.parseDouble(onePunch); 
				int months=12;
				
				double Total=monthly * months;
				String numberAsString = Double.toString(Total);
				String Total1="$"+numberAsString;
				if(TotalAmount.equals(Total1))
				{
					System.out.println("Annual amount as matched");
				}
				
			}else
			{
				if(MonthlyPrice.equals(TotalAmount))
				{
					System.out.println("Monthly amount as matched");
				}
			}
			}
			
			
			test.log(LogStatus.PASS, "Verify the Monthly And Annual Total");
			addScreenCapture();
			DriverScript.bResult = true;
			
		}
		catch(Exception e){
			String testCase= DriverScript.sTestCaseID;
			DriverScript.takeScreenShot(testCase);
			Log.error("Verify the Monthly And Annual Total not verified " +data);
			
			String screenShotPath = takeScreenShot("extentReportImage");
			test.log(LogStatus.FAIL, "Verify the Monthly And Annual Total not verified");
			test.log(LogStatus.ERROR,"Failure Snapshot : "+ test.addScreenCapture(screenShotPath));
			DriverScript.bResult = false;
         	}
		}

}
 
	
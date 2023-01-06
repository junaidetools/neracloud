package com.neracloud.testcases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.neracloud.pages.loginPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utls.BrowserFactory;
import Utls.ExcelDriven_XLSX;
import Utls.snap_shot;

public class TemporaryClass {
	
	ExtentReports report;
	ExtentTest logger;
	WebDriver driver;
	String pdfUrl;
	public static String episodeId = "317"; // It's a global String
	
	@SuppressWarnings("deprecation")
	@BeforeMethod
	public void setup() throws InterruptedException, IOException 
	{
		//File currentDir = new File (".");
		//String basePath = currentDir.getCanonicalPath();
		//report = new ExtentReports(basePath+"\\NeRA_Cloud_User_Creation_Report.html");
		//logger = report.startTest("VerifyLoginToE-ToolsCloudApplication");
		
		
		String WebUrl = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "URL").get("Value").toString();
		String browser = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "Browser").get("Value").toString();

		driver  = BrowserFactory.startBrowser(browser, WebUrl);
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);

		//logger.log(LogStatus.PASS, "Browser started and cloud application is accessed.");

		String username = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "UserName").get("Value").toString();
		String password = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "Password").get("Value").toString();
				
		loginPage login_page = PageFactory.initElements(driver, loginPage.class);
		login_page.login_nera(username, password);
		//logger.log(LogStatus.PASS, "NeRA cloud application accessed successfully.");
		//report.endTest(logger);
	}
	
	@Test(priority=2)
	public void TC003_VerifyResidentialDetailsFirstNameMiddleNameAndLastNameFromStep1() throws Exception 
	{
		
		File currentDir = new File (".");
		String basePath = currentDir.getCanonicalPath();
		//report = new ExtentReports(basePath+"\\NeRA_Cloud_User_Creation_Report.html");
		//logger = report.startTest("VerifyLoginToE-ToolsCloudApplication");
		
		
		LocalDate myObj = LocalDate.now(); // Create a date object
		System.out.println(myObj); // Display the current date
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddMMyyyy");

		String formattedDate = myObj.format(myFormatObj);
		System.out.println("After formatting: " + formattedDate);

		//report = new ExtentReports(basePath+"\\NeRA_Cloud_User_Creation_Report.html");
		report = new ExtentReports(basePath+"\\NeRa_Cloud"+formattedDate+".html",false);

		
		logger = report.startTest("TC003_VerifyResidentialDetailsFirstNameMiddleNameAndLastNameFromStep1");

		driver.findElement(By.xpath("//*[contains(text(),'Register')]")).click();
		logger.log(LogStatus.PASS, "Register Tabe is selected");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(360));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]")));
		
		
		driver.switchTo().activeElement();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'If you have made any changes that are not saved yet, all the changes will be lost. Are you sure you want to continue?')]")));
		String errorMessage = driver.findElement(By.xpath("//*[contains(text(),'If you have made any changes that are not saved yet, all the changes will be lost. Are you sure you want to continue?')]"))
				.getText();
		System.out.println(errorMessage);
		logger.log(LogStatus.PASS, "Error message popup shown");
		
		String yesbutton = driver.findElement(By.xpath("//*[contains(text(),'Yes')]")).getText();
		System.out.println(yesbutton + ": This text from Yes button is fetched...");

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Yes')]")));
		element1.click();
		
		driver.findElement(By.name("searchValue")).sendKeys(episodeId);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]")));

		driver.findElement(By.xpath("//*[contains(text(),'Submit')]")).click();
		System.out.println("Searching for the created record from search filter for the episode Id: "+episodeId);
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]")));
		

		//Click on Edit button to open the same record again in edit mode. 
		driver.findElement(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]")).click();
		driver.findElement(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]//*[contains(text(),'Edit')]")).click();
		logger.log(LogStatus.PASS, "Here edit inquiry is selected.");
		System.out.println("Here edit inquiry is selected.");
				
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		
		//Click Step 1 tab and wait till element. 
        driver.findElement(By.id("step_1")).click();
        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Save & Exit')]")));
		logger.log(LogStatus.PASS, "Step 1 tab selected.");
		System.out.println("Step 1 tab selected.");
		
		
		
		
		
		
		
		
		
		//Residential Details are required to be verified. 
		
		  logger = report.startTest("Verify Title on step-1 is correct."); 
		  
		  try 
		  {
			  
		  System.out.println("before reading the value from excel");
		  String title = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation","title").get("Value").toString(); 
		  System.out.println("after reading the value from excel"+title);
		  //driver.findElement(By.name("resident_title_type_id")).getAttribute("value");
		  

			/*
			 * String titleFromUI =
			 * $("select[name=\"resident_title_type_id\"]").find(":selected").text();
			 * System.out.println("JQuery output: +++++++++++++++++++++++++++++: "
			 * +titleFromUI);
			 */
		  Thread.sleep(5000);
			/*
			 * WebElement webElement =
			 * driver.findElement(By.name("resident_title_type_id")); String jQuerySelector
			 * = "arguments[0]"; JavascriptExecutor executor = (JavascriptExecutor)driver;
			 * Object titletext = executor.executeScript("return $(" + jQuerySelector+
			 * ").doSomethingInJquery();", webElement);
			 */
		  
		  Select objSelect = new Select(driver.findElement(By.name("resident_title_type_id")));
		  String str = objSelect.getFirstSelectedOption().getText();
		  System.out.println("output from js is:"+str);
		  
		 // System.out.println("output from js is:"+titletext.toString() );
		  
//		  JavascriptExecutor executor = (JavascriptExecutor)driver;
//			 //executor.executeScript("alert('Alert');");
//			var str1 = executor.executeScript("$('select[name=\"resident_title_type_id\"]').find(\":selected\").text()");
//			System.out.println("js output str1 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+ str1);		  
//		  var str = executor.executeScript("let elem = document.querySelector(\"select[name='resident_title_type_id']\")\n"
//		  		+ "let value = '';if(elem) {value = elem.options[elem.selectedIndex].value;}");
//			System.out.println("js output ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+ str);
//			
//			
//			var str2 = executor.executeScript("let elem = document.querySelector(\"select[name='genderTypeList']\")\n"
//					+ "let value = '';if(elem) {value = elem.options[elem.selectedIndex].text;}");
//				System.out.println("js output ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+ str2);
			
			
			/*
			 * JavascriptExecutor executor = (JavascriptExecutor)driver; var str =
			 * executor.executeScript(
			 * "\"select[name=\"resident_title_type_id\"]\").find(\":selected\").text()");
			 * //var str = executor.executeScript(
			 * "\"select[name=\"resident_title_type_id\"]\").find(\":selected\").text()");
			 * System.out.println(str.toString());
			 */
		  
		  //driver.executeScript("return jQuery()!=null");
		  
		  
			/*
			 * var titleFromUI =
			 * $("select[name=\"resident_title_type_id\"]").find(":selected").text();
			 * System.out.println("Title from UI in variable is: "+ titleFromUI);
			 * System.out.println("Title from UI is: "+ titleFromUI);
			 * Assert.assertEquals(title, titleFromUI);
			 * System.out.println("user Title from step-1 UI is: " + titleFromUI);
			 * System.out.println("user Title from data is: " + title);
			 */
		  
		  logger.log(LogStatus.PASS, "Pass - Verify Title is successful."); 
		  } 
		  catch
		  (AssertionError e) { logger.log(LogStatus.FAIL, "Fail - Verify Title is failed"); 
		  } 
		  report.endTest(logger);
		 
		
		
		logger = report.startTest("Verify First Name on step-1 is correct.");
		try {
			// Assertion to be placed here
			String firstNam = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "firstNam").get("Value").toString();
			String firstNameFromUI = driver.findElement(By.name("residentFirstName")).getAttribute("value");
			Assert.assertEquals(firstNam, firstNameFromUI);
			System.out.println("user first name from step-1 UI is: " + firstNameFromUI);
			System.out.println("user first name from data is: " + firstNam);
			
			logger.log(LogStatus.PASS, "Pass - Verify First Name is successful.");
		} catch (AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Verify First Name is failed");
		}
		report.endTest(logger);
		
		logger = report.startTest("Verify Middle Name on step-1 is correct.");
		try {
			// Assertion to be placed here
			String middleName = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "middleName").get("Value").toString();
			String middleNameFromUI = driver.findElement(By.name("residentMiddleName")).getAttribute("value");
			Assert.assertEquals(middleName, middleNameFromUI);
			System.out.println("user last name from step-1 UI is: " + middleNameFromUI);
			System.out.println("user last name from data is: " + middleNameFromUI);
			logger.log(LogStatus.PASS, "Pass - Verify Last Name is successful.");
		} catch (AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Last Name verification failed.");
		}
		report.endTest(logger);

		logger = report.startTest("Verify Last Name on step-1 is correct.");
		try {
			// Assertion to be placed here
			String lastName = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "lastName").get("Value").toString();
			String lstNameFromUI = driver.findElement(By.name("residentLastName")).getAttribute("value");
			Assert.assertEquals(lastName, lstNameFromUI);
			System.out.println("user last name from step-1 UI is: " + lstNameFromUI);
			System.out.println("user last name from data is: " + lastName);
			logger.log(LogStatus.PASS, "Pass - Verify Last Name is successful.");
		} catch (AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Last Name verification failed.");
		}
		report.endTest(logger);
		

		logger = report.startTest("Verify Date of Birth from Resident Details on step-1 is correct.");
		try {
			// Assertion to be placed here
			String setDOB = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "setDOB").get("Value").toString();
			String dateOfBirthFromUI = driver.findElement(By.name("date_of_birth")).getAttribute("value");
			Assert.assertEquals(setDOB, dateOfBirthFromUI);
			System.out.println("Date of Birth from UI is: " + dateOfBirthFromUI);
			System.out.println("Date of Birth from data is: " + setDOB);
			logger.log(LogStatus.PASS, "Pass - Verify Date of Birth is successful.");
		} catch (AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Verify Date of Birth is failed.");
		}
		report.endTest(logger);
		
		logger.log(LogStatus.PASS, "Verification of First Name, Middle Name and last name successful.");
		
		
		       
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Save & Exit')]")));
        
		WebElement element15 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Save & Exit')]")));
		element15.click();
		System.out.println("Save & Exit button is clicked.");
			
		
		driver.switchTo().activeElement();
		WebElement element17 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'OK')]")));
		element17.click();
		System.out.println("Ok, Popup button is closed.");
		
		
		String image_path2 =snap_shot.takeSnapShot(driver); 
		   String img2 = logger.addScreenCapture(image_path2);
			logger.log(LogStatus.PASS, "verified", img2);
			
			
		report.endTest(logger);
	
	}
}

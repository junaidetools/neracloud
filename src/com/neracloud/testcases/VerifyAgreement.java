package com.neracloud.testcases;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utls.BrowserFactory;
import Utls.ExcelDriven_XLSX;
import Utls.PdfReader;

public class VerifyAgreement {

	ExtentReports report;
	ExtentTest logger;
	WebDriver driver;
	String pdfUrl;



	@SuppressWarnings("deprecation")
	@Test
	public void TC001_VerifyPermanentAgreementSections() throws Exception {

		/*
		 * File currentDir = new File ("."); String basePath =
		 * currentDir.getCanonicalPath(); report = new
		 * ExtentReports(basePath+"\\NeRA_Cloud_Report.html");
		 */
		
		File currentDir = new File (".");
		String basePath = currentDir.getCanonicalPath();

		LocalDate myObj = LocalDate.now(); // Create a date object
		System.out.println(myObj); // Display the current date
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddMMyyyy");

		String formattedDate = myObj.format(myFormatObj);
		System.out.println("After formatting: " + formattedDate);
		report = new ExtentReports(basePath+"\\NeRa_Cloud"+formattedDate+".html");
		
		
		
		logger = report.startTest("TC001_VerifyPermanentAgreementSections");

		String WebUrl = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "URL").get("Value").toString();
		String browser = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "Browser").get("Value").toString();

		WebDriver driver = BrowserFactory.startBrowser(browser, WebUrl);
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);

		logger.log(LogStatus.PASS, "Browser started and cloud application accessed.");

		String username = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "UserName").get("Value").toString();
		String password = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "Password").get("Value").toString();
		// Provides email into email field.
		driver.findElement(By.id("email")).sendKeys(username);
		// Click next button to proceed with provided email address.
		driver.findElement(By.xpath("//*[contains(text(),'Next')]")).click();
		// Provides password in the password field.
		driver.findElement(By.id("loginpassword")).sendKeys(password);
		// Click login button to login
		driver.findElement(By.xpath("//*[contains(@class, 'btn-login btn btn-secondary')]")).click();

		logger.log(LogStatus.PASS, "Loging was successful.");

		Thread.sleep(5000);
		// Below lines were working fine on chrome
		WebElement neraLogo = driver.findElement(By.id("NeRAAppLogo"));
		Assert.assertTrue(neraLogo.isDisplayed(), "AWS Cognito Identity user loging failed with username: " + username);
		// driver.findElement(By.xpath("//*[contains(text(),'National e-Tools
		// Residential Agreement')]")).click();

		logger.log(LogStatus.PASS, "Home page is loaded successfully.");

		Thread.sleep(3000);

		// driver.findElement(By.xpath("//*[contains(@class, 'btn
		// btn-secondary')]")).click();
		// driver.findElement(By.xpath("//img[@src='./static/media/DropdownMenuLogout.c00a4b7c.svg']")).click();

		// driver.switchTo().activeElement();
		// driver.findElement(By.xpath("//*[contains(@class,'btn btn-primary') and
		// contains(text(),'Yes')]")).click();

		// logger.log(LogStatus.INFO, "Logging out from the application.");
		// logger.log(LogStatus.PASS, "Log out from the application.");

		driver.findElement(By.id("NeRAAppLogo")).click();
		logger.log(LogStatus.PASS, "NeRA cloud application selected.");

		driver.findElement(By.xpath("//*[contains(text(),'Register')]")).click();
		logger.log(LogStatus.PASS, "Register Tabe is selected");
		

		// Code below this line was happening only at home. there was popup message
		// shown at home but not being faced at office
		Thread.sleep(3000);
		driver.switchTo().activeElement();

		// driver.switchTo().frame("Invalid");
		String errorMessage = driver.findElement(By.xpath(
				"//*[contains(text(),'If you have made any changes that are not saved yet, all the changes will be lost. Are you sure you want to continue?')]"))
				.getText();
		System.out.println(errorMessage);
		logger.log(LogStatus.PASS, "Error message popup shown");

		// Code below this line was happening only at home. there was popup message
		// shown at home but not being faced at office
		String errorMessage2 = driver.findElement(By.xpath("//*[contains(@class, 'unsavedChangesModal modal-footer')]"))
				.getText();
		System.out.println(errorMessage2);
		logger.log(LogStatus.PASS, "Text from modal footer fetched.");

		// This thread sleep is used to wait for the time till loader is shown on the
		// popup message.
		Thread.sleep(8000);

		// driver.findElement(By.xpath("//*[contains(text(),'Yes, discard
		// changes')]")).click();

		// Code below this line was happening only at home. there was popup message
		// shown at home but not being faced at office
		String yesbutton = driver.findElement(By.xpath("//*[contains(text(),'Yes')]")).getText();
		System.out.println(yesbutton);
		driver.findElement(By.xpath("//*[contains(text(),'Yes')]")).click();
		logger.log(LogStatus.PASS, "Yes is selected on popup to continue...");

		System.out.println("Yes is selected on popup to continue");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By
				.xpath("//*[contains(text(),'Type')]/following::div[3]//*[contains(@class,'dropdown-heading-value')]"))
				.click();
		List<WebElement> dd_menu = driver.findElements(By.xpath("//*[contains(@class, 'dropdown-content')]//li/label/div/span"));

		
		for(int i=0; i<dd_menu.size(); i++) 
		{ 
			WebElement element=dd_menu.get(i);
			String innerhtml=element.getAttribute("innerHTML");
		
			if(innerhtml.contains("Permanent"))
				{
				element.click();
				break;
				}
		System.out.println("values from drop down ===========>"+innerhtml); 
		}
		 
		/*
		 * WebElement agreement_Type_DD = driver.findElement(By.xpath(
		 * "//*[contains(text(),'Type')]/following::div[3]//*[contains(@class,'dropdown-heading-value')]"
		 * )); Select agr_type_dd = new Select(agreement_Type_DD);
		 * agr_type_dd.selectByIndex(1);
		 */

		driver.findElement(By.xpath("//*[contains(text(),'Submit')]")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]")).click();
		driver.findElement(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]//*[contains(text(),'Edit')]")).click();
		logger.log(LogStatus.PASS, "Register Tabe is selected");
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);
		Thread.sleep(15000);

		driver.findElement(By.id("step_7")).click();
		// driver.findElement(By.xpath("//li[@id='step_7']")).click();
		logger.log(LogStatus.PASS, "Step 7 is selected. ");
		Thread.sleep(3000);

		// driver.findElement(By.name("previewfinalizeagreement")).click();
		driver.findElement(By.xpath("//*[contains(text(),'Preview Agreement')]")).click();
		logger.log(LogStatus.PASS, "Preview document is clicked now...");
		Thread.sleep(45000);

		WebElement filePath = driver.findElement(By.id("download_btn_file"));
		String file = filePath.getAttribute("href");

		System.out.println("File path is: " + file);
		String fileName = file.substring(file.lastIndexOf('=') + 1, file.length());

		System.out.println("file name is:" + fileName);

		String filePth = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "filePath").get("Value").toString();

		// file name and file path both are concatenated
		String fileaddress = filePth + fileName;
		System.out.println("Now file path is appended with perfect file path: " + fileaddress);

		/*
		 * }
		 * 
		 * @Test public void VerifyTextPara() throws Exception {
		 */
		String paratext = PdfReader.verifyPdf(fileaddress);
		
		
	    //Test Case Number 1
		logger = report.startTest("Verify Part A");
		
		try{
		    //Assertion to be placed here
			String partA = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "partA").get("Value").toString();
			Assert.assertTrue(paratext.contains(partA), "Part A verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part A verification failed.");
		}
		report.endTest(logger);
		
		

		//Test Case Number 2
		logger = report.startTest("Verify Content of Part A");
		
		try{
		    //Assertion to be placed here
			String ContentPartA = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "ContentPartA").get("Value").toString();
			Assert.assertTrue(paratext.contains(ContentPartA), "Content of part A verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part A verification failed.");
		}
		report.endTest(logger);
		
		
		//Test Case Number 3
		logger = report.startTest("Verify Part B");
		
		try{
			String PartB = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "PartB").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartB), "Part B verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part B verification failed.");
		}
		report.endTest(logger);
		



		//Test Case Number 4
		logger = report.startTest("Verify Content of Part B");
		
		try{
			String ContentPartB = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "ContentPartB").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartB), "Part B verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part B verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 5
		logger = report.startTest("Verify Part C");
		
		try{
			String PartC = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "PartC").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartC), "Part C verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part C verification failed.");
		}
		report.endTest(logger);

		

		
		
		 //Test Case Number 6
		logger = report.startTest("Verify Content of Part C");
		
		try{
			String ContentPartC = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "ContentPartC").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartC), "Part C verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part C verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 7
		logger = report.startTest("Verify Part D");
		
		try{
			String PartD = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "PartD").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartD), "Part D verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part D verification failed.");
		}
		report.endTest(logger);
		
		

		 //Test Case Number 8
		logger = report.startTest("Verify Content of Part D");
		
		try{
			String ContentPartD = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "ContentPartD").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartD), "Part D verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part D verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 9
		logger = report.startTest("Verify Part E");
		
		try{
			String PartE = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "PartE").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartE), "Part E verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part E verification failed.");
		}
		report.endTest(logger);
		
		

		
		 //Test Case Number 10
		logger = report.startTest("Verify Content of Part E");
		
		try{
			String ContentPartE = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "ContentPartE").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartE), "Part E verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part E verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 11
		logger = report.startTest("Verify Part F");
		
		try{
			String PartF = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "PartF").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartF), "Part F verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part F verification failed.");
		}
		report.endTest(logger);
		

		
		
		
		 //Test Case Number 12
		logger = report.startTest("Verify Content of Part F");
		
		try{
			String ContentPartF = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "ContentPartF").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartF), "Part F verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part F verification failed.");
		}
		report.endTest(logger);
		
		
		
		

		
		
		
		 //Test Case Number 13	
		logger = report.startTest("Verify Part G");
		
		try{
			String PartG = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "PartG").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartG), "Part G verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part G verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 14	
		logger = report.startTest("Verify Content of Part G");
		
		try{
			String ContentPartG = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "ContentPartG").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartG), "Part G verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part G verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 15
		logger = report.startTest("Verify Part H");
		
		try{
			String PartH = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "PartH").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartH), "Part H verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part H verification failed.");
		}
		report.endTest(logger);




		
		 //Test Case Number 16
		logger = report.startTest("Verify Content of Part H");
		
		try{
			String ContentPartH = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "ContentPartH").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartH), "Part H verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part H verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 17
		logger = report.startTest("Verify Part I");
		
		try{
			String PartI = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "PartI").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartI), "Part I verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part I verification failed.");
		}
		report.endTest(logger);


		
		
		
		 //Test Case Number 18
		logger = report.startTest("Verify Content of Part I");
		
		try{
			String ContentPartI = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "ContentPartI").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartI), "Part I verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part I verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 19
		logger = report.startTest("Verify Part J");
		
		try{
			String PartJ = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "PartJ").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartJ), "Part J verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part J verification failed.");
		}
		report.endTest(logger);
		
		

		
		 //Test Case Number 20
		logger = report.startTest("Verify Content of Part J");
		
		try{
			String ContentPartJ = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "ContentPartJ").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartJ), "Part J verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part J verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 21
		logger = report.startTest("Verify Part K");
		
		try{
			String PartK = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "PartK").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartK), "Part K verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part K verification failed.");
		}
		report.endTest(logger);



		
		
		 //Test Case Number 22
		logger = report.startTest("Verify Content of Part K");
		
		try{
			String ContentPartK = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "ContentPartK").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartK), "Part K verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part K verification failed.");
		}
		report.endTest(logger);
		


		 //Test Case Number 23
		logger = report.startTest("Verify Part L");
		
		try{
			String PartL = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "PartL").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartL), "Part L verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part L verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 24
		logger = report.startTest("Verify Content of Part L");
		
		try{
			String ContentPartL = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentAgreement", "ContentPartL").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartL), "Part L verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part L verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		

		report.endTest(logger);
		//report.flush();
		//report.close();
		driver.quit();
		// }

		/*
		 * @Test public void endOfTest() {
		 * 
		 * 
		 * 
		 * 
		 * 
		 * }
		 */
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void TC002_VerifyPermanentExtraAgreementSections() throws Exception {

		//report = new ExtentReports("C:\\Users\\hp\\eclipse-workspace\\NeRACloud_Automation\\NeRA_Cloud_Report.html");
		logger = report.startTest("TC002_VerifyPermanentExtraAgreementSections");

		String WebUrl = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "URL").get("Value").toString();
		String browser = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "Browser").get("Value").toString();

		WebDriver driver = BrowserFactory.startBrowser(browser, WebUrl);
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);

		logger.log(LogStatus.PASS, "Browser started and cloud application accessed.");

		String username = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "UserName").get("Value").toString();
		String password = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "Password").get("Value").toString();
		// Provides email into email field.
		driver.findElement(By.id("email")).sendKeys(username);
		// Click next button to proceed with provided email address.
		driver.findElement(By.xpath("//*[contains(text(),'Next')]")).click();
		// Provides password in the password field.
		driver.findElement(By.id("loginpassword")).sendKeys(password);
		// Click login button to login
		driver.findElement(By.xpath("//*[contains(@class, 'btn-login btn btn-secondary')]")).click();

		logger.log(LogStatus.PASS, "Loging was successful.");

		Thread.sleep(5000);
		// Below lines were working fine on chrome
		WebElement neraLogo = driver.findElement(By.id("NeRAAppLogo"));
		Assert.assertTrue(neraLogo.isDisplayed(), "AWS Cognito Identity user loging failed with username: " + username);
		// driver.findElement(By.xpath("//*[contains(text(),'National e-Tools
		// Residential Agreement')]")).click();

		logger.log(LogStatus.PASS, "Home page is loaded successfully.");

		Thread.sleep(3000);

		// driver.findElement(By.xpath("//*[contains(@class, 'btn
		// btn-secondary')]")).click();
		// driver.findElement(By.xpath("//img[@src='./static/media/DropdownMenuLogout.c00a4b7c.svg']")).click();

		// driver.switchTo().activeElement();
		// driver.findElement(By.xpath("//*[contains(@class,'btn btn-primary') and
		// contains(text(),'Yes')]")).click();

		// logger.log(LogStatus.INFO, "Logging out from the application.");
		// logger.log(LogStatus.PASS, "Log out from the application.");

		driver.findElement(By.id("NeRAAppLogo")).click();
		logger.log(LogStatus.PASS, "NeRA cloud application selected.");

		driver.findElement(By.xpath("//*[contains(text(),'Register')]")).click();
		logger.log(LogStatus.PASS, "Register Tabe is selected");
		

		// Code below this line was happening only at home. there was popup message
		// shown at home but not being faced at office
		Thread.sleep(3000);
		driver.switchTo().activeElement();

		// driver.switchTo().frame("Invalid");
		String errorMessage = driver.findElement(By.xpath(
				"//*[contains(text(),'If you have made any changes that are not saved yet, all the changes will be lost. Are you sure you want to continue?')]"))
				.getText();
		System.out.println(errorMessage);
		logger.log(LogStatus.PASS, "Error message popup shown");

		// Code below this line was happening only at home. there was popup message
		// shown at home but not being faced at office
		String errorMessage2 = driver.findElement(By.xpath("//*[contains(@class, 'unsavedChangesModal modal-footer')]"))
				.getText();
		System.out.println(errorMessage2);
		logger.log(LogStatus.PASS, "Text from modal footer fetched.");

		// This thread sleep is used to wait for the time till loader is shown on the
		// popup message.
		Thread.sleep(8000);

		// driver.findElement(By.xpath("//*[contains(text(),'Yes, discard
		// changes')]")).click();

		// Code below this line was happening only at home. there was popup message
		// shown at home but not being faced at office
		String yesbutton = driver.findElement(By.xpath("//*[contains(text(),'Yes')]")).getText();
		System.out.println(yesbutton);
		driver.findElement(By.xpath("//*[contains(text(),'Yes')]")).click();
		logger.log(LogStatus.PASS, "Yes is selected on popup to continue...");

		System.out.println("Yes is selected on popup to continue");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By
				.xpath("//*[contains(text(),'Type')]/following::div[3]//*[contains(@class,'dropdown-heading-value')]"))
				.click();
		List<WebElement> dd_menu = driver.findElements(By.xpath("//*[contains(@class, 'dropdown-content')]//li/label/div/span"));

		
		for(int i=0; i<dd_menu.size(); i++) 
		{ 
			WebElement element=dd_menu.get(i);
			String innerhtml=element.getAttribute("innerHTML");
		
			if(innerhtml.contains("Permanent / Extra Service"))
				{
				element.click();
				break;
				}
		System.out.println("values from drop down ===========>"+innerhtml); 
		}
		 
		/*
		 * WebElement agreement_Type_DD = driver.findElement(By.xpath(
		 * "//*[contains(text(),'Type')]/following::div[3]//*[contains(@class,'dropdown-heading-value')]"
		 * )); Select agr_type_dd = new Select(agreement_Type_DD);
		 * agr_type_dd.selectByIndex(1);
		 */

		driver.findElement(By.xpath("//*[contains(text(),'Submit')]")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath(
				"//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]"))
				.click();
		driver.findElement(By.xpath(
				"//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]//*[contains(text(),'Edit')]"))
				.click();
		logger.log(LogStatus.PASS, "Register Tabe is selected");
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);
		Thread.sleep(15000);

		driver.findElement(By.id("step_7")).click();
		// driver.findElement(By.xpath("//li[@id='step_7']")).click();
		logger.log(LogStatus.PASS, "Step 7 is selected. ");
		Thread.sleep(3000);

		// driver.findElement(By.name("previewfinalizeagreement")).click();
		driver.findElement(By.xpath("//*[contains(text(),'Preview Agreement')]")).click();
		logger.log(LogStatus.PASS, "Preview document is clicked now...");
		Thread.sleep(45000);

		WebElement filePath = driver.findElement(By.id("download_btn_file"));
		String file = filePath.getAttribute("href");

		System.out.println("File path is: " + file);
		String fileName = file.substring(file.lastIndexOf('=') + 1, file.length());

		System.out.println("file name is:" + fileName);

		String filePth = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "filePath").get("Value").toString();

		// file name and file path both are concatenated
		String fileaddress = filePth + fileName;
		System.out.println("Now file path is appended with perfect file path: " + fileaddress);

		/*
		 * }
		 * 
		 * @Test public void VerifyTextPara() throws Exception {
		 */
		String paratext = PdfReader.verifyPdf(fileaddress);

		
	    //Test Case Number 25
		logger = report.startTest("Verify Part A");
		
		try{
		    //Assertion to be placed here
			String partA = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "partA").get("Value").toString();
			Assert.assertTrue(paratext.contains(partA), "Part A verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part A verification failed.");
		}
		report.endTest(logger);
		
		

		//Test Case Number 26
		logger = report.startTest("Verify Content of Part A");
		
		try{
		    //Assertion to be placed here
			String ContentPartA = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "ContentPartA").get("Value").toString();
			Assert.assertTrue(paratext.contains(ContentPartA), "Content of part A verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part A verification failed.");
		}
		report.endTest(logger);
		
		
		//Test Case Number 27
		logger = report.startTest("Verify Part B");
		
		try{
			String PartB = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "PartB").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartB), "Part B verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part B verification failed.");
		}
		report.endTest(logger);
		



		//Test Case Number 28
		logger = report.startTest("Verify Content of Part B");
		
		try{
			String ContentPartB = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "ContentPartB").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartB), "Part B verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part B verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 29
		logger = report.startTest("Verify Part C");
		
		try{
			String PartC = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "PartC").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartC), "Part C verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part C verification failed.");
		}
		report.endTest(logger);

		

		
		
		 //Test Case Number 30
		logger = report.startTest("Verify Content of Part C");
		
		try{
			String ContentPartC = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "ContentPartC").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartC), "Part C verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part C verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 31
		logger = report.startTest("Verify Part D");
		
		try{
			String PartD = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "PartD").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartD), "Part D verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part D verification failed.");
		}
		report.endTest(logger);
		
		

		 //Test Case Number 32
		logger = report.startTest("Verify Content of Part D");
		
		try{
			String ContentPartD = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "ContentPartD").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartD), "Part D verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part D verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 33
		logger = report.startTest("Verify Part E");
		
		try{
			String PartE = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "PartE").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartE), "Part E verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part E verification failed.");
		}
		report.endTest(logger);
		
		

		
		 //Test Case Number 34
		logger = report.startTest("Verify Content of Part E");
		
		try{
			String ContentPartE = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "ContentPartE").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartE), "Part E verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part E verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 35
		logger = report.startTest("Verify Part F");
		
		try{
			String PartF = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "PartF").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartF), "Part F verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part F verification failed.");
		}
		report.endTest(logger);
		

		
		
		
		 //Test Case Number 36
		logger = report.startTest("Verify Content of Part F");
		
		try{
			String ContentPartF = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "ContentPartF").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartF), "Part F verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part F verification failed.");
		}
		report.endTest(logger);
		
		
		
		

		
		
		
		 //Test Case Number 37	
		logger = report.startTest("Verify Part G");
		
		try{
			String PartG = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "PartG").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartG), "Part G verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part G verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 38	
		logger = report.startTest("Verify Content of Part G");
		
		try{
			String ContentPartG = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "ContentPartG").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartG), "Part G verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part G verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 39
		logger = report.startTest("Verify Part H");
		
		try{
			String PartH = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "PartH").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartH), "Part H verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part H verification failed.");
		}
		report.endTest(logger);




		
		 //Test Case Number 40
		logger = report.startTest("Verify Content of Part H");
		
		try{
			String ContentPartH = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "ContentPartH").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartH), "Part H verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part H verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 41
		logger = report.startTest("Verify Part I");
		
		try{
			String PartI = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "PartI").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartI), "Part I verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part I verification failed.");
		}
		report.endTest(logger);


		
		
		
		 //Test Case Number 42
		logger = report.startTest("Verify Content of Part I");
		
		try{
			String ContentPartI = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "ContentPartI").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartI), "Part I verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part I verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 43
		logger = report.startTest("Verify Part J");
		
		try{
			String PartJ = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "PartJ").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartJ), "Part J verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part J verification failed.");
		}
		report.endTest(logger);
		
		

		
		 //Test Case Number 44
		logger = report.startTest("Verify Content of Part J");
		
		try{
			String ContentPartJ = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "ContentPartJ").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartJ), "Part J verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part J verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 45
		logger = report.startTest("Verify Part K");
		
		try{
			String PartK = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "PartK").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartK), "Part K verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part K verification failed.");
		}
		report.endTest(logger);



		
		
		 //Test Case Number 46
		logger = report.startTest("Verify Content of Part K");
		
		try{
			String ContentPartK = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "ContentPartK").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartK), "Part K verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part K verification failed.");
		}
		report.endTest(logger);
		


		 //Test Case Number 47
		logger = report.startTest("Verify Part L");
		
		try{
			String PartL = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "PartL").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartL), "Part L verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part L verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 48
		logger = report.startTest("Verify Content of Part L");
		
		try{
			String ContentPartL = ExcelDriven_XLSX.readExcelData("Testdata", "PermanentExtAgreement", "ContentPartL").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartL), "Part L verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part L verification failed.");
		}
		report.endTest(logger);
		
		

		report.endTest(logger);
		//report.flush();
		//report.close();
		driver.quit();
		// }

		/*
		 * @Test public void endOfTest() {
		 * 
		 * 
		 * 
		 * 
		 * 
		 * }
		 */
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void TC003_VerifyRespiteAgreementSections() throws Exception {

		//report = new ExtentReports("C:\\Users\\hp\\eclipse-workspace\\NeRACloud_Automation\\NeRA_Cloud_Report.html");
		logger = report.startTest("TC003_VerifyRespiteAgreementSections");

		String WebUrl = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "URL").get("Value").toString();
		String browser = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "Browser").get("Value").toString();

		WebDriver driver = BrowserFactory.startBrowser(browser, WebUrl);
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);

		logger.log(LogStatus.PASS, "Browser started and cloud application accessed.");

		String username = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "UserName").get("Value").toString();
		String password = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "Password").get("Value").toString();
		// Provides email into email field.
		driver.findElement(By.id("email")).sendKeys(username);
		// Click next button to proceed with provided email address.
		driver.findElement(By.xpath("//*[contains(text(),'Next')]")).click();
		// Provides password in the password field.
		driver.findElement(By.id("loginpassword")).sendKeys(password);
		// Click login button to login
		driver.findElement(By.xpath("//*[contains(@class, 'btn-login btn btn-secondary')]")).click();

		logger.log(LogStatus.PASS, "Loging was successful.");

		Thread.sleep(5000);
		// Below lines were working fine on chrome
		WebElement neraLogo = driver.findElement(By.id("NeRAAppLogo"));
		Assert.assertTrue(neraLogo.isDisplayed(), "AWS Cognito Identity user loging failed with username: " + username);
		// driver.findElement(By.xpath("//*[contains(text(),'National e-Tools
		// Residential Agreement')]")).click();

		logger.log(LogStatus.PASS, "Home page is loaded successfully.");

		Thread.sleep(3000);

		// driver.findElement(By.xpath("//*[contains(@class, 'btn
		// btn-secondary')]")).click();
		// driver.findElement(By.xpath("//img[@src='./static/media/DropdownMenuLogout.c00a4b7c.svg']")).click();

		// driver.switchTo().activeElement();
		// driver.findElement(By.xpath("//*[contains(@class,'btn btn-primary') and
		// contains(text(),'Yes')]")).click();

		// logger.log(LogStatus.INFO, "Logging out from the application.");
		// logger.log(LogStatus.PASS, "Log out from the application.");

		driver.findElement(By.id("NeRAAppLogo")).click();
		logger.log(LogStatus.PASS, "NeRA cloud application selected.");

		driver.findElement(By.xpath("//*[contains(text(),'Register')]")).click();
		logger.log(LogStatus.PASS, "Register Tabe is selected");
		

		// Code below this line was happening only at home. there was popup message
		// shown at home but not being faced at office
		Thread.sleep(3000);
		driver.switchTo().activeElement();

		// driver.switchTo().frame("Invalid");
		String errorMessage = driver.findElement(By.xpath(
				"//*[contains(text(),'If you have made any changes that are not saved yet, all the changes will be lost. Are you sure you want to continue?')]"))
				.getText();
		System.out.println(errorMessage);
		logger.log(LogStatus.PASS, "Error message popup shown");

		// Code below this line was happening only at home. there was popup message
		// shown at home but not being faced at office
		String errorMessage2 = driver.findElement(By.xpath("//*[contains(@class, 'unsavedChangesModal modal-footer')]"))
				.getText();
		System.out.println(errorMessage2);
		logger.log(LogStatus.PASS, "Text from modal footer fetched.");

		// This thread sleep is used to wait for the time till loader is shown on the
		// popup message.
		Thread.sleep(8000);

		// driver.findElement(By.xpath("//*[contains(text(),'Yes, discard
		// changes')]")).click();

		// Code below this line was happening only at home. there was popup message
		// shown at home but not being faced at office
		String yesbutton = driver.findElement(By.xpath("//*[contains(text(),'Yes')]")).getText();
		System.out.println(yesbutton);
		driver.findElement(By.xpath("//*[contains(text(),'Yes')]")).click();
		logger.log(LogStatus.PASS, "Yes is selected on popup to continue...");

		System.out.println("Yes is selected on popup to continue");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By
				.xpath("//*[contains(text(),'Type')]/following::div[3]//*[contains(@class,'dropdown-heading-value')]"))
				.click();
		List<WebElement> dd_menu = driver.findElements(By.xpath("//*[contains(@class, 'dropdown-content')]//li/label/div/span"));

		
		for(int i=0; i<dd_menu.size(); i++) 
		{ 
			WebElement element=dd_menu.get(i);
			String innerhtml=element.getAttribute("innerHTML");
		
			if(innerhtml.contains("Respite"))
				{
				element.click();
				break;
				}
		System.out.println("values from drop down ===========>"+innerhtml); 
		}
		 
		/*
		 * WebElement agreement_Type_DD = driver.findElement(By.xpath(
		 * "//*[contains(text(),'Type')]/following::div[3]//*[contains(@class,'dropdown-heading-value')]"
		 * )); Select agr_type_dd = new Select(agreement_Type_DD);
		 * agr_type_dd.selectByIndex(1);
		 */

		driver.findElement(By.xpath("//*[contains(text(),'Submit')]")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath(
				"//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]"))
				.click();
		driver.findElement(By.xpath(
				"//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]//*[contains(text(),'Edit')]"))
				.click();
		logger.log(LogStatus.PASS, "Register Tabe is selected");
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);
		Thread.sleep(15000);

		driver.findElement(By.id("step_7")).click();
		// driver.findElement(By.xpath("//li[@id='step_7']")).click();
		logger.log(LogStatus.PASS, "Step 7 is selected. ");
		Thread.sleep(3000);

		// driver.findElement(By.name("previewfinalizeagreement")).click();
		driver.findElement(By.xpath("//*[contains(text(),'Preview Agreement')]")).click();
		logger.log(LogStatus.PASS, "Preview document is clicked now...");
		Thread.sleep(45000);

		WebElement filePath = driver.findElement(By.id("download_btn_file"));
		String file = filePath.getAttribute("href");

		System.out.println("File path is: " + file);
		String fileName = file.substring(file.lastIndexOf('=') + 1, file.length());

		System.out.println("file name is:" + fileName);

		String filePth = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "filePath").get("Value").toString();

		// file name and file path both are concatenated
		String fileaddress = filePth + fileName;
		System.out.println("Now file path is appended with perfect file path: " + fileaddress);

		/*
		 * }
		 * 
		 * @Test public void VerifyTextPara() throws Exception {
		 */
		String paratext = PdfReader.verifyPdf(fileaddress);
		
		
		
	    //Test Case Number 49
		logger = report.startTest("Verify Part A");
		
		try{
		    //Assertion to be placed here
			String partA = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "partA").get("Value").toString();
			Assert.assertTrue(paratext.contains(partA), "Part A verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part A verification failed.");
		}
		report.endTest(logger);
		
		

		//Test Case Number 50
		logger = report.startTest("Verify Content of Part A");
		
		try{
		    //Assertion to be placed here
			String ContentPartA = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "ContentPartA").get("Value").toString();
			Assert.assertTrue(paratext.contains(ContentPartA), "Content of part A verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part A verification failed.");
		}
		report.endTest(logger);
		
		
		//Test Case Number 51
		logger = report.startTest("Verify Part B");
		
		try{
			String PartB = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "PartB").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartB), "Part B verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part B verification failed.");
		}
		report.endTest(logger);
		



		//Test Case Number 52
		logger = report.startTest("Verify Content of Part B");
		
		try{
			String ContentPartB = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "ContentPartB").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartB), "Part B verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part B verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 53
		logger = report.startTest("Verify Part C");
		
		try{
			String PartC = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "PartC").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartC), "Part C verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part C verification failed.");
		}
		report.endTest(logger);

		

		
		
		 //Test Case Number 54
		logger = report.startTest("Verify Content of Part C");
		
		try{
			String ContentPartC = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "ContentPartC").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartC), "Part C verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part C verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 55
		logger = report.startTest("Verify Part D");
		
		try{
			String PartD = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "PartD").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartD), "Part D verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part D verification failed.");
		}
		report.endTest(logger);
		
		

		 //Test Case Number 56
		logger = report.startTest("Verify Content of Part D");
		
		try{
			String ContentPartD = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "ContentPartD").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartD), "Part D verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part D verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 57
		logger = report.startTest("Verify Part E");
		
		try{
			String PartE = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "PartE").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartE), "Part E verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part E verification failed.");
		}
		report.endTest(logger);
		
		

		
		 //Test Case Number 58
		logger = report.startTest("Verify Content of Part E");
		
		try{
			String ContentPartE = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "ContentPartE").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartE), "Part E verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part E verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 59
		logger = report.startTest("Verify Part F");
		
		try{
			String PartF = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "PartF").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartF), "Part F verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part F verification failed.");
		}
		report.endTest(logger);
		

		
		
		
		 //Test Case Number 60
		logger = report.startTest("Verify Content of Part F");
		
		try{
			String ContentPartF = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "ContentPartF").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartF), "Part F verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part F verification failed.");
		}
		report.endTest(logger);
		
		
		
		

		
		
		
		 //Test Case Number 61	
		logger = report.startTest("Verify Part G");
		
		try{
			String PartG = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "PartG").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartG), "Part G verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part G verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 62	
		logger = report.startTest("Verify Content of Part G");
		
		try{
			String ContentPartG = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "ContentPartG").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartG), "Part G verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part G verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 63
		logger = report.startTest("Verify Part H");
		
		try{
			String PartH = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "PartH").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartH), "Part H verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part H verification failed.");
		}
		report.endTest(logger);




		
		 //Test Case Number 64
		logger = report.startTest("Verify Content of Part H");
		
		try{
			String ContentPartH = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "ContentPartH").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartH), "Part H verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part H verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 65
		logger = report.startTest("Verify Part I");
		
		try{
			String PartI = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "PartI").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartI), "Part I verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part I verification failed.");
		}
		report.endTest(logger);


		
		
		
		 //Test Case Number 66
		logger = report.startTest("Verify Content of Part I");
		
		try{
			String ContentPartI = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "ContentPartI").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartI), "Part I verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part I verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 67
		logger = report.startTest("Verify Part J");
		
		try{
			String PartJ = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "PartJ").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartJ), "Part J verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part J verification failed.");
		}
		report.endTest(logger);
		
		

		
		 //Test Case Number 68
		logger = report.startTest("Verify Content of Part J");
		
		try{
			String ContentPartJ = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "ContentPartJ").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartJ), "Part J verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part J verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 69
		logger = report.startTest("Verify Part K");
		
		try{
			String PartK = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "PartK").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartK), "Part K verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part K verification failed.");
		}
		report.endTest(logger);



		
		
		 //Test Case Number 70
		logger = report.startTest("Verify Content of Part K");
		
		try{
			String ContentPartK = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "ContentPartK").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartK), "Part K verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part K verification failed.");
		}
		report.endTest(logger);
		


		 //Test Case Number 71
		logger = report.startTest("Verify Part L");
		
		try{
			String PartL = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "PartL").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartL), "Part L verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part L verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 72
		logger = report.startTest("Verify Content of Part L");
		
		try{
			String ContentPartL = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteAgreement", "ContentPartL").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartL), "Part L verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part L verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		report.endTest(logger);
		//report.flush();
		//report.close();
		driver.quit();
		// }

		/*
		 * @Test public void endOfTest() {
		 * 
		 * 
		 * 
		 * 
		 * 
		 * }
		 */
	}
	@SuppressWarnings("deprecation")
	@Test
	public void TC004_VerifyRespiteExtraAgreementSections() throws Exception {

		//report = new ExtentReports("C:\\Users\\hp\\eclipse-workspace\\NeRACloud_Automation\\NeRA_Cloud_Report.html");
		logger = report.startTest("TC004_VerifyRespiteExtraAgreementSections");

		String WebUrl = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "URL").get("Value").toString();
		String browser = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "Browser").get("Value").toString();

		WebDriver driver = BrowserFactory.startBrowser(browser, WebUrl);
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);

		logger.log(LogStatus.PASS, "Browser started and cloud application accessed.");

		String username = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "UserName").get("Value").toString();
		String password = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "Password").get("Value").toString();
		// Provides email into email field.
		driver.findElement(By.id("email")).sendKeys(username);
		// Click next button to proceed with provided email address.
		driver.findElement(By.xpath("//*[contains(text(),'Next')]")).click();
		// Provides password in the password field.
		driver.findElement(By.id("loginpassword")).sendKeys(password);
		// Click login button to login
		driver.findElement(By.xpath("//*[contains(@class, 'btn-login btn btn-secondary')]")).click();

		logger.log(LogStatus.PASS, "Loging was successful.");

		Thread.sleep(5000);
		// Below lines were working fine on chrome
		WebElement neraLogo = driver.findElement(By.id("NeRAAppLogo"));
		Assert.assertTrue(neraLogo.isDisplayed(), "AWS Cognito Identity user loging failed with username: " + username);
		// driver.findElement(By.xpath("//*[contains(text(),'National e-Tools
		// Residential Agreement')]")).click();

		logger.log(LogStatus.PASS, "Home page is loaded successfully.");

		Thread.sleep(3000);

		// driver.findElement(By.xpath("//*[contains(@class, 'btn
		// btn-secondary')]")).click();
		// driver.findElement(By.xpath("//img[@src='./static/media/DropdownMenuLogout.c00a4b7c.svg']")).click();

		// driver.switchTo().activeElement();
		// driver.findElement(By.xpath("//*[contains(@class,'btn btn-primary') and
		// contains(text(),'Yes')]")).click();

		// logger.log(LogStatus.INFO, "Logging out from the application.");
		// logger.log(LogStatus.PASS, "Log out from the application.");

		driver.findElement(By.id("NeRAAppLogo")).click();
		logger.log(LogStatus.PASS, "NeRA cloud application selected.");

		driver.findElement(By.xpath("//*[contains(text(),'Register')]")).click();
		logger.log(LogStatus.PASS, "Register Tabe is selected");
		

		// Code below this line was happening only at home. there was popup message
		// shown at home but not being faced at office
		Thread.sleep(3000);
		driver.switchTo().activeElement();

		// driver.switchTo().frame("Invalid");
		String errorMessage = driver.findElement(By.xpath(
				"//*[contains(text(),'If you have made any changes that are not saved yet, all the changes will be lost. Are you sure you want to continue?')]"))
				.getText();
		System.out.println(errorMessage);
		logger.log(LogStatus.PASS, "Error message popup shown");

		// Code below this line was happening only at home. there was popup message
		// shown at home but not being faced at office
		String errorMessage2 = driver.findElement(By.xpath("//*[contains(@class, 'unsavedChangesModal modal-footer')]"))
				.getText();
		System.out.println(errorMessage2);
		logger.log(LogStatus.PASS, "Text from modal footer fetched.");

		// This thread sleep is used to wait for the time till loader is shown on the
		// popup message.
		Thread.sleep(8000);

		// driver.findElement(By.xpath("//*[contains(text(),'Yes, discard
		// changes')]")).click();

		// Code below this line was happening only at home. there was popup message
		// shown at home but not being faced at office
		String yesbutton = driver.findElement(By.xpath("//*[contains(text(),'Yes')]")).getText();
		System.out.println(yesbutton);
		driver.findElement(By.xpath("//*[contains(text(),'Yes')]")).click();
		logger.log(LogStatus.PASS, "Yes is selected on popup to continue...");

		System.out.println("Yes is selected on popup to continue");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By
				.xpath("//*[contains(text(),'Type')]/following::div[3]//*[contains(@class,'dropdown-heading-value')]"))
				.click();
		List<WebElement> dd_menu = driver.findElements(By.xpath("//*[contains(@class, 'dropdown-content')]//li/label/div/span"));

		
		for(int i=0; i<dd_menu.size(); i++) 
		{ 
			WebElement element=dd_menu.get(i);
			String innerhtml=element.getAttribute("innerHTML");
		
			if(innerhtml.contains("Respite / Extra Service"))
				{
				element.click();
				break;
				}
		System.out.println("values from drop down ===========>"+innerhtml); 
		}
		 
		/*
		 * WebElement agreement_Type_DD = driver.findElement(By.xpath(
		 * "//*[contains(text(),'Type')]/following::div[3]//*[contains(@class,'dropdown-heading-value')]"
		 * )); Select agr_type_dd = new Select(agreement_Type_DD);
		 * agr_type_dd.selectByIndex(1);
		 */

		driver.findElement(By.xpath("//*[contains(text(),'Submit')]")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath(
				"//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]"))
				.click();
		driver.findElement(By.xpath(
				"//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]//*[contains(text(),'Edit')]"))
				.click();
		logger.log(LogStatus.PASS, "Register Tabe is selected");
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);
		Thread.sleep(15000);

		driver.findElement(By.id("step_7")).click();
		// driver.findElement(By.xpath("//li[@id='step_7']")).click();
		logger.log(LogStatus.PASS, "Step 7 is selected. ");
		Thread.sleep(3000);

		// driver.findElement(By.name("previewfinalizeagreement")).click();
		driver.findElement(By.xpath("//*[contains(text(),'Preview Agreement')]")).click();
		logger.log(LogStatus.PASS, "Preview document is clicked now...");
		Thread.sleep(45000);

		WebElement filePath = driver.findElement(By.id("download_btn_file"));
		String file = filePath.getAttribute("href");

		System.out.println("File path is: " + file);
		String fileName = file.substring(file.lastIndexOf('=') + 1, file.length());

		System.out.println("file name is:" + fileName);

		String filePth = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "filePath").get("Value").toString();

		// file name and file path both are concatenated
		String fileaddress = filePth + fileName;
		System.out.println("Now file path is appended with perfect file path: " + fileaddress);

		/*
		 * }
		 * 
		 * @Test public void VerifyTextPara() throws Exception {
		 */
		String paratext = PdfReader.verifyPdf(fileaddress);

		
	    //Test Case Number 73
		logger = report.startTest("Verify Part A");
		
		try{
		    //Assertion to be placed here
			String partA = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "partA").get("Value").toString();
			Assert.assertTrue(paratext.contains(partA), "Part A verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part A verification failed.");
		}
		report.endTest(logger);
		
		

		//Test Case Number 74
		logger = report.startTest("Verify Content of Part A");
		
		try{
		    //Assertion to be placed here
			String ContentPartA = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "ContentPartA").get("Value").toString();
			Assert.assertTrue(paratext.contains(ContentPartA), "Content of part A verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part A verification failed.");
		}
		report.endTest(logger);
		
		
		//Test Case Number 75
		logger = report.startTest("Verify Part B");
		
		try{
			String PartB = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "PartB").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartB), "Part B verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part B verification failed.");
		}
		report.endTest(logger);
		



		//Test Case Number 76
		logger = report.startTest("Verify Content of Part B");
		
		try{
			String ContentPartB = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "ContentPartB").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartB), "Part B verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part B verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 77
		logger = report.startTest("Verify Part C");
		
		try{
			String PartC = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "PartC").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartC), "Part C verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part C verification failed.");
		}
		report.endTest(logger);

		

		
		
		 //Test Case Number 78
		logger = report.startTest("Verify Content of Part C");
		
		try{
			String ContentPartC = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "ContentPartC").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartC), "Part C verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part C verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 79
		logger = report.startTest("Verify Part D");
		
		try{
			String PartD = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "PartD").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartD), "Part D verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part D verification failed.");
		}
		report.endTest(logger);
		
		

		 //Test Case Number 80
		logger = report.startTest("Verify Content of Part D");
		
		try{
			String ContentPartD = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "ContentPartD").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartD), "Part D verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part D verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 81
		logger = report.startTest("Verify Part E");
		
		try{
			String PartE = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "PartE").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartE), "Part E verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part E verification failed.");
		}
		report.endTest(logger);
		
		

		
		 //Test Case Number 82
		logger = report.startTest("Verify Content of Part E");
		
		try{
			String ContentPartE = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "ContentPartE").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartE), "Part E verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part E verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 83
		logger = report.startTest("Verify Part F");
		
		try{
			String PartF = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "PartF").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartF), "Part F verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part F verification failed.");
		}
		report.endTest(logger);
		

		
		
		
		 //Test Case Number 84
		logger = report.startTest("Verify Content of Part F");
		
		try{
			String ContentPartF = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "ContentPartF").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartF), "Part F verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part F verification failed.");
		}
		report.endTest(logger);
		
		
		
		

		
		
		
		 //Test Case Number 85	
		logger = report.startTest("Verify Part G");
		
		try{
			String PartG = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "PartG").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartG), "Part G verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part G verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 86	
		logger = report.startTest("Verify Content of Part G");
		
		try{
			String ContentPartG = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "ContentPartG").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartG), "Part G verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part G verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 87
		logger = report.startTest("Verify Part H");
		
		try{
			String PartH = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "PartH").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartH), "Part H verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part H verification failed.");
		}
		report.endTest(logger);




		
		 //Test Case Number 88
		logger = report.startTest("Verify Content of Part H");
		
		try{
			String ContentPartH = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "ContentPartH").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartH), "Part H verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part H verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 89
		logger = report.startTest("Verify Part I");
		
		try{
			String PartI = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "PartI").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartI), "Part I verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part I verification failed.");
		}
		report.endTest(logger);


		
		
		
		 //Test Case Number 90
		logger = report.startTest("Verify Content of Part I");
		
		try{
			String ContentPartI = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "ContentPartI").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartI), "Part I verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part I verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 91
		logger = report.startTest("Verify Part J");
		
		try{
			String PartJ = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "PartJ").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartJ), "Part J verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part J verification failed.");
		}
		report.endTest(logger);
		
		

		
		 //Test Case Number 92
		logger = report.startTest("Verify Content of Part J");
		
		try{
			String ContentPartJ = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "ContentPartJ").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartJ), "Part J verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part J verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 93
		logger = report.startTest("Verify Part K");
		
		try{
			String PartK = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "PartK").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartK), "Part K verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part K verification failed.");
		}
		report.endTest(logger);



		
		
		 //Test Case Number 94
		logger = report.startTest("Verify Content of Part K");
		
		try{
			String ContentPartK = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "ContentPartK").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartK), "Part K verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part K verification failed.");
		}
		report.endTest(logger);
		


		 //Test Case Number 95
		logger = report.startTest("Verify Part L");
		
		try{
			String PartL = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "PartL").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(PartL), "Part L verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Part L verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		 //Test Case Number 96
		logger = report.startTest("Verify Content of Part L");
		
		try{
			String ContentPartL = ExcelDriven_XLSX.readExcelData("Testdata", "RespiteExtAgreement", "ContentPartL").get("Value").toString();
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains(ContentPartL), "Part L verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Content of part L verification failed.");
		}
		report.endTest(logger);
		
		
		
		
		
		
	
		
		report.endTest(logger);
		report.flush();
		//report.close();
		driver.quit();
		// }

		/*
		 * @Test public void endOfTest() {
		 * 
		 * 
		 * 
		 * 
		 * 
		 * }
		 */
	}
}

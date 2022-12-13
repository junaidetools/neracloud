package com.neracloud.testcases;

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

public class VerifyDocument {

	ExtentReports report;
	ExtentTest logger;
	WebDriver driver;
	String pdfUrl;



	@SuppressWarnings("deprecation")
	@Test
	public void TC001_VerifyPermanentAgreementSections() throws Exception {

		report = new ExtentReports("C:\\Users\\hp\\eclipse-workspace\\NeRACloud_Automation\\NeRA_Cloud_Report.html");
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

		System.out.println("YEs is selected on popup to continue");

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
		
		
	    //Test Case Number 1
		logger = report.startTest("Verify Part A");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part A – Charter of Aged Care Rights"), "Part A verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		 //Test Case Number 2
		logger = report.startTest("Verify Part B");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part B – Care and Accommodation"), "Part B verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		



		 //Test Case Number 3
		logger = report.startTest("Verify Part C");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part C – Fees and Payments"), "Part C verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);

		

		 //Test Case Number 4
		logger = report.startTest("Verify Part D");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part D – Rights and Responsibilities – General Conditions of Occupation"), "Part D verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		


		 //Test Case Number 5
		logger = report.startTest("Verify Part E");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part E – Rules of Occupancy"), "Part E verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		

		 //Test Case Number 6
		logger = report.startTest("Verify Part F");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part F – Code of Conduct"), "Part F verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		


		
		 //Test Case Number 7	
		logger = report.startTest("Verify Part G");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part G – Standard Care and Services under the Aged Care Act"), "Part G verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		 //Test Case Number 8
		logger = report.startTest("Verify Part H");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part H – Additional Services"), "Part H verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);




		 //Test Case Number 9
		logger = report.startTest("Verify Part I");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part I – Extra Conditions"), "Part I verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);


		
		 //Test Case Number 10
		logger = report.startTest("Verify Part J");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part J - Definitions"), "Part J verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		 //Test Case Number 11
		logger = report.startTest("Verify Part K");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part K – Permanent Annexure zohaib"), "Part K verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);





		 //Test Case Number 12
		logger = report.startTest("Verify Part L");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part L"), "Part L verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
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

		System.out.println("YEs is selected on popup to continue");

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

		
		//Test Case Number 13
		logger = report.startTest("Verify Part A");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part A – Charter of Aged Care Rights"), "Part A verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		//Test Case Number 14
		logger = report.startTest("Verify Part B");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part B – Care and Accommodation"), "Part B verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		

		
		
		
		//Test Case Number 15
		logger = report.startTest("Verify Part C");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part C – Fees and Payments"), "Part C verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);

		
		
		
		
		
	
		//Test Case Number 16
		logger = report.startTest("Verify Part D");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part D – Rights and Responsibilities – General Conditions of Occupation"), "Part D verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		


		//Test Case Number 17
		logger = report.startTest("Verify Part E");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part E – Extra Services"), "Part E verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		//Test Case Number 18
		logger = report.startTest("Verify Part F");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part F – Rules of Occupancy"), "Part F verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);



		
		//Test Case Number 19
		logger = report.startTest("Verify Part G");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part G – Code of Conduct"), "Part G verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);

		
		
		
		
		
		//Test Case Number 20
		logger = report.startTest("Verify Part H");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part H – Standard Care and Services under the Aged Care Act"), "Part H verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		//Test Case Number 21
		logger = report.startTest("Verify Part I");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part I – Additional Services"), "Part I verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);


		
		//Test Case Number 22
		logger = report.startTest("Verify Part J");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part J – Extra Conditions"), "Part J verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		


		//Test Case Number 23
		logger = report.startTest("Verify Part K");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part K - Definitions"), "Part K verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		//Test Case Number 23
		logger = report.startTest("Verify Part L");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part L"), "Part L verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
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

		System.out.println("YEs is selected on popup to continue");

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
		
		
		
		
		//Test Case Number 24
		logger = report.startTest("Verify Part A");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part A – Charter of Aged Care Rights"), "Part A verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);

		
		
		
		
		//Test Case Number 25
		logger = report.startTest("Verify Part B");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part B – Care and Services"), "Part B verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		
		//Test Case Number 26
		logger = report.startTest("Verify Part C");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part C – Fees and Payments"), "Part C verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);




		
		
		//Test Case Number 27
		logger = report.startTest("Verify Part D");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part D – Rights and Responsibilities – General Conditions of Occupation"), "Part D verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		
		
		//Test Case Number 28
		logger = report.startTest("Verify Part E");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part E – Rules of Occupancy"), "Part E verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		


		
		//Test Case Number 29
		logger = report.startTest("Verify Part F");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part F – Code of Conduct"), "Part F verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		//Test Case Number 30
		logger = report.startTest("Verify Part G");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part G – Standard Care and Services under the Aged Care Act"), "Part G verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		


		
		//Test Case Number 31
		logger = report.startTest("Verify Part H");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part H – Additional Services"), "Part H verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		//Test Case Number 32
		logger = report.startTest("Verify Part I");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part I – Extra Conditions"), "Part I verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		//Test Case Number 33
		logger = report.startTest("Verify Part J");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part J - Definitions"), "Part J verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		


		
		//Test Case Number 33
		logger = report.startTest("Verify Part J");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part J - Definitions"), "Part J verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		//Test Case Number 33
		logger = report.startTest("Verify Part K");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part K – Respite Annexure"), "Part K verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
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

		System.out.println("YEs is selected on popup to continue");

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

		
		
		
		//Test Case Number 34
		logger = report.startTest("Verify Part A");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part A – Charter of Aged Care Rights"), "Part A verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		
		//Test Case Number 35
		logger = report.startTest("Verify Part B");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part B – Care and Services"), "Part B verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		//Test Case Number 36
		logger = report.startTest("Verify Part C");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part C – Fees and Payments"), "Part C verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		


		
		//Test Case Number 37
		logger = report.startTest("Verify Part D");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part D – Rights and Responsibilities – General Conditions of Occupation"), "Part D verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		
		//Test Case Number 37
		logger = report.startTest("Verify Part E");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part E – Extra Services"), "Part E verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		
		//Test Case Number 38
		logger = report.startTest("Verify Part F");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part F – Rules of Occupancy"), "Part F verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		

		//Test Case Number 38
		logger = report.startTest("Verify Part G");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part G – Code of Conduct"), "Part G verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		//Test Case Number 39
		logger = report.startTest("Verify Part H");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part H – Standard Care and Services under the Aged Care Act"), "Part H verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		
		//Test Case Number 40
		logger = report.startTest("Verify Part I");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part I – Additional Services"), "Part I verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		



		//Test Case Number 41
		logger = report.startTest("Verify Part J");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part J – Extra Conditions"), "Part J verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);
		
		
		
		//Test Case Number 42
		logger = report.startTest("Verify Part K");
		
		try{
		    //Assertion to be placed here
			Assert.assertTrue(paratext.contains("Part K – Definitions"), "Part K verification failed.");
		    logger.log(LogStatus.PASS, "Pass");
		} catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail");
		}
		report.endTest(logger);	
		
		report.endTest(logger);
		report.flush();
		report.close();
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

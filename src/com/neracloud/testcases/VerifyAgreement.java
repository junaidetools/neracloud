package com.neracloud.testcases;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utls.BrowserFactory;
import Utls.ExcelDriven_XLSX;
import Utls.PdfReader;

public class VerifyAgreement {
	

	
	
	ExtentReports report;
	ExtentTest logger; 
	WebDriver driver; //=new ChromeDriver();
	String pdfUrl;
	
	private SoftAssert softAssert = new SoftAssert();
	
	@SuppressWarnings("deprecation")
	@Test(priority=1)
	public void TC001_LoginToGateway() throws Exception
	{		
		
		report=new ExtentReports("C:\\Users\\hp\\eclipse-workspace\\NeRACloud_Automation\\NeRA_Cloud_Report.html"); 
		logger=report.startTest("Agreement verification test cases.");
		
		String WebUrl = ExcelDriven_XLSX.readExcelData("Testdata","Config","URL").get("Value").toString();
		String browser = ExcelDriven_XLSX.readExcelData("Testdata","Config","Browser").get("Value").toString();
		
		WebDriver driver = BrowserFactory.startBrowser(browser, WebUrl);
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);
		
		logger.log(LogStatus.PASS, "Browser started and cloud application accessed.");
		
		String username = ExcelDriven_XLSX.readExcelData("Testdata","Config","UserName").get("Value").toString();
		String password = ExcelDriven_XLSX.readExcelData("Testdata","Config","Password").get("Value").toString();
		//Provides email into email field.
		driver.findElement(By.id("email")).sendKeys(username);
		//Click next button to proceed with provided email address.
		driver.findElement(By.xpath("//*[contains(text(),'Next')]")).click();
		//Provides password in the password field.
		driver.findElement(By.id("loginpassword")).sendKeys(password);
		//Click login button to login
		driver.findElement(By.xpath("//*[contains(@class, 'btn-login btn btn-secondary')]")).click();
		
		logger.log(LogStatus.PASS, "Loging was successful.");

	}
	
	@SuppressWarnings("deprecation")
	@Test(priority=2)
	public void TC002_AccessNeRAApplicationFromGateway() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);
		Thread.sleep(5000);
		//Below lines were working fine on chrome	
		WebElement neraLogo = driver.findElement(By.id("NeRAAppLogo"));
		Assert.assertTrue(neraLogo.isDisplayed(), "User loging failed.");
		//driver.findElement(By.xpath("//*[contains(text(),'National e-Tools Residential Agreement')]")).click();
		
		logger.log(LogStatus.PASS, "Home page is loaded successfully.");
		
		Thread.sleep(3000);
			
		//driver.findElement(By.xpath("//*[contains(@class, 'btn btn-secondary')]")).click();
		//driver.findElement(By.xpath("//img[@src='./static/media/DropdownMenuLogout.c00a4b7c.svg']")).click();
			
		//driver.switchTo().activeElement();
		//driver.findElement(By.xpath("//*[contains(@class,'btn btn-primary') and contains(text(),'Yes')]")).click();
			
		//logger.log(LogStatus.INFO, "Logging out from the application.");
		//logger.log(LogStatus.PASS, "Log out from the application.");
		
		driver.findElement(By.id("NeRAAppLogo")).click();
		logger.log(LogStatus.PASS, "NeRA cloud application selected.");
	}
	@SuppressWarnings("deprecation")
	@Test(priority=3)
	public void TC003_ClickOnRegisterTabToAccessClientDetailsPage() throws InterruptedException 
	{
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[contains(text(),'Register')]")).click(); 
		logger.log(LogStatus.INFO, "Register Tabe is selected"); 
		
		//Code below this line was happening only at home. there was popup message shown at home but not being faced at office
		Thread.sleep(3000);
		driver.switchTo().activeElement();
		
		//driver.switchTo().frame("Invalid");
		String errorMessage = driver.findElement(By.xpath("//*[contains(text(),'If you have made any changes that are not saved yet, all the changes will be lost. Are you sure you want to continue?')]")).getText();
		System.out.println(errorMessage);
		logger.log(LogStatus.INFO, "Error message popup shown"); 
		
		//Code below this line was happening only at home. there was popup message shown at home but not being faced at office
		String errorMessage2 = driver.findElement(By.xpath("//*[contains(@class, 'unsavedChangesModal modal-footer')]")).getText();
		System.out.println(errorMessage2);
		logger.log(LogStatus.INFO, "Text from modal footer fetched.");
		
		
		//This thread sleep is used to wait for the time till loader is shown on the popup message. 
		Thread.sleep(5000);
		
		
		
		//driver.findElement(By.xpath("//*[contains(text(),'Yes, discard changes')]")).click();
		
		//Code below this line was happening only at home. there was popup message shown at home but not being faced at office
		String yesbutton = driver.findElement(By.xpath("//*[contains(text(),'Yes')]")).getText();
		System.out.println(yesbutton);
		driver.findElement(By.xpath("//*[contains(text(),'Yes')]")).click();
		logger.log(LogStatus.INFO, "Yes is selected on popup to continue..."); 
		
	}
	@SuppressWarnings("deprecation")
	@Test(priority=4)
	public void TC004_ClickEditButtonToEditTheClientDetails() throws InterruptedException 
	{
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]")).click();
		driver.findElement(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]//*[contains(text(),'Edit')]")).click();
		logger.log(LogStatus.INFO, "Register Tabe is selected"); 
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);
		Thread.sleep(8000);
		
	}
	@SuppressWarnings("deprecation")
	@Test(priority=5)
	public void TC005_NavigateToStep7ToPreviewAgreement() throws InterruptedException 
	{
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);
		driver.findElement(By.id("step_7")).click();
		//driver.findElement(By.xpath("//li[@id='step_7']")).click();
		logger.log(LogStatus.PASS, "Step 7 is selected. ");
		Thread.sleep(2000);
	}
	@SuppressWarnings("deprecation")
	@Test(priority=6)
	public void TC006_ClickPreviewAgreementAndVerifyAgreementIsGenerated() throws Exception
	{
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);
		//driver.findElement(By.name("previewfinalizeagreement")).click();
		driver.findElement(By.xpath("//*[contains(text(),'Preview Agreement')]")).click();
		logger.log(LogStatus.INFO, "Preview document is clicked now...");
		Thread.sleep(30000);
		
		
		WebElement filePath = driver.findElement(By.id("download_btn_file"));
		String file= filePath.getAttribute("href");
		
		System.out.println("File path is: "+file);
		String fileName = file.substring(file.lastIndexOf('=')+1, file.length());
		
		System.out.println("file name is:" + fileName);
		
		String filePth = ExcelDriven_XLSX.readExcelData("Testdata","Config","filePath").get("Value").toString();
		
		//file name and file path both are concatenated  
		String fileaddress = filePth+fileName;
		System.out.println("Now file path is appended with perfect file path: "+fileaddress);
 

		/*
		 * }
		 * 
		 * @Test public void VerifyTextPara() throws Exception {
		 */
	    	 String paratext = PdfReader.verifyPdf(fileaddress);
	    	 
	    	 softAssert.assertTrue(paratext.contains("Part A – Charter of Aged Care Rights"), "Part A verification failed.");
	    	 System.out.println("Para A have been verified.");
	    	 logger.log(LogStatus.PASS, "Para A have been verified...."); 
	    	 
	    	 softAssert.assertTrue(paratext.contains("Part B – Care and Services"), "Part B verification failed.");
	    	 System.out.println("Para B have been verified.");
	    	 logger.log(LogStatus.PASS, "Para B have been verified...."); 
	    	 
	    	 softAssert.assertTrue(paratext.contains("Part C – Fees and Payments"), "Part C verification failed.");
	    	 System.out.println("Para C have been verified.");
	    	 logger.log(LogStatus.PASS, "Para C have been verified...."); 
	    	 
	    	 softAssert.assertTrue(paratext.contains("Part D – Rights and Responsibilities – General Conditions of Occupation"), "Part D verification failed.");
	    	 System.out.println("Para D have been verified.");
	    	 logger.log(LogStatus.PASS, "Para D have been verified...."); 
	    	 
	    	 softAssert.assertTrue(paratext.contains("Part E – Rules of Occupancy"), "Part E verification failed.");
	    	 System.out.println("Para E have been verified.");
	    	 logger.log(LogStatus.PASS, "Para E have been verified...."); 
	    	 
	    	 softAssert.assertTrue(paratext.contains("Part F – Code of Conduct"), "Part F verification failed.");
	    	 System.out.println("Para F have been verified.");
	    	 logger.log(LogStatus.PASS, "Para F have been verified...."); 
	    	 
	    	 softAssert.assertTrue(paratext.contains("Part G – Standard Care and Services under the Aged Care Act"), "Part G verification failed.");
	    	 System.out.println("Para G have been verified.");
	    	 logger.log(LogStatus.PASS, "Para G have been verified...."); 
	    	 
	    	 softAssert.assertTrue(paratext.contains("Part H – Additional Services"), "Part H verification failed.");
	    	 System.out.println("Para H have been verified.");
	    	 logger.log(LogStatus.PASS, "Para H have been verified.");
	    	 
	    	 softAssert.assertTrue(paratext.contains("Part I – Extra Conditions"), "Part I verification failed.");
	    	 System.out.println("Para I have been verified.");
	    	 logger.log(LogStatus.PASS, "Para I have been verified.");
	    	 
	    	 softAssert.assertTrue(paratext.contains("Part J - Definitions"), "Part J verification failed.");
	    	 System.out.println("Para J have been verified.");
	    	 logger.log(LogStatus.PASS, "Para J have been verified.");
	    	 
	    	 softAssert.assertTrue(paratext.contains("Part K – Respite Annexure"), "Part K verification failed.");
	    	 System.out.println("Para K have been verified.");
	    	 logger.log(LogStatus.PASS, "Para K have been verified.");
	    	 
	    	 softAssert.assertTrue(paratext.contains("Part L"), "Part L verification failed.");
	    	 System.out.println("Para L have been verified.");
	    	 logger.log(LogStatus.PASS, "Para L have been verified.");
	    	 
	    	 softAssert.assertAll();
	     
	    		report.endTest(logger); 
	    		report.flush();
	    		driver.quit();
	     //}
		
		
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

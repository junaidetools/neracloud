package com.neracloud.testcases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.neracloud.pages.loginPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utls.BrowserFactory;
import Utls.ExcelDriven_XLSX;

import Utls.snap_shot;

public class VerifyUserCreation {

	ExtentReports report;
	ExtentTest logger;
	WebDriver driver;
	String pdfUrl;

	/*
	 * @BeforeTest public void startReport() { }
	 */
	
	


	@SuppressWarnings("deprecation")
	@BeforeMethod
	public void setup() throws InterruptedException, IOException 
	{
		File currentDir = new File (".");
		String basePath = currentDir.getCanonicalPath();
		report = new ExtentReports(basePath+"\\NeRA_Cloud_User_Creation_Report.html");
		logger = report.startTest("VerifyLoginToE-ToolsCloudApplication");
		
		
		String WebUrl = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "URL").get("Value").toString();
		String browser = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "Browser").get("Value").toString();

		driver  = BrowserFactory.startBrowser(browser, WebUrl);
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);

		logger.log(LogStatus.PASS, "Browser started and cloud application is accessed.");

		String username = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "UserName").get("Value").toString();
		String password = ExcelDriven_XLSX.readExcelData("Testdata", "Config", "Password").get("Value").toString();
		
		
		loginPage login_page = PageFactory.initElements(driver, loginPage.class);
		
		login_page.login_nera(username, password);
		
		// Provides email into email field.
		//driver.findElement(By.id("email")).sendKeys(username);
		// Click next button to proceed with provided email address.
		//driver.findElement(By.xpath("//*[contains(text(),'Next')]")).click();
		// Provides password in the password field.
		//driver.findElement(By.id("loginpassword")).sendKeys(password);
		// Click login button to login
		//driver.findElement(By.xpath("//*[contains(@class, 'btn-login btn btn-secondary')]")).click();

		//logger.log(LogStatus.PASS, "Loging was successful.");

		//Thread.sleep(5000);

		//WebElement neraLogo = driver.findElement(By.id("NeRAAppLogo"));
		
		
		//Assert.assertTrue(neraLogo.isDisplayed(), "AWS Cognito Identity user loging failed with username: " + username);

		//logger.log(LogStatus.PASS, "Home page is loaded successfully.");

		//Thread.sleep(3000);

		//driver.findElement(By.id("NeRAAppLogo")).click();
		logger.log(LogStatus.PASS, "NeRA cloud application accessed successfully.");
		
		
		report.endTest(logger);
		
		
	}
	
	
	
	@Test(priority=1)
	public void TC001_VerifyInquiryCreationOfTypePermanent() throws Exception {

		
		logger = report.startTest("TC001_VerifyInquiryCreationOfTypePermanent");

		driver.findElement(By.xpath("//*[contains(text(),'Register')]")).click();
		logger.log(LogStatus.PASS, "Register Tabe is selected");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(360));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		//Thread.sleep(3000);
		driver.switchTo().activeElement();

		//Thread.sleep(10000);
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'If you have made any changes that are not saved yet, all the changes will be lost. Are you sure you want to continue?')]")));
		String errorMessage = driver.findElement(By.xpath("//*[contains(text(),'If you have made any changes that are not saved yet, all the changes will be lost. Are you sure you want to continue?')]"))
				.getText();
		System.out.println(errorMessage);
		logger.log(LogStatus.PASS, "Error message popup shown");

		// String errorMessage2 = driver.findElement(By.xpath("//*[contains(@class,
		// 'unsavedChangesModal modal-footer')]")).getText();
		// System.out.println(errorMessage2);
		//logger.log(LogStatus.PASS, "Text from modal footer fetched.");
		//System.out.println(errorMessage);
		//Thread.sleep(15000);
			
		
		String yesbutton = driver.findElement(By.xpath("//*[contains(text(),'Yes')]")).getText();
		System.out.println(yesbutton + ": This text from Yes button is fetched...");

		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		

		
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Yes')]")));
		element1.click();
		
		
		//driver.findElement(By.xpath("//*[contains(text(),'Yes')]")).click();
		logger.log(LogStatus.PASS, "Yes is selected on popup to continue...");
		System.out.println("Yes is selected on popup to continue...");
		
		
		
		driver.findElement(By.xpath("//*[contains(text(),'Add Enquiry')]")).click();

		
		driver.findElement(By.xpath("//*[contains(text(),'Edit')]")).click();
		
		//Thread.sleep(3000);
		String title = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "title").get("Value").toString();
		List<WebElement> title_menu = driver.findElements(By.xpath("//*[contains(@name, 'ResidentTitleID')]/*[@value]"));
		for (int i = 0; i < title_menu.size(); i++) {
			WebElement element = title_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(title)) {
				element.click();
				break;
			}
			System.out.println("values from Title drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "Title selected.");
		

		String firstNam = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "firstNam").get("Value")
				.toString();
		driver.findElement(By.id("ResidentFirstName")).sendKeys(firstNam);
		logger.log(LogStatus.PASS, "First Name provided.");
		
		
		// Fill middle name
		String middleName = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "middleName").get("Value").toString();
		driver.findElement(By.name("ResidentMiddleName")).sendKeys(middleName);
		logger.log(LogStatus.PASS, "Middle Name provided.");

		String lastName = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "lastName").get("Value").toString();
		driver.findElement(By.name("ResidentLastName")).sendKeys(lastName);
		logger.log(LogStatus.PASS, "Last Name provided.");

		String AddLine1 = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "AddLine1").get("Value").toString();
		driver.findElement(By.name("ResidentAddress1")).sendKeys(AddLine1);
		logger.log(LogStatus.PASS, "Address line 1 provided.");
		
		String AddLine2 = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "AddLine2").get("Value").toString();
		driver.findElement(By.name("ResidentAddress2")).sendKeys(AddLine2);
		logger.log(LogStatus.PASS, "Address line 2 provided.");

		
		System.out.println("Title, First name, Middle Name, Last name and addresses have been entered.");

		String setSuburb = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "setSuburb").get("Value")
				.toString();
		WebElement wb = driver.findElement(By.id("ResidentSuburb"));
		Actions action = new Actions(driver);
		action.moveToElement(wb).click().moveToElement(wb, 200, 0).sendKeys(setSuburb, Keys.ENTER).build().perform(); // .build().perform();
		// System.out.println("Here we need to press enter button");
		//Thread.sleep(1000);
		logger.log(LogStatus.PASS, "Suburb Selected.");

		String image_path = snap_shot.takeSnapShot(driver);
		System.out.println(image_path);
		// report.addScreenCaptureFromPath(image_path);
		String img = logger.addScreenCapture(image_path);
		logger.log(LogStatus.PASS, "verified", img);
		
		System.out.println("Suburb selected.");
		
		
		//Thread.sleep(10000);
		String phone = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "phone").get("Value").toString();
		driver.findElement(By.id("ResidentPhone")).sendKeys(phone);
		logger.log(LogStatus.PASS, "Phone number provided.");
		
		String mobile = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "mobile").get("Value").toString();
		driver.findElement(By.name("ResidentMobile")).sendKeys(mobile);
		logger.log(LogStatus.PASS, "Mobile number provided.");

		String email = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "email").get("Value").toString();
		driver.findElement(By.name("ResidentEmail")).sendKeys(email);
		logger.log(LogStatus.PASS, "Email number provided.");

		//Thread.sleep(10000);
		String residentId = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "residentId").get("Value").toString();
		driver.findElement(By.name("ResidentID")).sendKeys(residentId);
		logger.log(LogStatus.PASS, "Resident Id provided.");
		
		driver.findElement(By.name("ResidentDateofBirth")).click();
		// Fill date as mm/dd/yyyy as 09/25/2013
		String setDOB = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "setDOB").get("Value").toString();
		driver.findElement(By.name("ResidentDateofBirth")).sendKeys(setDOB);
		logger.log(LogStatus.PASS, "Date of Birth provided.");
		
		
		//Thread.sleep(10000);
		String gender = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "gender").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ResidentGenderType')]/*[@id]")).click();
		List<WebElement> gender_menu = driver.findElements(By.xpath("//*[contains(@name, 'ResidentGenderType')]/*[@id]"));
		for (int i = 0; i < gender_menu.size(); i++) {
			WebElement element = gender_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(gender)) {
				element.click();
				break;
			}
			System.out.println("values from gender drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "Gender Selected.");
		
		
		String maritalStatus = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "maritalStatus").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ResidentMaritalStatus')]/*[@id]")).click();
		List<WebElement> maritalstatus_menu = driver.findElements(By.xpath("//*[contains(@name, 'ResidentMaritalStatus')]/*[@id]"));
		for (int i = 0; i < maritalstatus_menu.size(); i++) {
			WebElement element = maritalstatus_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(maritalStatus)) {
				element.click();
				break;
			}
			System.out.println("values from Marital status drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "Marital status selected.");
		
		String preferredLanguage = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "preferredLanguage").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ResidentprimaryLang')]/*[@id]")).click();
		List<WebElement> pref_lang_menu = driver.findElements(By.xpath("//*[contains(@name, 'ResidentprimaryLang')]/*[@id]"));
		for (int i = 0; i < pref_lang_menu.size(); i++) {
			WebElement element = pref_lang_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(preferredLanguage)) {
				element.click();
				break;
			}
			System.out.println("values from Preferred Language drop down ===========>" + innerhtml);
		}
		
		logger.log(LogStatus.PASS, "Preferred Language selected.");
		
		
		String ethnicity = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "ethnicity").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ResidentEthnicity')]/*[@id]")).click();
		List<WebElement> Ethnicity_menu = driver.findElements(By.xpath("//*[contains(@name, 'ResidentEthnicity')]/*[@id]"));
		for (int i = 0; i < Ethnicity_menu.size(); i++) {
			WebElement element = Ethnicity_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(ethnicity)) {
				element.click();
				break;
			}
			System.out.println("values from Ethnicity drop down ===========>" + innerhtml);
		}
		
		logger.log(LogStatus.PASS, "Ethnicity selected.");
		
		
		String culturalIdentity = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "culturalIdentity").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ResidentCulturalIdentity')]/*[@id]")).click();
		List<WebElement> culturalIdentity_menu = driver.findElements(By.xpath("//*[contains(@name, 'ResidentCulturalIdentity')]/*[@id]"));
		for (int i = 0; i < culturalIdentity_menu.size(); i++) {
			WebElement element = culturalIdentity_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(culturalIdentity)) {
				element.click();
				break;
			}
			System.out.println("values from Cultural Identity drop down ===========>" + innerhtml);
		}
		
		logger.log(LogStatus.PASS, "Cultural Identity selected.");
		
		
		
		
		String religion = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "religion").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ResidentReligion')]/*[@id]")).click();
		List<WebElement> religion_menu = driver.findElements(By.xpath("//*[contains(@name, 'ResidentReligion')]/*[@id]"));
		for (int i = 0; i < religion_menu.size(); i++) {
			WebElement element = religion_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(religion)) {
				element.click();
				break;
			}
			System.out.println("values from religion drop down ===========>" + innerhtml);
		}
		

		driver.findElement(By.xpath("//*[contains(@id, 'exampleSelect')]")).click();
		
		logger.log(LogStatus.PASS, "All the fields from Resident Details are provided.");
		String image_path4 = snap_shot.takeSnapShot(driver);
		System.out.println(image_path4);
		// report.addScreenCaptureFromPath(image_path);
		String img4 = logger.addScreenCapture(image_path4);
		logger.log(LogStatus.PASS, "verified", img4);
		
		
		//Data into Enquiry Details section started to be entered.++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		/*
		 * driver.findElement(By.name("ResidentEnquiryDate")).click(); String
		 * setInquiryDate = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation",
		 * "setInquiryDate").get("Value").toString();
		 * driver.findElement(By.name("ResidentEnquiryDate")).sendKeys(setInquiryDate);
		 */
		logger.log(LogStatus.PASS, "Enquiry Date already Provided.");
		
		
		
		String enquirySource = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "enquirySource").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ResidentEnquirySource')]/*[@id]")).click();
		List<WebElement> enquirySource_menu = driver.findElements(By.xpath("//*[contains(@name, 'ResidentEnquirySource')]/*[@id]"));
		for (int i = 0; i < enquirySource_menu.size(); i++) {
			WebElement element = enquirySource_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(enquirySource)) {
				element.click();
				break;
			}
			System.out.println("values from Enquiry Source drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "Enquiry Source have been selected.");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@name, 'ResidentEnquiryAdmissionType')]/*[@value]")));
				
		String AdmType = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "AdmType").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ResidentEnquiryAdmissionType')]/*[@value]")).click();
		List<WebElement> dd_menu = driver.findElements(By.xpath("//*[contains(@name, 'ResidentEnquiryAdmissionType')]/*[@id]"));
		for (int i = 0; i < dd_menu.size(); i++) {
			WebElement element = dd_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(AdmType)) {
				element.click();
				break;
			}
			System.out.println("values from Resident Enquiry Admission Type drop down ===========>" + innerhtml);
		}
		System.out.println("Admission Type selected.");
		logger.log(LogStatus.PASS, "Resident Enquiry Admission Type have been selected.");
		
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@name, 'ResidentEnquiryAdmissionTimeFrame')]/*[@id]")));
		
		String admissionTimeFrame = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "admissionTimeFrame").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ResidentEnquiryAdmissionTimeFrame')]/*[@id]")).click();
		List<WebElement> admissionTimeFrame_menu = driver.findElements(By.xpath("//*[contains(@name, 'ResidentEnquiryAdmissionTimeFrame')]/*[@id]"));
		for (int i = 0; i < admissionTimeFrame_menu.size(); i++) {
			WebElement element = admissionTimeFrame_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(admissionTimeFrame)) {
				element.click();
				break;
			}
			System.out.println("values from Admission Time Frame drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "Admission Time Frame have been selected.");
		
		
		// Fill date as mm/dd/yyyy as 09/25/2013
		driver.findElement(By.name("ResidentEnquiryProposeAdmissionDate")).click();
		String setPropAdmDate = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "setPropAdmDate").get("Value").toString();
		driver.findElement(By.name("ResidentEnquiryProposeAdmissionDate")).sendKeys(setPropAdmDate);

		System.out.println("DOB, Enquiry Date and Proposed admission date selected.");
		logger.log(LogStatus.PASS, "Propose Admission Date have been selected.");
		
		
		
		String pensionerNumber = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "pensionerNumber").get("Value").toString();
		driver.findElement(By.name("ResidentEnquiryPensionNumber")).sendKeys(pensionerNumber);
		logger.log(LogStatus.PASS, "Pensioner Number provided.");		
		
		

		String anACCClass = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "anACCClass").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ResidentEnquiryResidentACCClass')]/*[@id]")).click();
		List<WebElement> anACCClass_menu = driver.findElements(By.xpath("//*[contains(@name, 'ResidentEnquiryResidentACCClass')]/*[@id]"));
		for (int i = 0; i < anACCClass_menu.size(); i++) {
			WebElement element = anACCClass_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(anACCClass)) {
				element.click();
				break;
			}
			System.out.println("values from AN-ACC Class drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "AN-ACC Class have been selected.");
		

		String residentCategory = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "residentCategory").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ResidentEnquiryResidentCategory')]/*[@id]")).click();
		List<WebElement> residentCategory_menu = driver.findElements(By.xpath("//*[contains(@name, 'ResidentEnquiryResidentCategory')]/*[@id]"));
		for (int i = 0; i < residentCategory_menu.size(); i++) {
			WebElement element = residentCategory_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(residentCategory)) {
				element.click();
				break;
			}
			System.out.println("values from resident Category drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "Resident Category have been selected.");
		

		
		String aCATStatus = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "aCATStatus").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ResidentEnquiryACAT')]/*[@id]")).click();
		List<WebElement> aCATStatus_menu = driver.findElements(By.xpath("//*[contains(@name, 'ResidentEnquiryACAT')]/*[@id]"));
		for (int i = 0; i < aCATStatus_menu.size(); i++) {
			WebElement element = aCATStatus_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(aCATStatus)) {
				element.click();
				break;
			}
			System.out.println("values from ACAT Status drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "ACAT Status have been selected.");
		

		// Fill date as mm/dd/yyyy as 09/25/2013
		driver.findElement(By.name("ResidentEnquiryACATApprovalDate")).click();
		String aCATApprovalDate = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "aCATApprovalDate").get("Value").toString();
		driver.findElement(By.name("ResidentEnquiryACATApprovalDate")).sendKeys(aCATApprovalDate);
		logger.log(LogStatus.PASS, "ACAT approval date have been selected.");
		System.out.println("ACAT approval date selected.");
		
		//Thread.sleep(10000);
		
		String myAgedCareNumber = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "myAgedCareNumber").get("Value").toString();
		driver.findElement(By.name("ResidentEnquiryAgedCareNumber")).sendKeys(myAgedCareNumber);
		logger.log(LogStatus.PASS, "myAgedCare Number provided.");	
		System.out.println("My aged care number is selected.");
		
		//Thread.sleep(10000);
		String mediCareNumber = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "mediCareNumber").get("Value").toString();
		driver.findElement(By.name("ResidentEnquiryMedicareNumber1")).sendKeys(mediCareNumber);


		
		driver.findElement(By.xpath("//*[contains(text(),'Flexible in Room Preference?')]")).click();
		//Thread.sleep(5000);
		
		
		String mediCareNumber2 = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "mediCareNumber2").get("Value").toString();
		driver.findElement(By.name("ResidentEnquiryMedicareNumber2")).sendKeys(mediCareNumber2);
		logger.log(LogStatus.PASS, "mediCare Number provided.");	
		System.out.println("Medicare number is selected.");
		
		//Thread.sleep(10000);
		driver.findElement(By.name("ResidentEnquiryMedicareExpiry")).click();
		String medicareCardExpiry = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "medicareCardExpiry").get("Value").toString();
		driver.findElement(By.name("ResidentEnquiryMedicareExpiry")).sendKeys(medicareCardExpiry);
		logger.log(LogStatus.PASS, "medicareCardExpiry date have been selected.");
		System.out.println("medicareCardExpiry date is selected.");
		
		//Thread.sleep(10000);
		String meansTestAssessment = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "meansTestAssessment").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ResidentEnquiryMeansAssessment')]/*[@id]")).click();
		List<WebElement> meansTestAssessment_menu = driver.findElements(By.xpath("//*[contains(@name, 'ResidentEnquiryMeansAssessment')]/*[@id]"));
		for (int i = 0; i < meansTestAssessment_menu.size(); i++) {
			WebElement element = meansTestAssessment_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(meansTestAssessment)) {
				element.click();
				break;
			}
			System.out.println("values from means Test Assessment drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "means Test Assessment have been selected.");
		System.out.println("means Test Assessment is selected.");
		
		
		String image_path5 = snap_shot.takeSnapShot(driver);
		System.out.println(image_path5);
		// report.addScreenCaptureFromPath(image_path);
		String img5 = logger.addScreenCapture(image_path5);
		logger.log(LogStatus.PASS, "verified", img5);
		
		//Data into Preferred Facilities section started to be entered.++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		WebElement element2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@name, 'FirstFacPreference')]/*[@value]")));
		element2.click();
		
		String FirstPref = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "FirstPref").get("Value").toString();
			
		//driver.findElement(By.xpath("//*[contains(@name, 'FirstFacPreference')]/*[@value]")).click();
		//Thread.sleep(5000);

		List<WebElement> dd_pref_menu = driver
				.findElements(By.xpath("//*[contains(@name, 'FirstFacPreference')]/*[@id]"));
		for (int i = 0; i < dd_pref_menu.size(); i++) {
			WebElement element = dd_pref_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(FirstPref)) {
				element.click();
				break;
			}
			System.out.println("values from Facility First Preference drop down ===========>" + innerhtml);
		}

		//driver.findElement(By.xpath("//*[contains(@class, 'fas fa-save mr-2')]")).click();
		String image_path3 = snap_shot.takeSnapShot(driver);
		System.out.println(image_path3);
		// report.addScreenCaptureFromPath(image_path);
		String img3 = logger.addScreenCapture(image_path3);
		logger.log(LogStatus.PASS, "verified", img3);
		
		System.out.println("First Preference selected.");
		
		String secondPreference = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "secondPreference").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'SecondFacPreference')]/*[@id]")).click();
		List<WebElement> secondPreference_menu = driver.findElements(By.xpath("//*[contains(@name, 'SecondFacPreference')]/*[@id]"));
		for (int i = 0; i < secondPreference_menu.size(); i++) {
			WebElement element = secondPreference_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(secondPreference)) {
				element.click();
				break;
			}
			System.out.println("values from Second Preference drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "Second Preference have been selected.");
		
		
		
		
		
		String thirdPreference = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "thirdPreference").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ThridFacPreference')]/*[@id]")).click();
		List<WebElement> thirdPreference_menu = driver.findElements(By.xpath("//*[contains(@name, 'ThridFacPreference')]/*[@id]"));
		for (int i = 0; i < thirdPreference_menu.size(); i++) {
			WebElement element = thirdPreference_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(thirdPreference)) {
				element.click();
				break;
			}
			System.out.println("values from Third Preference drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "Third Preference have been selected.");
		
		
		
		String extraServicePreference = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "extraServicePreference").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'ExtraServicePreference')]/*[@id]")).click();
		List<WebElement> extraServicePreference_menu = driver.findElements(By.xpath("//*[contains(@name, 'ExtraServicePreference')]/*[@id]"));
		for (int i = 0; i < extraServicePreference_menu.size(); i++) {
			WebElement element = extraServicePreference_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(extraServicePreference)) {
				element.click();
				break;
			}
			System.out.println("values from Extra Service Preference drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "Extra Service Preference have been selected.");
		
		
		
		
		
		String catersToDementiaNeeds = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "catersToDementiaNeeds").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'DimentiaSpecificPreference')]/*[@id]")).click();
		List<WebElement> catersToDementiaNeeds_menu = driver.findElements(By.xpath("//*[contains(@name, 'DimentiaSpecificPreference')]/*[@id]"));
		for (int i = 0; i < catersToDementiaNeeds_menu.size(); i++) {
			WebElement element = catersToDementiaNeeds_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(catersToDementiaNeeds)) {
				element.click();
				break;
			}
			System.out.println("values from Caters to Dementia needs drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "Caters to Dementia needs have been selected.");
		
		
		
		String roomPreference = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "roomPreference").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'DimentiaSpecificPreference')]/*[@id]")).click();
		List<WebElement> roomPreference_menu = driver.findElements(By.xpath("//*[contains(@name, 'DimentiaSpecificPreference')]/*[@id]"));
		for (int i = 0; i < roomPreference_menu.size(); i++) {
			WebElement element = roomPreference_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(roomPreference)) {
				element.click();
				break;
			}
			System.out.println("values from roomPreference drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "roomPreference have been selected.");
		
		

		String additionalServicePreference = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "additionalServicePreference").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'AdditionalServicePreference')]/*[@id]")).click();
		List<WebElement> additionalServicePreference_menu = driver.findElements(By.xpath("//*[contains(@name, 'AdditionalServicePreference')]/*[@id]"));
		for (int i = 0; i < additionalServicePreference_menu.size(); i++) {
			WebElement element = additionalServicePreference_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(additionalServicePreference)) {
				element.click();
				break;
			}
			System.out.println("values from additionalServicePreference drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "additionalServicePreference have been selected.");
		
		
		
		
		
		driver.findElement(By.xpath("//*[contains(@name, 'AdditionalServicePreference')]/*[@id]")).click();
		String image_path6 = snap_shot.takeSnapShot(driver);
		System.out.println(image_path6);
		// report.addScreenCaptureFromPath(image_path);
		String img6 = logger.addScreenCapture(image_path6);
		logger.log(LogStatus.PASS, "verified", img6);
		
		//Thread.sleep(10000);
		
		//System.out.println("here we need to start debugging the code");
		driver.findElement(By.xpath("//*[contains(@class, 'fas fa-save mr-2')]")).click();
		driver.switchTo().activeElement();
		
		
		
		String yesconfirmbutton = driver.findElement(By.xpath("//*[contains(text(),'Yes, Confirm')]")).getText();
		System.out.println(yesconfirmbutton);
		driver.findElement(By.xpath("//*[contains(text(),'Yes, Confirm')]")).click();
		logger.log(LogStatus.PASS, "Yes, Confirm is selected on popup to continue...");
		System.out.println("Yes, Confirm is selected on popup to continue...");

		
		//WebDriverWait wait21 = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		//Thread.sleep(6000);
		driver.switchTo().activeElement();

		String okbutton = driver.findElement(By.xpath("//*[contains(@class,'modal-body')]")).getText();
		System.out.println(okbutton);
		driver.findElement(By.xpath("//*[contains(text(),'OK')]")).click();
		logger.log(LogStatus.PASS, "New inquiry have been saved successfully...");
		System.out.println("New inquiry have been saved successfully...");
		
		
		//Below lines added just to confirm that element is visible.
		//WebDriverWait wait12 = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.elementToBeClickable(By.name("checklistType")));
		System.out.println("Screen is re-loaded successfully. So episode Id can be fetched now.");
		
		//WebDriverWait wait13 = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		String episodeId = driver.findElement(By.xpath("//input[@name='ResidentEpisodeID']")).getAttribute("value");
		//String episodeId = driver.findElement(By.xpath("//*[contains(@name, 'ResidentEpisodeID')]")).getText();
		System.out.println("Value of episode id is: " + episodeId);
		
		//String episodeId = driver.findElement(By.name("ResidentEpisodeID")).getAttribute("value");
		//WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		
		//Thread.sleep(6000);
		//Start to Close the record and re-open it. 
		//WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(120));
		WebElement element3 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Save & Exit')]")));
		element3.click();
		System.out.println("Save & Exit button is clicked.");
		
		
		//driver.findElement(By.xpath("//*[contains(text(),'Save & Exit')]")).click();
		driver.switchTo().activeElement();
		
		
		
		//WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement element4 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'OK')]")));
		element4.click();
		//Thread.sleep(6000);
		//driver.findElement(By.xpath("//*[contains(text(),'OK')]")).click();
		System.out.println("Ok, Popup button is closed.");
		
		
		//WebDriverWait wait7 = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		//Thread.sleep(6000);
		//Enter user first name and last name with comma and space and click submit button. 
		driver.findElement(By.name("searchValue")).sendKeys(episodeId);
		driver.findElement(By.xpath("//*[contains(text(),'Submit')]")).click();
		System.out.println("Searching for the created record from search filter for the episode Id: "+episodeId);
		
		//Thread.sleep(20000);
		//WebDriverWait wait18 = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		
		//Click on Edit button to open the same record again in edit mode. 
		driver.findElement(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]")).click();
		driver.findElement(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]//*[contains(text(),'Edit')]")).click();
		System.out.println("Search results are displayed");
		
		//Thread.sleep(7000);
		//WebDriverWait wait8 = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		

		//WebDriverWait wait9 = new WebDriverWait(driver, Duration.ofSeconds(120));
		WebElement element9 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Edit')]")));
		element9.click();
		//driver.findElement(By.xpath("//*[contains(text(),'Edit')]")).click();



		
		//WebDriverWait wait10 = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		
		logger = report.startTest("Verify First Name");
		try {
			// Assertion to be placed here
			String firstName = driver.findElement(By.id("ResidentFirstName")).getAttribute("value");
			System.out.println("user first name from UI is: " + firstName);
			Assert.assertTrue(firstNam.contains(firstName), "Inquiry Creation Failed. ");
			System.out.println("user first name from data is: " + firstNam);
			logger.log(LogStatus.PASS, "Pass - Verify First Name is successful.");
		} catch (AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Inquiry creation failed. First Name verification failed.");
		}
		report.endTest(logger);

		logger = report.startTest("Verify Last Name");
		try {
			// Assertion to be placed here
			String lstName = driver.findElement(By.name("ResidentLastName")).getAttribute("value");
			System.out.println("user last name from UI is: " + lstName);
			Assert.assertTrue(lastName.contains(lstName), "Inquiry Creation Failed. ");
			System.out.println("user last name from data is: " + lastName);
			logger.log(LogStatus.PASS, "Pass - Verify Last Name is successful.");
		} catch (AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Last Name verification failed.");
		}
		report.endTest(logger);

		logger = report.startTest("Verify Address Line 1");
		try {
			// Assertion to be placed here
			String AddressLine1 = driver.findElement(By.name("ResidentAddress1")).getAttribute("value");
			System.out.println("Address Line 1 from UI is: " + AddressLine1);
			Assert.assertTrue(AddLine1.contains(AddressLine1), "Inquiry Creation Failed. ");
			System.out.println("Address Line 1 from data is: " + AddLine1);
			logger.log(LogStatus.PASS, "Pass - Verify Address Line 1 is successful.");
		} catch (AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Address Line 1 verification failed.");
		}
		report.endTest(logger);

		logger = report.startTest("Verify Suburb");
		try {
			// Assertion to be placed here
			String Suburb = driver.findElement(By.className("css-dvua67-singleValue")).getText();
			System.out.println("Suburb from UI is: " + Suburb);
			Assert.assertTrue(setSuburb.contains(Suburb), "Inquiry Creation Failed. ");
			System.out.println("Suburb from data is: " + setSuburb);
			logger.log(LogStatus.PASS, "Pass - Verify Suburb is successful.");
		} catch (AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Suburb verification failed.");
		}
		report.endTest(logger);

		

		
		
		
		//Below code is used to delete the record. 
		
		
		//String episodeId = driver.findElement(By.name("ResidentEpisodeID")).getAttribute("value");
		//WebDriverWait wait14 = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		
		//Thread.sleep(6000);
		//Start to Close the record and re-open it. 
		//WebDriverWait wait15 = new WebDriverWait(driver, Duration.ofSeconds(120));
		WebElement element15 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Save & Exit')]")));
		element15.click();
		System.out.println("Save & Exit button is clicked.");
		
		
		//driver.findElement(By.xpath("//*[contains(text(),'Save & Exit')]")).click();
		driver.switchTo().activeElement();
		
		
		
		//WebDriverWait wait16 = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement element16 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'OK')]")));
		element16.click();
		//Thread.sleep(6000);
		//driver.findElement(By.xpath("//*[contains(text(),'OK')]")).click();
		System.out.println("Ok, Popup button is closed.");
		
		
		//WebDriverWait wait17 = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		//Thread.sleep(6000);
		//Enter user first name and last name with comma and space and click submit button. 
		//driver.findElement(By.name("searchValue")).sendKeys(episodeId);
		//driver.findElement(By.xpath("//*[contains(text(),'Submit')]")).click();
		//System.out.println("Searching for the created record from search filter for the episode Id: "+episodeId);
		
		
		
		
		//WebDriverWait wait19 = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		
		//Click on Edit button to open the same record again in edit mode. 
		driver.findElement(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]")).click();
		driver.findElement(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]//*[contains(text(),'Delete')]")).click();
		logger.log(LogStatus.PASS, "Here Delete inquiry is selected.");
		System.out.println("Here Delete inquiry is selected.");
		
		
		//Thread.sleep(7000);
		//WebDriverWait wait20 = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		

		
		driver.switchTo().activeElement();

		String delconfirmbutton = driver.findElement(By.xpath("//*[contains(text(),'Yes, delete')]")).getText();
		System.out.println(delconfirmbutton);
		driver.findElement(By.xpath("//*[contains(text(),'Yes, delete')]")).click();
		logger.log(LogStatus.PASS, "Yes, delete is selected on popup to continue...");
		logger.log(LogStatus.PASS, "Created inquiry have been deleted successfully...");
		System.out.println("Yes, Delete is selected on popup to delete the inquiry...");
		System.out.println("Created inquiry have been deleted successfully...");
		//WebDriverWait wait21 = new WebDriverWait(driver, Duration.ofSeconds(120));
		//WebElement element21 = wait21.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Edit')]")));
		//element21.click();
		
		
		
		
		
		
		report.endTest(logger);
		//report.flush();
		// report.close();
		//driver.quit();

	}
	
	
	
	
	
	
	
	  @AfterMethod public void tearDown(ITestResult result) throws Exception 
	  {
		   if(ITestResult.FAILURE==result.getStatus()) 
		   {
			   String image_path =snap_shot.takeSnapShot(driver); 
			   String img = logger.addScreenCapture(image_path);
				logger.log(LogStatus.PASS, "verified", img);
	  }
	  
	  driver.close();
	  	  
	  }
	 
	  @AfterSuite
	  public void endOfSuite()
	  {
		  report.flush();
		 // driver.quit();
	  }
	 

	 
}

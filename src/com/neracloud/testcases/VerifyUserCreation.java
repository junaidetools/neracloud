package com.neracloud.testcases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static io.github.seleniumquery.SeleniumQuery.$; 
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
	loginPage login_page;
	String pdfUrl;
	public static String episodeId = null; // It's a global String
	
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
				
		login_page = PageFactory.initElements(driver, loginPage.class);
		login_page.login_nera(username, password);
		//logger.log(LogStatus.PASS, "NeRA cloud application accessed successfully.");
		//report.endTest(logger);
		
		
	}
	
	@Test(priority=0)
	public void TC001_VerifyInquiryCreationOfTypePermanent() throws Exception {
		
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
		
		logger = report.startTest("TC001_VerifyInquiryCreationOfTypePermanent");

		
		driver.findElement(By.xpath("//*[contains(text(),'Register')]")).click();
		logger.log(LogStatus.PASS, "Register Tabe is selected");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(360));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));

		driver.switchTo().activeElement();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'If you have made any changes that are not saved yet, all the changes will be lost. Are you sure you want to continue?')]")));
		String errorMessage = driver.findElement(By.xpath("//*[contains(text(),'If you have made any changes that are not saved yet, all the changes will be lost. Are you sure you want to continue?')]"))
				.getText();
		System.out.println(errorMessage);
		logger.log(LogStatus.PASS, "Error message popup shown");
		
		String yesButtonText= login_page.getYesBtnText();
		
		//String yesbutton = driver.findElement(By.xpath("//*[contains(text(),'Yes')]")).getText();
		System.out.println(yesButtonText + ": This text from Yes button is fetched...");

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Yes')]")));
		element1.click();
		
		logger.log(LogStatus.PASS, "Yes is selected on popup to continue...");
		System.out.println("Yes is selected on popup to continue...");
				
		driver.findElement(By.xpath("//*[contains(text(),'Add Enquiry')]")).click();
		
		driver.findElement(By.xpath("//*[contains(text(),'Edit')]")).click();

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
		

		String firstNam = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "firstNam").get("Value").toString();
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

		String setSuburb = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "setSuburb").get("Value").toString();
		WebElement wb = driver.findElement(By.id("ResidentSuburb"));
		Actions action = new Actions(driver);
		action.moveToElement(wb).click().moveToElement(wb, 200, 0).sendKeys(setSuburb, Keys.ENTER).build().perform(); // .build().perform();
		logger.log(LogStatus.PASS, "Suburb Selected.");

		String image_path = snap_shot.takeSnapShot(driver);
		System.out.println(image_path);
		String img = logger.addScreenCapture(image_path);
		logger.log(LogStatus.PASS, "verified", img);
		
		System.out.println("Suburb selected.");
				
		String phone = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "phone").get("Value").toString();
		driver.findElement(By.id("ResidentPhone")).sendKeys(phone);
		logger.log(LogStatus.PASS, "Phone number provided.");
		
		String mobile = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "mobile").get("Value").toString();
		driver.findElement(By.name("ResidentMobile")).sendKeys(mobile);
		logger.log(LogStatus.PASS, "Mobile number provided.");

		String email = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "email").get("Value").toString();
		driver.findElement(By.name("ResidentEmail")).sendKeys(email);
		logger.log(LogStatus.PASS, "Email number provided.");

		String residentId = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "residentId").get("Value").toString();
		driver.findElement(By.name("ResidentID")).sendKeys(residentId);
		logger.log(LogStatus.PASS, "Resident Id provided.");
		
		driver.findElement(By.name("ResidentDateofBirth")).click();
		// Fill date as mm/dd/yyyy as 09/25/2013
		String setDOB = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "setDOB").get("Value").toString();
		driver.findElement(By.name("ResidentDateofBirth")).sendKeys(setDOB);
		logger.log(LogStatus.PASS, "Date of Birth provided.");
		
		
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
				
		String myAgedCareNumber = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "myAgedCareNumber").get("Value").toString();
		driver.findElement(By.name("ResidentEnquiryAgedCareNumber")).sendKeys(myAgedCareNumber);
		logger.log(LogStatus.PASS, "myAgedCare Number provided.");	
		System.out.println("My aged care number is selected.");
		
		String mediCareNumber = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "mediCareNumber").get("Value").toString();
		driver.findElement(By.name("ResidentEnquiryMedicareNumber1")).sendKeys(mediCareNumber);
		
		driver.findElement(By.xpath("//*[contains(text(),'Flexible in Room Preference?')]")).click();
				
		String mediCareNumber2 = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "mediCareNumber2").get("Value").toString();
		driver.findElement(By.name("ResidentEnquiryMedicareNumber2")).sendKeys(mediCareNumber2);
		logger.log(LogStatus.PASS, "mediCare Number provided.");	
		System.out.println("Medicare number is selected.");
		
		driver.findElement(By.name("ResidentEnquiryMedicareExpiry")).click();
		String medicareCardExpiry = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "medicareCardExpiry").get("Value").toString();
		driver.findElement(By.name("ResidentEnquiryMedicareExpiry")).sendKeys(medicareCardExpiry);
		logger.log(LogStatus.PASS, "medicareCardExpiry date have been selected.");
		System.out.println("medicareCardExpiry date is selected.");
		
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
		String img5 = logger.addScreenCapture(image_path5);
		logger.log(LogStatus.PASS, "verified", img5);
		
		//Data into Preferred Facilities section started to be entered.++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		WebElement element2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@name, 'FirstFacPreference')]/*[@value]")));
		element2.click();
		
		String FirstPref = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "FirstPref").get("Value").toString();
		
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

		
		String image_path3 = snap_shot.takeSnapShot(driver);
		System.out.println(image_path3);
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
		driver.findElement(By.xpath("//*[contains(@name, 'RoomFacPreference')]/*[@id]")).click();
		List<WebElement> roomPreference_menu = driver.findElements(By.xpath("//*[contains(@name, 'RoomFacPreference')]/*[@id]"));
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
				
		String image_path6 = snap_shot.takeSnapShot(driver);
		System.out.println(image_path6);
		String img6 = logger.addScreenCapture(image_path6);
		logger.log(LogStatus.PASS, "verified", img6);
		
		driver.findElement(By.xpath("//*[contains(@class, 'fas fa-save mr-2')]")).click();
		driver.switchTo().activeElement();
				
		String yesconfirmbutton = driver.findElement(By.xpath("//*[contains(text(),'Yes, Confirm')]")).getText();
		System.out.println(yesconfirmbutton);
		driver.findElement(By.xpath("//*[contains(text(),'Yes, Confirm')]")).click();
		logger.log(LogStatus.PASS, "Yes, Confirm is selected on popup to continue...");
		System.out.println("Yes, Confirm is selected on popup to continue...");
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		driver.switchTo().activeElement();
		String okbutton = driver.findElement(By.xpath("//*[contains(@class,'modal-body')]")).getText();
		System.out.println(okbutton);
		driver.findElement(By.xpath("//*[contains(text(),'OK')]")).click();
		logger.log(LogStatus.PASS, "New inquiry have been saved successfully...");
		System.out.println("New inquiry have been saved successfully...");
		
		
		//Below lines added just to confirm that element is visible.
		wait.until(ExpectedConditions.elementToBeClickable(By.name("checklistType")));
		System.out.println("Screen is re-loaded successfully. So episode Id can be fetched now.");
		
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		episodeId = driver.findElement(By.xpath("//input[@name='ResidentEpisodeID']")).getAttribute("value");
		System.out.println("Value of episode id is: " + episodeId);
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		WebElement element3 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Save & Exit')]")));
		element3.click();
		System.out.println("Save & Exit button is clicked.");
				
		driver.switchTo().activeElement();
		WebElement element4 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'OK')]")));
		element4.click();
		
		System.out.println("Ok, Popup button is closed.");
				
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
				 
		driver.findElement(By.name("searchValue")).sendKeys(episodeId);
		driver.findElement(By.xpath("//*[contains(text(),'Submit')]")).click();
		System.out.println("Searching for the created record from search filter for the episode Id: "+episodeId);
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		
		//Click on Edit button to open the same record again in edit mode. 
		driver.findElement(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]")).click();
		driver.findElement(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]//*[contains(text(),'Edit')]")).click();
		System.out.println("Search results are displayed");
		
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
		WebElement element9 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Edit')]")));
		element9.click();
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
				
		logger = report.startTest("Verify First Name is correct.");
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

		logger = report.startTest("Verify Last Name is correct.");
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

		logger = report.startTest("Verify Address Line 1 is correct.");
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

		logger = report.startTest("Verify Suburb is correct.");
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

		

		
		logger = report.startTest("Verify Date of Birth from Resident Details is correct.");
		try {
			// Assertion to be placed here
			String dateOfBirth = driver.findElement(By.name("ResidentDateofBirth")).getAttribute("value");
			System.out.println("Date of Birth from UI is: " + dateOfBirth);
			Assert.assertTrue(setDOB.contains(dateOfBirth), "Verify Date of Birth is failed.");
			System.out.println("Date of Birth from data is: " + setDOB);
			logger.log(LogStatus.PASS, "Pass - Verify Date of Birth is successful.");
		} catch (AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Verify Date of Birth is failed.");
		}
		report.endTest(logger);

		
		
		logger = report.startTest("Verify Proposed Admission Date from Enquiry details section is correct.");
		try {
			// Assertion to be placed here
			String proposedAdmissionDate = driver.findElement(By.name("ResidentEnquiryProposeAdmissionDate")).getAttribute("value");
			System.out.println("Proposed Admission Date from UI is: " + proposedAdmissionDate);
			Assert.assertTrue(setPropAdmDate.contains(proposedAdmissionDate), "Proposed Admission Date Failed. ");
			System.out.println("proposed Admission Date from data is: " + setPropAdmDate);
			logger.log(LogStatus.PASS, "Pass - Verify Proposed Admission Date is successful.");
		} catch (AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Verify Proposed Admission Date is failed.");
		}
		report.endTest(logger);
		
		
		
		logger = report.startTest("Verify First Preference from Preferred Facilities section is correct.");
		try {
			// Assertion to be placed here
			String firstPereference = driver.findElement(By.name("FirstFacPreference")).getAttribute("value");
			System.out.println("First Preference from UI is: " + firstPereference);
			Assert.assertTrue(FirstPref.contains(firstPereference), "First Preference Failed. ");
			System.out.println("First Preference from data is: " + FirstPref);
			logger.log(LogStatus.PASS, "Pass - Verify First Preference is successful.");
		} catch (AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Verify First Preference is failed.");
		}
		report.endTest(logger);
		
		//Below code is used to delete the record. 
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
				
		WebElement element15 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Save & Exit')]")));
		element15.click();
		System.out.println("Save & Exit button is clicked.");
			
		
		driver.switchTo().activeElement();
		WebElement element16 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'OK')]")));
		element16.click();
		System.out.println("Ok, Popup button is closed.");
			
		
		/*
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(
		 * "loaderContainer")));
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(
		 * "loaderContainer")));
		 * 
		 * 
		 * driver.findElement(By.
		 * xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]"
		 * )).click(); driver.findElement(By.
		 * xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]//*[contains(text(),'Delete')]"
		 * )).click(); logger.log(LogStatus.PASS, "Here Delete inquiry is selected.");
		 * System.out.println("Here Delete inquiry is selected.");
		 * 
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(
		 * "loaderContainer")));
		 * 
		 * driver.switchTo().activeElement();
		 * 
		 * String delconfirmbutton =
		 * driver.findElement(By.xpath("//*[contains(text(),'Yes, delete')]")).getText()
		 * ; System.out.println(delconfirmbutton);
		 * driver.findElement(By.xpath("//*[contains(text(),'Yes, delete')]")).click();
		 * logger.log(LogStatus.PASS,
		 * "Yes, delete is selected on popup to continue...");
		 * logger.log(LogStatus.PASS,
		 * "Created inquiry have been deleted successfully..."); System.out.
		 * println("Yes, Delete is selected on popup to delete the inquiry...");
		 * System.out.println("Created inquiry have been deleted successfully...");
		 */
		

		report.endTest(logger);
		//report.flush();
		// report.close();
		//driver.quit();

	}
	@Test(priority=1)
	public void TC002_VerifyAddRepresentativeIntoAlreadyCreatedInquiry() throws Exception 
	{
		logger = report.startTest("TC002_VerifyAddRepresentativeIntoAlreadyCreatedInquiry");

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
		
		driver.findElement(By.xpath("//*[contains(text(),'Add Representative')]")).click();
		driver.switchTo().activeElement();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@name, 'rep_title_type_id')]")));
		
		
		String representativeTitle = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "representativeTitle").get("Value").toString();
		List<WebElement> representativeTitle_menu = driver.findElements(By.xpath("//*[contains(@name, 'rep_title_type_id')]/*[@value]"));
		for (int i = 0; i < representativeTitle_menu.size(); i++) {
			WebElement element = representativeTitle_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(representativeTitle)) {
				element.click();
				break;
			}
			System.out.println("values from representativeTitle drop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "representativeTitle selected.");
		

		String representativeFirstNam = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "representativeFirstNam").get("Value")
				.toString();
		driver.findElement(By.name("rep_first_name")).sendKeys(representativeFirstNam);
		logger.log(LogStatus.PASS, "representative First Name provided.");
		
		
		// Fill middle name
		String representativeMiddleName = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "representativeMiddleName").get("Value").toString();
		driver.findElement(By.name("rep_middle_name")).sendKeys(representativeMiddleName);
		logger.log(LogStatus.PASS, "representativeMiddleName provided.");

		String representativeLastName = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "representativeLastName").get("Value").toString();
		driver.findElement(By.name("rep_last_name")).sendKeys(representativeLastName);
		logger.log(LogStatus.PASS, "representative Last Name provided.");
		
		//Here we need to provide relationship. 
		String relationship = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "relationship").get("Value").toString();
		driver.findElement(By.xpath("//*[contains(@name, 'relationship_type_id')]/*[@value]")).click();
		List<WebElement> relationship_menu = driver.findElements(By.xpath("//*[contains(@name, 'relationship_type_id')]/*[@value]"));
		for (int i = 0; i < relationship_menu.size(); i++) {
			WebElement element = relationship_menu.get(i);
			String innerhtml = element.getAttribute("innerHTML");

			if (innerhtml.contains(relationship)) {
				element.click();
				break;
			}
			System.out.println("values from relationshipdrop down ===========>" + innerhtml);
		}
		logger.log(LogStatus.PASS, "relationship have been selected.");
		
		
		
		String representativeAddLine1 = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "representativeAddLine1").get("Value").toString();
		driver.findElement(By.name("rep_address1")).sendKeys(representativeAddLine1);
		logger.log(LogStatus.PASS, "representative Address line 1 provided.");
		
		String representativeAddLine2 = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "representativeAddLine2").get("Value").toString();
		driver.findElement(By.name("rep_address2")).sendKeys(representativeAddLine2);
		logger.log(LogStatus.PASS, "representative Address line 2 provided.");

		
		System.out.println("Title, First name, Middle Name, Last name and addresses have been entered.");

		String representativeSetSuburb = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "representativeSetSuburb").get("Value").toString();
		WebElement wb = driver.findElement(By.id("rep_suburb"));
		
		
		
		Actions action = new Actions(driver);
		action.moveToElement(wb).click().moveToElement(wb, 200, 0).sendKeys(representativeSetSuburb).build().perform(); //
		 
		//wb.click(); 
		Thread.sleep(5000);
		//vTypeInField(wb, representativeSetSuburb);
		//wb.sendKeys(representativeSetSuburb);
		//Thread.sleep(5000);
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		List<WebElement> we=  (ArrayList<WebElement>) driver.findElements(By.className("css-kj6f9i-menu"));
		System.out.println(we.get(0).getText());	
		we.get(0).click();
		
		
		
		  
	
		
		
		logger.log(LogStatus.PASS, "Suburb Selected.");

		String image_path = snap_shot.takeSnapShot(driver);
		System.out.println(image_path);
		String img = logger.addScreenCapture(image_path);
		logger.log(LogStatus.PASS, "verified", img);
		
		System.out.println("Suburb selected.");
				
		String representativePhone = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "representativePhone").get("Value").toString();
		driver.findElement(By.id("rep_phone")).sendKeys(representativePhone);
		logger.log(LogStatus.PASS, "representative Phone number provided.");
		
		String representativeMobile = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "representativeMobile").get("Value").toString();
		driver.findElement(By.name("rep_mobile")).sendKeys(representativeMobile);
		logger.log(LogStatus.PASS, "representative Mobile number provided.");

		String representativeEmail = ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "representativeEmail").get("Value").toString();
		driver.findElement(By.name("rep_email")).sendKeys(representativeEmail);
		logger.log(LogStatus.PASS, "representative Email number provided.");
		
		
		  String representativeDrivingLicenseNo =ExcelDriven_XLSX.readExcelData("Testdata", "UserCreation", "representativeDrivingLicenseNo").get("Value").toString();
		  driver.findElement(By.name("rep_license")).sendKeys(
		  representativeDrivingLicenseNo); logger.log(LogStatus.PASS,
		  "representativeDrivingLicenseNo provided.");
		 
		
		WebElement element =driver.findElement(By.className("form-check"));
		element.click();
		  // Check whether the Check box is toggled on 		
        if (element.isSelected()) {					
            System.out.println("Representative Category radio is Toggled On");					

        } else {			
            System.out.println("Representative Category radio is Toggled Off");					
        }
		
        WebElement element2 =driver.findElement(By.xpath("//*[contains(text(),'Billing Contact')]"));
        element2.click();
		   		
        if (element2.isSelected()) {					
            System.out.println("Other Contact Category Checkbox is Toggled On");					

        } else {			
            System.out.println("Other Contact Category Checkbox is Toggled Off");					
        }
		
        WebElement element3 =driver.findElement(By.id("is_primarycontact"));
        element3.click();
		  // Check whether the Check box is toggled on 		
        if (element3.isSelected()) {					
            System.out.println("Primary Contact Checkbox is Toggled On");					

        } else {			
            System.out.println("Primary Contact Checkbox is Toggled Off");					
        }
		
        
 
        //Click on Save button to close the add representative dialog. 
        driver.findElement(By.xpath("//*[contains(@class,'btn btn-primary')]")).click();
        //Click on Yes button shown on top of add representative dialog as popup. 
        driver.switchTo().activeElement();
        driver.findElement(By.xpath("//*[contains(@class, 'unsavedChangesModal modal-footer')]//*[contains(text(),'Yes')]")).click();

        
        String image_path3 =snap_shot.takeSnapShot(driver); 
		   String img3 = logger.addScreenCapture(image_path3);
			logger.log(LogStatus.PASS, "verified", img3);
			
			
        //Click on Cancel button to close the add representative dialog. 
        driver.findElement(By.xpath("//*[contains(@class,'btn btn-secondary') and contains(text(),'Cancel')]")).click();
        
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Save & Exit')]")));
        
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		driver.findElement(By.xpath("//*[contains(@class, 'fas fa-save mr-2')]")).click();
		
		driver.switchTo().activeElement();
		WebElement element16 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'OK')]")));
		element16.click();
		System.out.println("Ok, Popup button is closed.");
		
		//String yesconfirmbutton = driver.findElement(By.xpath("//*[contains(text(),'Yes, Confirm')]")).getText();
		//System.out.println(yesconfirmbutton);
		//driver.findElement(By.xpath("//*[contains(text(),'Yes, Confirm')]")).click();
		logger.log(LogStatus.PASS, "Ok, Popup button is closed.");
		//System.out.println("Yes, Confirm is selected on popup to continue...");

		
		
		//Click Step 1 tab and wait till element. 
        driver.findElement(By.id("step_1")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Save & Exit')]")));
		logger.log(LogStatus.PASS, "Step 1 tab selected.");
		System.out.println("Step 1 tab selected.");
		
		
		
		//Click on edit button from grid
		driver.findElement(By.xpath("//*[contains(@class, 'd-block w-100 text-center')]")).click();
		System.out.println("three lines edit button is clicked..");
		//Select edit button from edit drop down. 
		driver.findElement(By.xpath("//*[contains(text(),'View')]")).click();
		System.out.println("View button selected.");
		
		
		driver.switchTo().activeElement();
		
		//rep_first_name
		logger = report.startTest("Verify Add representative First name is correct.");
		try {
			// Assertion to be placed here
			String representativeFirstNm = driver.findElement(By.id("rep_first_name")).getAttribute("value");
			System.out.println("representativeFirstNam from UI is: " + representativeFirstNm);
			Assert.assertTrue(representativeFirstNam.contains(representativeFirstNm), "Proposed Admission Date Failed. ");
			System.out.println("representativeFirstNam from data is: " + representativeFirstNam);
			logger.log(LogStatus.PASS, "Pass - Verify representativeFirstNam is successful.");
		} catch (AssertionError e) {
			logger.log(LogStatus.FAIL, "Fail - Verify representativeFirstNam is failed.");
		}
		report.endTest(logger);
		
		
		
		
		
        //Click on Cancel button to close the add representative dialog. 
       // driver.findElement(By.xpath("//*[contains(@class,'btn btn-secondary') and contains(text(),'Cancel')]")).click();
        
		
		WebElement okButtonToClosePopup = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'OK')]")));
		okButtonToClosePopup.click();
		System.out.println("Ok, Popup button is closed.");
        
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
		
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

	
	@Test(priority=2)
	public void TC003_VerifyResidentialDetailsFirstNameMiddleNameAndLastNameFromStep1() throws Exception 
	{
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
		  System.out.println("after reading the value from excel");
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
			 * = "arguments[0]"; ((JavascriptExecutor) driver).executeScript("return $(" +
			 * jQuerySelector+ ").doSomethingInJquery();", webElement);
			 */
		  
		  JavascriptExecutor executor = (JavascriptExecutor)driver;
			// executor.executeScript("alert('Alert');");
				/*
				 * var str1 = executor.executeScript(
				 * "$('select[name=\"resident_title_type_id\"]').find(\":selected\").text()");
				 * System.out.
				 * println("js output str1 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "
				 * + str1);
				 */	  
		//  executor.executeScript("let elem = document.querySelector(\"select[name='resident_title_type_id']\")\n"
		 // 		+ "let value = '';if(elem) {value = elem.options[elem.selectedIndex].value}");
			//System.out.println("js output ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+ str);
			
			
		/*
		 * executor.
		 * executeScript("let elem = document.querySelector(\"select[name='genderTypeList']\")\n"
		 * + "let value = '';if(elem) {value = elem.options[elem.selectedIndex].text}");
		 */
				//System.out.println("js output ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+ str2);
			
			Object txt = executor.executeScript("return document.getElementById('resident_title_type_id').value");
			System.out.println("select "+txt.toString());
			
			String jQuerySelector = "arguments[0]";
			((JavascriptExecutor) drivewer).executeScript("return $(" + jQuerySelector+ ").doSomethingInJquery();", webElement);
			
			/*
			 * WebElement elem = executor.executeScrip(document.querySelector(
			 * "select[name='resident_title_type_id']")
			 * 
			 * let value = '';
			 * 
			 * if(elem) {
			 * 
			 * value = elem.options[elem.selectedIndex].text}
			 */
					
					
			
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
	
	@Test(priority=3)
	public void TC004_VerifyResidentialDetailsMaritalStatusPrefLanguageAndReligionFromStep1() throws Exception 
	{
		logger = report.startTest("TC003_VerifyResidentialDetailsMaritalStatusPrefLanguageAndReligionFromStep1");

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

	@Test(priority=4)
	public void TC005_VerifyDeleteInquiryAfterCreation() throws Exception 
	{
		logger = report.startTest("TC003_VerifyDeleteInquiryAfterCreation");

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
		driver.findElement(By.xpath("//*[contains(@class, 'rt-tr-group')][1]//*[contains(@class,'rt-td')][5]//*[contains(@class,'btn-group')]//*[contains(text(),'Delete')]")).click();
		logger.log(LogStatus.PASS, "Here Delete inquiry is selected.");
		System.out.println("Here Delete inquiry is selected.");
				
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loaderContainer")));
				
		driver.switchTo().activeElement();

		String delconfirmbutton = driver.findElement(By.xpath("//*[contains(text(),'Yes, delete')]")).getText();
		System.out.println(delconfirmbutton);
		driver.findElement(By.xpath("//*[contains(text(),'Yes, delete')]")).click();
		logger.log(LogStatus.PASS, "Yes, delete is selected on popup to continue...");
		logger.log(LogStatus.PASS, "Created inquiry have been deleted successfully...");
		System.out.println("Yes, Delete is selected on popup to delete the inquiry...");
		System.out.println("Created inquiry have been deleted successfully...");
		 
		String image_path =snap_shot.takeSnapShot(driver); 
		   String img = logger.addScreenCapture(image_path);
			logger.log(LogStatus.PASS, "verified", img);
			
			
		report.endTest(logger);
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

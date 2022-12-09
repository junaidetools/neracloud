package com.neracloud.testcases;

import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utls.BrowserFactory;




public class LoginTest {

	ExtentReports report;
	ExtentTest logger; 
	WebDriver driver;

	@SuppressWarnings("deprecation")
	@Test
	public void Verifylogin() throws Exception {


		
		report=new ExtentReports("C:\\Users\\hp\\eclipse-workspace\\NeRACloud_Automation\\NeRA_Cloud_Report.html"); 
		logger=report.startTest("Verifylogin");
		 
		
		
		WebDriver driver = BrowserFactory.startBrowser("chrome","https://offshore-dev.e-tools.com.au/eToolsGateway/Login");
		
		logger.log(LogStatus.INFO, "Browser started and cloud application accessed.");
		
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);

		// Provides email into email field.
		driver.findElement(By.id("email")).sendKeys("fukkomupsa@vusra.com");
		// Click next button to proceed with provided email address.
		driver.findElement(By.xpath("//*[contains(text(),'Next')]")).click();
		// Provides password in the password field.
		driver.findElement(By.id("loginpassword")).sendKeys("Zohaib@12");
		// Click login button to login
		
		
		driver.findElement(By.xpath("//*[contains(@class, 'btn-login btn btn-secondary')]")).click();
		
		logger.log(LogStatus.INFO, "Login Successful. ");
		
		Thread.sleep(90000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		//String errorMessage = driver.switchTo().alert().getText();
		
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		//String errorMessage = driver.findElement(By.cssSelector(".modal-content h5 > h5")).getText();
	    //System.out.println(errorMessage); Assert.assertEquals(errorMessage, "Incorrect email or password.");
		
		//Alert alert = driver.switchTo().alert();
		//alert.accept();
		
		//driver.switchTo().frame("ModelFrameTitle");
		
		
		driver.switchTo().activeElement();
		//driver.switchTo().frame("Invalid");
		String errorMessage = driver.findElement(By.xpath("//*[contains(text(),'Incorrect email or password.')]")).getText();
		System.out.println(errorMessage);
		 
			
		
		report.endTest(logger); 
		report.flush();
		driver.get("C:\\Users\\hp\\eclipse-workspace\\NeRACloud_Automation\\NeRA_Cloud_Report.html");
		
		driver.quit();  
	}
	
	
}

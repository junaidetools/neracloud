package com.neracloud.testcases;

import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utls.BrowserFactory;
import Utls.ExcelDriven_XLSX;

public class MultipleLoginTest {
	ExtentReports report;
	ExtentTest logger; 
	WebDriver driver;
	
	@SuppressWarnings("deprecation")
	@Test(dataProvider="credentials")
	public void TC001_Login_verifyMultipleLogin(String scenario, String username, String password) throws Exception
	{		
		
		report=new ExtentReports("C:\\Users\\hp\\eclipse-workspace\\NeRACloud_Automation\\NeRA_Cloud_Report.html"); 
		logger=report.startTest("verifyMultipleLogin");
		
		String WebUrl = ExcelDriven_XLSX.readExcelData("Testdata","Config","URL").get("Value").toString();
		String browser = ExcelDriven_XLSX.readExcelData("Testdata","Config","Browser").get("Value").toString();
		
		WebDriver driver = BrowserFactory.startBrowser(browser, WebUrl);
		driver.manage().timeouts().implicitlyWait(140, TimeUnit.SECONDS);
		
		logger.log(LogStatus.INFO, "Browser started and cloud application accessed.");
		
		//Provides email into email field.
		driver.findElement(By.id("email")).sendKeys(username);
		//Click next button to proceed with provided email address.
		driver.findElement(By.xpath("//*[contains(text(),'Next')]")).click();
		//Provides password in the password field.
		driver.findElement(By.id("loginpassword")).sendKeys(password);
		//Click login button to login
		driver.findElement(By.xpath("//*[contains(@class, 'btn-login btn btn-secondary')]")).click();
		
		logger.log(LogStatus.INFO, "Loging was successful.");
	
		if(scenario.equals("AWSCognitoIdentity"))
		{
			WebElement neraLogo = driver.findElement(By.id("NeRAAppLogo"));
			Assert.assertTrue(neraLogo.isDisplayed(), "AWS Cognito Identity user loging failed with username: "+username);

			logger.log(LogStatus.INFO, "Home page is loaded successfully.");
			
			driver.findElement(By.xpath("//*[contains(@class, 'btn btn-secondary')]")).click();
			driver.findElement(By.xpath("//img[@src='./static/media/DropdownMenuLogout.c00a4b7c.svg']")).click();
			
			driver.switchTo().activeElement();
			driver.findElement(By.xpath("//*[contains(@class,'btn btn-primary') and contains(text(),'Yes')]")).click();
			
			logger.log(LogStatus.INFO, "Logging out from the application.");
			logger.log(LogStatus.PASS, "Log out from the application.");
			
			Thread.sleep(2000);
			
		}
		else if(scenario.equals("AzureADUser"))
		{
			WebElement neraLogo = driver.findElement(By.id("NeRAAppLogo"));
			Assert.assertTrue(neraLogo.isDisplayed(), "Azure Active Directory user login failed with username: "+username);
			
			logger.log(LogStatus.INFO, "Home page is loaded successfully..");
			
			driver.findElement(By.xpath("//*[contains(@class, 'btn btn-secondary')]")).click();
			driver.findElement(By.xpath("//img[@src='./static/media/DropdownMenuLogout.c00a4b7c.svg']")).click();
			
			
			driver.switchTo().activeElement();
			driver.findElement(By.xpath("//*[contains(@class,'btn btn-primary') and contains(text(),'Yes')]")).click();
			
			logger.log(LogStatus.INFO, "Logging out from the application..");
			logger.log(LogStatus.PASS, "Log out from the application.");
			Thread.sleep(2000);
		}
		
		
		else if(scenario.equals("BothWrong"))
		{
			/*
			 * String errorMessage =
			 * driver.findElement(By.cssSelector(".modal-content h5 > h5")).getText();
			 * System.out.println(errorMessage); Assert.assertEquals(errorMessage,
			 * "Incorrect email or password.");
			 */
			
			driver.switchTo().activeElement();
			String errorMessage = driver.findElement(By.xpath("//*[contains(text(),'Incorrect email or password.')]")).getText();
			System.out.println(errorMessage);
			//Assert.assertTrue(errorMessage.contains("Incorrect"));
			
			logger.log(LogStatus.INFO, "Invalid credentials test passed..");
			//WebElement error = driver.findElement(By.xpath("//*[contains(text(),'Incorrect email or password.')]"));
			//Assert.assertTrue(error.isDisplayed(), "Username and password both wrong failed with username: "+username);
		}
		
		else if(scenario.equals("CorrectUsername"))
		{
			/*
			 * String errorMessage =
			 * driver.findElement(By.cssSelector(".modal-content h5 > h5")).getText();
			 * System.out.println(errorMessage); Assert.assertEquals(errorMessage,
			 * "Incorrect email or password.");
			 */
			driver.switchTo().activeElement();
//			
			String errorMessage = driver.findElement(By.xpath("//*[contains(text(),'Incorrect email or password.')]")).getText();
			System.out.println(errorMessage);
			//Assert.assertTrue(errorMessage.contains("Incorrect"));
			
			logger.log(LogStatus.INFO, "Invalid credentials test passed...");
			//WebElement error = driver.findElement(By.xpath("//*[contains(text(),'Incorrect email or password.')]"));
			//Assert.assertTrue(error.isDisplayed(), "Wrong password test case failed with username: "+username);
		}
		
		else 
		{
			/*
			 * String errorMessage =
			 * driver.findElement(By.cssSelector(".modal-content h5 > h5")).getText();
			 * System.out.println(errorMessage); Assert.assertEquals(errorMessage,
			 * "Incorrect email or password.");
			 */
			driver.switchTo().activeElement();
			String errorMessage = driver.findElement(By.xpath("//*[contains(text(),'Incorrect email or password.')]")).getText();
			System.out.println(errorMessage);
			//Assert.assertTrue(errorMessage.contains("Incorrect"));
			
			
			logger.log(LogStatus.INFO, "Invalid credentials test passed....");
			//WebElement error = driver.findElement(By.xpath("//*[contains(text(),'Incorrect email or password.')]"));
			//Assert.assertTrue(error.isDisplayed(), "Test case failed with username: "+username);
		}
		
		report.endTest(logger); 
		report.flush();
		driver.quit();
	}

	@DataProvider(name="credentials")
	public Object[][] getData()
	{
		return new Object[][]
				{
			{"AWSCognitoIdentity","lortumorzi@vusra.com","Ubitian@087"},
			{"AWSCognitoIdentity","lortumorzi@vusra.com","Ubitian@087"},
			{"AzureADUser","lortumorzi@vusra.com","Ubitian@087"},
			{"BothWrong","junaidtariq@e-tools.com","test@abd123"},
			{"CorrectUsername","lortumorzi@vusra.com","Test@abd123"},
			{"CorrectPassword","junaidtarqi@vusra.com","Ubitian@087"}
						
				};
	}
	
}

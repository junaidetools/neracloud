package com.neracloud.testcases;



import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


import org.testng.annotations.Test;


import com.neracloud.pages.loginPage;

import Utls.BrowserFactory;


public class createAgreement {
	
	
	WebDriver driver;
	

	@SuppressWarnings("deprecation")
	@Test(priority=1)
	public void loginToApplication() throws InterruptedException 
	{
		WebDriver driver = BrowserFactory.startBrowser("chrome", "https://offshore-dev.e-tools.com.au/eToolsGateway/Login");
		
		loginPage login_page= PageFactory.initElements(driver, loginPage.class);
		
		
		login_page.login_nera("fukkomupsa@vusra.com", "Zohaib@123");
		
		//Thread.sleep(5000);		
		//driver.manage().window().maximize();
		//driver.get("https://offshore-dev.e-tools.com.au/eToolsGateway/Login");
		//System.out.println(driver.getTitle());
		//Thread.sleep(5000);
		//driver.quit();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		System.out.println("last step done");

		
	}



	@SuppressWarnings("deprecation")
	@Test(priority=2)
	public void createNewAgreement() throws InterruptedException
	{
		//WebDriver driver;
		System.out.println("creatNewAgreement execution started");
		driver.findElement(By.xpath("//*[contains(text(),'Add Enquiry')]")).click();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);

		System.out.println("Access to NeRA is successful.");
		//Thread.sleep(5000);
		System.out.println("Add inquiry page accessed");
		driver.quit();
	}
	
	
	


}

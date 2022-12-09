package com.neracloud.testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class VerifyTitle {
	
	WebDriver driver;
	
	@BeforeTest
	@Parameters("browser")
	public void verifypageTitle(String browserName) throws InterruptedException
	{
		if(browserName.equalsIgnoreCase("chrome")) 
		{
			//System.setProperty("webdriver.chrome.driver", "C:\\SeleniumDrivers\\chromedriver.exe");
			driver=new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("firefox")) 
		{
			
			driver = new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("IE"))
		{
			//System.setProperty("webdriver.ie.driver", "C:\\SeleniumDrivers\\IEDriverServer.exe");
			
			driver = new InternetExplorerDriver();
			
		}
		
		driver.manage().window().maximize();
		
		
	}
	@Test
	public void loginToApplication()
	{
		driver.get("https://offshore-dev.e-tools.com.au/eToolsGateway/Login");
		System.out.println(driver.getTitle());
	}
	
	
	
	
	
	
	@AfterTest
	public void logoutFromApplication() throws InterruptedException
	{
		Thread.sleep(5000);
		driver.quit();
		
	}
	
	
	
	
	

}

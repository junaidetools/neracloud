package com.neracloud.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class loginPage {
	
	
	WebDriver driver;
	
	public loginPage(WebDriver localdriver) 
	{
		this.driver=localdriver;
	}
	

	
	@FindBy(how=How.ID, using="email") 
	@CacheLookup
	WebElement username;
	
	
	@FindBy(how=How.XPATH, using="//*[contains(text(),'Next')]")
	@CacheLookup
	WebElement next_button;
	
	@FindBy(how=How.ID, using="loginpassword") 
	@CacheLookup
	WebElement password;
	
	@FindBy(how=How.XPATH, using="//*[contains(@class, 'btn-login btn btn-secondary')]")
	@CacheLookup
	WebElement login_button;
	
	@FindBy(how=How.ID, using="NeRAAppLogo") 
	@CacheLookup
	WebElement selectNera;
	
	@FindBy(how=How.XPATH, using="//*[contains(text(),'Yes')]")
	@CacheLookup
	WebElement yesBtn;
	
	public String getYesBtnText() {
		return yesBtn.getText();
	}
	
	@SuppressWarnings("deprecation")
	public void login_nera(String uid, String pass) throws InterruptedException 
	{
		driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
		//Thread.sleep(5000);
		username.sendKeys(uid);
		
		//Thread.sleep(5000);
		next_button.click();
		//Thread.sleep(5000);
		password.sendKeys(pass);
		login_button.click();
		System.out.println("Login to Gateway is successful.");
		//Thread.sleep(75000);
		selectNera.click();
		System.out.println("Access to NeRA Cloud application is successful.");
		//Thread.sleep(5000);
		
		WebElement neraLogo = selectNera;
		Assert.assertTrue(neraLogo.isDisplayed(), "AWS Cognito Identity user loging failed with username: " + uid);
		
	}
	
	
	

}

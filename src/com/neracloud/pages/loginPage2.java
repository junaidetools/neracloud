package com.neracloud.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class loginPage2 {
	

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
	
	
	public void enterEmail(String email)
	{
		username.sendKeys(email);
	}

}

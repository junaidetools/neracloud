package Utls;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserFactory {
	
	static WebDriver driver;
	
	
	public static WebDriver startBrowser(String browserName, String url) 
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
		driver.get(url);
		
		
		return driver;
	}

}

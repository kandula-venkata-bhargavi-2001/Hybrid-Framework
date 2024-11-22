package commonFunctions;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary
{
	private static final String Url = null;
	public static WebDriver driver;
	public static Properties conpro;
	
	//method for Launching browser
	public static WebDriver startBrowser()throws Throwable
	{
		conpro = new Properties();
		
		//load property file
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
		{
			Reporter.log("Browser Value Is Not Matching",true);
			
		}
		return driver;
			
		}

      
//Method For Launching URL	
public static void openurl()
{
	driver.get(conpro.getProperty("Url"));
}

//Method for Wait for any element
public static void WaitForElement(String LType,String LValue,String myWait)
{
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(myWait)));
	if(LType.equalsIgnoreCase("xpath"))
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LValue)));
	}
	if(LType.equalsIgnoreCase("id"))
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LValue)));
	}
	if(LType.equalsIgnoreCase("name"))
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LValue)));
	}
}
//method for textboxes
public static void typeAction(String LType,String LValue,String TestData)
{
	if(LType.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(LValue)).clear();
		driver.findElement(By.xpath(LValue)).sendKeys(TestData);
	}
	
	if(LType.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(LValue)).clear();
		driver.findElement(By.name(LValue)).sendKeys(TestData);
	}
	if(LType.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(LValue)).clear();
		driver.findElement(By.id(LValue)).sendKeys(TestData);
	}
}

//Method for clickAction
public static void clickAction(String LType,String LValue) throws Throwable
{
	if(LType.equalsIgnoreCase("xpath"))
	{
		Thread.sleep(2000);
		driver.findElement(By.xpath(LValue)).click();
	}
	if(LType.equalsIgnoreCase("name"))
	{
		Thread.sleep(2000);
		driver.findElement(By.name(LValue)).click();
	}
	if(LType.equalsIgnoreCase("id"))
	{
		Thread.sleep(2000);
		driver.findElement(By.id(LValue)).sendKeys(Keys.ENTER);
	}
}
public static void ValidateTitle(String Expected_Title) 
{
	String Actual_Title = driver.getTitle();
	System.out.println(Actual_Title);
	try {
		Assert.assertEquals(Actual_Title, Expected_Title,"Title is Not Matching");
	} catch(AssertionError e)
	{
		System.out.println(e.getMessage());
	}
}	
//Method for closeBrowser
public static void closebrowser()
{
	driver.quit();
}	
//Method for listBoxes
public static void dropDownAction(String LType,String LValue,String TestData) throws Throwable
{
	if(LType.equalsIgnoreCase("xpath"))
	{
		int value = Integer.parseInt(TestData);
		Select element = new Select(driver.findElement(By.xpath(LValue)));
		element.selectByIndex(value);
	}
	if(LType.equalsIgnoreCase("id"))
	{
		int value = Integer.parseInt(TestData);
		Select element = new Select(driver.findElement(By.id(LValue)));
		element.selectByIndex(value);
	}
	if(LType.equalsIgnoreCase("name"))
	{
		int value = Integer.parseInt(TestData);
		Select element = new Select(driver.findElement(By.name(LValue)));
		element.selectByIndex(value);
	}
}

//Method for Capture stock number
public static void capturestock(String LType,String LValue) throws Throwable	
{
	String stockNum="";
	if(LType.equalsIgnoreCase("xpath"))
	{
		stockNum=driver.findElement(By.xpath(LValue)).getAttribute("value");
	}
	if(LType.equalsIgnoreCase("id"))
	{
		stockNum=driver.findElement(By.id(LValue)).getAttribute("value");
	}
	if(LType.equalsIgnoreCase("name"))
	{
		stockNum=driver.findElement(By.name(LValue)).getAttribute("value");
	}
	
   //create notepad into capturedata folder and write
	FileWriter fW = new FileWriter("./CaptureData/stocknumber.txt"); 
    BufferedWriter bW = new BufferedWriter(fW);
    bW.write(stockNum);
    bW.flush();
    bW.close();	
}	

//method for stocktable
public static void stockTable()throws Throwable
{
	FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data = br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
	Thread.sleep(2000);
	driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	Thread.sleep(2000);
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	Thread.sleep(2000);
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
	Thread.sleep(2000);
	Actions ac=new Actions(driver);
	ac.moveToElement(driver.findElement(By.xpath(conpro.getProperty("search-button")))).click().build().perform();
//	driver.findElement(By.xpath(conpro.getProperty("search-button"))).sendKeys(Keys.ENTER);
	Thread.sleep(3000);
	String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
	Reporter.log(Exp_Data+"   "+Act_Data,true);
	try {
		Assert.assertEquals(Act_Data,Exp_Data,"Stock Number Not Found In Table");
	} catch (AssertionError e)
	{
		System.out.println(e.getMessage());
	}
}
//method for capturing supplier number into notepad
public static void capturesup(String LType,String LValue) throws Throwable
{
	String supplierNum="";
	if(LType.equalsIgnoreCase("xpath"))
	{
		supplierNum=driver.findElement(By.xpath(LValue)).getAttribute("value");
	}
	if(LType.equalsIgnoreCase("name"))
	{
		supplierNum=driver.findElement(By.name(LValue)).getAttribute("value");
	}
	if(LType.equalsIgnoreCase("id"))
	{
		supplierNum=driver.findElement(By.id(LValue)).getAttribute("value");
	}
	FileWriter fw = new FileWriter("./CaptureData/supplier.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(supplierNum);
	bw.flush();
	bw.close();
	
	}

//method for verifying supplier number in supplier table
public static void supplierTable() throws Throwable
{
	//read supplier number from above notepad
	FileReader fr = new FileReader("./CaptureData/supplier.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data = br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(3000);
	String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
	Reporter.log(Exp_Data+"         "+Act_Data,true);
	try {
	Assert.assertEquals(Act_Data, Exp_Data,"Supplier Number Not Matching");	
		
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
}
//method for capturing customer number into notepad
public static void capturecus(String LType,String LValue) throws Throwable
{
	String customerNum="";
	if(LType.equalsIgnoreCase("xpath"))
	{
		customerNum=driver.findElement(By.xpath(LValue)).getAttribute("value");
	}
	if(LType.equalsIgnoreCase("name"))
	{
		customerNum=driver.findElement(By.name(LValue)).getAttribute("value");
	}
	if(LType.equalsIgnoreCase("id"))
	{
		customerNum=driver.findElement(By.id(LValue)).getAttribute("value");
	}
	FileWriter fw = new FileWriter("./CaptureData/customer.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(customerNum);
	bw.flush();
	bw.close();
	
	
}
//method for verifying customer number in supplier table
public static void customerTable() throws Throwable
{
	//read supplier number from above notepad
	FileReader fr = new FileReader("./CaptureData/customer.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data = br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(3000);
	String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
	Reporter.log(Exp_Data+"         "+Act_Data,true);
	try {
	Assert.assertEquals(Act_Data, Exp_Data,"customer Number Not Matching");	
		
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
}
}
	


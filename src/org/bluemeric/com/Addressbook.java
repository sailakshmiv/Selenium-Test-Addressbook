package org.bluemeric.com;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.bluemeric.com.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;

@Listeners(org.uncommons.reportng.HTMLReporter.class)
	public class Addressbook implements ITestListener{

		
	Utility utility = new Utility();
	String workspace=System.getProperty("user.dir");
	Properties prop = new Properties();
	ExtentReports report = Suite.report;
	ExtentTest test = Suite.test;
	Suite suite = new Suite();
	WebDriver driver = suite.newDriver();
	//static String url = "http://" + System.getProperty("APP_ENDPOINT");
	static String url = "http://104.199.224.192:9006/";
	@Parameters({"suiteName"})
	@BeforeMethod
	 public void createreport(ITestContext arg0,@Optional String suiteName){
		
		try{		
		Thread.sleep(10000);
		
		test = report.startTest(arg0.getName());
	    test.assignCategory(suiteName);
		}
		catch(Exception e){
		e.printStackTrace();
		}
	 }
	
	@Parameters({"param","param1"})
	@Test
	public void title(@Optional String param,@Optional String param1) throws IOException{

		driver.get(url);
		driver.manage().window().maximize();
		Assert.assertEquals("Addressbook", driver.getTitle());
		System.out.println("=====>Addressbook Dashboard");
	}
	
	@Parameters({"param","param1","param2"})
	@Test
	public void add_contacts(@Optional String param,@Optional String param1,@Optional String param2) {
		
		WebDriverWait wait =new WebDriverWait(driver,100);
		
		for(int i=0; i<=1 ; i++){
			int j = i+1;
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='ROOT-2521314']/div/div[2]/div/div/div/div/div[1]/div/div/div[2]/div")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='gwt-uid-5']")))).clear();
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='gwt-uid-5']")))).sendKeys("Test"+j);
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='gwt-uid-7']")))).clear();
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='gwt-uid-7']")))).sendKeys("Test"+j);
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='gwt-uid-9']")))).clear();
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='gwt-uid-9']")))).sendKeys("1234567890");
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='gwt-uid-11']")))).clear();
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='gwt-uid-11']")))).sendKeys("Test"+j+"@gmail.com");
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='ROOT-2521314']/div/div[2]/div/div[2]/div/table/tbody/tr[1]/td[3]/div/div[1]/div")))).click();			
			String sno = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='ROOT-2521314']/div/div[2]/div/div/div/div/div[2]/div/div[3]/table/tbody/tr["+j+"]/td[1]")))).getText();
			int actual = Integer.parseInt(sno);
			Assert.assertEquals(i, actual); 
			driver.navigate().refresh();
		}
		System.out.println("=====>Contacts Added");
		
	}
	
	@Parameters({"param","param1"})
	@Test
	public void monitor(@Optional String param,@Optional String param1) throws IOException{
																					
		WebDriverWait wait =new WebDriverWait(driver,100);
		
		for(int i=0; i<=1 ; i++){
			int j = i+1;											
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='ROOT-2521314']/div/div[2]/div/div/div/div/div[2]/div/div[3]/table/tbody/tr["+j+"]/td[1]")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='gwt-uid-5']")))).clear();
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='gwt-uid-5']")))).sendKeys("Test"+j+"_Edited");
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='gwt-uid-7']")))).clear();
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='gwt-uid-7']")))).sendKeys("Test"+j+"_Edited");
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='ROOT-2521314']/div/div[2]/div/div[2]/div/table/tbody/tr[1]/td[3]/div/div[1]/div")))).click();	
			driver.navigate().refresh();
			String text = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//*[@id='ROOT-2521314']/div/div[2]/div/div/div/div/div[2]/div/div[3]/table/tbody/tr["+j+"]/td[2]")))).getText();
			Assert.assertEquals("Test"+j+"_Edited", text); 
			driver.navigate().refresh();
		}
		System.out.println("=====>Contacts Edited");
	}
	
	@Parameters({"suiteName"})
	@AfterMethod
		public void screenshot(ITestResult arg0,@Optional String suiteName) {
		
		Capabilities caps = ((RemoteWebDriver)driver).getCapabilities();
		String screenshotname =suiteName+arg0.getName().toString(); 
		try {
		     String screenshot =  utility.screenshot(screenshotname);;
			 System.setProperty("org.uncommons.reportng.escape-output", "false");
			   Reporter.setCurrentTestResult(arg0); //// output gets lost without this entry
			   String browser = "Browser: "+caps.getBrowserName().toUpperCase();
			   String platform = "Platform: "+caps.getPlatform().toString();
			   Reporter.log("<b> <font color='blue'>"+url+"\n"+browser+"\n"+platform+"</font></b>");
			   Reporter.log(
				  "<a title= \"title\" href=\"" + screenshot + "\">" +
				 "<img width=\"700\" height=\"550\" src=\"" + screenshot +
				  "\"></a>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			int result=arg0.getStatus();
		      String testcase =suiteName+arg0.getName().toString(); 
		     Properties prop = new Properties();
		     if(result ==1){
		      prop.put(testcase, "PASS");
		      test.log(LogStatus.PASS, arg0.getName()+"Screencast below:" + test.addScreenCapture("./html/"+screenshotname+".png"),suiteName+arg0.getName());
		     }else if(result ==2){
		      prop.put(testcase, "FAIL"); 
		      test.log(LogStatus.FAIL, arg0.getName()+"Screencast below:" + test.addScreenCapture("./html/"+screenshotname+".png"),arg0.getThrowable());
		     }else if(result ==3){
		      prop.put(testcase, "SKIP"); 
		      test.log(LogStatus.SKIP, arg0.getName()+"Screencast below:" + test.addScreenCapture("./html/"+screenshotname+".png"),arg0.getThrowable());
		     }
		     try {
		      FileInputStream fs = new FileInputStream(workspace + "file.Properties");
		      prop.load(fs);
		      fs.close();
		      FileOutputStream fos = new FileOutputStream(workspace + "file.Properties");
		      prop.store(fos, "Test Result");
	
		      fos.flush();
		     } catch (IOException e) { 
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		     }
			
			report.endTest(test);
			report.flush();
		}
		@Override
		public void onFinish(ITestContext arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStart(ITestContext arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTestFailure(ITestResult arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTestSkipped(ITestResult arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTestStart(ITestResult arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTestSuccess(ITestResult arg0) {
			// TODO Auto-generated method stub
			
		}

}
	

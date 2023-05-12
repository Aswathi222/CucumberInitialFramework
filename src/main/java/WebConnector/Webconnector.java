package WebConnector;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.core.api.Scenario;

public class Webconnector  {
	Assert asrt;
	WebElement WebElement;
	public static WebDriver driver=null;
	public  SessionId session=null;
	public static Properties prop = new Properties();
	public Select select;
	
	public Webconnector(){
    	try {
    		prop.load( new FileInputStream("./src/test/config/application.properties") );
    	} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public WebDriver getDriver() {
		return this.getDriver();
	}
	
	public void setDriver(WebDriver driver){
		this.driver=driver;
	}
	
    public void setUpDriver(){
        String browser = prop.getProperty("browser");
        if (browser == null) {
            browser = "chrome";
        }
        switch (browser) {
            case "chrome":
            	System.setProperty("webdriver.chrome.driver","./src/test/lib/chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("start-maximized");
            	   //session = ((ChromeDriver)driver).getSessionId();
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
            	System.setProperty("webdriver.gecko.driver","./src/test/lib/geckodriver.exe");
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                 //session = ((FirefoxDriver)driver).getSessionId();
                break;
            default:
                throw new IllegalArgumentException("Browser \"" + browser + "\" isn't supported.");
        }
    }
    
    public void closeDriver(Scenario scenario){
        if(scenario.isFailed()){
           saveScreenshotsForScenario(scenario);
        }
        driver.close();
    }

    private void saveScreenshotsForScenario(final Scenario scenario) {
        final byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png");
    }
    
    public void waitForPageLoad(int timeout){
       ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";");
    }

    public String getSpecificColumnData(String FilePath, String SheetName, String ColumnName) throws InvalidFormatException, IOException {
    	FileInputStream fis = new FileInputStream(FilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(SheetName);
        XSSFRow row = sheet.getRow(0);
        int col_num = -1;
        for(int i=0; i < row.getLastCellNum(); i++)
        {
            if(row.getCell(i).getStringCellValue().trim().equals(ColumnName))
                col_num = i;
        }
        row = sheet.getRow(1);
        XSSFCell cell = row.getCell(col_num);
        String value = cell.getStringCellValue();
        fis.close();
        System.out.println("Value of the Excel Cell is - "+ value);    	 
    	return value;
    }
    
    public void setSpecificColumnData(String FilePath, String SheetName, String ColumnName) throws IOException{
    	FileInputStream fis;
		fis = new FileInputStream(FilePath);
		FileOutputStream fos = null;
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(SheetName);
        XSSFRow row = null;
        XSSFCell cell = null;
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        int col_Num = -1;
        row = sheet.getRow(0);
        for(int i = 0; i < row.getLastCellNum(); i++)
        {
            if(row.getCell(i).getStringCellValue().trim().equals(ColumnName))
            {
                col_Num = i;
            }
        }
        row = sheet.getRow(1);
        if(row == null)
            row = sheet.createRow(1);
        cell = row.getCell(col_Num);
        if(cell == null)
            cell = row.createCell(col_Num);
        font.setFontName("Comic Sans MS");
        font.setFontHeight(14.0);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
        style.setFillForegroundColor(HSSFColor.GREEN.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);
        cell.setCellValue("PASS");
		fos = new FileOutputStream(FilePath);
        workbook.write(fos);
        fos.close();
    }
    
    public By getElementWithLocator(String WebElement) throws Exception {
		String locatorTypeAndValue = prop.getProperty(WebElement);
		String[] locatorTypeAndValueArray = locatorTypeAndValue.split(",");
		String locatorType = locatorTypeAndValueArray[0].trim();
		String locatorValue = locatorTypeAndValueArray[1].trim();
		switch (locatorType.toUpperCase()) {
		case "ID":
			return By.id(locatorValue);
		case "NAME":
			return By.name(locatorValue);
		case "TAGNAME":
			return By.tagName(locatorValue);
		case "LINKTEXT":
			return By.linkText(locatorValue);
		case "PARTIALLINKTEXT":
			return By.partialLinkText(locatorValue);
		case "XPATH":
			return By.xpath(locatorValue);
		case "CSS":
			return By.cssSelector(locatorValue);
		case "CLASSNAME":
			return By.className(locatorValue);
		default:
			return null;
		}
    }
    
    public WebElement FindAnElement(String WebElement) throws Exception{
    	return driver.findElement(getElementWithLocator(WebElement));
    }
    
    public void PerformActionOnElement(String WebElement, String Action, String Text) throws Exception {
    	switch (Action) {
		case "Click":
			FindAnElement(WebElement).click();
			break;
		case "Type":
			FindAnElement(WebElement).sendKeys(Text);
			break;
		case "Clear":
			FindAnElement(WebElement).clear();
			break;
		case "WaitForElementDisplay":
			waitForCondition("Presence",WebElement,60);
			break;
		case "WaitForElementClickable":
			waitForCondition("Clickable",WebElement,60);
			break;
		case "ElementNotDisplayed":
			waitForCondition("NotPresent",WebElement,60);
			break;
		default:
			throw new IllegalArgumentException("Action \"" + Action + "\" isn't supported.");
		}
    }
    
    public void waitForCondition(String TypeOfWait, String WebElement, int Time){
    	try {
	    	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Time, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(Exception.class);
	    	switch (TypeOfWait)
	    	{
	    	case "PageLoad":
	    		wait.until( ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
	    		break;
	    	case "Clickable":
	    		wait.until(ExpectedConditions.elementToBeClickable(FindAnElement(WebElement)));
	    		break;
	    	case "Presence":
	    		wait.until(ExpectedConditions.presenceOfElementLocated(getElementWithLocator(WebElement)));
	    		break;
	    	case "Visibility":
	    		wait.until(ExpectedConditions.visibilityOfElementLocated(getElementWithLocator(WebElement)));
	    		break;
	    	case "NotPresent":
	    		wait.until(ExpectedConditions.invisibilityOfElementLocated(getElementWithLocator(WebElement)));
	    		break;
	    	default:
	    		Thread.sleep(Time*1000);
	    	}
    	}
	    	catch(Exception e)
	    	{
	    		throw new IllegalArgumentException("wait For Condition \"" + TypeOfWait + "\" isn't supported.");
	    	}
    }
    
    public void selectorByVisibleText(String locator,String text) throws Exception {     //drpdown
		WebElement = FindAnElement(locator);
		highLighterMethod(driver, WebElement); 
		select = new Select(WebElement);
	    select.selectByVisibleText(text);
	   
   }
    
    public void highLighterMethod(WebDriver driver, WebElement element){
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    String bgColor = element.getCssValue("backgroundColor");
	        js.executeScript("arguments[0].setAttribute('style', 'background: "+bgColor+"; border: 2px solid red;');", element);
	       
	        }
    
    public void selectCheckBox(String locator) throws Exception {
 	   WebElement  = FindAnElement(locator);
 	    boolean isSelected = WebElement.isSelected();
 		//performing click operation if element is not checked
 	 if(isSelected == false) {
 		 WebElement.click();
 	    
 	  }
    }
    
    public boolean validateText(String locator) throws Exception {
		WebElement = FindAnElement(locator);
		boolean status = WebElement.isDisplayed();
		asrt.assertTrue(status);
		return status;		
	}
    
    public void switchTab(int index) throws Exception {
		
    	List<String> newTab = new ArrayList<String>(driver.getWindowHandles());
    	driver.switchTo().window(newTab.get(index));
	}
    
    
   
}
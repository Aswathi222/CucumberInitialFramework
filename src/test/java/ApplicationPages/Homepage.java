package ApplicationPages;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;

import WebConnector.webconnector;
import static WebConnector.webconnector.driver;
import java.io.IOException;

public class Homepage {
	webconnector wc=new webconnector();

    public void goToHomePage() throws InvalidFormatException, IOException{
    	String URL=wc.getSpecificColumnData("./src/test/testdata/data.xlsx","sheet1", "URL");
        driver.get(URL);
        wc.waitForCondition("PageLoad","",60);
    }

    public void selectCareersLink() throws Exception {
    	
    	wc.waitForCondition("Clickable", "CareersLink",60);
    	wc.PerformActionOnElement("CareersLink", "Click","");
    	    	
    }
    
   public void checkOpenPositionPage() throws Exception {
    	
    	wc.waitForCondition("Visibility", "OpenPositions",60);   	
    	Assert.assertTrue(wc.FindAnElement("OpenPositions").isDisplayed(),"Open position page reached successfully");
    	
    }
    
}
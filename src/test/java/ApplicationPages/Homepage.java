package ApplicationPages;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;


import WebConnector.Webconnector;
import static WebConnector.Webconnector.driver;
import java.io.IOException;

public class Homepage {
	Webconnector wc=new Webconnector();
	

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
	    wc.switchTab(1);
    	wc.waitForCondition("Visibility", "OpenPositions",60);   	
    	Assert.assertTrue(wc.FindAnElement("OpenPositions").isDisplayed(),"Open position page reached successfully");
    	System.out.println("hvhfvfv");
    	Thread.sleep(4000);
    	wc.switchTab(0);
    	
    }
    
}
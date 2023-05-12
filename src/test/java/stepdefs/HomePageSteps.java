package stepdefs;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import ApplicationPages.Homepage;
import WebConnector.Webconnector;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class HomePageSteps extends Webconnector {
    private Homepage homePage;
	private String scenDesc;

    public HomePageSteps() {
        this.homePage = new Homepage();
    }
    
    @Before
	public void before(Scenario scenario) {
		this.scenDesc = scenario.getName();
		setUpDriver();
	}
    
    @After
    public void after(Scenario scenario){
    	closeDriver(scenario);
    }
	
	@BeforeStep
	public void beforeStep() throws InterruptedException {
		Thread.sleep(2000);
	}

    
    @Given("^User navigates to SEmentor HomePage$")
    public void user_navigates_to_sementor_homepage() throws Throwable {
    	this.homePage.goToHomePage();
    }

    @Then("^User click on careers page$")
    public void user_click_on_careers_page() throws Throwable {
    	homePage.selectCareersLink();
    }
    
    @Then("^User check whether open postion title is displyed$")
    public void user_check_whether_open_postion_title_is_displyed() throws Throwable {
        homePage.checkOpenPositionPage();
    }

    
}
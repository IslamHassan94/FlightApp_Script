package com.eurowings.stepDef;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.eurowings.pages.CookieConsentPage;
import com.eurowings.pages.FlightStatusPage;
import com.eurowings.util.DateUtil;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FlightStatusPageStepDef {
    private WebDriver driver;
    private FlightStatusPage flightStatusPage;
    private CookieConsentPage cookieConsentPage;

    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterStep
    public void takeScreenshotAfterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            try {
                FileOutputStream fos = new FileOutputStream("screenshots/" + scenario.getName() + ".png");
                fos.write(screenshot);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("the user navigates to the flight status page")
    public void the_user_navigates_to_the_flight_status_page() {
        driver.get("https://www.eurowings.com/en/information/at-the-airport/flight-status.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        cookieConsentPage = new CookieConsentPage(driver);
        flightStatusPage = new FlightStatusPage(driver);
        cookieConsentPage.acceptCookie();
    }

    @When("the user enters {string} and {string} as the flight route")
    public void the_user_enters_route(String departure, String destination) {
        flightStatusPage.fillRoutes(departure, destination);
    }

    @When("the user selects {string} as the travel date")
    public void the_user_selects_date(String date) {
        String dateValue = DateUtil.getDate(date);
        flightStatusPage.fillDate(dateValue);
    }

    @And("the user clicks the show flight status button")
    public void the_user_clicks_the_show_flight_status_button() {
        flightStatusPage.clickShowFlightStatusBtn();
    }

    @Then("the search results should display flights from {string} to {string}")
    public void the_search_results_should_display_flights(String departure, String destination) {
        Assert.assertTrue(flightStatusPage.checkCorrectAirports(departure, destination));
    }

    @Then("the results should be for the date {string}")
    public void the_results_should_be_for_the_date(String date) {
        String dateValue = DateUtil.getDate(date);
        Assert.assertEquals(dateValue, flightStatusPage.getDateResult());
    }

    @When("the user enters {string} as the flight number")
    public void the_user_enters_as_the_flight_number(String flightNumber) {
        flightStatusPage.fillFlightNumberField(flightNumber);
    }

    @Then("the search results should display the route for flight {string}")
    public void the_search_results_should_display_the_route(String flightNumber) {
        Assert.assertTrue(flightStatusPage.checkFlightNumbers(flightNumber));
    }

    @And("the flight status should be displayed")
    public void the_flight_status_should_be_displayed() {
        Assert.assertTrue(flightStatusPage.isFlightStatusDisplayed());
    }

    @Then("a message is displayed to inform the user")
    public void a_message_is_displayed_to_inform_the_user() {
        Assert.assertTrue(flightStatusPage.isNoResultMessageDisplayed());
    }

    @Then("the submit button is disabled")
    public void the_submit_button_is_disabled() {
        Assert.assertTrue(flightStatusPage.isShowFlightStatusBtnDisabled());
    }

}

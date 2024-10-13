package com.eurowings.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.eurowings.util.DateUtil;

public class FlightStatusPage {
	private WebDriver driver;
	private WebDriverWait wait;
	private By departureButton = By.xpath("//button//span[contains(text(),'Departure')]");
	private By departureInput = By.xpath("//input[@aria-label='Departure airport']");
	private By destinationButton = By.xpath("//button//span[contains(text(),'Destination')]");
	private By destinationInput = By.xpath("//input[@aria-label='Destination airport']");
	private By dateButton = By.xpath("//input[contains(@name, 'datepicker')]");
//    private By showFlightStatusBtn = By.xpath("//button[@type='submit']//span[contains(text(),'Show')]");
	private By showFlightStatusBtn = By
			.xpath("//button[@type='submit']//parent::div//span//span[.='Show flight status']");
	private By checkBoxes = By.xpath("//input[@name='search-method']");
	private By flightNumberInput = By.name("flightNumber");
	private By allResultedAirports = By.xpath("//div[@class='o-search-flight-status__card-airports']");
	private By getAllResultedFlightNumbers = By.xpath("//div[@class='o-search-flight-status__card-flight-number']");
	private By allResultedStatues = By.xpath("//div[contains(@class, 'o-search-flight-status__card-flight-status')]");
	private By resultedDate = By.xpath(
			"//button[@class='o-search-flight-status__date-navigation__date o-search-flight-status__date-navigation__date--active o-search-flight-status__date-navigation__date--align-center']//div");
	private By noResultsMessage = By.xpath("//h2[contains(text(),'Unfortunately')]");
//	private By disabledSubmitButton = By.xpath("//button[@disabled='disabled']//span[contains(text(),'Show')]");
	private By disabledSubmitButton = By.xpath("//button[@disabled]//parent::div//span//span[.='Show flight status']");

	public FlightStatusPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(25));
	}

	public boolean isShowFlightStatusBtnDisabled() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(disabledSubmitButton));
		return driver.findElement(disabledSubmitButton).isDisplayed();
	}

	public void fillRoutes(String departure, String destination) {
		fillDepartureField(departure);
		fillDestinationField(destination);
	}

	public void fillDate(String date) {
		if (date == null || date.isEmpty())
			return;
		driver.findElement(dateButton).click();
		WebElement desiredDate = driver.findElement(By.xpath("//input[@value='" + date.trim() + "']"));
		desiredDate.click();
		wait.until(ExpectedConditions.invisibilityOf(desiredDate));
	}

	public void clickShowFlightStatusBtn() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(showFlightStatusBtn)).click();
	}

	public void fillFlightNumberField(String flightNumber) {
		clickFlightNumberCheckbox();
		wait.until(ExpectedConditions.visibilityOfElementLocated(flightNumberInput)).sendKeys(flightNumber);
	}

	private void clickFlightNumberCheckbox() {
		driver.findElements(checkBoxes).get(1).click();
	}

	private void fillDepartureField(String departure) {
		driver.findElement(departureButton).click();
		driver.findElement(departureInput).sendKeys(departure + Keys.ENTER);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(departureInput));
	}

	private void fillDestinationField(String destination) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		wait.until(ExpectedConditions.elementToBeClickable(destinationButton)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(destinationInput)).sendKeys(destination + Keys.ENTER);
	}

	public boolean checkCorrectAirports(String departure, String destination) {
		boolean correct = true;
		List<WebElement> rows = driver.findElements(allResultedAirports);
		for (int i = 0; i < rows.size(); i++) {
			String depText = rows.get(i)
					.findElements(By.xpath("//div[@class='o-search-flight-status__card-airports']/child::p")).get(0)
					.getText();
			String desText = rows.get(i)
					.findElements(By.xpath("//div[@class='o-search-flight-status__card-airports']/child::p")).get(1)
					.getText();
			System.out.println("0-> " + depText);
			System.out.println("1-> " + desText);
			if (depText.equals(departure) && desText.equals(destination)) {
				continue;
			} else {
				correct = false;
				break;
			}
		}
		return correct;
	}

	public boolean checkFlightNumbers(String flightNumber) {
		boolean correct = true;
		List<WebElement> rows = driver.findElements(getAllResultedFlightNumbers);
		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i).getText().equals(flightNumber)) {
				continue;
			} else {
				correct = false;
				break;
			}
		}
		return correct;
	}

	public boolean isFlightStatusDisplayed() {
		boolean displayed = true;
		List<WebElement> allStatus = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allResultedStatues));
		for (int i = 0; i < allStatus.size(); i++) {
			if (allStatus.get(i).isDisplayed()) {
				continue;
			} else {
				displayed = false;
				break;
			}
		}
		return displayed;
	}

	public String getDateResult() {
		String oldDate = wait.until(ExpectedConditions.visibilityOfElementLocated(resultedDate)).getText();
		String newDate = DateUtil.convertDateFormat(oldDate);
		return newDate;
	}

	public boolean isNoResultMessageDisplayed() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(noResultsMessage)).isDisplayed();
	}

}

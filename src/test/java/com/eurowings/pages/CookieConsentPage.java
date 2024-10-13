package com.eurowings.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CookieConsentPage {
	private WebDriverWait wait;
	private By acceptCookieBtn = By.xpath("//span[contains(text(),'I understand')]");

	public CookieConsentPage(WebDriver driver) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	public void acceptCookie() {
		wait.until(ExpectedConditions.elementToBeClickable(acceptCookieBtn)).click();
	}
}

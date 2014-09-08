package com.ignite.testtask.testing.manager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestDriver {
	
	private WebDriver driver;
	private TestLogger log;
	
	public TestDriver(WebDriver driver, TestLogger log) {
		this.driver = driver;
		this.log = log;
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public boolean loadPage(String url) {
		long start = System.currentTimeMillis();
		driver.get(url);
		String time = getTime(System.currentTimeMillis(), start);
		log.log("Page at " + log.getLink(url) + " loaded successfully in " + time + " Sec.");
		if(driver.getCurrentUrl().equals(url)) {
			return true;
		}else {
			log.indent("Loaded url did not match target url. Current url: " + log.getLink(driver.getCurrentUrl()));
			return false;
		}
	}
	
	public WebElement findElement(By locator) throws TestCaseFailureException {
		try {
			WebElement element = driver.findElement(locator);
			log.log("Element located " + locator.toString() + " successfully located on page " + log.getLink(driver.getCurrentUrl()) + ".");
			return element;
		} catch (Exception e) {
			log.log("Element located " + locator.toString() + " could not be found on page " + log.getLink(driver.getCurrentUrl()) + ".");
			throw new TestCaseFailureException();
		}
	}
	
	public String click(WebElement toClick) {
		String currentUrl = driver.getCurrentUrl();
		String elementText = toClick.getText();
		long start = System.currentTimeMillis();
		toClick.click();
		String time = getTime(System.currentTimeMillis(), start);
		log.log("Clicked element " + elementText);
		if(!driver.getCurrentUrl().equals(currentUrl)) {
			log.indent("Element click loaded page " + driver.getCurrentUrl() + " in " + time + " Sec.");
		}
		return driver.getCurrentUrl();
	}
	
	private String getTime(long currentTimeMillis, long start) {
		long elapsedMs = currentTimeMillis - start;
		double elapsedSec = elapsedMs/1000.0;
		return String.format("%.2f", elapsedSec);
	}

	public void elementShouldNotExist(By locator) {
		try {
			driver.findElement(locator);
			log.log("Element located " + locator.toString() + " was found but should not have existed. Page: " + log.getLink(driver.getCurrentUrl()) + ".");
			throw new TestCaseFailureException();
		} catch (Exception e) {
			log.log("Element located " + locator.toString() + " should not exist and was not found on page " + log.getLink(driver.getCurrentUrl()) + ".");
		}
		
	}
 	
}

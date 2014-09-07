package com.ignite.testtask.testing.testcases;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.ignite.testtask.testing.manager.ITestCase;

public class TestCaseOne implements ITestCase{
	
	private ArrayList<String> log = new ArrayList<String>();
	private HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_10);
	
	public String getName() {
		return "Test Case One!";
	}

	public ArrayList<String> getLiveLog() {
		return log;
	}

	public void preTest() {
		driver.setJavascriptEnabled(true);
		driver.get(TestData.TARGET_URL);
		if(driver.getCurrentUrl().equals(TestData.TARGET_URL)) {
			log.add("Loaded " + TestData.TARGET_URL + " successfully.<br>");
		}else {
			log.add("Loaded " + TestData.TARGET_URL + " with warnings.<br>");
			log.add("<blockquote>Expected url " + TestData.TARGET_URL + " but actual url was " + driver.getCurrentUrl() +"</blockquote>");
		}
		log.add("Pre Test Complete!<br>");
	}

	public void test() {
		WebElement header = driver.findElementByXPath(TestData.HEADER);
		log.add("Header Text: " + header.getText() + "<br>");
		if(header.getText().equals("Home page")) {
			log.add("Header has correct text.<br>");
		}else {
			log.add("Header has incorrect text.<br>");
			
		}
		WebElement team = driver.findElementByXPath(TestData.NEW_TEAM_XPATH);
		log.add("Team Text: " + team.getText() + "<br>");
		if(team.getText().equals("Add new team")) {
			log.add("Team link has correct text.<br>");
		}else {
			log.add("Team link  has incorrect text.<br>");
			
		}
		WebElement list = driver.findElementByXPath(TestData.TEAM_LIST_XPATH);
		log.add("list Text: " + list.getText() + "<br>");
		if(list.getText().equals("Team list")) {
			log.add("Team list link has correct text.<br>");
		}else {
			log.add("Team list link has incorrect text.<br>");
			
		}
		log.add("Test Complete!<br>");
	}

	public void postTest() {
		log.add("Post Test Complete!<br>");
	}

}

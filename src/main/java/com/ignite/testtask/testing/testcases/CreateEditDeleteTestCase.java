package com.ignite.testtask.testing.testcases;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.ignite.testtask.testing.manager.ITestCase;
import com.ignite.testtask.testing.manager.TestCaseFailureException;
import com.ignite.testtask.testing.manager.TestDriver;
import com.ignite.testtask.testing.manager.TestLogger;

public class CreateEditDeleteTestCase implements ITestCase{
	
	
	private static final String LOWLY_RATED_TEAM = "LowlyRatedTeam";
	private static final String HIGHLY_RATED_TEAM = "HighlyRatedTeam";
	private TestDriver driver;
	private TestLogger log;
	
	public CreateEditDeleteTestCase() {
		log = new TestLogger();
		HtmlUnitDriver htmlUnit = new HtmlUnitDriver(BrowserVersion.FIREFOX_10);
		htmlUnit.setJavascriptEnabled(true);
		driver = new TestDriver(htmlUnit, log);
	}
	
	public String getName() {
		return "CreateEditDelete";
	}

	public ArrayList<String> getLiveLog() {
		return log.getOutput();
	}

	public void preTest() {
		driver.loadPage(TestData.TARGET_URL);
		log.log("Pre Test Complete!");
	}

	public void test() {
		try {
			WebElement newTeam = driver.findElement(By.xpath(TestData.NEW_TEAM_XPATH));
			driver.click(newTeam);
			createTeam(HIGHLY_RATED_TEAM, 100);
			WebElement addAnother = driver.findElement(By.xpath(TestData.ADD_ANOTHER));
			driver.click(addAnother);
			createTeam(LOWLY_RATED_TEAM, 10);
			WebElement teamListLink = driver.findElement(By.xpath(TestData.TEAM_LIST_LINK));
			driver.click(teamListLink);
			
			WebElement row1TeamName = driver.findElement(By.xpath(TestData.TEAM_TABLE_NAME_ROW1));
			WebElement row1TeamRate = driver.findElement(By.xpath(TestData.TEAM_TABLE_RATE_ROW1));
			validateTeam(row1TeamName, HIGHLY_RATED_TEAM, row1TeamRate, 100, 1);
			
			WebElement row2TeamName = driver.findElement(By.xpath(TestData.TEAM_TABLE_NAME_ROW2));
			WebElement row2TeamRate = driver.findElement(By.xpath(TestData.TEAM_TABLE_RATE_ROW2));
			validateTeam(row2TeamName, LOWLY_RATED_TEAM, row2TeamRate, 10, 1);
			
			WebElement row1TeamEdit = driver.findElement(By.xpath(TestData.TEAM_TABLE_EDIT_ROW1));
			driver.click(row1TeamEdit);
			
			WebElement nameField = driver.findElement(By.xpath(TestData.TEAM_NAME_FIELD));
			WebElement rateField = driver.findElement(By.xpath(TestData.TEAM_RATE_FIELD));
			WebElement editButton = driver.findElement(By.xpath(TestData.TEAM_SUBMIT));
			nameField.sendKeys("_EDITED");
			rateField.sendKeys("5");
			editButton.click();
			log.log("Edited team  " + HIGHLY_RATED_TEAM + " with rating " + 100);
			log.indent("New name: " + HIGHLY_RATED_TEAM + "_EDITED");
			log.indent("New rate: " + 1005);
			teamListLink = driver.findElement(By.xpath(TestData.TEAM_LIST_LINK));
			driver.click(teamListLink);
			
			WebElement editedRow1TeamName = driver.findElement(By.xpath(TestData.TEAM_TABLE_NAME_ROW1));
			WebElement editedRow1TeamRate = driver.findElement(By.xpath(TestData.TEAM_TABLE_RATE_ROW1));
			validateTeam(editedRow1TeamName, HIGHLY_RATED_TEAM + "_EDITED", editedRow1TeamRate, 1005, 1);
			
			WebElement row1TeamDelete = driver.findElement(By.xpath(TestData.TEAM_TABLE_DELETE_ROW1));
			driver.click(row1TeamDelete);
			log.log("Removed team in row one with name: " + HIGHLY_RATED_TEAM);
			teamListLink = driver.findElement(By.xpath(TestData.TEAM_LIST_LINK));
			driver.click(teamListLink);
			
			log.log("Team with name: " + LOWLY_RATED_TEAM + " should now be in row one.");
			row1TeamName = driver.findElement(By.xpath(TestData.TEAM_TABLE_NAME_ROW1));
			row1TeamRate = driver.findElement(By.xpath(TestData.TEAM_TABLE_RATE_ROW1));
			validateTeam(row1TeamName, LOWLY_RATED_TEAM, row1TeamRate, 10, 1);
			
			row1TeamDelete = driver.findElement(By.xpath(TestData.TEAM_TABLE_DELETE_ROW1));
			driver.click(row1TeamDelete);
			log.log("Removed team now in row one with name: " + LOWLY_RATED_TEAM);
			teamListLink = driver.findElement(By.xpath(TestData.TEAM_LIST_LINK));
			driver.click(teamListLink);
			
			log.log("With zero teams the table should be empty.");
			driver.elementShouldNotExist(By.xpath(TestData.TEAM_TABLE_BODY));
			
			log.log("Test Complete with verdict: Success!");
		} catch (TestCaseFailureException e) {
			log.log("Test Complete with verdict: Failure!");
			e.printStackTrace();
		}
	}

	private void validateTeam(WebElement displayedTeamName, String expectedTeamName, WebElement displayedTeamRate, int expectedTeamRate, int row) throws TestCaseFailureException {
		log.log("Expecting team in row " + row + " with name: " + expectedTeamName + " and Rate: " + expectedTeamRate + " on page: " + log.getLink(driver.getDriver().getCurrentUrl()));
		String name = displayedTeamName.getText();
		int rate = Integer.parseInt(displayedTeamRate.getText());
		log.indent("Displayed name: " + name);
		log.indent("Displayed rate: " + rate);
		if(!name.equals(expectedTeamName) || rate != expectedTeamRate) {
			throw new TestCaseFailureException();
		}
	}

	private void createTeam(String name, int rating) throws TestCaseFailureException {
		WebElement nameText = driver.findElement(By.xpath(TestData.TEAM_NAME_FIELD));
		WebElement ratingText = driver.findElement(By.xpath(TestData.TEAM_RATE_FIELD));
		WebElement addButton = driver.findElement(By.xpath(TestData.TEAM_SUBMIT));
		nameText.sendKeys(name);
		ratingText.sendKeys(rating + "");
		addButton.click();
		log.log("Created team  " + name + " with rating " + rating);
	}

	public void postTest() {
		driver.loadPage("http://ignitetestingtask.felmlyd.cloudbees.net/team/list.html");
		try {
			while(true) {
				WebElement delete = driver.findElement(By.linkText("Delete"));
				delete.click();
				WebElement backToList = driver.findElement(By.xpath(TestData.TEAM_LIST_LINK));
				backToList.click();
			}
		}catch(Exception e) {
			
		}
		log.log("Post Test Complete!");
	}

}

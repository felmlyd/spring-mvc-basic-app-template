package com.ignite.testtask.testing.testcases;

import java.util.ArrayList;

import com.ignite.testtask.testing.manager.ITestCase;

public class TestCaseTwo implements ITestCase{
	
	private ArrayList<String> log = new ArrayList<String>();
	
	public String getName() {
		return "Test Case Two!";
	}

	public ArrayList<String> getLiveLog() {
		return log;
	}

	public void preTest() {
		log.add("PRE TEST!<br>");
	}

	public void test() {
		log.add("TEST!<br>");
	}

	public void postTest() {
		log.add("POST TEST<br>");
	}

}

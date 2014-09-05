package com.ignite.testtask.testing.manager;

import java.util.ArrayList;

import com.ignite.testtask.testing.testcases.TestCaseOne;
import com.ignite.testtask.testing.testcases.TestCaseTwo;

public class TestManager {
	
	public static ArrayList<ITestCase> caseList = new ArrayList<ITestCase>();
	
	public static void init() {
		caseList.add(new TestCaseOne());
		caseList.add(new TestCaseTwo());
	}
	
	public static ArrayList<String> getCaseNames() {
		ArrayList<String> caseNames = new ArrayList<String>();
		for(ITestCase tc : caseList) {
			caseNames.add(tc.getName());
		}
		return caseNames;
	}
	
	public static ITestCase getTestCaseByName(String name) {
		for(ITestCase tc : caseList) {
			if(tc.getName().equals(name)) {
				return tc;
			}
		}
		return null;
	}
	
	public static void runTests() {
		for(ITestCase tc : caseList) {
			final ITestCase ftc = tc;
			Thread thread = new Thread(new Runnable() {
				
				public void run() {
					ftc.preTest();
					ftc.test();
					ftc.postTest();
				}
			});
			thread.start();
		}
	}
}

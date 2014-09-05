package com.ignite.testtask.testing.manager;

import java.util.ArrayList;

public interface ITestCase {
	
	public String getName();
	
	public ArrayList<String> getLiveLog();
	
	public void preTest();
	
	public void test();
	
	public void postTest();
	
}

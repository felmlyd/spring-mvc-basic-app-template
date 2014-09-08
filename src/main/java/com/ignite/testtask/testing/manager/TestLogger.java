package com.ignite.testtask.testing.manager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.By.ByPartialLinkText;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.By.ByXPath;

public class TestLogger {
	private static final String INDENT = "&nbsp&nbsp&nbsp&nbsp&nbsp";
	private ArrayList<String> logs = new ArrayList<String>();
	
	public TestLogger() {
	}
	
	public void log(String text) {
		logs.add(getTimeStamp() + text + "<br>");
	}
	
	public void indent(String text) {
		log(INDENT + text);
	}
	
	public ArrayList<String> getOutput() {
		return logs;
	}
	
	private String getTimeStamp() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return df.format(date) + ": ";
	}
	
	public String getLink(String url) {
		String link = "<a href=\"url\">";
		link = link + url + "</a>";
		return link;
	}
	
	public String byType(By loc) {
		if(loc instanceof ByClassName) {
			return "class";
		}else if(loc instanceof ByCssSelector) {
			return "css selector";
		}else if(loc instanceof ByLinkText) {
			return "link text";
		}else if(loc instanceof ByName) {
			return "name";
		}else if(loc instanceof ByPartialLinkText) {
			return "partial link text";
		}else if(loc instanceof ByTagName) {
			return "tag name";
		}else if(loc instanceof ByXPath) {
			return "xpath";
		}else {
			return "id";
		}
	}
}

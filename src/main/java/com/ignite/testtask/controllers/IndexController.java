package com.ignite.testtask.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ignite.testtask.testing.manager.ITestCase;
import com.ignite.testtask.testing.manager.TestManager;

@Controller
public class IndexController {

	@RequestMapping(value="/")
	public ModelAndView mainPage() {
		return getIndextView();
	}

	@RequestMapping(value="/index")
	public ModelAndView indexPage() {
		return getIndextView();
	}
    
	private ModelAndView getIndextView() {
		ModelAndView index = new ModelAndView("index");
		List<String> cases = TestManager.getCaseNames();
		index.addObject("cases", cases);
		return index;
	}
	
	@RequestMapping(value="/cases/status/{id}")
	public ModelAndView editTeamPage(@PathVariable String id) {
		ModelAndView tcView = new ModelAndView("TestCase");
		ITestCase tc = TestManager.getTestCaseByName(id);
		tcView.addObject("name", tc.getName());
		tcView.addObject("log", tc.getLiveLog());
		return tcView;
	}
}

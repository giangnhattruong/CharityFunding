package com.funix.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getCoverPage() {
		ModelAndView mv =
				new ModelAndView();
		
		mv.setViewName("index");
		
        return mv;
	}
	
	@RequestMapping(value = "/explore",
			method = RequestMethod.GET)
	public ModelAndView getLandingPage() {
		ModelAndView mv =
				new ModelAndView();
		
		// Get and send campaign object
		addNavItemMap(mv);
		mv.setViewName("main/explore");
		
        return mv;
	}
	
	@RequestMapping(value = "/about",
			method = RequestMethod.GET)
	public ModelAndView getAboutPage() {
		ModelAndView mv =
				new ModelAndView();
		
		addNavItemMap(mv);
		mv.setViewName("main/about");
		
		return mv;
	}
	
	@RequestMapping(value = "/contact",
			method = RequestMethod.GET)
	public ModelAndView getContactPage() {
		ModelAndView mv =
				new ModelAndView();
		
		addNavItemMap(mv);
		mv.setViewName("main/contact");
		
		return mv;
	}
	
	@RequestMapping(value = "/campaign",
			method = RequestMethod.GET)
	public ModelAndView getCampaignPage(@RequestParam int id) {
		ModelAndView mv =
				new ModelAndView();
		
		// Get and send campaign object
		addNavItemMap(mv);
		mv.setViewName("main/campaignDetails");
		
		return mv;
	}

	private void addNavItemMap(ModelAndView mv) {
		Map<String, String> navItemMap = 
				new HashMap<>();
		
		navItemMap.put("About", "/about");
		navItemMap.put("Contact", "/contact");

		mv.addObject("navItemMap", navItemMap);
	}
	
}

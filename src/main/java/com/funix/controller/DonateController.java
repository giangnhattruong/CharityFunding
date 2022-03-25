package com.funix.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/donate")
public class DonateController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getDonatePage() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and send userId, campaignID
		addNavItemMap(mv);
		mv.setViewName("main/donate");
		
        return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processDonate() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and add history object to DB
		addNavItemMap(mv);
		mv.addObject("message", "Thank you for your support. Your help will save many hoping hearts!")
		mv.setViewName("main/success");
		
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

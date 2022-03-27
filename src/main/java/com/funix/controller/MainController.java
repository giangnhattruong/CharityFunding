package com.funix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.funix.service.Navigation;

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
		Navigation.addMainNavItemMap(mv);
		mv.setViewName("main/explore");
		
        return mv;
	}
	
	@RequestMapping(value = "/about",
			method = RequestMethod.GET)
	public ModelAndView getAboutPage() {
		ModelAndView mv =
				new ModelAndView();
		
		Navigation.addMainNavItemMap(mv);
		mv.setViewName("main/about");
		
		return mv;
	}
	
	@RequestMapping(value = "/contact",
			method = RequestMethod.GET)
	public ModelAndView getContactPage() {
		ModelAndView mv =
				new ModelAndView();
		
		Navigation.addMainNavItemMap(mv);
		mv.setViewName("main/contact");
		
		return mv;
	}
	
	@RequestMapping(value = "/campaign",
			method = RequestMethod.GET)
	public ModelAndView getCampaignPage(@RequestParam int id) {
		ModelAndView mv =
				new ModelAndView();
		
		// Get and send campaign object
		Navigation.addMainNavItemMap(mv);
		mv.setViewName("main/campaignDetails");
		
		return mv;
	}
	
}

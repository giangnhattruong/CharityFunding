package com.funix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.funix.service.Navigation;

@Controller
@RequestMapping("/donate")
public class DonateController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getDonationPage() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and send userId, campaignID
		Navigation.addMainNavItemMap(mv);
		mv.setViewName("main/donate");
		
        return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processTransaction() {
		ModelAndView mv =
				new ModelAndView();
		
		//Randomly hash transactionCode to stimulate bank API
		//Get and add history object to DB
		
		Navigation.addMainNavItemMap(mv);
		mv.addObject("message", "Thank you for your support. "
				+ "Your help will save many hoping hearts!");
		mv.setViewName("main/success");
		
        return mv;
	}
	
}

package com.funix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping(value = "campaigns", 
			method = RequestMethod.GET)
	public ModelAndView manageCampaigns() {
		ModelAndView mv =
				new ModelAndView();
		
		mv.setViewName("admin/manageCampaigns");
		
        return mv;
	}
	
}

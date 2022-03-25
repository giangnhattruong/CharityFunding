package com.funix.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getUserDashboard() {
		//if session does not contain userID, redirect to explore
		
		return getHistories();
	}

	@RequestMapping(value = "histories", 
			method = RequestMethod.GET)
	public ModelAndView getHistories() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and send campaignList
		addNavItemMap(mv);
		mv.setViewName("user/histories");
		
        return mv;
	}
	
	@RequestMapping(value = "profile", 
			method = RequestMethod.GET)
	public ModelAndView getProfileForm() {
		ModelAndView mv =
				new ModelAndView();
		
		//-> get and send user object
		addNavItemMap(mv);
		mv.setViewName("user/profile");
		
        return mv;
	}
	
	@RequestMapping(value = "profile", 
			method = RequestMethod.POST)
	public ModelAndView updateProfile() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and update user info in DB
		mv.addObject("message", "Infomation has been successfully updated.");
		mv.setViewName("redirect:/user/histories");
		
		return mv;
	}
	
	@RequestMapping(value = "update-password", 
			method = RequestMethod.GET)
	public ModelAndView getPasswordForm() {
		ModelAndView mv =
				new ModelAndView();
		
		//-> get and send password String
		addNavItemMap(mv);
		mv.setViewName("user/updatePassword");
		
        return mv;
	}
	
	@RequestMapping(value = "update-password", 
			method = RequestMethod.POST)
	public ModelAndView updatePassword() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and update user password in DB
		mv.addObject("message", "Password has been successfully updated.");
		mv.setViewName("redirect:/user/histories");
		
		return mv;
	}
	
	private void addNavItemMap(ModelAndView mv) {
		Map<String, String> navItemMap = 
				new HashMap<>();
		
		navItemMap.put("Histories", "/user/histories");
		navItemMap.put("Profile", "/user/profile");

		mv.addObject("navItemMap", navItemMap);
	}
	
}

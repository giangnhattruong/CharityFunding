package com.funix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.funix.service.Navigation;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String getUserDashboard() {
		return "redirect:/user/donation-history";
	}

	@RequestMapping(value = "donation-history", 
			method = RequestMethod.GET)
	public ModelAndView getHistory() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and send campaignList
		Navigation.addUserNavItemMap(mv);
		mv.setViewName(getRoute("user/donationHistory"));
		
        return mv;
	}
	
	@RequestMapping(value = "update-profile", 
			method = RequestMethod.GET)
	public ModelAndView getProfileForm() {
		ModelAndView mv =
				new ModelAndView();
		
		//-> get and send user object
		Navigation.addUserNavItemMap(mv);
		mv.setViewName(getRoute("user/updateProfile"));
		
        return mv;
	}
	
	@RequestMapping(value = "updateProfile", 
			method = RequestMethod.POST)
	public ModelAndView updateProfile() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and update user info in DB
		mv.addObject("message", "Infomation has been successfully updated.");
		mv.setViewName("redirect:/user/donation-history");
		
		return mv;
	}
	
	@RequestMapping(value = "update-password", 
			method = RequestMethod.GET)
	public ModelAndView getPasswordForm() {
		ModelAndView mv =
				new ModelAndView();
		
		//-> get and send password String
		Navigation.addUserNavItemMap(mv);
		mv.setViewName(getRoute("user/updatePassword"));
		
        return mv;
	}
	
	@RequestMapping(value = "update-password", 
			method = RequestMethod.POST)
	public ModelAndView updatePassword() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and update user password in DB
		mv.addObject("message", "Password has been successfully updated.");
		mv.setViewName("redirect:/user/donation-history");
		
		return mv;
	}
	
	private String getRoute(String url) {
		/*if session not contains userID
		 *redirect to landing page*/
		if (false) {
			return "redirect:/explore";
		}
		
		return url;
	}
	
}

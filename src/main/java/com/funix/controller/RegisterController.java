package com.funix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.funix.service.Navigation;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getRegisterForm() {
		//if session contains userID or adminID, redirect to explore
		
		ModelAndView mv =
				new ModelAndView();
		
		Navigation.addMainNavItemMap(mv);
		mv.setViewName("user/register");
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addUser() {
		ModelAndView mv =
				new ModelAndView();
		
		//add user to DB
		//set session with userID
		//encodeURL(register/verify) and send email
		
		Navigation.addMainNavItemMap(mv);
		mv.addObject("message", "Your account has been created. Please check your email to verify.");
		mv.setViewName("main/success");
		
		return mv;
	}
	
	@RequestMapping(value = "verify",
			method = RequestMethod.GET)
	public ModelAndView verifyUser() {
		ModelAndView mv =
				new ModelAndView();
		
		//session -> get userID
		//updateUserStatus to 1
		
		Navigation.addMainNavItemMap(mv);
		mv.addObject("message", "Your account has been verified. Please login to confirm.");
		mv.setViewName("redirect:/login");
		
		return mv;
	}
	
}

package com.funix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.funix.controller.service.Navigation;

@Controller
@RequestMapping("/login")
public class LoginController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getLoginForm() {
		//if session contains userID or adminID, redirect to explore
		
		ModelAndView mv =
				new ModelAndView();
		
		Navigation.addMainNavItemMap(mv);
		mv.setViewName("user/login");
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView doLogin() {
		ModelAndView mv =
				new ModelAndView();
		
		//HttpSession -> add userID/adminID
		
		Navigation.addMainNavItemMap(mv);
		mv.setViewName("redirect:/explore");
		
		return mv;
	}
	
	@RequestMapping(value = "forgot-password",
			method = RequestMethod.GET)
	public ModelAndView getEmailForm() {		
		ModelAndView mv =
				new ModelAndView();
		
		Navigation.addMainNavItemMap(mv);
		mv.setViewName("user/forgotPassword");
		
		return mv;
	}
	
	@RequestMapping(value = "forgot-password",
			method = RequestMethod.POST)
	public ModelAndView verifyEmail() {
		ModelAndView mv =
				new ModelAndView();
		
		Navigation.addMainNavItemMap(mv);
		
		//Get email -> find user ID in DB
		//if ID exists
		//HttpSession -> add user ID
		//HttpReponse -> create encodeURL("user/update-password")
		//Send encodeURL to email
//		mv.addObject("message", "Email has been sent. Please check your email to reset password.")
//		mv.setViewName("main/success");
		
		//if ID does not exist
//		mv.addObject("message", "User does not exists, please try again with difference email.")
//		mv.setViewName("user/forgotPassword");
		
		return mv;
	}
	
}

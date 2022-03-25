package com.funix.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getRegisterForm() {
		//if session contains userID or adminID, redirect to explore
		
		ModelAndView mv =
				new ModelAndView();
		
		addNavItemMap(mv);
		mv.setViewName("user/register");
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView verifyEmail() {
		ModelAndView mv =
				new ModelAndView();
		
		//if user does not exist in DB
		//HttpSession -> add user object
		//HttpResponse -> encodeURL("/register/create-user")
		//send encodeURL to email
//		addNavItemMap(mv);
//		mv.addObject("message", "Email has been sent. Please check your email to verify the new account.");
//		mv.setViewName("main/success");
		
		//if user exists
//		mv.addObject("message", "User already exists, please login.")
//		mv.setViewName("redirect:/login");
		
		return mv;
	}
	
	@RequestMapping(value = "create-user",
			method = RequestMethod.GET)
	public ModelAndView createUser() {
		ModelAndView mv =
				new ModelAndView();
		
		//session -> user object
		//add user to DB
		
		addNavItemMap(mv);
		mv.addObject("message", "Your account has been created. Please login to confirm.");
		mv.setViewName("redirect:/login");
		
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

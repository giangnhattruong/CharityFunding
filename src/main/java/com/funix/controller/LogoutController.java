package com.funix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.funix.service.Navigation;

@Controller
@RequestMapping("/logout")
public class LogoutController {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doLogout() {
		//if session does not contain userID or adminID, redirect to explore
		
		ModelAndView mv =
				new ModelAndView();
		
		//invalidate session
		
		Navigation.addMainNavItemMap(mv);
		mv.addObject("message", "You have logged out successful. See you again soon!");
		mv.setViewName("main/success");
		
		return mv;
	}
	
}

/*
 * LogoutController.java    1.00    2022-04-15
 */

package com.funix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.funix.dao.IUserDAO;
import com.funix.dao.UserDAOImpl;
import com.funix.model.User;
import com.funix.service.Navigation;

/**
 * Handle logout user.
 * @author Giang_Nhat_Truong
 *
 */
@Controller
@RequestMapping("/logout")
public class LogoutController {
	
	/**
	 * Handle logout.
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doLogout(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// If user haven't login yet, redirect to home page.
		if (!doesUserLoggedIn(request)) {
			mv.setViewName("redirect:/explore");
		} else {
			// Log user out.
			HttpSession session = request.getSession();
			session.invalidate();
			
			// Redirect to success page.
			Navigation.addMainNavItemMap(mv);
			mv.addObject("message", "You have logged out successful. See you again soon!");
			mv.setViewName("main/success");
		}
		
		return mv;
	}
	
	/**
	 * Check if there is a user logged in.
	 * @param user
	 * @return
	 */
	private boolean doesUserLoggedIn(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		return email != null && !email.equals("");
	}
	
}

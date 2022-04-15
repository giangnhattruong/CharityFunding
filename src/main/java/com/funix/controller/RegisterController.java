/*
 * RegisterController.java    1.00    2022-04-13
 */

package com.funix.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.funix.auth.IAuthTokenizer;
import com.funix.auth.JWTImpl;
import com.funix.dao.IUserDAO;
import com.funix.dao.UserDAOImpl;
import com.funix.javamail.EmailAPIImpl;
import com.funix.javamail.IEmailAPI;
import com.funix.model.User;
import com.funix.service.Navigation;
import com.funix.service.PasswordService;

/**
 * Handle register new user and verify user.
 * @author Giang_Nhat_Truong
 *
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
	
	/**
	 * Domain name for building verification link.
	 */
	private static final String DOMAIN = "http://localhost:8080";
	
	/**
	 * Life span of an verification's token (3 days).
	 */
	private static final double VERIFY_TOKEN_LIVE_TIME_MINS = 4320;
	
	/**
	 * Life span of an authentication's token.
	 */
	private static final double AUTH_TOKEN_LIVE_TIME_MINS = 180;

	/**
	 * DataSource for initializing
	 * DAO objects and manipulating
	 * data in the database.
	 */
	@Autowired
	private DataSource dataSource;
	
	/**
	 * PasswordEncoder for encoding
	 * password when creating new user. 
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * Render register form.
	 * @param message
	 * @param user
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getRegisterForm(
			@ModelAttribute("message") String message, 
			@ModelAttribute("user") User user) {
		ModelAndView mv = new ModelAndView();
		Navigation.addMainNavItemMap(mv);
		mv.addObject("user", user);
		mv.setViewName("user/register");
		
		return mv;
	}
	
	/**
	 * Add new user to database and send email
	 * contain random password and verification link.
	 * @param user
	 * @param result
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addUser(
			@ModelAttribute("user") User user, 
		    BindingResult result,
		    HttpServletRequest request,
		    HttpServletResponse response,
		    RedirectAttributes redirectAttributes)  {
		ModelAndView mv = new ModelAndView();
		IUserDAO userDAO = new UserDAOImpl(dataSource);
		IEmailAPI emailAPI = new EmailAPIImpl();
		String validatingMessage = user.validate();
		String randomPassword = PasswordService
				.generateRandomPassword();
		user.setPassword(passwordEncoder, randomPassword);
		
		if(userDAO.checkForUser(user.getEmail())) {
			// Return error if user existed.
			redirectAttributes
				.addFlashAttribute("message", "Please sign up with "
					+ "a different email. This email have already existed.");
			redirectAttributes
				.addFlashAttribute("user", user);
			mv.setViewName("redirect:/register");
		} else if (!validatingMessage.equals("success")) {
			// Return error if validation failed.
			redirectAttributes
				.addFlashAttribute("message", validatingMessage);
			redirectAttributes
				.addFlashAttribute("user", user);
			mv.setViewName("redirect:/register");
		} else {
			// Add new user to database.
			userDAO.create(user);
			
			// Send account activation email.
			int userID = userDAO.getUserID(user.getEmail());
			String token = generateToken(userID, 
					user.getUserRole(), VERIFY_TOKEN_LIVE_TIME_MINS);
			String verifyURL = DOMAIN
					+ request.getContextPath() 
					+ "/register/verify?token="
					+ token;
			emailAPI.sendVerificationMessage(randomPassword, 
					verifyURL, user.getEmail());
			
			Navigation.addMainNavItemMap(mv);
			mv.addObject("message", 
						"Your account has been successfully created. "
						+ "Please check your email for verification.");
			mv.setViewName("main/success");
		}
		
		return mv;
	}
	
	/**
	 * Verify new user by token.
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "verify",
			method = RequestMethod.GET)
	public ModelAndView verifyUser(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		IUserDAO userDAO = new UserDAOImpl(dataSource);
		IAuthTokenizer authTokenizer = new JWTImpl();
		String token = request.getParameter("token");
		User user = authTokenizer.decodeUser(token);
		
		if (user == null) {
			// Return error if authentication failed.
			mv.addObject("message", "Your verification URL is invalid "
					+ "or has been expired.");
			mv.setViewName("main/error");
		} else {
			// Enable user status.
			userDAO.enableUserStatus(user.getUserID());
			
			// Set cookie with auth token.
			String authToken = generateToken(user.getUserID(), 
						user.getUserRole(), AUTH_TOKEN_LIVE_TIME_MINS);
			Cookie cookie = new Cookie("authToken", authToken);
			cookie.setMaxAge((int) (AUTH_TOKEN_LIVE_TIME_MINS * 60));
			response.addCookie(cookie);
			
			mv.addObject("message", "Your account has been verified. "
					+ "Welcome to Charity Funding!");
			mv.setViewName("main/success");
		}
		
		Navigation.addMainNavItemMap(mv);
		return mv;
	}

	/**
	 * Generate token from user ID and user role.
	 * @param user
	 * @param userID
	 * @return
	 */
	private String generateToken(int userID, int userRole,
			double tokenLiveMins) {
		IAuthTokenizer authTokenizer = 
				new JWTImpl(userID, userRole, tokenLiveMins);
		return authTokenizer.encodeUser();
	}
	
}

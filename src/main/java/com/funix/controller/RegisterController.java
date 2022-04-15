/*
 * RegisterController.java    1.00    2022-04-14
 */

package com.funix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
	private static final int VERIFY_TOKEN_LIVE_TIME_MINS = 4320;
	
	/**
	 * Life span of an authentication's token.
	 */
	private static final int SESSION_LIVE_TIME_MINS = 180;

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
			@ModelAttribute("user") User user,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// If there is user logged in, redirect home page.
		if (isUserLoggedIn(getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			Navigation.addMainNavItemMap(mv);
			mv.addObject("user", user);
			mv.setViewName("user/register");
		}
		
		return mv;
	}
	
	/**
	 * Add new user to database and send email
	 * contain random password and activation URL.
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
		    RedirectAttributes redirectAttributes)  {
		ModelAndView mv = new ModelAndView();
		IUserDAO userDAO = new UserDAOImpl(dataSource);
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
			
			// Send activation URL to user email.
			String verifyToken = generateToken(user.getEmail());
			String verifyURL = DOMAIN
					+ request.getContextPath() 
					+ "/register/verify?token="
					+ verifyToken;
			IEmailAPI emailAPI = new EmailAPIImpl();
			emailAPI.sendVerificationMessage(randomPassword, 
					verifyURL, user.getEmail());
			
			// Redirect to success page.
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
	public ModelAndView verifyUser(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		IUserDAO userDAO = new UserDAOImpl(dataSource);
		IAuthTokenizer authTokenizer = new JWTImpl();
		String token = request.getParameter("token");
		String email = authTokenizer.decodeUser(token);
		
		if (email == null || email.equals("")) {
			// Return error if authentication failed.
			mv.addObject("message", "Your verification URL is invalid "
					+ "or has been expired.");
			mv.setViewName("main/error");
		} else {
			// Enable user and log user in.
			userDAO.enableUserStatus(email);
			User user = userDAO.getUserSimpleInfo(email);
			doLogin(request, user);
			
			// Redirect to success page.
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
	 * @param userStatus
	 * @return
	 */
	private String generateToken(String email) {
		IAuthTokenizer authTokenizer = 
				new JWTImpl(email, VERIFY_TOKEN_LIVE_TIME_MINS);
		return authTokenizer.encodeUser();
	}

	/**
	 * Add user email to session for logged in.
	 * @param request
	 * @param email
	 */
	private void doLogin(HttpServletRequest request, User user) {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(
				SESSION_LIVE_TIME_MINS * 60);
		session.setAttribute("email", user.getEmail());
		session.setAttribute("userRole", user.getUserRole());
	}
	
	/**
	 * Get user info from session.
	 * @param request
	 * @return
	 */
	private User getUserFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		IUserDAO userDAO = new UserDAOImpl(dataSource);
		return userDAO.getUserSimpleInfo(email);
	}
	
	/**
	 * Check if there is a user logged in.
	 * @param user
	 * @return
	 */
	private boolean isUserLoggedIn(User user) {
		return user.getUserID() != 0;
	}
	
}

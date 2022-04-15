/*
 * LoginController.java    1.00    2022-04-15
 */

package com.funix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
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

/**
 * Handle login user and forgot password.
 * @author Giang_Nhat_Truong
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	/**
	 * Domain name for building verification link.
	 */
	private static final String DOMAIN = "http://localhost:8080";
	
	/**
	 * Life span of an reset password's token.
	 */
	private static final int RESET_PASSWORD_TOKEN_LIVE_TIME_MINS = 15;
	
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
	 * Get user login form.
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getLoginForm(
			@ModelAttribute("message") String message,
			@ModelAttribute("user") User user,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// If there is user logged in, redirect to home page.
		if (doesUserExist(getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			Navigation.addMainNavItemMap(mv);
			mv.addObject("message", message);
			mv.addObject("uesr", user);
			mv.setViewName("user/login");
		}
		
		return mv;
	}
	
	/**
	 * Handle login user.
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView doLogin(
			@ModelAttribute("user") User user,
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		
		// Get user from database.
		IUserDAO userDAO = new UserDAOImpl(dataSource);
		User originalUser = userDAO.getUserSimpleInfo(user.getEmail());
		
		if (!originalUser.isAuthenticated(passwordEncoder, 
				user.getEmail(), user.getPassword())) {
			// Return error if info don't match.
			redirectAttributes.addFlashAttribute("message", 
					"Invalid username or password.");
			redirectAttributes.addFlashAttribute("user", user);
			mv.setViewName("redirect:/login");
		} else if (!originalUser.getUserStatus()) {
			// Return error if user is in-active.
			redirectAttributes.addFlashAttribute("message", 
					"Your account is not active. "
					+ "Please contact admin for more information.");
			redirectAttributes.addFlashAttribute("user", user);
			mv.setViewName("redirect:/login");
		} else {
			// If info match, login and redirect to home page.
			doLogin(request, originalUser);
			mv.setViewName("redirect:/explore");
		}
		
		return mv;
	}
	
	/**
	 * Render forgot-password form.
	 * @param message
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "forgot-password",
			method = RequestMethod.GET)
	public ModelAndView getEmailForm(
			@ModelAttribute("message") String message,
			HttpServletRequest request) {		
		ModelAndView mv = new ModelAndView();
		Navigation.addMainNavItemMap(mv);
		mv.addObject("message", message);
		mv.setViewName("user/forgotPassword");
		return mv;
	}
	
	/**
	 * Send a reset password URL to user email.
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "forgot-password",
			method = RequestMethod.POST)
	public ModelAndView verifyEmail(HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		String emailInput = request.getParameter("email");
		IUserDAO userDAO = new UserDAOImpl(dataSource);
		
		if (!userDAO.checkForUser(emailInput)) {
			// Return error if email doesn't exists in database.
			redirectAttributes.addFlashAttribute("message",
					"Sorry, we can find your account. "
					+ "Please try a different email.");
			mv.setViewName("redirect:/login/forgot-password");
		} else {
			// Send reset password email to user.
			String resetPasswordToken = generateToken(emailInput);
			String resetPasswordURL = DOMAIN
					+ request.getContextPath() 
					+ "/login/update-password?token="
					+ resetPasswordToken;
			IEmailAPI emailAPI = new EmailAPIImpl();
			emailAPI.sendResetPasswordURL(resetPasswordURL, emailInput,
					RESET_PASSWORD_TOKEN_LIVE_TIME_MINS);
			
			// Redirect to success page.
			Navigation.addMainNavItemMap(mv);
			mv.addObject("message", 
						"Email has been sent.");
			mv.setViewName("main/success");
		}
		
		return mv;
	}
	
	/**
	 * Verify token then
	 * login and render update password.
	 * @param message
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update-password",
			method = RequestMethod.GET)
	public ModelAndView getUpdatePasswordForm(
			@ModelAttribute("message") String message,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// Get user from token
		String token = request.getParameter("token");
		User user = getUserFromToken(token);
		
		if (!doesUserExist(user)) {
			// Return error if authentication failed.
			mv.addObject("message", "Your verification URL is invalid "
					+ "or has been expired.");
			mv.setViewName("main/error");
		} else {
			// Log user in and render update passsword form.
			doLogin(request, user);
			mv.addObject("message", message);
			mv.addObject("isOldPasswordRequired", false);
			mv.setViewName("user/updatePassword");
		}
		
		return mv;
	}
	
	/**
	 * Update user password.
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update-password",
			method = RequestMethod.POST)
	public ModelAndView getUpdatePasswordForm(
			RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// Get user from session.
		User user = getUserFromSession(request);
		
		// Redirect to home page if user didn't login.
		if (!doesUserExist(user)) {
			mv.setViewName("redirect:/explore");
		} else {
			// Validate new password.
			String newPassword = request.getParameter("newPassword");
			String confirmPassword = request.getParameter("confirmPassword");
			String validatingMessage = user
					.validateNewPassword(passwordEncoder,
							newPassword, confirmPassword);
			
			if (!validatingMessage.equals("success")) {
				// Return error if validate failed with token for authorization.
				redirectAttributes.addFlashAttribute("message", 
						validatingMessage);
				String token = generateToken(user.getEmail());
				mv.setViewName("redirect:/login/update-password?token=" + token);
			} else {
				// Update user new password.				
				IUserDAO userDAO = new UserDAOImpl(dataSource);
				userDAO.update(user.getEmail(), 
						passwordEncoder.encode(newPassword));
				
				// Redirect to success page.
				Navigation.addAdminNavItemMap(mv);
				mv.addObject("message", 
						"Your password has been successfully updated.");
				mv.setViewName("main/success");
			}
		}
		
		return mv;
	}

	/**
	 * Get user from URL token.
	 * @param token
	 * @return
	 */
	private User getUserFromToken(String token) {
		IAuthTokenizer authTokenizer = new JWTImpl();
		String email = authTokenizer.decodeUser(token);
		IUserDAO userDAO = new UserDAOImpl(dataSource);
		User user = userDAO.getUserSimpleInfo(email);
		return user;
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
				new JWTImpl(email, RESET_PASSWORD_TOKEN_LIVE_TIME_MINS);
		return authTokenizer.encodeUser();
	}

	/**
	 * Add user email to session for logging in.
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
	private boolean doesUserExist(User user) {
		return user.getUserID() != 0;
	}
	
}

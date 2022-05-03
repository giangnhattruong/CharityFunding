/**
 * UserController.java    1.00    2022-04-09
 */

package com.funix.controller;

import java.util.List;
import java.util.Map;

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
import com.funix.banktransaction.ITransaction;
import com.funix.banktransaction.TransactionImpl;
import com.funix.dao.DonationHistoryDAOImpl;
import com.funix.dao.IDonationHistoryDAO;
import com.funix.dao.IUserDAO;
import com.funix.dao.UserDAOImpl;
import com.funix.model.DonationHistory;
import com.funix.model.DonationHistoryFilter;
import com.funix.model.User;
import com.funix.service.Navigation;

/**
 * Handle all routes for user managing pages.
 * @author Giang_Nhat_Truong
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
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
	 * Main route /user redirect to 
	 * user donation history page.
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getUserDashboard() {
		return "redirect:/user/donation-history";
	}

	/**
	 * Render user donation history.
	 * @param filter
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "donation-history", method = RequestMethod.GET)
	public ModelAndView getHistory(
			@ModelAttribute("filter") DonationHistoryFilter filter,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		// Get user object contains email and user role from token.
		User authUser = getUserFromSession(request);
		
		// Redirect to home page if user is not legal.
		if (!isLegalUser(request, authUser)) {
			mv.setViewName("redirect:/explore");
		} else {
			// Verify donation history
			IDonationHistoryDAO historyDAO = 
					new DonationHistoryDAOImpl(dataSource);
			ITransaction transaction = new TransactionImpl();
			Map<String, Boolean> transactionCodeMap =
					transaction.verify(historyDAO.getTransactionCodeList());
			
			// Verify transaction codes.
			historyDAO.verifyHistoryStatus(transactionCodeMap);
			
			// Get all history.
			List<DonationHistory> historyList = historyDAO
					.getUserHistory(authUser.getEmail(), filter);
			
			// Get history summary.
			long numberOfTransactions = historyList.stream().count();
			double donationSum = historyList.stream()
					.mapToDouble(h -> h.getDonation()).sum();
			
			Navigation.addUserNavItemMap(mv);		
			mv.addObject("filter", filter);
			mv.addObject("historyList", historyList);
			mv.addObject("numberOfTransactions", numberOfTransactions);
			mv.addObject("donationSum", donationSum);
			mv.setViewName("user/donationHistory");
		}
		
		return mv;
	}

	/**
	 * Handle donation history search form submit.
	 * @param filter
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "donation-history", method = RequestMethod.POST)
	public ModelAndView searchHistory(
			@ModelAttribute("filter") DonationHistoryFilter filter, 
		    BindingResult result,
		    RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		redirectAttributes.addFlashAttribute("filter", filter);
		mv.setViewName("redirect:/user/donation-history");
		return mv;
	}
	
	/**
	 * Render user update profile form.
	 * @param request
	 * @param message
	 * @param error
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update-profile", method = RequestMethod.GET)
	public ModelAndView getProfileForm(
			HttpServletRequest request,
			@ModelAttribute("message") String message, 
			@ModelAttribute("error") String error, 
			@ModelAttribute("user") User user,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();

		// Get user object contains email and user role from token.
		User authUser = getUserFromSession(request);
		
		// Redirect to home page if user is not legal.
		if (!isLegalUser(request, authUser)) {
			mv.setViewName("redirect:/explore");
		} else {
			
			/*
			 * Check if the user instance is not passed from
			 * the last failed submit, then get a user instance from
			 * database.
			 */
			if (error.equals("")) {
				IUserDAO userDAO = new UserDAOImpl(dataSource);
				user = userDAO.getUserSimpleInfo(authUser.getEmail());
			}
			
			//Handle nav menu in case admin or user render profile page.
			if (authUser.getUserRole() == 0) {
				Navigation.addUserNavItemMap(mv);
			} else if (authUser.getUserRole() == 1) {
				Navigation.addAdminNavItemMap(mv);
			}
			
			mv.addObject("user", user);
			mv.addObject("message", message);
			mv.addObject("error", error);
			mv.setViewName("user/updateProfile");
		}
		
		return mv;
	}

	/**
	 * Handle update user profile.
	 * @param user
	 * @param result
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "update-profile", method = RequestMethod.POST)
	public ModelAndView updateProfile(
			@ModelAttribute("user") User user, 
		    BindingResult result,
		    RedirectAttributes redirectAttributes,
		    HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		// Get user object contains email and user role from token.
		User authUser = getUserFromSession(request);
		
		// Redirect to home page if user is not legal.
		if (!isLegalUser(request, authUser)) {
			mv.setViewName("redirect:/explore");
		} else {
			String validatingMessage = user.validate();
			
			if (!validatingMessage.equals("success")) {
				// Return error to profile page.
				redirectAttributes
					.addFlashAttribute("error", validatingMessage);
				redirectAttributes
					.addFlashAttribute("user", user);
			} else {
				// Update user in database.
				IUserDAO userDAO = new UserDAOImpl(dataSource);
				user.setUserRole(authUser.getUserRole());
				user.setUserStatus(1);
				userDAO.update(authUser.getEmail(), user);
				
				// Redirect to profile page with success message.
				redirectAttributes
					.addFlashAttribute("message", 
							"Your profile has been successfully updated.");
			}
			
			mv.setViewName("redirect:/user/update-profile");
		}
		
		return mv;
	}

	/**
	 * Render update user password form.
	 * @param message
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "update-password", method = RequestMethod.GET)
	public ModelAndView getPasswordForm(
			@ModelAttribute("message") String message,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// Get user object contains email and user role from token.
		User authUser = getUserFromSession(request);
		
		// Redirect to home page if user is not legal.
		if (!isLegalUser(request, authUser)) {
			mv.setViewName("redirect:/explore");
		} else {
			//Handle nav menu in case admin or user render profile page.
			if (authUser.getUserRole() == 0) {
				Navigation.addUserNavItemMap(mv);
			} else if (authUser.getUserRole() == 1) {
				Navigation.addAdminNavItemMap(mv);
			}
			
			mv.addObject("message", message);
			mv.addObject("isOldPasswordRequired", true);
			mv.setViewName("user/updatePassword");
		}
		
		return mv;
	}

	/**
	 * Handle update user password.
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "update-password", method = RequestMethod.POST)
	public ModelAndView updatePassword(
		    RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		// Get user object contains email and user role from token.
		User authUser = getUserFromSession(request);
		
		// Redirect to home page if user is not legal.
		if (!isLegalUser(request, authUser)) {
			mv.setViewName("redirect:/explore");
		} else {
			// Validate old and new password.
			IUserDAO userDAO = new UserDAOImpl(dataSource);
			User user = userDAO.getUserSimpleInfo(authUser.getEmail());
			String oldPassword = request.getParameter("oldPassword");
			String newPassword = request.getParameter("newPassword");
			String confirmPassword = request.getParameter("confirmPassword");
			String validatingMessage = user.validatePassword(passwordEncoder, 
					oldPassword, newPassword, confirmPassword);
			
			if (!validatingMessage.equals("success")) {
				// Return error if validate failed.
				redirectAttributes
					.addFlashAttribute("message", validatingMessage);
				mv.setViewName("redirect:/user/update-password");
			} else {
				// Update user new encoded password to database.
				userDAO.update(authUser.getEmail(), 
						passwordEncoder.encode(newPassword));
				
				// Redirect to profile page with success message.
				redirectAttributes
					.addFlashAttribute("message", 
						"Your password has been successfully updated.");
				mv.setViewName("redirect:/user/update-profile");
			}
		}
		
		return mv;
	}
	
	/**
	 * Get user from session.
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
	 * Check if there is a user with active status.
	 * @param user
	 * @return
	 */
	private boolean isLegalUser(HttpServletRequest request, User user) {
		boolean result = user.getUserStatus() == 1;
		
		// Log user out if their status is in-active.
		if (result == false) {
			HttpSession session = request.getSession();
			session.invalidate();
		}
		
		return result;
	}

}

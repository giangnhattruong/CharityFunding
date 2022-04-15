/**
 * UserController.java    1.00    2022-04-09
 */

package com.funix.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.funix.dao.DonationHistoryDAOImpl;
import com.funix.dao.IDonationHistoryDAO;
import com.funix.dao.IUserDAO;
import com.funix.dao.UserDAOImpl;
import com.funix.model.DonationHistory;
import com.funix.model.DonationHistoryFilter;
import com.funix.model.User;
import com.funix.service.Navigation;
import com.funix.service.NullConvert;

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
	 * JavaMailSender for sending email.
	 */
	@Autowired
	private JavaMailSender emailSender;

	@RequestMapping(method = RequestMethod.GET)
	public String getUserDashboard() {
		return "redirect:/user/donation-history";
	}

	@RequestMapping(value = "donation-history", method = RequestMethod.GET)
	public ModelAndView getHistory(
			@ModelAttribute("filter") DonationHistoryFilter filter,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		IDonationHistoryDAO historyDAO = 
				new DonationHistoryDAOImpl(dataSource);
//		HttpSession session = request.getSession();
//		int userID = NullConvert.toInt((String) session.getAttribute("userID"));
		int userID = 21;
		List<DonationHistory> historyList = historyDAO
				.getManyUserHistories(userID, filter);
		
		Navigation.addUserNavItemMap(mv);		
		mv.addObject("filter", filter);
		mv.addObject("historyList", historyList);
		mv.setViewName(getRoute("user/donationHistory"));
		return mv;
	}

	@RequestMapping(value = "donation-history", method = RequestMethod.POST)
	public ModelAndView searchHistory(
			@ModelAttribute("filter") DonationHistoryFilter filter, 
		    BindingResult result,
		    RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		redirectAttributes.addFlashAttribute("filter", filter);
		mv.setViewName(getRoute("redirect:/user/donation-history"));
		return mv;
	}
	
	@RequestMapping(value = "update-profile", method = RequestMethod.GET)
	public ModelAndView getProfileForm(
			HttpServletRequest request,
			@ModelAttribute("message") String message, 
			@ModelAttribute("error") String error, 
			@ModelAttribute("user") User user,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		IUserDAO userDAO = new UserDAOImpl(dataSource);
//		HttpSession session = request.getSession();
//		int userID = NullConvert.toInt((String) session.getAttribute("userID"));
		int userID = 21;
		
		/*
		 * Check if the user instance is not passed from
		 * the last failed submit, then get a user instance from
		 * database.
		 */
		if (user.getUserID() == 0) {
			user = userDAO.getUser(userID);
		}
		
		Navigation.addUserNavItemMap(mv);
		mv.addObject("user", user);
		mv.addObject("message", message);
		mv.addObject("error", error);
		mv.setViewName(getRoute("user/updateProfile"));
		
		return mv;
	}

	@RequestMapping(value = "update-profile", method = RequestMethod.POST)
	public ModelAndView updateProfile(
			@ModelAttribute("user") User user, 
		    BindingResult result,
		    RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		IUserDAO userDAO = new UserDAOImpl(dataSource);
		String validatingMessage = user.validate();
//		HttpSession session = request.getSession();
//		int userID = NullConvert.toInt((String) session.getAttribute("userID"));
//		int userRole = NullConvert.toInt((String) session.getAttribute("userRole"));
		int userID = 21;
		int userRole = 0;

		if (!validatingMessage.equals("success")) {
			user.setUserID(userID);
			redirectAttributes
				.addFlashAttribute("error", validatingMessage);
			redirectAttributes
			.addFlashAttribute("user", user);
		} else {
			user.setUserRole(userRole);
			user.setUserStatus(true);
			userDAO.update(userID, user);
			redirectAttributes
				.addFlashAttribute("message", 
						"Your profile has been successfully updated.");
		}
		
		mv.setViewName("redirect:/user/update-profile");
		return mv;
	}

	@RequestMapping(value = "update-password", method = RequestMethod.GET)
	public ModelAndView getPasswordForm(
			@ModelAttribute("error") String error, 
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		Navigation.addUserNavItemMap(mv);
		mv.addObject("error", error);
		mv.setViewName(getRoute("user/updatePassword"));
		
		return mv;
	}

	@RequestMapping(value = "update-password", method = RequestMethod.POST)
	public ModelAndView updatePassword(
			HttpServletRequest request,
		    RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		IUserDAO userDAO = new UserDAOImpl(dataSource);
//		HttpSession session = request.getSession();
//		int userID = NullConvert.toInt((String) session.getAttribute("userID"));
		int userID = 21;
		User user = userDAO.getUserCredential(userID);
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		String validatingMessage = user.validatePassword(passwordEncoder, 
				oldPassword, newPassword, confirmPassword);

		if (!validatingMessage.equals("success")) {
			user.setUserID(userID);
			redirectAttributes
				.addFlashAttribute("error", validatingMessage);
			mv.setViewName("redirect:/user/update-password");
		} else {
			String newEncodedPassword = passwordEncoder
					.encode(newPassword);
			userDAO.update(userID, newEncodedPassword);
			redirectAttributes
				.addFlashAttribute("message", 
						"Your password has been successfully updated.");
			mv.setViewName("redirect:/user/update-profile");
		}
		
		return mv;
	}

	private String getRoute(String url) {
		/*
		 * if session not contains userID redirect to landing page
		 */
		if (false) {
			return "redirect:/explore";
		}

		return url;
	}

}

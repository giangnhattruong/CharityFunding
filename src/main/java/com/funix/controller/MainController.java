/*
 * MainController.java    1.00    2022-04-15
 */

package com.funix.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.funix.banktransaction.ITransaction;
import com.funix.banktransaction.TransactionImpl;
import com.funix.dao.CampaignDAOImpl;
import com.funix.dao.DonationHistoryDAOImpl;
import com.funix.dao.ICampaignDAO;
import com.funix.dao.IDonationHistoryDAO;
import com.funix.dao.IUserDAO;
import com.funix.dao.UserDAOImpl;
import com.funix.javamail.EmailAPIImpl;
import com.funix.javamail.IEmailAPI;
import com.funix.model.Campaign;
import com.funix.model.CampaignFilter;
import com.funix.model.DonationHistory;
import com.funix.model.User;
import com.funix.service.Navigation;

/**
 * Render landing page.
 * @author Giang_Nhat_Truong
 *
 */
@Controller
@RequestMapping("/")
public class MainController {
	
	/**
	 * DataSource for initializing
	 * DAO objects and manipulating
	 * data in the database.
	 */
	@Autowired
	private DataSource dataSource;
	
	/**
	 * Render cover page.
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getCoverPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
        return mv;
	}
	
	/**
	 * Render landing page.
	 * @param filter
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/explore",
			method = RequestMethod.GET)
	public ModelAndView getLandingPage(
			@ModelAttribute("filter") CampaignFilter filter) {
		ModelAndView mv = new ModelAndView();
		
		// Get campaign list from filter object.
		ICampaignDAO campaignDAO = 
				new CampaignDAOImpl(dataSource);
		List<Campaign> campaignList = campaignDAO
				.getManyCampaigns(filter);
		
		// Add campaigns for showing.
		Navigation.addMainNavItemMap(mv);
		mv.addObject("filter", filter);
		mv.addObject("campaignList", campaignList);
		mv.setViewName("main/explore");
        return mv;
	}
	
	/**
	 * Handle main search campaigns form.
	 * @param filter
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/explore",
			method = RequestMethod.POST)
	public ModelAndView searchCampaigns(
			@ModelAttribute("filter") CampaignFilter filter,
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		redirectAttributes.addFlashAttribute("filter", filter);
		mv.setViewName("redirect:/explore/#allCampaigns");
		return mv;		
	}

	/**
	 * Render campaign details page.
	 * @param campaignID
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/campaign/{campaignID}",
			method = RequestMethod.GET)
	public ModelAndView getCampaignPage(
			@PathVariable("campaignID") int campaignID,
			@ModelAttribute("message") String message,
			@ModelAttribute("validateMessage") String validateMessage,
			@ModelAttribute("transaction") TransactionImpl transaction,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		ICampaignDAO campaignDAO = 
				new CampaignDAOImpl(dataSource);
		Campaign campaign = campaignDAO
				.getCampaign(campaignID);
		
		// Return error if there is not a campaign.
		if (campaign.isEmpty()) {
			mv.addObject("message", "No campaign found.");
			mv.setViewName("main/error");
		} else {
			User user = getUserFromSession(request);
			Navigation.addMainNavItemMap(mv);
			mv.addObject("message", message);
			mv.addObject("validateMessage", validateMessage);
			mv.addObject("transaction", transaction);
			mv.addObject("campaign", campaign);
			mv.addObject("user", user);
			mv.setViewName("main/campaignDetails");
		}
		
		return mv;
	}
	
	/**
	 * Handle donation submit.
	 * @param campaignID
	 * @param transaction
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/campaign/{campaignID}",
			method = RequestMethod.POST)
	public ModelAndView addDonation(
			@PathVariable("campaignID") int campaignID,
			@ModelAttribute("transaction") TransactionImpl transaction,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		User user = getUserFromSession(request);
		
		// Redirect to home page if there is no user logged in.
		if (user.isEmpty()) {
			mv.setViewName("redirect:/explore");
		} else {
			// Validate transaction input before sending.
			String validateMessage = transaction.validate();
			
			if (!validateMessage.equals("success")) {
				redirectAttributes
					.addFlashAttribute("transaction", transaction);
				redirectAttributes
					.addFlashAttribute("validateMessage", validateMessage);
			} else {
				// Send/execute transaction.
				transaction.send();
				
				/*
				 * If transaction is sent successfully, add the transaction
				 * to donation history database table, send notify emails to user and
				 * admin.
				 */
				if (transaction.isTransactionSuccess()) {
					
					// Add transaction to database.
					IDonationHistoryDAO donationHistoryDAO =
							new DonationHistoryDAOImpl(dataSource);
					DonationHistory donation = 
							new DonationHistory(campaignID, user.getUserID(), 
									transaction.getAmount(), 
									transaction.getTransactionCode());
					donationHistoryDAO.create(donation);
					
					// Send notify email to user and admin.
					IEmailAPI emailAPI = new EmailAPIImpl();
					emailAPI.sendTransactionNotifyToUser(user.getFullname(), 
							user.getEmail(), transaction.getAmount(), 
							transaction.getTransactionCode());
					emailAPI.sendTransactionNotifyToAdmin(donation);
					
					redirectAttributes.addFlashAttribute("message", 
							"Transaction has been successfully sent. "
							+ "Thank you very much for your support!");
				} else {
					redirectAttributes.addFlashAttribute("message", 
							"Transaction was unable to send. Please try again or "
							+ "contact your bank for more infomation about the issue.");
				}
			}
		}
		
		mv.setViewName("redirect:/campaign/" + campaignID + "#donationSection");
		return mv;
	}

	/**
	 * Get user from session.
	 * @param request
	 * @return
	 */
	private User getUserFromSession(HttpServletRequest request) {
		IUserDAO userDAO = new UserDAOImpl(dataSource);
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		User user = userDAO.getUserSimpleInfo(email);
		return user;
	}
	
	/**
	 * Render contact page.
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/contact",
			method = RequestMethod.GET)
	public ModelAndView getContactPage(
			@ModelAttribute("message") String message) {
		ModelAndView mv = new ModelAndView();
		Navigation.addMainNavItemMap(mv);
		mv.addObject("message", message);
		mv.setViewName("main/contact");
		return mv;
	}
	
	/**
	 * Handle contact form submit.
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/contact",
			method = RequestMethod.POST)
	public ModelAndView sendContactMessage(
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		IEmailAPI emailAPI = new EmailAPIImpl();
		String fullname = request.getParameter("fullname");
		String userEmail = request.getParameter("email");
		String userMessage = request.getParameter("message");
		emailAPI.sendContactMessage(fullname, userEmail, userMessage);
		redirectAttributes.addFlashAttribute("message", 
				"Your message has been sent.");
		mv.setViewName("redirect:/contact");
		return mv;
	}
	
}

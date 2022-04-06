/*
 * AdminController.java    1.00    2022-04-05
 */

package com.funix.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.cloudinary.Cloudinary;
import com.funix.dao.CampaignDAOImpl;
import com.funix.dao.DonationHistoryDAOImpl;
import com.funix.dao.ICampaignDAO;
import com.funix.dao.IDonationHistoryDAO;
import com.funix.dao.IUserDAO;
import com.funix.dao.UserDAOImpl;
import com.funix.model.Campaign;
import com.funix.model.CampaignFilter;
import com.funix.model.DonationHistory;
import com.funix.model.DonationHistoryFilter;
import com.funix.model.User;
import com.funix.model.UserFilter;
import com.funix.multipart.CloudinaryImpl;
import com.funix.multipart.IImageAPI;
import com.funix.service.Navigation;
import com.funix.service.NullConvert;
import com.funix.service.SQLConvert;

/**
 * Handle all routes for admin manage pages.
 * @author HP
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	/**
	 * Inject DataSource instance and then
	 * inject the instance to initialize
	 * DAO objects.
	 */
	@Autowired
	private DataSource dataSource;
	
	/**
	 * Inject Cloudinary instance and then
	 * inject the instance to initialize
	 * ImageAPI objects.
	 */
	@Autowired
	private Cloudinary cloudinary;
	
	/**
	 * Main route /admin redirect to 
	 * admin donation history manage page.
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getAdminDashboard() {
		// if session does not contain adminID, redirect to explore
		return "redirect:/admin/donation-history";
	}

	/**
	 * Render admin donation history managing page. Get or initialize
	 * filter object and send to DAO, to get a list of 
	 * donation history for view.
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "donation-history", method = RequestMethod.GET)
	public ModelAndView getDonationHistory(
			@ModelAttribute("filter") DonationHistoryFilter filter) {
		ModelAndView mv = new ModelAndView();
		IDonationHistoryDAO historyDAO = 
				new DonationHistoryDAOImpl(dataSource);
		List<DonationHistory> historyList = historyDAO
				.getManyAdminHistories(filter);
		
		Navigation.addAdminNavItemMap(mv);
		mv.addObject("filter", filter);
		mv.addObject("historyList", historyList);
		mv.setViewName(getRoute("admin/donationHistory"));
		return mv;
	}

	/**
	 * Handle donation history search form submit, bind all
	 * form input to filter object and redirect back to
	 * admin donation history managing page.
	 * @param filter
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "donation-history", method = RequestMethod.POST)
	public ModelAndView searchDonationHistory(
			@ModelAttribute("filter") DonationHistoryFilter filter, 
		    BindingResult result,
		    RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		redirectAttributes.addFlashAttribute("filter", filter);
		mv.setViewName(getRoute("redirect:/admin/donation-history"));
		return mv;
	}

	/**
	 * Render admin campaign managing page. Get or initialize
	 * filter object and send to DAO, to get a list of 
	 * campaign for view.
	 * @param message
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "campaigns", method = RequestMethod.GET)
	public ModelAndView manageCampaigns(
			@ModelAttribute("message") String message,
			@ModelAttribute("filter") CampaignFilter filter) {
		ModelAndView mv = new ModelAndView();
		IImageAPI imageAPI = new CloudinaryImpl(cloudinary);
		ICampaignDAO campaignDAO = new CampaignDAOImpl(dataSource, imageAPI);
		List<Campaign> campaignList = campaignDAO
				.getManyCampaigns(filter);
		
		Navigation.addAdminNavItemMap(mv);
		mv.addObject("message", message);
		mv.addObject("filter", filter);
		mv.addObject("campaignList", campaignList);
		mv.setViewName(getRoute("admin/campaigns"));
		return mv;
	}

	/**
	 * Handle campaign search form submit, bind all
	 * form input to filter object and redirect back to
	 * admin campaign managing page.
	 * @param filter
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "campaigns", method = RequestMethod.POST)
	public ModelAndView searchCampaigns(
			@ModelAttribute("filter") CampaignFilter filter,
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		redirectAttributes.addFlashAttribute("filter", filter);
		mv.setViewName("redirect:/admin/campaigns");
		return mv;
	}

	/**
	 * Render campaign creating form. Get or initialize campaign
	 * instance for form default values. Get error message send
	 * by the last submit to show on the beginning of the form.
	 * @param message
	 * @param campaign
	 * @return
	 */
	@RequestMapping(value = "campaigns/new", method = RequestMethod.GET)
	public ModelAndView getCampaignCreateForm(
			@ModelAttribute("message") String message, 
			@ModelAttribute("campaign") Campaign campaign) {
		ModelAndView mv = new ModelAndView();
		Navigation.addAdminNavItemMap(mv);
		mv.addObject("formTitle", "Create");
		mv.addObject("formAction", "/admin/campaigns/new");
		mv.addObject("campaign", campaign);
		mv.setViewName(getRoute("admin/createOrUpdateCampaign"));
		return mv;
	}

	/**
	 * Handle campaign creating form submit, bind campaign instance
	 * to get values from form, validate new campaign and create
	 * new record in database.
	 * @param campaign
	 * @param result
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "campaigns/new", method = RequestMethod.POST)
	public ModelAndView createCampaign(
			@ModelAttribute("campaign") Campaign campaign, 
		    BindingResult result,
		    HttpServletRequest request,
		    RedirectAttributes redirectAttributes)  {
		ModelAndView mv = new ModelAndView();
		IImageAPI imageAPI = new CloudinaryImpl(cloudinary);
		ICampaignDAO campaignDAO = new CampaignDAOImpl(dataSource, imageAPI);
		String message = "";
		
		/*
		 * All campaign fields is set by Spring binding result,
		 * except LocalDate fields have to be set explicitly.
		 */
		String startDateString = request.getParameter("startDate");
		String endDateString = request.getParameter("endDate");
		LocalDate startDate = NullConvert.toLocalDate(startDateString);
		LocalDate endDate = NullConvert.toLocalDate(endDateString);
		campaign.setStartDate(startDate);
		campaign.setEndDate(endDate);
		
		message = campaignDAO.create(campaign);
		
		if (message.equals("success")) {
			redirectAttributes
				.addFlashAttribute("message", 
						"Campaign has been successfully created.");
			mv.setViewName("redirect:/admin/campaigns");
		} else {
			redirectAttributes
				.addFlashAttribute("message", message);
			redirectAttributes
			.addFlashAttribute("campaign", campaign);
			mv.setViewName("redirect:/admin/campaigns/new");
		}
		
		return mv;
	}
	
	/**
	 * Render campaign updating form. Get campaign instance
	 * pass by the last failed updating submit or get campaign instance 
	 * from database to set form default values. Also get the
	 * error message from the last updating submit to show in
	 * the beginning of the form.
	 * @param pathID
	 * @param message
	 * @param campaign
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "campaigns/update/{pathID}", 
			method = RequestMethod.GET)
	public ModelAndView getCampaignUpdateForm(
			@PathVariable int pathID, 
			@ModelAttribute("message") String message, 
			@ModelAttribute("campaign") Campaign campaign, 
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		IImageAPI imageAPI = new CloudinaryImpl(cloudinary);
		ICampaignDAO campaignDAO = 
				new CampaignDAOImpl(dataSource, imageAPI);
		int campaignID = pathID;
			
		/*
		 * Check if the campaign instance is not passed from
		 * the last failed submit, then get a campaign instance from
		 * database.
		 */
		if (campaign.getCampaignID() == 0) {
			campaign = campaignDAO.getCampaign(campaignID);
		}
		
		// Get image thumbnail and pass to view.
		String imgURL = campaign.getImgURL();
		if (imgURL != null) {
			String imgHTML = imageAPI
					.transformImage(imgURL, 80, 60);
			mv.addObject("imgHTML", imgHTML);
		}
		
		Navigation.addAdminNavItemMap(mv);
		mv.addObject("formTitle", "Update");
		mv.addObject("formAction", 
				"/admin/campaigns/update/" + campaignID);
		mv.addObject("campaign", campaign);
		mv.setViewName(getRoute("admin/createOrUpdateCampaign"));
		
		return mv;
	}

	/**
	 * Handle campaign updating form. Bind campaign instance
	 * to set values automatically from Spring form. Validate
	 * and update campaign record in database.
	 * @param campaignID
	 * @param campaign
	 * @param result
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "campaigns/update/{campaignID}", 
			method = RequestMethod.POST)
	public ModelAndView updateCampaign(
			@PathVariable int campaignID, 
			@ModelAttribute("campaign") Campaign campaign, 
		    BindingResult result,
		    HttpServletRequest request,
		    RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		IImageAPI imageAPI = new CloudinaryImpl(cloudinary);
		ICampaignDAO campaignDAO = new CampaignDAOImpl(dataSource, imageAPI);
		String message = "";
		
		/*
		 * All campaign fields is set by Spring binding result,
		 * except LocalDate fields have to be set explicitly.
		 */
		String startDateString = request.getParameter("startDate");
		String endDateString = request.getParameter("endDate");
		LocalDate startDate = NullConvert.toLocalDate(startDateString);
		LocalDate endDate = NullConvert.toLocalDate(endDateString);
		campaign.setStartDate(startDate);
		campaign.setEndDate(endDate);
		message = campaignDAO.update(campaignID, campaign);
		
		if (message.equals("success")) {
			redirectAttributes
				.addFlashAttribute("message", 
						"Campaign has been successfully updated.");
			mv.setViewName("redirect:/admin/campaigns");
		} else {
			campaign.setCampaignID(campaignID);
			redirectAttributes
				.addFlashAttribute("message", message);
			redirectAttributes
			.addFlashAttribute("campaign", campaign);
			mv.setViewName("redirect:/admin/campaigns/update/" + campaignID);
		}
		
		return mv;
	}

	/**
	 * Handle delete many campaigns.
	 * @param request
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "campaigns/delete", method = RequestMethod.POST)
	public ModelAndView deleteCampaign(HttpServletRequest request,
			RedirectAttributes redirectAttrs) {
		ModelAndView mv = new ModelAndView();
		IImageAPI imageAPI = new CloudinaryImpl(cloudinary);
		ICampaignDAO campaignDAO = new CampaignDAOImpl(dataSource, imageAPI);
		String message = "";
		
		String[] campaignIDArray = request.getParameterValues("campaignIDs");
		String campaignIDs = SQLConvert.convertList(campaignIDArray);
		
		if (campaignIDArray != null) {
			campaignDAO.delete(campaignIDs);
			message = "Delete campaign(s) successful.";
		} else {
			message = "Delete failed. No items selected.";
		}
		
		redirectAttrs.addFlashAttribute("message", message);
		mv.setViewName("redirect:/admin/campaigns");

		return mv;
	}

	/**
	 * Render admin user managing page. Get or initialize
	 * filter object and send to DAO, to get a list of 
	 * user for view.
	 * @param message
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "users", method = RequestMethod.GET)
	public ModelAndView manageUsers(
			@ModelAttribute("message") String message,
			@ModelAttribute("filter") UserFilter filter) {
		ModelAndView mv = new ModelAndView();
		IUserDAO userDao = new UserDAOImpl(dataSource);
		List<User> userList = userDao.getManyUsers(filter);
		Navigation.addAdminNavItemMap(mv);
		mv.addObject("message", message);
		mv.addObject("filter", filter);
		mv.addObject("userList", userList);
		mv.setViewName(getRoute("admin/users"));
		return mv;
	}

	/**
	 * Handle user search form submit, bind all
	 * form input to filter object and redirect back to
	 * admin user managing page.
	 * @param filter
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "users", method = RequestMethod.POST)
	public ModelAndView searchUsers(
			@ModelAttribute("filter") UserFilter filter,
			BindingResult result, 
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		redirectAttributes.addFlashAttribute("filter", filter);
		mv.setViewName("redirect:/admin/users");
		return mv;
	}

	/**
	 * Render user creating form. Get or initialize user
	 * instance for form default values. Get error message send
	 * by the last submit to show on the beginning of the form.
	 * @param message
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "users/new", method = RequestMethod.GET)
	public ModelAndView getUserCreateForm(
			@ModelAttribute("message") String message, 
			@ModelAttribute("user") User user) {
		ModelAndView mv = new ModelAndView();
		user.setPassword("");
		user.setConfirmPassword("");
		Navigation.addAdminNavItemMap(mv);
		mv.addObject("formTitle", "Create");
		mv.addObject("formAction", "/admin/users/new");
		mv.addObject("user", user);
		mv.setViewName(getRoute("admin/createOrUpdateUser"));
		return mv;
	}

	/**
	 * Handle user creating form submit, bind user instance
	 * to get values from form, validate new user and create
	 * new record in database.
	 * @param user
	 * @param result
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "users/new", method = RequestMethod.POST)
	public ModelAndView createUser(
			@ModelAttribute("user") User user, 
		    BindingResult result,
		    HttpServletRequest request,
		    RedirectAttributes redirectAttributes)  {
		ModelAndView mv = new ModelAndView();
		IUserDAO userDAO = new UserDAOImpl(dataSource);
		String message = user.validate();
		
		if (message.equals("success")) {
			userDAO.create(user);
			redirectAttributes
				.addFlashAttribute("message", 
						"User has been successfully created.");
			mv.setViewName("redirect:/admin/users");
		} else {
			redirectAttributes
				.addFlashAttribute("message", message);
			redirectAttributes
			.addFlashAttribute("user", user);
			mv.setViewName("redirect:/admin/users/new");
		}
		
		return mv;
	}
	
	/**
	 * Render user updating form. Get user instance
	 * pass by the last failed updating submit or get user instance 
	 * from database to set form default values. Also get the
	 * error message from the last updating submit to show in
	 * the beginning of the form.
	 * @param pathID
	 * @param message
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "users/update/{pathID}", 
			method = RequestMethod.GET)
	public ModelAndView getUserUpdateForm(
			@PathVariable int pathID, 
			@ModelAttribute("message") String message, 
			@ModelAttribute("user") User user) {
		ModelAndView mv = new ModelAndView();
		IUserDAO userDAO = new UserDAOImpl(dataSource);
		int userID = pathID;
			
		/*
		 * Check if the user instance is not passed from
		 * the last failed submit, then get a user instance from
		 * database.
		 */
		if (user.getUserID() == 0) {
			user = userDAO.getUser(userID);
		}
		
		Navigation.addAdminNavItemMap(mv);
		mv.addObject("formTitle", "Update");
		mv.addObject("formAction", 
				"/admin/users/update/" + userID);
		mv.addObject("user", user);
		mv.setViewName(getRoute("admin/createOrUpdateUser"));
		
		return mv;
	}

	/**
	 * Handle user updating form. Bind user instance
	 * to set values automatically from Spring form. 
	 * Validate and update user record in database.
	 * @param userID
	 * @param user
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "users/update/{userID}", 
			method = RequestMethod.POST)
	public ModelAndView updateUser(
			@PathVariable int userID, 
			@ModelAttribute("user") User user, 
		    BindingResult result,
		    RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		IUserDAO userDAO = new UserDAOImpl(dataSource);
		String message = user.validate();

		if (message.equals("success")) {
			userDAO.update(userID, user);
			redirectAttributes
				.addFlashAttribute("message", 
						"User has been successfully updated.");
			mv.setViewName("redirect:/admin/users");
		} else {
			user.setUserID(userID);
			redirectAttributes
				.addFlashAttribute("message", message);
			redirectAttributes
			.addFlashAttribute("user", user);
			mv.setViewName("redirect:/admin/users/update/" + userID);
		}
		
		return mv;
	}

	/**
	 * Handle delete many users.
	 * @param request
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "users/delete", method = RequestMethod.POST)
	public ModelAndView deleteUser(
			HttpServletRequest request,
			RedirectAttributes redirectAttrs) {
		ModelAndView mv = new ModelAndView();
		IUserDAO userDAO = new UserDAOImpl(dataSource);
		String message = "";
		String[] userIDArray = request.getParameterValues("userIDs");
		String userIDs = SQLConvert.convertList(userIDArray);
		
		if (userIDArray != null) {
			userDAO.delete(userIDs);
			message = "Delete user(s) successful.";
		} else {
			message = "Delete failed. No items selected.";
		}
		
		redirectAttrs.addFlashAttribute("message", message);
		mv.setViewName("redirect:/admin/users");

		return mv;
	}

	/**
	 * Redirect to landing page if there is no
	 * admin user logged in.
	 * @param url
	 * @return
	 */
	private String getRoute(String url) {
		/*
		 * if session not contains user role admin 
		 * redirect to landing page.
		 */
		if (false) {
			return "redirect:/explore";
		}

		return url;
	}

}

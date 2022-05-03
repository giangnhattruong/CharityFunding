/*
 * AdminController.java    1.00    2022-04-05
 */

package com.funix.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloudinary.Cloudinary;
import com.funix.auth.IAuthTokenizer;
import com.funix.auth.JWTImpl;
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
import com.funix.model.DonationHistoryFilter;
import com.funix.model.User;
import com.funix.model.UserFilter;
import com.funix.multipart.CloudinaryImpl;
import com.funix.multipart.IImageAPI;
import com.funix.processfile.IProcessFile;
import com.funix.processfile.ProcessFileImpl;
import com.funix.service.Navigation;
import com.funix.service.NullConvert;
import com.funix.service.PasswordService;
import com.funix.service.SQLConvert;

/**
 * Handle all routes for admin managing pages.
 * @author Giang_Nhat_Truong
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	/**
	 * Domain name for building verification link.
	 */
	private static final String DOMAIN = "http://localhost:8080";
	
	/**
	 * Life span of an verification's token (3 days).
	 */
	private static final int VERIFY_TOKEN_LIVE_TIME_MINS = 4320;
	
	/**
	 * DataSource for initializing
	 * DAO objects and manipulating
	 * data in the database.
	 */
	@Autowired
	private DataSource dataSource;
	
	/**
	 * Cloudinary for initializing
	 * ImageAPI objects and manipulating
	 * images on Cloudinary server.
	 */
	@Autowired
	private Cloudinary cloudinary;
	
	/**
	 * PasswordEncoder for encoding
	 * password when creating new user. 
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * Main route /admin redirect to 
	 * all donation history page.
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getAdminDashboard() {
		// if session does not contain adminID, redirect to explore
		return "redirect:/admin/donation-history";
	}

	/**
	 * Render all donation history.
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "donation-history", method = RequestMethod.GET)
	public ModelAndView getDonationHistory(
			@ModelAttribute("filter") DonationHistoryFilter filter,
			@ModelAttribute("message") String message,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
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
			
			// Get donation history.
			List<DonationHistory> historyList = historyDAO
					.getAllHistory(filter);
			
			// Get donation summary
			long numberOfTransactions = historyList.stream().count();
			double donationSum = historyList.stream()
					.mapToDouble(h -> h.getDonation()).sum();
			
			Navigation.addAdminNavItemMap(mv);
			mv.addObject("filter", filter);
			mv.addObject("message", message);
			mv.addObject("historyList", historyList);
			mv.addObject("numberOfTransactions", numberOfTransactions);
			mv.addObject("donationSum", donationSum);
			mv.setViewName("admin/donationHistory");
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
	@RequestMapping(value = "donation-history", 
			method = RequestMethod.POST)
	public ModelAndView searchDonationHistory(
			@ModelAttribute("filter") DonationHistoryFilter filter,
		    BindingResult result,
		    RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		redirectAttributes.addFlashAttribute("filter", filter);
		mv.setViewName("redirect:/admin/donation-history");
		return mv;
	}
	
	/**
	 * Handle upload donation history file.
	 * @param multipartFile
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "donation-history/upload",
			method = RequestMethod.POST)
	public ModelAndView uploadDonationHistory(
			@RequestParam("file") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		
		// Get history list from uploaded file.
		IProcessFile processFile = new ProcessFileImpl();
		List<DonationHistory> historyList = processFile
				.getDonationHistory(multipartFile);
		
		if (historyList.isEmpty()) {
			// If there is no history, or file can't be processed, return error.
			redirectAttributes.addFlashAttribute("message", 
					"File can not be processed. "
					+ "Please check your file and upload again.");
			mv.setViewName("redirect:/admin/donation-history");
		} else {
			// If file is successfully processed, then create history.
			IDonationHistoryDAO historyDAO =
					new DonationHistoryDAOImpl(dataSource);
			for (DonationHistory history: historyList) {
				historyDAO.create(history);
			}
			
			redirectAttributes.addFlashAttribute("message", 
					"File has been uploaded and processed.");
			mv.setViewName("redirect:/admin/donation-history");
		}
		
		return mv;
	}

	/**
	 * Render admin campaign managing page.
	 * @param message
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "campaigns", 
			method = RequestMethod.GET)
	public ModelAndView manageCampaigns(
			@ModelAttribute("message") String message,
			@ModelAttribute("filter") CampaignFilter filter,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			// Get all charity campaigns.
			ICampaignDAO campaignDAO = 
					new CampaignDAOImpl(dataSource);
			List<Campaign> campaignList = campaignDAO
					.getManyCampaigns(filter);
			
			// Get campaigns summary
			long numberOfCampaigns = campaignList.stream().count();
			double donationSum = campaignList.stream()
					.mapToDouble(c -> c.getTotalDonations()).sum();
			int supporterSum = campaignList.stream()
					.mapToInt(c -> c.getTotalSupporters()).sum();
			
			Navigation.addAdminNavItemMap(mv);
			mv.addObject("message", message);
			mv.addObject("filter", filter);
			mv.addObject("campaignList", campaignList);
			mv.addObject("numberOfCampaigns", numberOfCampaigns);
			mv.addObject("donationSum", donationSum);
			mv.addObject("supporterSum", supporterSum);
			mv.setViewName("admin/campaigns");
		}
		
		return mv;
	}

	/**
	 * Handle campaign search form submit.
	 * @param filter
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "campaigns", 
			method = RequestMethod.POST)
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
	 * Render campaign creating form.
	 * @param message
	 * @param campaign
	 * @return
	 */
	@RequestMapping(value = "campaigns/new",
			method = RequestMethod.GET)
	public ModelAndView getCampaignCreatingForm(
			@ModelAttribute("message") String message, 
			@ModelAttribute("campaign") Campaign campaign,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			Navigation.addAdminNavItemMap(mv);
			mv.addObject("formTitle", "Create");
			mv.addObject("formAction", "/admin/campaigns/new");
			mv.addObject("campaign", campaign);
			mv.setViewName("admin/createOrUpdateCampaign");
		}
		
		return mv;
	}

	/**
	 * Handle campaign creating.
	 * @param campaign
	 * @param result
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "campaigns/new", 
			method = RequestMethod.POST)
	public ModelAndView createCampaign(
			@ModelAttribute("campaign") Campaign campaign, 
		    BindingResult result,
		    HttpServletRequest request,
		    RedirectAttributes redirectAttributes)  {
		ModelAndView mv = new ModelAndView();
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			IImageAPI imageAPI = new CloudinaryImpl(cloudinary);
			ICampaignDAO campaignDAO = 
					new CampaignDAOImpl(dataSource, imageAPI);
			String validatingMessage = "";
			
			/*
			 * Set campaign startDate, endDate and status manually.
			 */
			String startDateString = request.getParameter("startDate");
			String endDateString = request.getParameter("endDate");
			LocalDate startDate = NullConvert.toLocalDate(startDateString);
			LocalDate endDate = NullConvert.toLocalDate(endDateString);
			campaign.setStartDate(startDate);
			campaign.setEndDate(endDate);
			int campaignStatus = request
					.getParameter("open") != null ? 1 : 0;
			campaign.setCampaignStatus(campaignStatus);
			
			validatingMessage = campaignDAO.create(campaign);
			
			if (!validatingMessage.equals("success")) {
				// Return error if validate failed.
				redirectAttributes
					.addFlashAttribute("message", validatingMessage);
				redirectAttributes
					.addFlashAttribute("campaign", campaign);
				mv.setViewName("redirect:/admin/campaigns/new");
			} else {
				// Redirect to all campaigns page with success message.
				redirectAttributes
					.addFlashAttribute("message", 
						"Campaign has been successfully created.");
				mv.setViewName("redirect:/admin/campaigns");
			}
		}
		
		return mv;
	}
	
	/**
	 * Render campaign updating form.
	 * @param pathID
	 * @param message
	 * @param campaign
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "campaigns/update/{campaignID}", 
			method = RequestMethod.GET)
	public ModelAndView getCampaignUpdatingForm(
			@PathVariable int campaignID, 
			@ModelAttribute("message") String message, 
			@ModelAttribute("campaign") Campaign campaign, 
			RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			IImageAPI imageAPI = new CloudinaryImpl(cloudinary);
			ICampaignDAO campaignDAO = 
					new CampaignDAOImpl(dataSource, imageAPI);
			Campaign originalCampaign = campaignDAO
					.getCampaign(campaignID);
			
			/**
			 * Check if campaign is allowed to be updated.
			 */
			if (!isUpdateAllowed(originalCampaign)) {
				// Return error if campaign is not allow to be updated.
				redirectAttributes
					.addFlashAttribute("message", 
							"You cannot edit this campaign.");
				mv.setViewName("redirect:/admin/campaigns");
			} else {
				/*
				 * Check if the campaign instance is not passed from
				 * the last failed submit, then get a campaign instance from
				 * database.
				 */
				if (message.equals("")) {
					campaign = originalCampaign;
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
				mv.setViewName("admin/createOrUpdateCampaign");
			}
		}
		
		return mv;
	}

	/**
	 * Handle campaign updating.
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
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			IImageAPI imageAPI = new CloudinaryImpl(cloudinary);
			ICampaignDAO campaignDAO = 
					new CampaignDAOImpl(dataSource, imageAPI);
			String validatingMessage = "";
			Campaign originalCampaign = campaignDAO
					.getCampaign(campaignID);
			
			/**
			 * Check if campaign is allowed to be updated.
			 */
			if (!isUpdateAllowed(originalCampaign)) {
				// Return error if campaign is not allow to be updated.
				redirectAttributes
					.addFlashAttribute("message", 
							"You cannot edit this campaign.");
				mv.setViewName("redirect:/admin/campaigns");
			} else {
				/*
				 * Set campaign startDate, endDate and status manually.
				 */
				String startDateString = request.getParameter("startDate");
				String endDateString = request.getParameter("endDate");
				LocalDate startDate = NullConvert.toLocalDate(startDateString);
				LocalDate endDate = NullConvert.toLocalDate(endDateString);
				campaign.setStartDate(startDate);
				campaign.setEndDate(endDate);
				int campaignStatus = request
						.getParameter("open") != null ? 1 : 0;
				campaign.setCampaignStatus(campaignStatus);
				validatingMessage = campaignDAO.update(campaignID, campaign);
				
				if (!validatingMessage.equals("success")) {
					// Return error if validate failed.
					campaign.setCampaignID(campaignID);
					redirectAttributes
						.addFlashAttribute("message", validatingMessage);
					redirectAttributes
						.addFlashAttribute("campaign", campaign);
					mv.setViewName("redirect:/admin/campaigns/update/" 
						+ campaignID);
				} else {
					// Redirect to all campaigns page with success message.
					redirectAttributes
						.addFlashAttribute("message", 
							"Campaign has been successfully updated.");
					mv.setViewName("redirect:/admin/campaigns");
				}
			}
		}
		
		return mv;
	}

	/**
	 * Handle delete many campaigns.
	 * @param request
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "campaigns/delete", 
			method = RequestMethod.POST)
	public ModelAndView deleteCampaign(HttpServletRequest request,
			RedirectAttributes redirectAttrs) {
		ModelAndView mv = new ModelAndView();
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			IImageAPI imageAPI = new CloudinaryImpl(cloudinary);
			ICampaignDAO campaignDAO = 
					new CampaignDAOImpl(dataSource, imageAPI);
			String message = "";
			
			/*
			 * Get array of campaign IDs from checkbox inputs,
			 * convert to string for using with SQL query
			 * "IN" operator.
			 */
			String[] campaignIDArray = request
					.getParameterValues("campaignIDs");
			String campaignIDs = SQLConvert
					.convertToListString(campaignIDArray);
			
			if (campaignIDArray != null) {
				campaignDAO.delete(campaignIDs);
				message = "Delete campaign(s) successful.";
			} else {
				message = "Delete failed. No items selected.";
			}
			
			redirectAttrs.addFlashAttribute("message", message);
			mv.setViewName("redirect:/admin/campaigns");
		}

		return mv;
	}

	/**
	 * Render admin user managing page.
	 * @param message
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "users", method = RequestMethod.GET)
	public ModelAndView manageUsers(
			@ModelAttribute("message") String message,
			@ModelAttribute("filter") UserFilter filter,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			// Get all users.
			IUserDAO userDao = new UserDAOImpl(dataSource);
			List<User> userList = userDao.getManyUsers(filter);
			
			// Get users summary.
			long numberOfUsers = userList.stream().count();
			double donationSum = userList.stream()
					.mapToDouble(u -> u.getTotalDonations()).sum();
			
			Navigation.addAdminNavItemMap(mv);
			mv.addObject("message", message);
			mv.addObject("filter", filter);
			mv.addObject("userList", userList);
			mv.addObject("numberOfUsers", numberOfUsers);
			mv.addObject("donationSum", donationSum);
			mv.setViewName("admin/users");
		}
		return mv;
	}

	/**
	 * Handle user search form submit.
	 * @param filter
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "users", method = RequestMethod.POST)
	public ModelAndView searchUsers(
			@ModelAttribute("filter") UserFilter filter,
			BindingResult result, 
			RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			redirectAttributes.addFlashAttribute("filter", filter);
			mv.setViewName("redirect:/admin/users");
		}
		
		return mv;
	}

	/**
	 * Render user creating form.
	 * @param message
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "users/new", method = RequestMethod.GET)
	public ModelAndView getUserCreatingForm(
			@ModelAttribute("message") String message, 
			@ModelAttribute("user") User user,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			Navigation.addAdminNavItemMap(mv);
			mv.addObject("formTitle", "Create");
			mv.addObject("formAction", "/admin/users/new");
			mv.addObject("user", user);
			mv.setViewName("admin/createOrUpdateUser");
		}
		
		return mv;
	}

	/**
	 * Handle user creating.
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
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			IUserDAO userDAO = new UserDAOImpl(dataSource);
			IEmailAPI emailAPI = new EmailAPIImpl();
			String validatingMessage = user.validate();
			
			// Set random password for user.
			String randomPassword = PasswordService
					.generateRandomPassword();
			user.setPassword(passwordEncoder, randomPassword);
			
			if(userDAO.checkForUser(user.getEmail())) {
				// Return error if user existed.
				redirectAttributes
				.addFlashAttribute("message", "Please try a different email. "
						+ "This email have already existed.");
				redirectAttributes
				.addFlashAttribute("user", user);
				mv.setViewName("redirect:/admin/users/new");
			} else if (!validatingMessage.equals("success")) {
				// Return error if validation failed.
				redirectAttributes
				.addFlashAttribute("message", validatingMessage);
				redirectAttributes
				.addFlashAttribute("user", user);
				mv.setViewName("redirect:/admin/users/new");
			} else {
				// Add new user to database.
				userDAO.create(user);
				
				// Send account activation email.
				String token = generateToken(user.getEmail(), 
						user.getUserRole(), 
						user.getUserStatus(), 
						VERIFY_TOKEN_LIVE_TIME_MINS);
				String verifyURL = DOMAIN
						+ request.getContextPath() 
						+ "/register/verify?token="
						+ token;
				emailAPI.sendVerificationMessage(randomPassword, 
						verifyURL, user.getEmail());
				
				// Redirect to all users page with success message.
				redirectAttributes
				.addFlashAttribute("message", 
						"User has been successfully created.");
				mv.setViewName("redirect:/admin/users");
			}
		}
		
		return mv;
	}

	/**
	 * Generate token from user ID and user role.
	 * @param user
	 * @param userID
	 * @param userStatus
	 * @return
	 */
	private String generateToken(String email, int userRole,
			int userStatus, double tokenLiveMins) {
		IAuthTokenizer authTokenizer = 
				new JWTImpl(email, VERIFY_TOKEN_LIVE_TIME_MINS);
		return authTokenizer.encodeUser();
	}
	
	/**
	 * Render user updating form.
	 * @param pathID
	 * @param message
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "users/update/{userID}", 
			method = RequestMethod.GET)
	public ModelAndView getUserUpdatingForm(
			@PathVariable int userID, 
			@ModelAttribute("message") String message, 
			@ModelAttribute("user") User user,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			IUserDAO userDAO = new UserDAOImpl(dataSource);
			
			/*
			 * Check if the user instance is not passed from
			 * the last failed submit, then get a user instance from
			 * database.
			 */
			if (message.equals("")) {
				user = userDAO.getUserSummaryInfo(userID);
			}
			
			/*
			 * Check if user is allowed to be updated.
			 */
			if (!isUpdateAllowed(user)) {
				redirectAttributes.addFlashAttribute("message", 
						"Sorry! You cannot edit this user.");
				mv.setViewName("redirect:/admin/users");
			} else {
				Navigation.addAdminNavItemMap(mv);
				mv.addObject("formTitle", "Update");
				mv.addObject("formAction", 
						"/admin/users/update/" + userID);
				mv.addObject("user", user);
				mv.setViewName("admin/createOrUpdateUser");
			}
		}
		
		return mv;
	}

	/**
	 * Handle user updating.
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
		    RedirectAttributes redirectAttributes,
		    HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {			
			String validatingMessage = user.validate();
			IUserDAO userDAO = new UserDAOImpl(dataSource);
			User originalUser = userDAO.getUserSimpleInfo(userID);

			/*
			 * If user have activate their account, then admin can
			 * ban or re-activate that user. Otherwise, keep their status
			 * as 0-not-verified.
			 */
			if (originalUser.getUserStatus() != 0) {
				// Check if user is banned by admin or not.
				int userStatus = request
						.getParameter("activate") != null ? 1 : 2;
				user.setUserStatus(userStatus);
			}
			
			/**
			 * Check if original user is not admin or deleted,
			 * then allow user to be updated.
			 */
			if (!isUpdateAllowed(originalUser)) {
				// Return error if user try to update another admin user or deleted user.
				redirectAttributes
					.addFlashAttribute("message", "This action is forbidden!");
				mv.setViewName("redirect:/admin/users");
			} else if (!validatingMessage.equals("success")) {
				// Return error if validate failed.
				redirectAttributes
					.addFlashAttribute("message", validatingMessage);
				redirectAttributes
					.addFlashAttribute("user", user);
				mv.setViewName("redirect:/admin/users/update/" + userID);
			} else {
				// Update user.
				userDAO.update(userID, user);
				redirectAttributes
					.addFlashAttribute("message", 
						"User has been successfully updated.");
				mv.setViewName("redirect:/admin/users");
			}
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
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			IUserDAO userDAO = new UserDAOImpl(dataSource);
			String message = "";
			
			/* 
			 * Get array of user ID from checkbox inputs and convert
			 * to string for using with SQL query "IN" operator. 
			 */
			String[] userIDArray = request.getParameterValues("userIDs");
			String userIDs = SQLConvert.convertToListString(userIDArray);
			
			// Delete user in list.
			if (userIDArray != null) {
				userDAO.delete(userIDs);
				message = "Delete user(s) successful.";
			} else {
				message = "Delete failed. No items selected.";
			}
			
			redirectAttrs.addFlashAttribute("message", message);
			mv.setViewName("redirect:/admin/users");
		}

		return mv;
	}
	
	/**
	 * Handle reset password for many users.
	 * @param request
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "users/reset-passwords", method = RequestMethod.POST)
	public ModelAndView resetUserPasswords(
			HttpServletRequest request,
			RedirectAttributes redirectAttrs) {
		ModelAndView mv = new ModelAndView();
		
		// If user is not admin, redirect to home page.
		if (!isLegalUser(request, getUserFromSession(request))) {
			mv.setViewName("redirect:/explore");
		} else {
			IUserDAO userDAO = new UserDAOImpl(dataSource);
			IEmailAPI emailAPI = new EmailAPIImpl();
			String message = "";
			String[] userIDArray = request.getParameterValues("userIDs");
			
			/**
			 * Check if there are users selected, then check if user
			 * is not an admin, generate a random password, encode this
			 * password, update user new encoded password and send 
			 * the new password to user email.
			 */
			if (userIDArray != null) {
				// Prepare an email list that can't receive message.
				boolean emailResult = false;
				List<String> emailFailedList = new ArrayList<>();
				
				for (String userIDString: userIDArray) {
					// Get user info.
					int userID = NullConvert.toInt(userIDString);
					User user = userDAO.getUserSummaryInfo(userID);
					
					// Update allowed user only.
					if (isUpdateAllowed(user)) {
						// Update user new random password.
						String randomPassword = PasswordService
								.generateRandomPassword();
						String encodedPassword = passwordEncoder
								.encode(randomPassword);
						userDAO.update(userID, encodedPassword);
						
						// Send email with new password to user.
						String email = user.getEmail();
						emailResult = emailAPI
								.sendNewPassword(randomPassword, email);
						
						/*
						 * In case errors happen when sending email, 
						 * add these email which failed to receive
						 * message to list, to be show on notify message.
						 */
						if (emailResult == false) {
							emailFailedList.add(email);
						}
					}
				}
				
				message = emailResult == true ?
						"All passwords were changed." :
							"All passwords were changed, "
							+ "but failed to send emails "
							+ "to users "
							+ emailFailedList.toString()
							.replace('[', '(')
							.replace(']', ')') + ".";
			} else {
				message = "Change password failed. No items selected.";
			}
			
			redirectAttrs.addFlashAttribute("message", message);
			mv.setViewName("redirect:/admin/users");
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
	 * Check if there is a admin.
	 * @param user
	 * @return
	 */
	private boolean isLegalUser(HttpServletRequest request, User user) {
		return user.getUserStatus() == 1 && user.getUserRole() > 0;
	}

	/**
	 * Check if user is not admin (role-1) or not deleted (status-3).
	 * Then allow user to be updated.
	 * @param user
	 * @return
	 */
	private boolean isUpdateAllowed(User user) {
		return user.getUserRole() != 1 || user.getUserStatus() != 3;
	}
	
	/**
	 * Check if campaign is not deleted (status-2).
	 * Then allow campaign to be updated.
	 * @param user
	 * @return
	 */
	private boolean isUpdateAllowed(Campaign campaign) {
		return campaign.getCampaignStatus() != 2;
	}

}

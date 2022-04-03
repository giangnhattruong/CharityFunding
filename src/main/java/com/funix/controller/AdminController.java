package com.funix.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloudinary.Cloudinary;
import com.funix.dao.CampaignDAOImpl;
import com.funix.dao.ICampaignDAO;
import com.funix.model.Campaign;
import com.funix.model.CampaignFilter;
import com.funix.multipart.CloudinaryImpl;
import com.funix.multipart.IImageAPI;
import com.funix.service.Navigation;
import com.funix.service.NullConvert;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private Cloudinary cloudinary;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getAdminDashboard() {
		// if session does not contain adminID, redirect to explore

		return "redirect:/admin/donation-history";
	}

	@RequestMapping(value = "donation-history", method = RequestMethod.GET)
	public ModelAndView getDonationHistory() {
		ModelAndView mv = new ModelAndView();

		// Get and send donationHistory
		Navigation.addAdminNavItemMap(mv);
		mv.addObject("received", "true");
		mv.addObject("notReceived", "true");
		mv.setViewName(getRoute("admin/donationHistory"));

		return mv;
	}

	@RequestMapping(value = "donation-history", method = RequestMethod.POST)
	public ModelAndView searchDonationHistory(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		String userKeyword = request.getParameter("userKeyword");
		String campaignKeyword = request.getParameter("campaignKeyword");
		String transactionKeyword = request.getParameter("transactionKeyword");
		String received = request.getParameter("received");
		String notReceived = request.getParameter("notReceived");
		String sort = request.getParameter("sort");

		// Get and send donationHistory
		Navigation.addAdminNavItemMap(mv);
		mv.addObject("userKeyword", userKeyword);
		mv.addObject("campaignKeyword", campaignKeyword);
		mv.addObject("transactionKeyword", transactionKeyword);
		mv.addObject("received", received);
		mv.addObject("notReceived", notReceived);
		mv.addObject("sort", sort);
		mv.setViewName(getRoute("admin/donationHistory"));

		return mv;
	}

	@RequestMapping(value = "campaigns", method = RequestMethod.GET)
	public ModelAndView manageCampaigns(
			@ModelAttribute("message") String message,
			@ModelAttribute("filter") CampaignFilter filter
			) {
		ModelAndView mv = new ModelAndView();
		IImageAPI imageAPI = new CloudinaryImpl(cloudinary);
		ICampaignDAO campaignDAO = new CampaignDAOImpl(dataSource, imageAPI);
		List<Campaign> campaignList = campaignDAO.getManyCampaigns(filter);
		
		Navigation.addAdminNavItemMap(mv);
		mv.addObject("message", message);
		mv.addObject("filter", filter);
		mv.addObject("campaignList", campaignList);
		mv.setViewName(getRoute("admin/campaigns"));
		
		return mv;
	}

	@RequestMapping(value = "campaigns", method = RequestMethod.POST)
	public ModelAndView searchCampaigns(HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		CampaignFilter filter = new CampaignFilter();
		String keyword = request.getParameter("keyword");
		String location = request.getParameter("location");
		String open = request.getParameter("open");
		String closed = request.getParameter("closed");
		String sort = request.getParameter("sort");
		filter.setFilter(keyword, location, open, closed, sort);

		redirectAttributes.addFlashAttribute("filter", filter);
		mv.setViewName("redirect:/admin/campaigns");

		return mv;
	}

	@RequestMapping(value = { "campaigns/new", "campaigns/update" }, 
			method = RequestMethod.GET)
	public ModelAndView getCampaignForm(
			@ModelAttribute("message") String message, 
			@ModelAttribute("campaign") Campaign campaign, 
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		IImageAPI imageAPI = new CloudinaryImpl(cloudinary);
		ICampaignDAO campaignDAO = new CampaignDAOImpl(dataSource, imageAPI);
		String currentURI = request.getRequestURI();
		String formTitle = "";
		String formAction = "";

		if (currentURI.endsWith("new")) {
			formTitle = "Create";
			formAction = "/admin/campaigns/new";
		} else if (currentURI.endsWith("update")) {
			formTitle = "Update";
			formAction = "/admin/campaigns/update";
			String id = request.getParameter("id");
			int campaignID = NullConvert.toInt(id);
			
			if (campaignID != 0) {
				campaign = campaignDAO.getCampaign(campaignID);
			}
			
			String imgURL = campaign.getImgURL();
			
			if (imgURL != null) {
				String imgHTML = imageAPI
						.transformImage(imgURL, 80, 60);
				mv.addObject("imgHTML", imgHTML);
			}
		}
		
		Navigation.addAdminNavItemMap(mv);
		mv.addObject("formTitle", formTitle);
		mv.addObject("formAction", formAction);
		mv.addObject("campaign", campaign);
		mv.setViewName(getRoute("admin/createOrUpdateCampaign"));

		return mv;
	}

	@RequestMapping(value = "campaigns/new", method = RequestMethod.POST)
	public ModelAndView createCampaign(
			@ModelAttribute("campaign")Campaign campaign, 
		    BindingResult result,
		    HttpServletRequest request,
		    RedirectAttributes redirectAttributes)  {
		ModelAndView mv = new ModelAndView();
		IImageAPI imageAPI = new CloudinaryImpl(cloudinary);
		ICampaignDAO campaignDAO = new CampaignDAOImpl(dataSource, imageAPI);
		String message = "";
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

	@RequestMapping(value = "campaigns/update", method = RequestMethod.POST)
	public ModelAndView updateCampaign(
			@ModelAttribute("campaign")Campaign campaign, 
		    BindingResult result,
		    HttpServletRequest request,
		    RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		IImageAPI imageAPI = new CloudinaryImpl(cloudinary);
		ICampaignDAO campaignDAO = new CampaignDAOImpl(dataSource, imageAPI);
		String message = "";
		String startDateString = request.getParameter("startDate");
		String endDateString = request.getParameter("endDate");
		LocalDate startDate = NullConvert.toLocalDate(startDateString);
		LocalDate endDate = NullConvert.toLocalDate(endDateString);
		campaign.setStartDate(startDate);
		campaign.setEndDate(endDate);
		message = campaignDAO.update(campaign.getCampaignID(), campaign);
		
		if (message.equals("success")) {
			redirectAttributes
				.addFlashAttribute("message", 
						"Campaign has been successfully updated.");
			mv.setViewName("redirect:/admin/campaigns");
		} else {
			redirectAttributes
				.addFlashAttribute("message", message);
			redirectAttributes
			.addFlashAttribute("campaign", campaign);
			mv.setViewName("redirect:/admin/campaigns/update");
		}
		
		return mv;
	}

	@RequestMapping(value = "campaigns/delete", method = RequestMethod.POST)
	public ModelAndView deleteCampaign(HttpServletRequest request,
			RedirectAttributes redirectAttrs) {
		ModelAndView mv = new ModelAndView();
		IImageAPI imageAPI = new CloudinaryImpl(cloudinary);
		ICampaignDAO campaignDAO = new CampaignDAOImpl(dataSource, imageAPI);
		String message = "";
		
		String[] campaignIDArray = request.getParameterValues("campaignIDs");
		String campaignIDs = Arrays.toString(campaignIDArray)
				.replace('[', '(').replace(']', ')');
		
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

	@RequestMapping(value = "users", method = RequestMethod.GET)
	public ModelAndView manageUsers() {
		ModelAndView mv = new ModelAndView();

		// Get and send userList
		Navigation.addAdminNavItemMap(mv);
		mv.addObject("active", "true");
		mv.addObject("inactive", "true");
		mv.setViewName(getRoute("admin/users"));

		return mv;
	}

	@RequestMapping(value = "users", method = RequestMethod.POST)
	public ModelAndView searchUsers(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		String keyword = request.getParameter("keyword");
		String active = request.getParameter("active");
		String inactive = request.getParameter("inactive");
		String sort = request.getParameter("sort");

		// Get and send userList
		Navigation.addAdminNavItemMap(mv);
		mv.addObject("keyword", keyword);
		mv.addObject("active", active);
		mv.addObject("inactive", inactive);
		mv.addObject("sort", sort);
		mv.setViewName(getRoute("admin/users"));

		return mv;
	}

	@RequestMapping(value = { "users/new", "users/update" }, method = RequestMethod.GET)
	public ModelAndView getUserForm(@RequestParam int id) {
		ModelAndView mv = new ModelAndView();

		// if (url is update && id exists)
		// -> get and send user object
		Navigation.addAdminNavItemMap(mv);
		mv.setViewName(getRoute("admin/createOrUpdateUser"));

		return mv;
	}

	@RequestMapping(value = "users/new", method = RequestMethod.POST)
	public ModelAndView createUser() {
		ModelAndView mv = new ModelAndView();

		// Get and add user to DB
		mv.addObject("message", "User has been successfully created.");
		mv.setViewName("redirect:/admin/users");

		return mv;
	}

	@RequestMapping(value = "users/update", method = RequestMethod.POST)
	public ModelAndView updateUser() {
		ModelAndView mv = new ModelAndView();

		// Get and update campaign in DB
		mv.addObject("message", "User has been successfully updated.");
		mv.setViewName("redirect:/admin/users");

		return mv;
	}

	@RequestMapping(value = "users/delete", method = RequestMethod.POST)
	public ModelAndView deleteUser(@RequestParam Map<String, String> requestParams) {
		ModelAndView mv = new ModelAndView();

		// Get requestParams.values() -> campaignIDs
		// Delete many campaign in a transaction in DB
		mv.addObject("message", "Delete user(s) successful.");
		mv.setViewName("redirect:/admin/users");

		return mv;
	}

	private String getRoute(String url) {
		/*
		 * if session not contains adminID redirect to landing page
		 */
		if (false) {
			return "redirect:/explore";
		}

		return url;
	}

}

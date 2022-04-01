package com.funix.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.funix.dao.CampaignDAOImpl;
import com.funix.model.Campaign;
import com.funix.model.CampaignFilter;
import com.funix.service.Navigation;
import com.microsoft.sqlserver.jdbc.SQLServerException;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private CampaignDAOImpl campaignDAO;
	
	@Autowired
	public CampaignFilter filter;

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
			@ModelAttribute("message") String message
			) {
		ModelAndView mv = new ModelAndView();
		
		List<Campaign> campaignList = campaignDAO.getManyCampaigns(filter);
		
		Navigation.addAdminNavItemMap(mv);
		mv.addObject("message", message);
		mv.addObject("filter", filter);
		mv.addObject("campaignList", campaignList);
		mv.setViewName(getRoute("admin/campaigns"));
		
		return mv;
	}

	@RequestMapping(value = "campaigns", method = RequestMethod.POST)
	public ModelAndView searchCampaigns(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String keyword = request.getParameter("keyword");
		String location = request.getParameter("location");
		String open = request.getParameter("open");
		String closed = request.getParameter("closed");
		String sort = request.getParameter("sort");
		
		filter.setFilter(keyword, location, open, closed, sort);

		Navigation.addAdminNavItemMap(mv);
		mv.setViewName("redirect:/admin/campaigns");

		return mv;
	}

	@RequestMapping(value = { "campaigns/new", "campaigns/update" }, method = RequestMethod.GET)
	public ModelAndView getCampaignForm() {
		ModelAndView mv = new ModelAndView();

		// if (url is update && id exists)
		// -> get and send campaign object
		Navigation.addAdminNavItemMap(mv);
		mv.setViewName(getRoute("admin/createOrUpdateCampaign"));

		return mv;
	}

	@RequestMapping(value = "campaigns/new", method = RequestMethod.POST)
	public ModelAndView createCampaign() {
		ModelAndView mv = new ModelAndView();

		// Get and add campaign to DB
		mv.addObject("message", "Campaign has been successfully created.");
		mv.setViewName("redirect:/admin/campaigns");

		return mv;
	}

	@RequestMapping(value = "campaigns/update", method = RequestMethod.POST)
	public ModelAndView updateCampaign() {
		ModelAndView mv = new ModelAndView();

		// Get and update campaign in DB
		mv.addObject("message", "Campaign has been successfully updated.");
		mv.setViewName("redirect:/admin/campaigns");

		return mv;
	}

	@RequestMapping(value = "campaigns/delete", method = RequestMethod.POST)
	public ModelAndView deleteCampaign(HttpServletRequest request,
			RedirectAttributes redirectAttrs) {
		ModelAndView mv = new ModelAndView();
		String message = "Delete campaign(s) successful.";
		
		String[] campaignIDArray = request.getParameterValues("campaignIDs");
		String campaignIDs = Arrays.toString(campaignIDArray)
				.replace('[', '(').replace(']', ')');
		
		if (campaignIDArray != null) {
			campaignDAO.delete(campaignIDs);
		} else {
			message = "Delete failed.";
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

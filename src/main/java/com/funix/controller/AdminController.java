package com.funix.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAdminDashboard() {
		//if session does not contain adminID, redirect to explore
		
		return manageCampaigns();
	}
	
	@RequestMapping(value = "campaigns", 
			method = RequestMethod.GET)
	public ModelAndView manageCampaigns() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and send campaignList
		addNavItemMap(mv);
		mv.setViewName("admin/campaigns");
		
        return mv;
	}
	
	@RequestMapping(value = "users", 
			method = RequestMethod.GET)
	public ModelAndView manageUsers() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and send userList
		addNavItemMap(mv);
		mv.setViewName("admin/users");
		
        return mv;
	}
	
	@RequestMapping(value = {"campaigns/new", "campaigns/update"}, 
			method = RequestMethod.GET)
	public ModelAndView getCampaignForm(@RequestParam int id) {
		ModelAndView mv =
				new ModelAndView();
		
		//if (url is update && id exists) 
		//-> get and send campaign object
		addNavItemMap(mv);
		mv.setViewName("admin/createOrUpdateCampaign");
		
        return mv;
	}
	
	@RequestMapping(value = "campaigns/new", 
			method = RequestMethod.POST)
	public ModelAndView createCampaign() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and add campaign to DB
		mv.addObject("message", "Campaign has been successfully created.");
		mv.setViewName("redirect:/admin/campaigns");
		
		return mv;
	}
	
	@RequestMapping(value = "campaigns/update", 
			method = RequestMethod.POST)
	public ModelAndView updateCampaign() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and update campaign in DB
		mv.addObject("message", "Campaign has been successfully updated.");
		mv.setViewName("redirect:/admin/campaigns");
		
		return mv;
	}
	
	@RequestMapping(value = "campaigns/delete", 
			method = RequestMethod.POST)
	public ModelAndView deleteCampaign(@RequestParam Map<String,String> requestParams) {
		ModelAndView mv =
				new ModelAndView();
		
		//Get requestParams.values() -> campaignIDs
		//Delete many campaign in a transaction in DB
		mv.addObject("message", "Delete campaign(s) successful.");
		mv.setViewName("redirect:/admin/campaigns");
		
		return mv;
	}
	
	@RequestMapping(value = {"users/new", "users/update"}, 
			method = RequestMethod.GET)
	public ModelAndView getUserForm(@RequestParam int id) {
		ModelAndView mv =
				new ModelAndView();
		
		//if (url is update && id exists) 
		//-> get and send user object
		addNavItemMap(mv);
		mv.setViewName("admin/createOrUpdateUser");
		
        return mv;
	}
	
	@RequestMapping(value = "users/new", 
			method = RequestMethod.POST)
	public ModelAndView createUser() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and add user to DB
		mv.addObject("message", "User has been successfully created.");
		mv.setViewName("redirect:/admin/users");
		
		return mv;
	}
	
	@RequestMapping(value = "users/update", 
			method = RequestMethod.POST)
	public ModelAndView updateUser() {
		ModelAndView mv =
				new ModelAndView();
		
		//Get and update campaign in DB
		mv.addObject("message", "User has been successfully updated.");
		mv.setViewName("redirect:/admin/users");
		
		return mv;
	}
	
	@RequestMapping(value = "users/delete", 
			method = RequestMethod.POST)
	public ModelAndView deleteUser(@RequestParam Map<String,String> requestParams) {
		ModelAndView mv =
				new ModelAndView();
		
		//Get requestParams.values() -> campaignIDs
		//Delete many campaign in a transaction in DB
		mv.addObject("message", "Delete user(s) successful.");
		mv.setViewName("redirect:/admin/users");
		
		return mv;
	}

	private void addNavItemMap(ModelAndView mv) {
		Map<String, String> navItemMap = 
				new HashMap<>();
		
		navItemMap.put("Campaigns", "/admin/campaigns");
		navItemMap.put("Users", "/admin/users");

		mv.addObject("navItemMap", navItemMap);
	}
	
}

package com.funix.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

public class Navigation {

	public static void addMainNavItemMap(ModelAndView mv) {
		Map<String, String> navItemMap = 
				new HashMap<>();
		
		navItemMap.put("About", "/about");
		navItemMap.put("Contact", "/contact");

		mv.addObject("navItemMap", navItemMap);
	}

	public static void addAdminNavItemMap(ModelAndView mv) {
		Map<String, String> navItemMap = 
				new HashMap<>();
		
		navItemMap.put("Campaigns", "/admin/campaigns");
		navItemMap.put("Users", "/admin/users");
		navItemMap.put("Donation history", "/admin/donation-history");

		mv.addObject("navItemMap", navItemMap);
	}
	
	public static void addUserNavItemMap(ModelAndView mv) {
		Map<String, String> navItemMap = 
				new HashMap<>();
		
		navItemMap.put("Donation history", "/user/donation-history");
		navItemMap.put("Update profile", "/user/update-profile");

		mv.addObject("navItemMap", navItemMap);
	}

}

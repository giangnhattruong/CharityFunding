package com.funix.controller.service;

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

		mv.addObject("navItemMap", navItemMap);
	}
	
	public static void addUserNavItemMap(ModelAndView mv) {
		Map<String, String> navItemMap = 
				new HashMap<>();
		
		navItemMap.put("Histories", "/user/histories");
		navItemMap.put("Profile", "/user/profile");

		mv.addObject("navItemMap", navItemMap);
	}

}

/*
 * Navigation.java    1.00    2022-04-05
 */

package com.funix.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

/**
 * Helper class for passing navigation-bar titles and links
 * from Controller to View to be used as navigation items.
 * To use the same dynamic header component in all views.
 * @author Giang_Nhat_Truong
 *
 */
public class Navigation {

	/**
	 * Pass navigation titles and links
	 * of main pages to View.
	 * @param mv
	 */
	public static void addMainNavItemMap(ModelAndView mv) {
		Map<String, String> navItemMap = 
				new HashMap<>();
		
		navItemMap.put("About", "/about");
		navItemMap.put("Contact", "/contact");

		mv.addObject("navItemMap", navItemMap);
	}

	/**
	 * Pass navigation titles and links
	 * of admin manage pages to View.
	 * @param mv
	 */
	public static void addAdminNavItemMap(ModelAndView mv) {
		Map<String, String> navItemMap = 
				new HashMap<>();
		
		navItemMap.put("Campaigns", "/admin/campaigns");
		navItemMap.put("Users", "/admin/users");
		navItemMap.put("Donation history", "/admin/donation-history");

		mv.addObject("navItemMap", navItemMap);
		mv.addObject("role", "admin");
	}
	
	/**
	 * Pass navigation titles and links
	 * of user manage pages to View.
	 * @param mv
	 */
	public static void addUserNavItemMap(ModelAndView mv) {
		Map<String, String> navItemMap = 
				new HashMap<>();
		
		navItemMap.put("Donation history", "/user/donation-history");
		navItemMap.put("Profile", "/user/update-profile");

		mv.addObject("navItemMap", navItemMap);
		mv.addObject("role", "user");
	}

}

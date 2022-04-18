/*
 * MainController.java    1.00    2022-04-15
 */

package com.funix.controller;

import java.util.List;

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

import com.funix.dao.CampaignDAOImpl;
import com.funix.dao.ICampaignDAO;
import com.funix.model.Campaign;
import com.funix.model.CampaignFilter;
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
		ICampaignDAO campaignDAO = new CampaignDAOImpl(dataSource);
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
	
	@RequestMapping(value = "/about",
			method = RequestMethod.GET)
	public ModelAndView getAboutPage() {
		ModelAndView mv =
				new ModelAndView();
		
		Navigation.addMainNavItemMap(mv);
		mv.setViewName("main/about");
		
		return mv;
	}
	
	@RequestMapping(value = "/contact",
			method = RequestMethod.GET)
	public ModelAndView getContactPage() {
		ModelAndView mv =
				new ModelAndView();
		
		Navigation.addMainNavItemMap(mv);
		mv.setViewName("main/contact");
		
		return mv;
	}
	
	@RequestMapping(value = "/campaign",
			method = RequestMethod.GET)
	public ModelAndView getCampaignPage(@RequestParam int id) {
		ModelAndView mv =
				new ModelAndView();
		
		// Get and send campaign object
		Navigation.addMainNavItemMap(mv);
		mv.setViewName("main/campaignDetails");
		
		return mv;
	}
	
}

/*
 * CampaignFilter.java    1.00    2022-04-05
 */

package com.funix.model;

import com.funix.service.SQLConvert;

/**
 * Campaign filter model contains inputs 
 * taken from campaign search form, 
 * which can be converted to special strings in 
 * SQL query for data filter purpose.
 * @author Giang_Nhat_Truong
 *
 */
public class CampaignFilter {
	
	/**
	 * Charity campaign title or description keyword.
	 */
	private String keyword;
	
	/**
	 * Charity campaign location keyword.
	 */
	private String location;
	
	/**
	 * Status of a charity campaign is still open.
	 */
	private boolean statusOpen;
	
	/**
	 * Status of a charity campaign is closed.
	 */
	private boolean statusClosed;
	
	/**
	 * Order of campaign records sorted by 
	 * specific fields in campaign data table.
	 */
	private String sortBy;

	/**
	 * Default constructor used to initialize
	 * campaign search form values.
	 */
	public CampaignFilter() {
		statusOpen = true;
		statusClosed = true;
		sortBy = "date-desc";
	}

	/**
	 * Get campaign title/description filter string for SQL query.
	 * @return
	 */
	public String getKeywordFilter() {
		return SQLConvert.convertKeyword(keyword);
	}

	/**
	 * Get campaign location filter string for SQL query.
	 * @return
	 */
	public String getLocationFilter() {
		return SQLConvert.convertKeyword(location);
	}

	/**
	 * Get campaign status filter string for SQL query.
	 * @return
	 */
	public String getStatusFilter() {
		return SQLConvert.convertStatus(statusOpen, statusClosed);
	}

	/**
	 * Get SQL "ORDER BY" clause to sort campaign table records.
	 * @return
	 */
	public String getOrderByFilter() {
		return SQLConvert.convertCampaignOrder(sortBy);
	}

	//Getters and setters
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean getStatusOpen() {
		return statusOpen;
	}

	public void setStatusOpen(boolean statusOpen) {
		this.statusOpen = statusOpen;
	}

	public boolean getStatusClosed() {
		return statusClosed;
	}

	public void setStatusClosed(boolean statusClosed) {
		this.statusClosed = statusClosed;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

}

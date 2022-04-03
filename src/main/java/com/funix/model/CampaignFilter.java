package com.funix.model;

import com.funix.service.FilterMapping;

public class CampaignFilter {
	private String keyword;
	private String location;
	private String statusOn;
	private String statusOff;
	private String sortBy;
	
	public CampaignFilter() {
		keyword = "";
		location = "";
		statusOn = "true";
		statusOff = "true";
		sortBy = "date-desc";
	}

	public void setFilter(String statusOn, String statusOff, String sortBy) {
		this.setFilter(null, null, statusOn, statusOff, sortBy);
	}
	
	public void setFilter(String keyword, String location, 
			String statusOn, String statusOff, String sortBy) {
		this.keyword = keyword;
		this.location = location;
		this.statusOn = statusOn;
		this.statusOff = statusOff;
		this.sortBy = sortBy;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getLocation() {
		return location;
	}

	public String getStatusOn() {
		return statusOn;
	}

	public String getStatusOff() {
		return statusOff;
	}

	public String getSortBy() {
		return sortBy;
	}

	public String getKeywordFilter() {
		return FilterMapping.convertKeyword(keyword);
	}

	public String getLocationFilter() {
		return FilterMapping.convertKeyword(location);
	}

	public String getStatusFilter() {
		return FilterMapping.convertStatus(statusOn, statusOff);
	}

	public String getOrderByFilter() {
		return FilterMapping.convertCampaignOrder(sortBy);
	}

}

package com.funix.model;

import com.funix.service.FilterMapping;

public class CampaignFilter {
	private String keyword;
	private String location;
	private boolean statusOpen;
	private boolean statusClosed;
	private String sortBy;

	public CampaignFilter() {
		statusOpen = true;
		statusClosed = true;
		sortBy = "date-desc";
	}

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

	public String getKeywordFilter() {
		return FilterMapping.convertKeyword(keyword);
	}

	public String getLocationFilter() {
		return FilterMapping.convertKeyword(location);
	}

	public String getStatusFilter() {
		return FilterMapping.convertStatus(statusOpen, statusClosed);
	}

	public String getOrderByFilter() {
		return FilterMapping.convertCampaignOrder(sortBy);
	}

}

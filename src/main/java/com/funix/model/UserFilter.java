package com.funix.model;

import com.funix.service.FilterMapping;

public class UserFilter {
	private String keyword;
	private String statusOn;
	private String statusOff;
	private String sortBy;

	public UserFilter() {
		keyword = "";
		statusOn = "true";
		statusOff = "true";
		sortBy = "date-desc";
	}

	public void setFilter(String statusOn, String statusOff, String sortBy) {
		this.setFilter(null, statusOn, statusOff, sortBy);
	}

	public void setFilter(String keyword, String statusOn, String statusOff, String sortBy) {
		this.keyword = keyword;
		this.statusOn = statusOn;
		this.statusOff = statusOff;
		this.sortBy = sortBy;
	}

	public String getKeyword() {
		return keyword;
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

	public String getStatusOnFilter() {
		return FilterMapping.convertStatus(statusOn, statusOff);
	}

	public String getSortByFilter() {
		return FilterMapping.convertUserOrder(sortBy);
	}

}

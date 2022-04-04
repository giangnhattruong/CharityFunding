package com.funix.model;

import com.funix.service.FilterMapping;

public class UserFilter {
	private String keyword;
	private boolean statusActive;
	private boolean statusInactive;
	private String sortBy;

	public UserFilter() {
		statusActive = true;
		statusInactive = true;
		sortBy = "date-desc";
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public boolean getStatusActive() {
		return statusActive;
	}

	public void setStatusActive(boolean statusActive) {
		this.statusActive = statusActive;
	}

	public boolean getStatusInactive() {
		return statusInactive;
	}

	public void setStatusInactive(boolean statusInactive) {
		this.statusInactive = statusInactive;
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

	public String getStatusFilter() {
		return FilterMapping.convertStatus(statusActive, statusInactive);
	}

	public String getSortByFilter() {
		return FilterMapping.convertUserOrder(sortBy);
	}

}

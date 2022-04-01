package com.funix.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.funix.service.UserFilterMapping;

public class UserFilter {

	@Autowired
	private UserFilterMapping userFilterMapping;

	private String keyword;
	private String status;
	private String orderBy;

	public UserFilter(String keyword, String statusOn, String statusOff, String sortBy) {
		userFilterMapping.setStatusOn(statusOn);
		userFilterMapping.setStatusOff(statusOff);
		userFilterMapping.setSortBy(sortBy);
		this.keyword = keyword;
		this.status = userFilterMapping.convertStatus();
		this.orderBy = userFilterMapping.convertOrder();
	}

	public String getKeyword() {
		return keyword;
	}

	public String getStatus() {
		return status;
	}

	public String getOrderBy() {
		return orderBy;
	}

}

/*
 * UserFilter.java    1.00    2022-04-05
 */

package com.funix.model;

import java.util.Arrays;

import com.funix.service.SQLConvert;

/**
 * User filter model contains inputs taken from
 * user search form, which can be converted
 * to special strings in SQL query for 
 * data filter purpose.
 * @author Giang_Nhat_Truong
 *
 */
public class UserFilter {
	
	/**
	 * User email or fullname keyword.
	 */
	private String keyword;
	
	/**
	 * Status of an user:
	 * 0-In-active
	 * 1-Active
	 * 2-Banned
	 * 3-Deleted
	 */
	private String[] statuses;
	
	/**
	 * Role of an user.
	 * 0-User
	 * 1-Admin
	 */
	private String[] roles;
	
	/**
	 * Order of user records sorted by 
	 * specific fields in user data table.
	 */
	private String sortBy;

	/**
	 * Default constructor used to initialize
	 * user search form values.
	 */
	public UserFilter() {
		statuses = new String[]{"0", "1", "2"};
		roles = new String[]{"0", "1"};
		sortBy = "date-desc";
	}

	/**
	 * Get user email/fullname filter string for SQL query.
	 * @return
	 */
	public String getKeywordFilter() {
		return SQLConvert.convertKeyword(keyword);
	}

	/**
	 * Get user status filter string for SQL query.
	 * @return
	 */
	public String getStatusFilter() {
		return SQLConvert.convertToListString(statuses);
	}
	
	/**
	 * Get user role filter string for SQL query.
	 * @return
	 */
	public String getRoleFilter() {
		return SQLConvert.convertToListString(roles);
	}

	/**
	 * Get SQL "ORDER BY" clause to sort user table records.
	 * @return
	 */
	public String getSortByFilter() {
		return SQLConvert.convertUserOrder(sortBy);
	}

	//Getters and setters
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getStatuses() {
		return Arrays.toString(statuses);
	}

	public void setStatuses(String[] statuses) {
		this.statuses = statuses;
	}

	public String getRoles() {
		return Arrays.toString(roles);
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

}

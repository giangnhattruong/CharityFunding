/*
 * UserFilter.java    1.00    2022-04-05
 */

package com.funix.model;

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
	 * Status of an user active/verified.
	 */
	private boolean statusActive;
	
	/**
	 * Status of an user in-active/unverified.
	 */
	private boolean statusInactive;
	
	/**
	 * Role of an user admin/user.
	 */
	private boolean roleAdmin;
	
	/**
	 * Role of an user admin/user.
	 */
	private boolean roleUser;
	
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
		statusActive = true;
		statusInactive = true;
		roleAdmin = true;
		roleUser = true;
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
		return SQLConvert.convertStatus(statusActive, statusInactive);
	}
	
	/**
	 * Get user role filter string for SQL query.
	 * @return
	 */
	public String getRoleFilter() {
		return SQLConvert.convertRole(roleAdmin, roleUser);
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
	
	public boolean getRoleAdmin() {
		return roleAdmin;
	}

	public void setRoleAdmin(boolean roleAdmin) {
		this.roleAdmin = roleAdmin;
	}

	public boolean isRoleUser() {
		return roleUser;
	}

	public void setRoleUser(boolean roleUser) {
		this.roleUser = roleUser;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

}

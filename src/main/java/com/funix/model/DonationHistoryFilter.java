/*
 * DonationHistoryFilter.java    1.00    2022-04-05
 */

package com.funix.model;

import java.util.Arrays;

import com.funix.service.SQLConvert;

/**
 * Donation history filter model contains inputs 
 * taken from donation history search form, 
 * which can be converted to special strings in 
 * SQL query for data filter purpose.
 * @author Giang_Nhat_Truong
 *
 */
public class DonationHistoryFilter {
	
	/**
	 * User email or fullname keyword.
	 */
	private String userKeyword;
	
	/**
	 * Charity campaign title or location keyword.
	 */
	private String campaignKeyword;
	
	/**
	 * Bank transaction code keyword.
	 */
	private String transactionKeyword;
	
	/**
	 * Status of a donating transaction:
	 * 0-Not-verified
	 * 1-Verified
	 */
	private String[] statuses;
	
	/**
	 * Order of donation history records sorted by 
	 * specific fields in donation history data table.
	 */
	private String sortBy;

	/**
	 * Default constructor used to initialize
	 * donation history search form values.
	 */
	public DonationHistoryFilter() {
		statuses = new String[] {"0", "1"};
		sortBy = "date-desc";
	}

	/**
	 * Get user email/fullname filter string for SQL query.
	 * @return
	 */
	public String getUserKeywordFilter() {
		return SQLConvert.convertKeyword(userKeyword);
	}

	/**
	 * Get campaign title/location filter string for SQL query.
	 * @return
	 */
	public String getCampaignKeywordFilter() {
		return SQLConvert.convertKeyword(campaignKeyword);
	}

	/**
	 * Get bank transaction code filter string for SQL query.
	 * @return
	 */
	public String getTransactionKeywordFilter() {
		return SQLConvert.convertKeyword(transactionKeyword);
	}

	/**
	 * Get donating transaction status filter string for SQL query.
	 * @return
	 */
	public String getStatusFilter() {
		return SQLConvert.convertToListString(statuses);
	}

	/**
	 * Get SQL "ORDER BY" clause to sort donation history table records.
	 * @return
	 */
	public String getSortByFilter() {
		return SQLConvert.convertDonationHistoryOrder(sortBy);
	}

	//Getters and setters
	public String getUserKeyword() {
		return userKeyword;
	}

	public void setUserKeyword(String userKeyword) {
		this.userKeyword = userKeyword;
	}

	public String getCampaignKeyword() {
		return campaignKeyword;
	}

	public void setCampaignKeyword(String campaignKeyword) {
		this.campaignKeyword = campaignKeyword;
	}

	public String getTransactionKeyword() {
		return transactionKeyword;
	}

	public void setTransactionKeyword(String transactionKeyword) {
		this.transactionKeyword = transactionKeyword;
	}

	public String getStatuses() {
		return Arrays.toString(statuses);
	}

	public void setStatuses(String[] statuses) {
		this.statuses = statuses;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

}

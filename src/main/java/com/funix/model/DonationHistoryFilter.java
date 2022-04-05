/*
 * DonationHistoryFilter.java    1.00    2022-04-05
 */

package com.funix.model;

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
	 * Status of a donating transaction is verified/success.
	 */
	private boolean statusOk;
	
	/**
	 * Status of a donating transaction is not verified/not success.
	 */
	private boolean statusNotOk;
	
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
		statusOk = true;
		statusNotOk = true;
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
		return SQLConvert.convertStatus(statusOk, statusNotOk);
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

	public boolean getStatusOk() {
		return statusOk;
	}

	public void setStatusOk(boolean statusOk) {
		this.statusOk = statusOk;
	}

	public boolean getStatusNotOk() {
		return statusNotOk;
	}

	public void setStatusNotOk(boolean statusNotOk) {
		this.statusNotOk = statusNotOk;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

}

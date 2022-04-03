package com.funix.model;

import com.funix.service.FilterMapping;

public class UserDonationHistoryFilter {
	private String campaignKeyword;
	private String transactionKeyword;
	private String statusOn;
	private String statusOff;
	private String sortBy;

	public UserDonationHistoryFilter() {
		campaignKeyword = "";
		transactionKeyword = "";
		statusOn = "true";
		statusOff = "true";
		sortBy = "date-desc";
	}

	public void setFilter(String statusOn, String statusOff, String sortBy) {
		this.setFilter(null, null, statusOn, statusOff, sortBy);
	}

	public void setFilter(String campaignKeyword, String transactionKeyword, String statusOn, String statusOff,
			String sortBy) {
		this.campaignKeyword = campaignKeyword;
		this.transactionKeyword = transactionKeyword;
		this.statusOn = statusOn;
		this.statusOff = statusOff;
		this.sortBy = sortBy;
	}

	public String getCampaignKeyword() {
		return campaignKeyword;
	}

	public String getTransactionKeyword() {
		return transactionKeyword;
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

	public String getCampaignKeywordFilter() {
		return FilterMapping.convertKeyword(campaignKeyword);
	}

	public String getTransactionKeywordFilter() {
		return FilterMapping.convertKeyword(transactionKeyword);
	}

	public String getStatusFilter() {
		return FilterMapping.convertStatus(statusOn, statusOff);
	}

	public String getSortByFilter() {
		return FilterMapping.convertUserDonationHistoryOrder(sortBy);
	}

}

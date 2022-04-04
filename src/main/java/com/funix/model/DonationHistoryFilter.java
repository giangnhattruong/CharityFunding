package com.funix.model;

import com.funix.service.FilterMapping;

public class DonationHistoryFilter {
	private String userKeyword;
	private String campaignKeyword;
	private String transactionKeyword;
	private boolean statusOk;
	private boolean statusNotOk;
	private String sortBy;

	public DonationHistoryFilter() {
		statusOk = true;
		statusNotOk = true;
		sortBy = "date-desc";
	}

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

	public String getUserKeywordFilter() {
		return FilterMapping.convertKeyword(userKeyword);
	}

	public String getCampaignKeywordFilter() {
		return FilterMapping.convertKeyword(campaignKeyword);
	}

	public String getTransactionKeywordFilter() {
		return FilterMapping.convertKeyword(transactionKeyword);
	}

	public String getStatusFilter() {
		return FilterMapping.convertStatus(statusOk, statusNotOk);
	}

	public String getSortByFilter() {
		return FilterMapping.convertDonationHistoryOrder(sortBy);
	}

}

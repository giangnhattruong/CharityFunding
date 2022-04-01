package com.funix.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.funix.service.CampaignFilterMapping;
import com.funix.service.DonationHistoryFilterMapping;

public class DonationHistoryFilter {
	@Autowired
	private DonationHistoryFilterMapping donationHistoryFilterMapping;

	private String userKeyword;
	private String campaignKeyword;
	private String transactionKeyword;
	private String status;
	private String orderBy;

	public DonationHistoryFilter(String userKeyword, String campaignKeyword, String transactionKeyword, String statusOn,
			String statusOff, String sortBy) {
		donationHistoryFilterMapping.setStatusOn(statusOn);
		donationHistoryFilterMapping.setStatusOff(statusOff);
		donationHistoryFilterMapping.setSortBy(sortBy);
		this.userKeyword = userKeyword;
		this.campaignKeyword = campaignKeyword;
		this.transactionKeyword = transactionKeyword;
		this.status = donationHistoryFilterMapping.convertStatus();
		this.orderBy = donationHistoryFilterMapping.convertOrder();
	}

	public String getUserKeyword() {
		return userKeyword;
	}

	public String getCampaignKeyword() {
		return campaignKeyword;
	}

	public String getTransactionKeyword() {
		return transactionKeyword;
	}

	public String getStatus() {
		return status;
	}

	public String getOrderBy() {
		return orderBy;
	}

}

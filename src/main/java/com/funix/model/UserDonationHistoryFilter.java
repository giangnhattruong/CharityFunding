package com.funix.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.funix.service.UserDonationHistoryFilterMapping;

public class UserDonationHistoryFilter {
	@Autowired
	private UserDonationHistoryFilterMapping userDonationHistoryFilterMapping;

	private String campaignKeyword;
	private String transactionKeyword;
	private String status;
	private String orderBy;

	public UserDonationHistoryFilter(String campaignKeyword, String transactionKeyword, String statusOn,
			String statusOff, String sortBy) {
		userDonationHistoryFilterMapping.setStatusOn(statusOn);
		userDonationHistoryFilterMapping.setStatusOff(statusOff);
		userDonationHistoryFilterMapping.setSortBy(sortBy);
		this.campaignKeyword = campaignKeyword;
		this.transactionKeyword = transactionKeyword;
		this.status = userDonationHistoryFilterMapping.convertStatus();
		this.orderBy = userDonationHistoryFilterMapping.convertOrder();
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

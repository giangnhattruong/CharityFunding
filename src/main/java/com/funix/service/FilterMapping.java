package com.funix.service;

public class FilterMapping {
	public static String convertKeyword(String keyword) {
		return keyword == null ? 
							"%%" :
							"%" + keyword + "%";
	}
	
	public static String convertStatus(boolean statusOn, boolean statusOff) {
		String status = "";

		if (statusOn == true && statusOff == true) {
			status = "%%";
		} else if (statusOn == true && statusOff == false) {
			status = "%1%";
		} else if (statusOn == false && statusOff == true) {
			status = "%0%";
		}

		return status;
	}
	
	public static String convertCampaignOrder(String sortBy) {
		String orderBy = "ORDER BY ";

		switch (sortBy) {
		case "date-desc":
			orderBy += "startDate DESC, endDate DESC, campaignID DESC";
			break;
		case "target-desc":
			orderBy += "targetAmount DESC, campaignID DESC";
			break;
		case "total-donations-desc":
			orderBy += "totalDonations DESC, campaignID DESC";
			break;
		case "total-supporters-desc":
			orderBy += "totalSupporters DESC, campaignID DESC";
			break;
		case "latest-donation-date-desc":
			orderBy += "latestDonationDate DESC, campaignID DESC";
			break;
		case "title-desc":
			orderBy += "title DESC, campaignID DESC";
			break;
		case "date-asc":
			orderBy += "startDate, endDate, ID";
			break;
		case "target-asc":
			orderBy += "targetAmount ASC, campaignID DESC";
			break;
		case "total-donations-asc":
			orderBy += "totalDonations ASC, campaignID DESC";
			break;
		case "total-supporters-asc":
			orderBy += "totalSupporters ASC, campaignID DESC";
			break;
		case "latest-donation-date-asc":
			orderBy += "latestDonationDate ASC, campaignID DESC";
			break;
		case "title-asc":
			orderBy += "title, campaignID DESC";
			break;
		}

		return orderBy;
	}
	
	public static String convertUserOrder(String sortBy) {
		String orderBy = "ORDER BY ";

		switch (sortBy) {
		case "date-desc":
			orderBy += "userID DESC";
			break;
		case "email-desc":
			orderBy += "email, userID DESC";
			break;
		case "total-donations-desc":
			orderBy += "totalDonations DESC, userID DESC";
			break;
		case "donation-date-desc":
			orderBy += "latestDonationDate DESC, userID DESC";
			break;
		case "date-asc":
			orderBy += "userID";
			break;
		case "email-asc":
			orderBy += "email DESC, userID DESC";
			break;
		case "total-donations-asc":
			orderBy += "totalDonations ASC, userID DESC";
			break;
		case "donation-date-asc":
			orderBy += "latestDonationDate ASC, userID ASC";
			break;
		}

		return orderBy;
	}
	
	public static String convertDonationHistoryOrder(String sortBy) {
		String orderBy = "ORDER BY ";

		switch (sortBy) {
		case "date-desc":
			orderBy += "donationDate DESC, userID DESC";
			break;
		case "donation-desc":
			orderBy += "donation DESC, userID DESC";
			break;
		case "email-desc":
			orderBy += "email DESC, userID DESC";
			break;
		case "title-desc":
			orderBy += "title DESC, userID DESC";
			break;
		case "transaction-code-desc":
			orderBy += "transactionCode DESC, userID DESC";
			break;
		case "date-asc":
			orderBy += "donationDate ASC, userID ASC";
			break;
		case "donation-asc":
			orderBy += "donation ASC, userID DESC";
			break;
		case "email-asc":
			orderBy += "email ASC, userID DESC";
			break;
		case "title-asc":
			orderBy += "title ASC, userID DESC";
			break;
		case "transaction-code-asc":
			orderBy += "transactionCode ASC, userID DESC";
			break;
		}

		return orderBy;
	}
	
}

/*
 * SQLConvert.java    1.00    2022-04-05
 */

package com.funix.service;

import java.util.Arrays;
import java.util.List;

/**
 * Helper class for converting inputs from search form
 * to special strings which can be add to SQL query for
 * filter and sorting purposes.
 * @author Giang_Nhat_Truong
 *
 */
public class SQLConvert {
	
	/**
	 * Convert keyword input to string which
	 * can be used in SQL "LIKE" operator.
	 * @param keyword
	 * @return
	 */
	public static String convertKeyword(String keyword) {
		return keyword == null ? 
							"%%" :
							"%" + keyword + "%";
	}
	
	/**
	 * Convert status inputs to string which
	 * can be used in SQL "IN" operator.
	 * @param status
	 * @return
	 */
	public static String convertToListString(String[] list) {
		return Arrays.toString(list)
			.replace("[", "(")
			.replace("]", ")");
	}
	
	/**
	 * Convert sortBy inputs from campaign search form
	 * to string which can be used in SQL "ORDER BY" clause.
	 * @param sortBy
	 * @return
	 */
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
			orderBy += "startDate ASC, endDate ASC, campaignID ASC";
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
			orderBy += "title ASC, campaignID DESC";
			break;
		}

		return orderBy;
	}
	
	/**
	 * Convert sortBy inputs from user search form
	 * to string which can be used in SQL "ORDER BY" clause.
	 * @param sortBy
	 * @return
	 */
	public static String convertUserOrder(String sortBy) {
		String orderBy = "ORDER BY ";

		switch (sortBy) {
		case "date-desc":
			orderBy += "userID DESC";
			break;
		case "email-desc":
			orderBy += "email DESC, userID DESC";
			break;
		case "total-donations-desc":
			orderBy += "totalDonations DESC, userID DESC";
			break;
		case "donation-times-desc":
			orderBy += "donationTimes DESC, userID DESC";
			break;
		case "latest-donation-date-desc":
			orderBy += "latestDonationDate DESC, userID DESC";
			break;
		case "date-asc":
			orderBy += "userID ASC";
			break;
		case "email-asc":
			orderBy += "email ASC, userID DESC";
			break;
		case "total-donations-asc":
			orderBy += "totalDonations ASC, userID DESC";
			break;
		case "donation-times-asc":
			orderBy += "donationTimes ASC, userID DESC";
			break;
		case "latest-donation-date-asc":
			orderBy += "latestDonationDate ASC, userID ASC";
			break;
		}

		return orderBy;
	}
	
	/**
	 * Convert sortBy inputs from donation history search form
	 * to string which can be used in SQL "ORDER BY" clause.
	 * @param sortBy
	 * @return
	 */
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

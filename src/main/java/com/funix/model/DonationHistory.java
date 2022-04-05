/*
 * DonationHistory.java    1.00    2022-04-05
 */

package com.funix.model;

import java.time.LocalDate;

/**
 * Donation history model which contains all
 * information of a donation/transaction history.
 * @author Giang_Nhat_Truong
 *
 */
public class DonationHistory {
	
	/**
	 * Donation history ID from database.
	 */
	private int donationHistoryID;
	
	/**
	 * User ID who donated to a campaign.
	 */
	private int userID;
	
	/**
	 * User email who donated to a campaign.
	 */
	private String email;
	
	/**
	 * User full name who donated to a campaign.
	 */
	private String fullname;
	
	/**
	 * Campaign ID got donation.
	 */
	private int campaignID;
	
	/**
	 * Campaign title got donation.
	 */
	private String title;
	
	/**
	 * Campaign location got donation.
	 */
	private String location;
	
	/**
	 * Donation amount of the user to campaign.
	 */
	private double donation;
	
	/**
	 * Donation/transaction date.
	 */
	private LocalDate donationDate;
	
	/**
	 * Bank transaction code.
	 */
	private String transactionCode;
	
	/**
	 * Donation status: verified or not verified.
	 */
	private boolean donationStatus;

	/**
	 * Default constructor.
	 */
	public DonationHistory() {
	}
	
	/**
	 * Constructor used in DonationHistoryRowMapper.
	 * @param donationHistoryID
	 * @param userID
	 * @param email
	 * @param fullname
	 * @param campaignID
	 * @param title
	 * @param location
	 * @param donation
	 * @param donationDate
	 * @param transactionCode
	 * @param donationStatus
	 */
	public DonationHistory(int donationHistoryID, int userID, 
			String email, String fullname, int campaignID,
			String title, String location, double donation, 
			LocalDate donationDate, String transactionCode,
			boolean donationStatus) {
		this.donationHistoryID = donationHistoryID;
		this.userID = userID;
		this.email = email;
		this.fullname = fullname;
		this.campaignID = campaignID;
		this.title = title;
		this.location = location;
		this.donation = donation;
		this.donationDate = donationDate;
		this.transactionCode = transactionCode;
		this.donationStatus = donationStatus;
	}
	
	/**
	 * Validate all donation history fields to prepare for 
	 * creating history record in donation history table in database.
	 * @return
	 */
	public String validate() {
		String message = "";
		
		if (donation < 5 || donation > 1000) {
			message = "Donation amount must be greater than $5 and "
					+ "below $1000.";
		} else {
			message = "success";
		}
		
		return message;
	}

	//Getters and setters.
	public int getDonationHistoryID() {
		return donationHistoryID;
	}

	public void setDonationHistoryID(int donationHistoryID) {
		this.donationHistoryID = donationHistoryID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public int getCampaignID() {
		return campaignID;
	}

	public void setCampaignID(int campaignID) {
		this.campaignID = campaignID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getDonation() {
		return donation;
	}

	public void setDonation(double donation) {
		this.donation = donation;
	}

	public LocalDate getDonationDate() {
		return donationDate;
	}

	public void setDonationDate(LocalDate donationDate) {
		this.donationDate = donationDate;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public boolean getDonationStatus() {
		return donationStatus;
	}

	public void setDonationStatus(boolean donationStatus) {
		this.donationStatus = donationStatus;
	}

}

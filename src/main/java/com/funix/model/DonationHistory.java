package com.funix.model;

import java.time.LocalDate;

public class DonationHistory {
	private int donationHistoryID;
	private int userID;
	private String email;
	private String fullname;
	private int campaignID;
	private String title;
	private String location;
	private double donation;
	private LocalDate donationDate;
	private String transactionCode;
	private boolean donationStatus;

	public DonationHistory() {
	}
	
	public DonationHistory(int userID,  int campaignID, double donation, 
			String transactionCode, boolean donationStatus) {
		this(0, userID, null, null, campaignID, 
				null, null, 0, null, transactionCode, donationStatus);
	}

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

package com.funix.model;

import java.util.Date;

public class Campaign {
	private int campaignID;
	private String title;
	private String description;
	private double targetAmount;
	private String location;
	private String imgURL;
	private Date startDate;
	private Date endDate;
	private int campaignStatus;
	private Date dateCreated;
	private double totalDonations;
	private int totalSupporters;
	private Date latestDonationDate;

	public Campaign() {
	}

	public Campaign(String title, String description, 
			double targetAmount, String location, String imgURL,
			Date startDate, Date endDate, int campaignStatus) {
		this(0, title, description, targetAmount, 
				location, imgURL, startDate, endDate, 
				campaignStatus, null, 0, 0, null);
	}

	public Campaign(int campaignID, String title, String description, 
			double targetAmount, String location, String imgURL, 
			Date startDate, Date endDate, int campaignStatus, 
			Date dateCreated, double totalDonations,
			int totalSupporters, Date latestDonationDate) {
		this.campaignID = campaignID;
		this.title = title;
		this.description = description;
		this.targetAmount = targetAmount;
		this.location = location;
		this.imgURL = imgURL;
		this.startDate = startDate;
		this.endDate = endDate;
		this.campaignStatus = campaignStatus;
		this.dateCreated = dateCreated;
		this.totalDonations = totalDonations;
		this.totalSupporters = totalSupporters;
		this.latestDonationDate = latestDonationDate;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(double targetAmount) {
		this.targetAmount = targetAmount;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getCampaignStatus() {
		return campaignStatus;
	}

	public void setCampaignStatus(int campaignStatus) {
		this.campaignStatus = campaignStatus;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public double getTotalDonations() {
		return totalDonations;
	}

	public void setTotalDonations(double totalDonations) {
		this.totalDonations = totalDonations;
	}

	public int getTotalSupporters() {
		return totalSupporters;
	}

	public void setTotalSupporters(int totalSupporters) {
		this.totalSupporters = totalSupporters;
	}

	public Date getLatestDonationDate() {
		return latestDonationDate;
	}

	public void setLatestDonationDate(Date latestDonationDate) {
		this.latestDonationDate = latestDonationDate;
	}

}

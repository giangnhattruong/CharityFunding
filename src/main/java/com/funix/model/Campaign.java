package com.funix.model;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public class Campaign {
	private int campaignID;
	private String title;
	private String description;
	private double targetAmount;
	private String location;
	private String imgURL;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean campaignStatus;
	private LocalDate dateCreated;
	private double totalDonations;
	private int totalSupporters;
	private LocalDate latestDonationDate;
	private MultipartFile file;
	private int validateErrorCount;

	/**
	 * Default constructor
	 */
	public Campaign() {
		startDate = LocalDate.now();
		endDate = startDate.plusMonths(3);
		campaignStatus = true;
	}
	
	/**
	 * Constructor used in CampaignRowMapper class
	 * @param campaignID
	 * @param title
	 * @param description
	 * @param targetAmount
	 * @param location
	 * @param imgURL
	 * @param startDate
	 * @param endDate
	 * @param campaignStatus
	 * @param dateCreated
	 * @param totalDonations
	 * @param totalSupporters
	 * @param latestDonationDate
	 */
	public Campaign(int campaignID, String title, String description, 
			double targetAmount, String location, String imgURL, 
			LocalDate startDate, LocalDate endDate, boolean campaignStatus, 
			LocalDate dateCreated, double totalDonations,
			int totalSupporters, LocalDate latestDonationDate) {
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
	
	public boolean validateTextFieldsOnly() {
		validate();
		return validateErrorCount == 1;
	}
	
	public String validate() {
		StringBuilder messages = new StringBuilder();
		messages.append("Please fulfill all requirements below and re-submit:");
		messages.append("</br>");
		validateErrorCount = 0;
		
		if (title == null || title.length() < 3 || 
				title.length() > 60) {
			messages.append(++validateErrorCount + ". ");
			messages.append("Title must contains at least 3 characters"
					+ " and can not exceed 60 characters.");
			messages.append("</br>");
		}
		
		if (description == null || description.length() < 120) {
			messages.append(++validateErrorCount + ". ");
			messages.append("Description must contains "
					+ "at least 120 characters.");
			messages.append("</br>");
		}
		
		if (targetAmount < 1000) {
			messages.append(++validateErrorCount + ". ");
			messages.append("Target amount can not less than 1000.");
			messages.append("</br>");
		}
		
		if (location == null || location.length() < 3 || 
				location.length() > 60) {
			messages.append(++validateErrorCount + ". ");
			messages.append("Location must contains at least 3 characters"
					+ " and can not exceed 60 characters.\n");
			messages.append("</br>");
		}
		
		if (imgURL == null || !imgURL.startsWith("http")) {
			messages.append(++validateErrorCount + ". ");
			messages.append("There must be 1 cover image.\n");
			messages.append("</br>");
		}
		
		if (startDate.plusMonths(3).isBefore(LocalDate.now()) ||
				startDate.minusMonths(3).isAfter(LocalDate.now())) {
			messages.append(++validateErrorCount + ". ");
			messages.append("Campaign start date must be with in 3 months"
					+ " prior to today or within 3 months after today.\n");
			messages.append("</br>");
		}
		
		if (endDate.minusMonths(1).isBefore(startDate)) {
			messages.append(++validateErrorCount + ". ");
			messages.append("Campaign end date must be at least 1 months"
					+ " after start date.\n");
			messages.append("</br>");
		}
		
		return validateErrorCount == 0 ? "success" : messages.toString();
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean getCampaignStatus() {
		return campaignStatus;
	}

	public void setCampaignStatus(boolean campaignStatus) {
		this.campaignStatus = campaignStatus;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
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

	public LocalDate getLatestDonationDate() {
		return latestDonationDate;
	}

	public void setLatestDonationDate(LocalDate latestDonationDate) {
		this.latestDonationDate = latestDonationDate;
	}
	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}

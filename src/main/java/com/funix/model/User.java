package com.funix.model;

import java.util.Date;

public class User {
	private int userID;
	private String email;
	private String password;
	private String fullname;
	private String address;
	private String phone;
	private int role;
	private int userStatus;
	private Date dateCreated;
	private double totalDonations;
	private int donationTimes;
	private Date latestDonationDate;

	public User() {
	}
	
	public User(String email, String password, String fullname, 
			String address, String phone, int role, int userStatus) {
		this(0, email, password, fullname, address, 
				phone, role, userStatus, null, 0, 0, null);
	}

	public User(int userID, String email, String password, 
			String fullname, String address, String phone, 
			int role, int userStatus, Date dateCreated, 
			double totalDonations, int donationTimes, 
			Date latestDonationDate) {
		this.userID = userID;
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.address = address;
		this.phone = phone;
		this.role = role;
		this.userStatus = userStatus;
		this.dateCreated = dateCreated;
		this.totalDonations = totalDonations;
		this.donationTimes = donationTimes;
		this.latestDonationDate = latestDonationDate;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
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

	public int getDonationTimes() {
		return donationTimes;
	}

	public void setDonationTimes(int donationTimes) {
		this.donationTimes = donationTimes;
	}

	public Date getLatestDonationDate() {
		return latestDonationDate;
	}

	public void setLatestDonationDate(Date latestDonationDate) {
		this.latestDonationDate = latestDonationDate;
	}

}

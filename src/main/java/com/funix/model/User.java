/*
 * User.java    1.00    2022-04-05
 */

package com.funix.model;

import java.time.LocalDate;

/**
 * User model which contains all
 * information of a user.
 * @author Giang_Nhat_Truong
 *
 */
public class User {
	
	/**
	 * User ID from database.
	 */
	private int userID;
	
	/**
	 * User email address.
	 */
	private String email;
	
	/**
	 * User account password.
	 */
	private String password;
	
	/**
	 * User confirm password, used for
	 * validating when creating or updating
	 * user informations.
	 */
	private String confirmPassword;
	
	/**
	 * User full name.
	 */
	private String fullname;
	
	/**
	 * User physical address.
	 */
	private String address;
	
	/**
	 * User phone number.
	 */
	private String phone;
	
	/**
	 * User role: admin or normal user.
	 */
	private int userRole;
	
	/**
	 * User status: verified or unverified.
	 */
	private boolean userStatus;
	
	/**
	 * User created date in database.
	 */
	private LocalDate dateCreated;
	
	/**
	 * Total donations of the user
	 * at current time.
	 */
	private double totalDonations;
	
	/**
	 * User's total number of donations
	 * at current time.
	 */
	private int donationTimes;
	
	/**
	 * Latest donation date of user.
	 */
	private LocalDate latestDonationDate;
	
	/**
	 * Count errors from user form input values.
	 */
	private int validateErrorCount;

	/**
	 * Default constructor.
	 */
	public User() {
	}
	
	/**
	 * Constructor used in UserRowMapper class.
	 * @param userID
	 * @param email
	 * @param password
	 * @param fullname
	 * @param address
	 * @param phone
	 * @param userRole
	 * @param userStatus
	 * @param dateCreated
	 * @param totalDonations
	 * @param donationTimes
	 * @param latestDonationDate
	 */
	public User(int userID, String email, String password, 
			String fullname, String address, String phone, 
			int userRole, boolean userStatus, LocalDate dateCreated, 
			double totalDonations, int donationTimes, 
			LocalDate latestDonationDate) {
		this.userID = userID;
		this.email = email;
		this.password = password;
		this.confirmPassword = password;
		this.fullname = fullname;
		this.address = address;
		this.phone = phone;
		this.userRole = userRole;
		this.userStatus = userStatus;
		this.dateCreated = dateCreated;
		this.totalDonations = totalDonations;
		this.donationTimes = donationTimes;
		this.latestDonationDate = latestDonationDate;
	}
	
	/**
	 * Validate all user fields to prepare for creating or 
	 * updating record in user table in database.
	 * @return
	 */
	public String validate() {
		StringBuilder messages = new StringBuilder();
		messages.append("Please fulfill all requirements below and re-submit:");
		messages.append("</br>");
		validateErrorCount = 0;
		String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		String passwordRegex = 
				"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)"
				+ "(?=.*[@#$!%*?&])[A-Za-z\\d@#$!%*?&]{6,12}$";
		String phoneRegex = "^\\d{10}$";
		
		if (!email.matches(emailRegex)) {
			messages.append(++validateErrorCount + ". ");
			messages.append("There must be a valid email address.");
			messages.append("</br>");
		}
		
		if (!password.matches(passwordRegex)) {
			messages.append(++validateErrorCount + ". ");
			messages.append("Password must be 6-12 characters and contains "
					+ "a minimum of one uppercase letter (A-Z), "
					+ "a minimum of one lowercase letter (a-z), "
					+ "a minimum of one special character (@#$!%*?&) and "
					+ "a minimum of one digit (0-9).");
			messages.append("</br>");
		}
		
		if (!password.equals(confirmPassword)) {
			messages.append(++validateErrorCount + ". ");
			messages.append("Password must be matched.");
			messages.append("</br>");
		}
		
		if (fullname.length() < 3 ||
				fullname.length() > 60) {
			messages.append(++validateErrorCount + ". ");
			messages.append("Fullname must be 3-60 characters.");
			messages.append("</br>");
		}
		
		if (!address.equals("") && 
				(address.length() < 3 || address.length() > 60)) {
			messages.append(++validateErrorCount + ". ");
			messages.append("Address must be 3-60 characters.");
			messages.append("</br>");
		}
		
		if (!phone.equals("") && 
				!phone.matches(phoneRegex)) {
			messages.append(++validateErrorCount + ". ");
			messages.append("Phone must be a 10 digits number.");
			messages.append("</br>");
		}
		
		return validateErrorCount == 0 ? "success" : messages.toString();
	}

	//Getters and setters.
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

	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}

	public boolean getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(boolean userStatus) {
		this.userStatus = userStatus;
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

	public int getDonationTimes() {
		return donationTimes;
	}

	public void setDonationTimes(int donationTimes) {
		this.donationTimes = donationTimes;
	}

	public LocalDate getLatestDonationDate() {
		return latestDonationDate;
	}

	public void setLatestDonationDate(LocalDate latestDonationDate) {
		this.latestDonationDate = latestDonationDate;
	}
	
}

/*
 * User.java    1.00    2022-04-05
 */

package com.funix.model;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.funix.service.PasswordService;

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
	 * Default constructor, with default password
	 * contains 2 upper-case letters, 3 lower-case letters,
	 * 2 special characters and 2 digits, 
	 * generated by PasswordService class.
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
		int errorCount = 0;
		String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		String phoneRegex = "^\\d{10}$";
		
		if (!email.matches(emailRegex)) {
			messages.append(++errorCount + ". ");
			messages.append("There must be a valid email address.");
			messages.append("</br>");
		}
		
		if (fullname.length() < 3 ||
				fullname.length() > 60) {
			messages.append(++errorCount + ". ");
			messages.append("Fullname must be 3-60 characters.");
			messages.append("</br>");
		}
		
		if (!address.equals("") && 
				(address.length() < 3 || address.length() > 60)) {
			messages.append(++errorCount + ". ");
			messages.append("Address must be 3-60 characters.");
			messages.append("</br>");
		}
		
		if (!phone.matches(phoneRegex)) {
			messages.append(++errorCount + ". ");
			messages.append("Phone must be a 10 digits number.");
			messages.append("</br>");
		}
		
		return errorCount == 0 ? "success" : messages.toString();
	}
	
	/**
	 * Validate new password for changing password.
	 * @param passwordEncoder
	 * @param oldPassword
	 * @param newPassword
	 * @param confirmPassword
	 * @return
	 */
	public String validateNewPassword(
			PasswordEncoder passwordEncoder,
			String oldPassword,
			String newPassword,
			String confirmPassword) {
		StringBuilder messages = new StringBuilder();
		messages.append("Please fulfill all requirements below and re-submit:");
		messages.append("</br>");
		int errorCount = 0;
		String passwordRegex = 
				"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)"
				+ "(?=.*[@#$!%*?&])[A-Za-z\\d@#$!%*?&]{6,12}$";
		
		if (!passwordEncoder.matches(oldPassword, password)) {
			messages.append(++errorCount + ". ");
			messages.append("Old password must match "
					+ "your current password.");
			messages.append("</br>");
		}
		
		if (!newPassword.matches(passwordRegex)) {
			messages.append(++errorCount + ". ");
			messages.append("Password must be 6-12 characters and contains "
					+ "a minimum of one uppercase letter (A-Z), "
					+ "a minimum of one lowercase letter (a-z), "
					+ "a minimum of one special character (@#$!%*?&) and "
					+ "a minimum of one digit (0-9).");
			messages.append("</br>");
		}
		
		if (!newPassword.equals(confirmPassword)) {
			messages.append(++errorCount + ". ");
			messages.append("Confirm password must match "
					+ "the new password.");
			messages.append("</br>");
		}
		
		return errorCount == 0 ? "success" : messages.toString();
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

	public void setPassword(
			PasswordEncoder passwordEncoder, 
			String password) {
		this.password = passwordEncoder.encode(password);
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

	@Override
	public String toString() {
		return "User [userID=" + userID + ", email=" + email + ", password=" + password + ", fullname=" + fullname
				+ ", address=" + address + ", phone=" + phone + ", userRole=" + userRole + ", userStatus=" + userStatus
				+ ", dateCreated=" + dateCreated + ", totalDonations=" + totalDonations + ", donationTimes="
				+ donationTimes + ", latestDonationDate=" + latestDonationDate + "]";
	}
	
	
	
}

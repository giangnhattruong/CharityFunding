/*
 * Interface IEmailAPI.java    1.00    2022-04-08
 */

package com.funix.javamail;

import com.funix.model.DonationHistory;

/**
 * Email API for sending email to users.
 * @author Giang_Nhat_Truong
 *
 */
public interface IEmailAPI {
	
	/**
	 * Send to user an message with 
	 * user new auto-generated password
	 * and an new account activation URL.
	 * @param password
	 * @param URL
	 * @param email
	 */
	boolean sendVerificationMessage(String password, 
			String URL, String email);
	
	/**
	 * Send to user an message with 
	 * user new auto-generated password.
	 * @param password
	 * @param URL
	 * @param email
	 */
	boolean sendNewPassword(String password, 
			String URL, String email);
	
	/**
	 * Over loading sendNewPassword method with default login URL.
	 * @param password
	 * @param URL
	 * @param email
	 */
	boolean sendNewPassword(String password, String email);

	/**
	 * Send to user an message with 
	 * reset password URL.
	 * @param URL
	 * @param email
	 * @param liveMins
	 */
	boolean sendResetPasswordURL(String URL, 
			String email, int liveMins);
	
	/**
	 * Send contact messages from website visitors to admin.
	 * @param fullname
	 * @param userEmail
	 * @param userMessage
	 * @return
	 */
	boolean sendContactMessage(String fullname,
			String userEmail, String userMessage);
	
	/**
	 * Send a notify message to user after user donate successfully.
	 * @param fullname
	 * @param email
	 * @param donation
	 * @param transactionCode
	 * @return
	 */
	boolean sendTransactionNotifyToUser(String fullname, 
			String email, double donation, String transactionCode);
	
	/**
	 * Send a notify message to admin after user donate successfully.
	 * @param donation
	 * @return
	 */
	boolean sendTransactionNotifyToAdmin(DonationHistory donation);
		
}

/*
 * Interface IEmailAPI.java    1.00    2022-04-08
 */

package com.funix.javamail;

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
	 * Send to user an message with 
	 * reset password URL.
	 * @param URL
	 * @param email
	 * @param liveMins
	 */
	boolean sendResetPasswordURL(String URL, 
			String email, int liveMins);
		
}

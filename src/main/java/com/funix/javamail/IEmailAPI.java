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
	 * and an new account verifying URL.
	 * @param password
	 * @param URL
	 */
	void sendAccountVerifingMessage(String password, String URL, String email);
	
}

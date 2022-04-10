/*
 * EmailAPI.java    1.00    2022-04-08
 */

package com.funix.javamail;

import javax.mail.Message;

/**
 * EmailAPI for sending email to user.
 */
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailAPIImpl implements IEmailAPI {

	/**
	 * JavaMailSender which is configured
	 * with connection to SMTP to help
	 * sending email.
	 */
	private JavaMailSender emailSender;
	
	/**
	 * Default constructor
	 * @param emailSender
	 */
	public EmailAPIImpl(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}
	
	/**
	 * Send to user email an account verifying message contains 
	 * new auto-generated password and a account verifying URL. 
	 */
	@Override
	public void sendVerifingMessage(String password, String URL, String email) {

	}

	/**
	 * Send to user email a new auto-generated password.. 
	 */
	@Override
	public void sendNewPassword(String password, String email) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("noreply@charityfunding.com");
		mailMessage.setTo(email);
		mailMessage.setSubject("Test from Spring Mail");
		mailMessage.setText("Your password has been reset. "
				+ "Please login with your new password: " + password);
		emailSender.send(mailMessage);
	}

}

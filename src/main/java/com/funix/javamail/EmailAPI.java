/*
 * EmailAPI.java    1.00    2022-04-08
 */

package com.funix.javamail;

/**
 * EmailAPI for sending email to user.
 */
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailAPI implements IEmailAPI {

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
	public EmailAPI(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}
	
	/**
	 * Send to user email an account verifying message contains 
	 * new auto-generated password and a account verifying URL. 
	 */
	@Override
	public void sendAccountVerifingMessage(String password, String URL, String email) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("noreply@charityfunding.com");
		mailMessage.setTo("truonggnfx13372@funix.edu.vn");
		mailMessage.setSubject("Test from Spring Mail");
		mailMessage.setText("Nothing, just a test.");
		emailSender.send(mailMessage);
	}

}

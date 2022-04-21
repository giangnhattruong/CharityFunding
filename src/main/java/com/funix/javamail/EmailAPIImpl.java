/*
 * EmailAPI.java    1.00    2022-04-08
 */

package com.funix.javamail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.funix.config.MyKey;
import com.funix.model.DonationHistory;

public class EmailAPIImpl implements IEmailAPI {
	
	/**
	 * Admin email which receiving contact message from users.
	 */
	private static final String ADMIN_EMAIL = "truonggnfx13372@funix.edu.vn";
	
	/**
	 * Send to user email an account verifying message contains 
	 * new auto-generated password and a account verifying URL.
	 * @param password
	 * @param URL
	 * @param email
	 */
	@Override
	public boolean sendVerificationMessage(String password, 
			String URL, String email) {
	    String subject = "Welcome to Charity Funding, please verify "
	    		+ "your account first so you can explore "
	    		+ "and donate.";
		String content = "<p>Your account has been successfully created. "
	    		+ "Please click this link to verify and use "
	    		+ "the password below to log into your new account:</p>"
	    		+ "<p><u><a href='" + URL + "'>"
	    		+ "Click this link to verify your account.<a></u><br></p>"
	    		+ "<p><strong>Your password: " + password + "</strong></p>";
	    Session session = getMailSession();
	    
		try {
			Message message = getMessage(email, 
					session, subject, content);
			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Send to user email a new auto-generated password.
	 * @param password
	 * @param URL
	 * @param email
	 */
	@Override
	public boolean sendNewPassword(String password, 
			String URL, String email) {
		String subject = "Your new password at Charity Funding";
		String content = "<p>Please login with your new password: " 
				+ "<strong>" + password + "</strong></p>"
				+ "<u><a href='" + URL + "'>Login now<a></u>";
		Session session = getMailSession();
		
		try {
			Message message = getMessage(email, 
					session, subject, content);
		    Transport.send(message);
		    return true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Over loading sendNewPassword method with default login URL.
	 * @param password
	 * @param email
	 * @return
	 */
	public boolean sendNewPassword(String password, String email) {
		String defaultLoginURL = 
				"http://localhost:8080/CharityFunding_truonggnfx13372/login";
		return sendNewPassword(password, defaultLoginURL, email);
	}

	/**
	 * Send to user an message with 
	 * reset password URL.
	 * @param URL
	 * @param email
	 * @param liveMins
	 */
	@Override
	public boolean sendResetPasswordURL(String URL, String email, int liveMins) {
		String subject = "Reset password at Charity Funding";
		String content = "<p>Please click on the link to update your password:</p>" 
				+ "<p><u><a href='" + URL + "'>"
				+ "Click here to update your password<a></u></p>"
				+ "<p>This link will be expired after "
				+ liveMins
				+ " minutes.</p>";
		Session session = getMailSession();
		
		try {
			Message message = getMessage(email, 
					session, subject, content);
		    Transport.send(message);
		    return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Send contact messages from website visitors to admin.
	 * @param fullname
	 * @param userEmail
	 * @param userMessage
	 * @return
	 */
	@Override
	public boolean sendContactMessage(String fullname, String userEmail, String userMessage) {
		String subject = "Charity Funding contact message from user";
		String content = "<p>Sender name: " + fullname + "</p>"
				+ "<p>Email: " + userEmail + "</p>"
				+ "<p>Message: " + userMessage + "</p>";
		Session session = getMailSession();
		
		try {
			Message message = getMessage(ADMIN_EMAIL, 
					session, subject, content);
		    Transport.send(message);
		    return true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Send a notify message to user after user donate successfully.
	 * @param fullname
	 * @param email
	 * @param donation
	 * @param transactionCode
	 * @return
	 */
	@Override
	public boolean sendTransactionNotifyToUser(String fullname, String email, 
			double donation, String transactionCode) {
		String subject = "Your donation of $" + donation + " has been successfully sent";
		String content = "<p>Dear " + fullname + ", we appreciate your donation. "
				+ "Your help means a lot to those who are struggling right now. "
				+ "And we're working hard to deliver your donation to them as soon "
				+ "as posible.</p>"
				+ "<p>Your transaction code: " + transactionCode + "</p>";
		Session session = getMailSession();
		
		try {
			Message message = getMessage(email, 
					session, subject, content);
		    Transport.send(message);
		    return true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Send a notify message to admin after user donate successfully.
	 * @param fullname
	 * @param email
	 * @param donation
	 * @param transactionCode
	 * @return
	 */
	@Override
	public boolean sendTransactionNotifyToAdmin(DonationHistory donation) {
		String subject = "User ID " + donation.getUserID() + " has donated $" 
				+ donation.getDonation() + " to campaign ID " + donation.getCampaignID();
		String content = "<p>The transaction has been added to the database "
				+ "and waiting for bank verification.</p>"
				+ "<p>Transaction code: " + donation.getTransactionCode() + "</p>";
		Session session = getMailSession();
		
		try {
			Message message = getMessage(ADMIN_EMAIL, 
					session, subject, content);
		    Transport.send(message);
		    return true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Prepare mail session.
	 * @return
	 */
	private Session getMailSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.setProperty("mail.smtp.ssl.enable", "false");
	    
	    Session session = Session.getInstance(props, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(
	            		MyKey.JAVA_MAIL_EMAIL, 
	            		MyKey.JAVA_MAIL_PASSWORD);
	        }
	    });
		return session;
	}

	/**
	 * Prepare mail message with multipart.
	 * @param email
	 * @param session
	 * @param subject
	 * @param content
	 * @return
	 * @throws MessagingException
	 * @throws AddressException
	 */
	private Message getMessage(String email, Session session, 
			String subject, String content)
			throws MessagingException, AddressException {
		Message message = new MimeMessage(session);
	    message.setFrom(
	    		new InternetAddress("no_reply@charityfunding.com"));
	    message.setRecipients(
	      Message.RecipientType.TO, 
	      InternetAddress.parse(email));
	    message.setSubject(subject);
	    
	    MimeBodyPart mimeBodyPart = new MimeBodyPart();
	    mimeBodyPart.setContent(content, "text/html; charset=utf-8");

	    Multipart multipart = new MimeMultipart();
	    multipart.addBodyPart(mimeBodyPart);

	    message.setContent(multipart);
		return message;
	}
	
}

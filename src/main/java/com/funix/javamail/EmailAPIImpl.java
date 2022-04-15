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

public class EmailAPIImpl implements IEmailAPI {
	
	/**
	 * Send to user email an account verifying message contains 
	 * new auto-generated password and a account verifying URL. 
	 * @throws MessagingException 
	 * @throws AddressException 
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
	 * Send to user email a new auto-generated password.. 
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	@Override
	public boolean sendNewPassword(String password, 
			String URL, String email) {
		String subject = "Your password at Charity Funding has been reset";
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

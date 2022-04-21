package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.crypto.SecretKey;
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
import com.funix.model.User;
import com.funix.service.NullConvert;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class Test {

	public static void main(String[] args) {
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
		  String date = "22/04/05";

		  //convert String to LocalDate
		  LocalDate localDate = LocalDate.parse(date, formatter);
		  System.out.println(localDate);
	}
	
	public static String generateRandomCode() {
		StringBuilder stringBuilder = 
				new StringBuilder();
		
		for (int i = 0; i < 60; i++) {
			Random random = new Random();
			char randomChar = (char) (random.nextInt(26) + 65);
			stringBuilder.append(randomChar);
		}
		
		return stringBuilder.toString();
	}
	
	public static void sendVerifingMessage() 
			throws AddressException, MessagingException {
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
	    
	    Message message = new MimeMessage(session);
	    message.setFrom(new InternetAddress("no_reply@charityfunding.com"));
	    message.setRecipients(
	      Message.RecipientType.TO, 
	      InternetAddress.parse("truonggnfx13372@funix.edu.vn"));
	    message.setSubject("Mail Subject");

	    String msg = "<a href='#'>This is my first email using JavaMailer.<a>";

	    MimeBodyPart mimeBodyPart = new MimeBodyPart();
	    mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

	    Multipart multipart = new MimeMultipart();
	    multipart.addBodyPart(mimeBodyPart);

	    message.setContent(multipart);

	    Transport.send(message);
	}
	
	public static User decodeJWT(String token) throws JwtException {
		User user = new User();
		SecretKey secretKey = Keys
				.hmacShaKeyFor(Decoders
						.BASE64
						.decode(MyKey.JWT_SECRET));
		
		Jws<Claims> result = Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token);
		
		String userEmail = (String) result.getBody()
				.get("userEmail");		
		String userRole = result.getBody()
				.get("userRole").toString();
		user.setEmail(userEmail);
		user.setUserRole(NullConvert.toInt(userRole));
		return user;	
	}
	
	public static String encodeJWT(String userEmail, 
			int userRole,
			double liveMins,
			String secret) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		long liveMillis = (long) (liveMins * 60 * 1000);
		long expireMillis = nowMillis + liveMillis;
		Date expire =  new Date(expireMillis);

		SecretKey secretKey = Keys
				.hmacShaKeyFor(Decoders
						.BASE64.decode(MyKey.JWT_SECRET));
		
		String jws = Jwts.builder()
				.setIssuer("CharityFunding")
				.setSubject("verifyemail")
				.claim("userEmail", userEmail)
				.claim("userRole", userRole)
				.setIssuedAt(now)
				.setExpiration(expire)
				.signWith(secretKey)
				.compact();
		
		return jws;
	}
	
	public static boolean testEmail(String email) {
		String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		System.out.println("Email: " + email.matches(emailRegex));
		return email.matches(emailRegex);
	}
	
	public static boolean testPassword(String password) {
		String passwordRegex = 
				"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)"
				+ "(?=.*[@#$!%*?&])[A-Za-z\\d@#$!%*?&]{8,10}$";
		System.out.println("Password: " + password.matches(passwordRegex));
		return password.matches(passwordRegex);
	}
	
	public static boolean testPhone(String phone) {
		String phoneRegex = "^\\d{10}$";
		System.out.println("Phone: " + phone.matches(phoneRegex));
		return phone.matches(phoneRegex);
	}

	public static List<DonationHistory> testIO() {
		List<DonationHistory> historyList = new ArrayList<>();
		
		File file = new File("test.csv");
		try (FileReader reader = new FileReader(file)) {
			BufferedReader bufferReader = new BufferedReader(reader);
			int lineNumber = 0;
			
			while (true) {
				String line = bufferReader.readLine();
				lineNumber++;
				
				// Screen out file header.
				if (lineNumber == 1) {
					continue;
				}
				
				if (line == null) {
					break;
				}
					
				// Get row values and add to DonationHistory object.
				DonationHistory history = new DonationHistory();
				String[] values = line.split(",");
				history.setUserID(NullConvert.toInt(values[0]));
				history.setCampaignID(NullConvert.toInt(values[1]));
				history.setDonation(NullConvert.toDouble(values[2]));
				history.setTransactionCode(values[3]);
				System.out.println(history);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return historyList;
	}

}

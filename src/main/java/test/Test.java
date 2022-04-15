package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

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
		try {
			sendVerifingMessage();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendVerifingMessage() 
			throws AddressException, MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
//	    props.put("mail.debug", "true");
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

	public static void testIO() {
		try (FileReader reader = new FileReader("testInput.csv");
				FileWriter writer = new FileWriter("testOutput.txt", false)) {
			BufferedReader bufferReader = new BufferedReader(reader);
			StringBuilder content = new StringBuilder();
			String output = "";
			int lineNumber = 0;
			
			while (true) {
				String line = bufferReader.readLine();
				lineNumber++;
				
				if (lineNumber == 1) {
					continue;
				}
				
				if (line == null) {
					break;
				}
					
				content.append(line);
				content.append("\n");
			}
			
			output = content.toString();
			writer.write(output);
			System.out.println("Test IO done.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

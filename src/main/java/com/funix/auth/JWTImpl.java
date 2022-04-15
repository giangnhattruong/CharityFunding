package com.funix.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import com.funix.config.MyKey;
import com.funix.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JWTImpl implements IAuthTokenizer {
	
	/**
	 * User ID.
	 */
	private int userID;
	
	/**
	 * User role.
	 */
	private int userRole;
	
	/**
	 * Token life span in minutes.
	 */
	private double liveMins;
	
	/**
	 * Secret string for encoding to jws token.
	 */
	private final String SECRET = MyKey.JWT_SECRET;
	
	/**
	 * Default constructor.
	 */
	public JWTImpl() {
	}
	
	/**
	 * Constructor with info for encoding token.
	 * @param userID
	 * @param userRole
	 * @param liveMins
	 */
	public JWTImpl(int userID, int userRole, double liveMins) {
		this.userID = userID;
		this.userRole = userRole;
		this.liveMins = liveMins;
	}

	/**
	 * Encode user ID and user role to jws token.
	 * @param liveMins
	 * @return
	 */
	@Override
	public String encodeUser() {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		long liveMillis = (long) (liveMins * 60 * 1000);
		long expireMillis = nowMillis + liveMillis;
		Date expire =  new Date(expireMillis);
		SecretKey key = Keys
				.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
		
		String jws = Jwts.builder()
				.setIssuer("Charity Funding Authentication")
				.setSubject("verifyemail")
				.claim("userID", userID)
				.claim("userRole", userRole)
				.setIssuedAt(now)
				.setExpiration(expire)
				.signWith(key)
				.compact();
		
		return jws;
	}

	/**
	 * Get user info from a jws token.
	 * @param token
	 * @return
	 */
	@Override
	public User decodeUser(String token) {
		User user = new User();
		SecretKey key = Keys
				.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
		
		try {
			Jws<Claims> result = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token);
			int userID = (Integer) result.getBody()
					.get("userID");		
			int userRole = (Integer) result.getBody()
					.get("userRole");
			user.setUserID(userID);
			user.setUserRole(userRole);
		} catch (JwtException e) {
			user = null;
			System.out.println("Authentication failed.");
			e.printStackTrace();
		}
		
		return user;
	}
	
}

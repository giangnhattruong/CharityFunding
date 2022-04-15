package com.funix.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import com.funix.config.MyKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JWTImpl implements IAuthTokenizer {
	
	/**
	 * User email.
	 */
	private String email;
	
	/**
	 * Token life span in minutes.
	 */
	private int liveMins;
	
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
	 * @param email
	 * @param userRole
	 * @param liveMins
	 */
	public JWTImpl(String email, int liveMins) {
		this.email = email;
		this.liveMins = liveMins;
	}

	/**
	 * Encode user email and user role to jws token.
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
				.claim("email", email)
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
	public String decodeUser(String token) {
		String email = "";
		SecretKey key = Keys
				.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
		
		try {
			Jws<Claims> result = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token);
			email = (String) result.getBody()
					.get("email");		
		} catch (JwtException | IllegalArgumentException e) {
			System.out.println("Authentication failed.");
			e.printStackTrace();
		}
		
		return email;
	}
	
}

/*
 * Interface ITokenizer.java    1.00    2022-04-13
 */

package com.funix.auth;

/**
 * Encode and decode user credential for authentication.
 * @author Giang_Nhat_Truong
 *
 */
public interface IAuthTokenizer {
	
	/**
	 * Encode user email and user role to an token.
	 * @param liveMins
	 * @return
	 */
	String encodeUser();
	
	/**
	 * Get user email from a token.
	 * @param token
	 * @return
	 */
	String decodeUser(String token);
	
}

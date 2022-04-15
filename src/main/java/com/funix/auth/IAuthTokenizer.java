/*
 * Interface ITokenizer.java    1.00    2022-04-13
 */

package com.funix.auth;

import com.funix.model.User;

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
	 * Get user info from a token.
	 * @param token
	 * @return
	 */
	User decodeUser(String token);
	
}

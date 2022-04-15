/**
 * Interface IUserDAO.java    1.00    2022-04-05
 */

package com.funix.dao;

import java.util.List;

import com.funix.model.User;
import com.funix.model.UserFilter;

/**
 * Interface for User DAO to 
 * create, get, update, delete users
 * and execute procedures from database.
 * @author Giang_Nhat_Truong
 *
 */
public interface IUserDAO {
	
	/**
	 * Check if user existed in database.
	 * @param email
	 * @return
	 */
	boolean checkForUser(String email);
	
	/**
	 * Create a new user in database, return user ID.
	 * @param newUser
	 * @return 
	 */
	void create(User newUser);
	
	/**
	 * Get user ID from user email.
	 * @param email
	 * @return
	 */
	int getUserID(String email);
	
	/**
	 * Get a user from database for updating.
	 * @param userID
	 * @return
	 */
	User getUser(int userID);
	
	/**
	 * Update an user's status to verified
	 * @param userID
	 */
	void enableUserStatus(int userID);
	
	/**
	 * Get many users base on search and sort filter.
	 * @param userFilter
	 * @return
	 */
	List<User> getManyUsers(UserFilter userFilter);

	/**
	 * Check if a user is admin.
	 * @param userID
	 * @return
	 */
	boolean isAdmin(int userID);

	/**
	 * Get user credential for authentication.
	 * @param userID
	 * @return
	 */
	User getUserCredential(int userID);
	
	/**
	 * Update an existing user in database.
	 * @param userID
	 * @param newUser
	 */
	void update(int userID, User newUser);
	
	/**
	 * Update user password.
	 * @param userID
	 * @param password
	 */
	void update(int userID, String password);
	
	/**
	 * Delete an existing user in database.
	 * @param userIDs
	 */
	void delete(String userIDs);
	
}

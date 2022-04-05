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
	 * Create a new user in database.
	 * @param newUser
	 */
	void create(User newUser);
	
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
	 * Update an existing user in database.
	 * @param userID
	 * @param newUser
	 */
	void update(int userID, User newUser);
	
	/**
	 * Delete an existing user in database.
	 * @param userIDs
	 */
	void delete(String userIDs);
}

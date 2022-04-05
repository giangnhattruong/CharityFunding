/*
 * UserDAOImpl.java    1.00    2022-04-05
 */

package com.funix.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.funix.model.User;
import com.funix.model.UserFilter;

/**
 * User DAO to 
 * create, get, update, delete users
 * and execute procedures from database.
 * @author Giang_Nhat_Truong
 *
 */
public class UserDAOImpl implements IUserDAO {
	
	/**
	 * JdbcTemplate contains functions to execute,
	 * get and update data from database.
	 */
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * Inject dataSource from Controller.
	 * @param dataSource
	 */
	public UserDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Create new user.
	 */
	@Override
	public void create(User newUser) {
		String SQL = "INSERT INTO userTbl"
				+ "(email, password, fullname, address, "
				+ "phone, userRole, userStatus) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(SQL, newUser.getEmail(), 
				newUser.getPassword(), newUser.getFullname(), 
				newUser.getAddress(), newUser.getPhone(), 
				newUser.getUserRole(), newUser.getUserStatus());
	}

	/**
	 * Get a user to pass values for user updating form.
	 */
	@Override
	public User getUser(int userID) {
		String SQL = "SELECT * FROM dbo.getUserDonationSummary() "
				+ "WHERE userID = ?";
		User user = jdbcTemplate.queryForObject(SQL, 
				new UserRowMapper(), userID);
		return user;
	}

	/**
	 * Update user status to verified after user confirmed by 
	 * clicking the verifying link in email.
	 */
	@Override
	public void enableUserStatus(int userID) {
		String SQL = "EXECUTE updateUserStatus 1, ?";
		jdbcTemplate.update(SQL, userID);
	}

	/**
	 * Get users base on filter object taken from
	 * user search form.
	 */
	@Override
	public List<User> getManyUsers(UserFilter userFilter) {
		String SQL = "SELECT * FROM dbo.getUserDonationSummary() "
				+ "WHERE (LOWER(email) LIKE ? OR "
				+ "LOWER(fullname) LIKE ?) AND "
				+ "userStatus LIKE ? "
				+ userFilter.getSortByFilter();
		List<User> userList = jdbcTemplate.query(SQL, 
				new UserRowMapper(), userFilter.getKeywordFilter(),
				userFilter.getKeywordFilter(),
				userFilter.getStatusFilter());
		return userList;
	}

	/**
	 * Update an existing user.
	 */
	@Override
	public void update(int userID, User newUser) {
		String SQL = "UPDATE userTbl "
				+ "SET email = ?, password = ?, fullname = ?, "
				+ "address = ?, phone = ?, userRole = ?, userStatus = ? "
				+ "WHERE userID = ?";
		jdbcTemplate.update(SQL, newUser.getEmail(),
				newUser.getPassword(), newUser.getFullname(),
				newUser.getAddress(), newUser.getPhone(),
				newUser.getUserRole(), newUser.getUserStatus(),
				userID);
	}

	/**
	 * Delete an existing user.
	 */
	@Override
	public void delete(String userIDs) {
		String SQL = "BEGIN TRANSACTION "
				+ "DELETE userTbl "
				+ "WHERE userID IN " + userIDs
				+ " COMMIT TRANSACTION";
		jdbcTemplate.update(SQL);
	}

}

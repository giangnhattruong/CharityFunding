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
	 * Check if user existed in database.
	 * @param email
	 * @return
	 */
	public boolean checkForUser(String email) {
		String SQL = "SELECT COUNT(*) FROM userTbl "
				+ "WHERE email = ?";
		int result = jdbcTemplate.queryForObject(SQL, 
				Integer.class, email);
		return result != 0;
	}
	
	/**
	 * Create new user.
	 */
	@Override
	public void create(User user) {
		String SQL = "INSERT INTO userTbl"
				+ "(email, password, fullname, address, "
				+ "phone, userRole, userStatus) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		/*
		 * If user role is updated to admin,
		 * then status must be on
		 */
		if (user.getUserRole() == 1) {
			user.setUserStatus(true);
		}
		
		jdbcTemplate.update(SQL, user.getEmail(), 
				user.getPassword(), user.getFullname(), 
				user.getAddress(), user.getPhone(), 
				user.getUserRole(), user.getUserStatus());
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
				+ "LOWER(fullname) LIKE ? OR "
				+ "phone LIKE ?) AND "
				+ "userStatus LIKE ? AND "
				+ "userRole LIKE ? "
				+ userFilter.getSortByFilter();
		List<User> userList = jdbcTemplate.query(SQL, 
				new UserRowMapper(), 
				userFilter.getKeywordFilter(),
				userFilter.getKeywordFilter(),
				userFilter.getKeywordFilter(),
				userFilter.getStatusFilter(),
				userFilter.getRoleFilter());
		return userList;
	}
	
	/**
	 * Check if a user is admin.
	 * @param userID
	 * @return
	 */
	@Override
	public boolean isAdmin(int userID) {
		String SQL = "SELECT userRole from userTbl WHERE userID = ?";
		int userRole = jdbcTemplate.queryForObject(SQL, Integer.class, userID);
		return userRole > 0;
	}
	
	/**
	 * Update an user informations
	 * except email and password
	 * (only user, not admin).
	 */
	@Override
	public void update(int userID, User user) {
		String SQL = "UPDATE userTbl "
				+ "SET fullname = ?, address = ?, phone = ?, "
				+ "userRole = ?, userStatus = ? "
				+ "WHERE userID = ?";
		
		/*
		 * If user role is updated to admin,
		 * then status must be on
		 */
		if (user.getUserRole() == 1) {
			user.setUserStatus(true);
		}
		
		jdbcTemplate.update(SQL, user.getFullname(),
				user.getAddress(), user.getPhone(),
				user.getUserRole(), user.getUserStatus(),
				userID);
	}
	
	/**
	 * Get user email.
	 * @param userID
	 * @return
	 */
	@Override
	public String getUserEmail(int userID) {
		String SQL = "SELECT email FROM userTbl WHERE userID = ?";
		String email = jdbcTemplate
				.queryForObject(SQL, String.class, userID);
		return email;
	}

	/**
	 * Get user password.
	 */
	@Override
	public String getUserPassword(int userID) {
		String SQL = "SELECT password FROM userTbl WHERE userID = ?";
		String password = jdbcTemplate
				.queryForObject(SQL, String.class, userID);
		return password;
	}
	
	/**
	 * Update user password.
	 */
	@Override
	public void update(int userID, String password) {
		String SQL = "UPDATE userTbl "
				+ "SET password = ? "
				+ "WHERE userID = ?";
		jdbcTemplate.update(SQL, password, userID);
	}
	
	/**
	 * Delete an existing user.
	 */
	@Override
	public void delete(String userIDs) {
		String SQL = "BEGIN TRANSACTION "
				+ "DELETE userTbl "
				+ "WHERE userID IN " + userIDs
				+ " AND userRole = 0"
				+ " COMMIT TRANSACTION";
		jdbcTemplate.update(SQL);
	}

}

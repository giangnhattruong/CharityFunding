/*
 * UserDAOImpl.java    1.00    2022-04-05
 */

package com.funix.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.funix.model.User;
import com.funix.model.UserFilter;
import com.funix.service.NullConvert;

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
	 * Get user ID from user email.
	 * @param email
	 * @return
	 */
	public int getUserID(String email) {
		String SQL = "SELECT userID FROM userTbl WHERE email = ?";
		int userID = 0;
		
		try {
			String userIDString = jdbcTemplate
					.queryForObject(SQL, String.class, email);
			userID = NullConvert.toInt(userIDString);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return userID;
	}
	
	/**
	 * Get user credential for authentication.
	 * @param userID
	 * @return
	 */
	@Override
	public User getUserSimpleInfo(int userID) {
		String SQL = "SELECT * FROM userTbl WHERE userID = ?";
		User user = new User();
		
		try {
			user = jdbcTemplate
			.queryForObject(SQL, new UserSimpleMapper(), userID);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	/**
	 * Get user credential for authentication.
	 * @param userID
	 * @return
	 */
	@Override
	public User getUserSimpleInfo(String email) {
		String SQL = "SELECT * FROM userTbl WHERE email = ?";
		User user = new User();
		
		try {
			user = jdbcTemplate
					.queryForObject(SQL, new UserSimpleMapper(), email);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	/**
	 * Get a user to pass values for user updating form.
	 */
	@Override
	public User getUserSummaryInfo(int userID) {
		String SQL = "SELECT * FROM dbo.getUserDonationSummary() "
				+ "WHERE userID = ?";
		User user = new User();
		
		try {
			user = jdbcTemplate.queryForObject(SQL,
					new UserSummaryMapper(), userID);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
				
		return user;
	}

	/**
	 * Enable user status.
	 */
	@Override
	public void enableUserStatus(int userID) {
		String SQL = "UPDATE userTbl "
				+ "SET userStatus = 1 "
				+ "WHERE userID = ?";
		jdbcTemplate.update(SQL, userID);
	}

	/**
	 * Enable user status.
	 */
	@Override
	public void enableUserStatus(String email) {
		String SQL = "UPDATE userTbl "
				+ "SET userStatus = 1 "
				+ "WHERE email = ?";
		jdbcTemplate.update(SQL, email);
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
				new UserSummaryMapper(), 
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
	 * Check if a user is admin.
	 * @param email
	 * @return
	 */
	@Override
	public boolean isAdmin(String email) {
		String SQL = "SELECT userRole from userTbl WHERE email = ?";
		int userRole = jdbcTemplate.queryForObject(SQL, Integer.class, email);
		return userRole > 0;
	}
	
	/**
	 * Update an user informations
	 * except email and password
	 * (only update user, not admin).
	 * @param userID
	 * @param newUser
	 */
	@Override
	public void update(int userID, User user) {
		String SQL = "UPDATE userTbl "
				+ "SET fullname = ?, address = ?, phone = ?, "
				+ "userRole = ?, userStatus = ? "
				+ "WHERE userID = ?";
		
		/*
		 * If user role is updated to admin,
		 * then status must be turned on.
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
	 * Update an user informations
	 * except email and password
	 * (only update user, not admin).
	 * @param email
	 * @param newUser
	 */
	@Override
	public void update(String email, User user) {
		String SQL = "UPDATE userTbl "
				+ "SET fullname = ?, address = ?, phone = ?, "
				+ "userRole = ?, userStatus = ? "
				+ "WHERE email = ?";
		
		/*
		 * If user role is updated to admin,
		 * then status must be turned on.
		 */
		if (user.getUserRole() == 1) {
			user.setUserStatus(true);
		}
		
		jdbcTemplate.update(SQL, user.getFullname(),
				user.getAddress(), user.getPhone(),
				user.getUserRole(), user.getUserStatus(),
				email);
	}

	/**
	 * Update user password.
	 * @param userID
	 * @param password
	 */
	@Override
	public void update(int userID, String password) {
		String SQL = "UPDATE userTbl "
				+ "SET password = ? "
				+ "WHERE userID = ?";
		jdbcTemplate.update(SQL, password, userID);
	}

	/**
	 * Update user password.
	 * @param email
	 * @param password
	 */
	@Override
	public void update(String email, String password) {
		String SQL = "UPDATE userTbl "
				+ "SET password = ? "
				+ "WHERE email = ?";
		jdbcTemplate.update(SQL, password, email);
	}
	
	/**
	 * Delete an existing user.
	 * @param userIDs
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

/*
 * UserRowMapper.java    1.00    2022-04-05
 */

package com.funix.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.funix.model.User;
import com.funix.service.NullConvert;

/**
 * Row mapper for user record.
 * @author Giang_Nhat_Truong
 *
 */
public class UserRowMapper implements RowMapper<User> {

	/**
	 * Map to get all user fields in a record/row.
	 */
	@Override
	public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		int userID = resultSet.getInt("userID");
		String email = resultSet.getString("email");
		String password = resultSet.getString("password");
		String fullname = resultSet.getString("fullname");
		String address = resultSet.getString("address");
		String phone = resultSet.getString("phone");
		int userRole = resultSet.getInt("userRole");
		int status = resultSet.getInt("userStatus");
		boolean userStatus = status == 1 ? true : false;
		LocalDate dateCreated = NullConvert
				.toLocalDate(resultSet.getDate("dateCreated"));
		double totalDonations = resultSet.getDouble("totalDonations");
		int donationTimes = resultSet.getInt("donationTimes");
		LocalDate latestDonationDate = NullConvert
				.toLocalDate(resultSet.getDate("latestDonationDate"));
		
		return new User(userID, email, password, fullname, 
				address, phone, userRole, userStatus, dateCreated, 
				totalDonations, donationTimes, latestDonationDate);
	}

}

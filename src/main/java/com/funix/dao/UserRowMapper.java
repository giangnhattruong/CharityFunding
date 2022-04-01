package com.funix.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.funix.model.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		int userID = resultSet.getInt("userID");
		String email = resultSet.getString("email");
		String password = resultSet.getString("password");
		String fullname = resultSet.getString("fullname");
		String address = resultSet.getString("address");
		String phone = resultSet.getString("phone");
		int role = resultSet.getInt("role");
		int userStatus = resultSet.getInt("userStatus");
		Date dateCreated = resultSet.getDate("dateCreated");
		double totalDonations = resultSet.getDouble("totalDonations");
		int donationTimes = resultSet.getInt("donationTimes");
		Date latestDonationDate = resultSet.getDate("latestDonationDate");
		
		return new User(userID, email, password, fullname, 
				address, phone, role, userStatus, dateCreated, 
				totalDonations, donationTimes, latestDonationDate);
	}

}

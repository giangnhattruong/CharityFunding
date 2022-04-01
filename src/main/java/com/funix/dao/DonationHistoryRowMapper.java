package com.funix.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.funix.model.DonationHistory;
import com.funix.model.User;

public class DonationHistoryRowMapper implements RowMapper<DonationHistory> {

	@Override
	public DonationHistory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		int donationHistoryID = resultSet.getInt("donationHistoryID");
		int userID = resultSet.getInt("userID");
		String email = resultSet.getString("email");
		String fullname = resultSet.getString("fullname");
		int campaignID = resultSet.getInt("campaignID");
		String title = resultSet.getString("title");
		String location = resultSet.getString("location");
		double donation = resultSet.getDouble("donation");
		Date donationDate = resultSet.getDate("donationDate");
		String transactionCode = resultSet.getString("transactionCode");
		
		return new DonationHistory(donationHistoryID, userID, 
				email, fullname, campaignID, title, location, 
				donation, donationDate, transactionCode);
	}

}

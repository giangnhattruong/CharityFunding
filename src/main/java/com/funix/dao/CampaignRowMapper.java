package com.funix.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.funix.model.Campaign;

public class CampaignRowMapper implements RowMapper<Campaign> {

	@Override
	public Campaign mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		int campaignID = resultSet.getInt("campaignID");
		String title = resultSet.getString("title");
		String description = resultSet.getString("description");
		double targetAmount = resultSet.getDouble("targetAmount");
		String location = resultSet.getString("location");
		String imgURL = resultSet.getString("imgURL");
		Date startDate = resultSet.getDate("startDate");
		Date endDate = resultSet.getDate("endDate");
		int campaignStatus = resultSet.getInt("campaignStatus");
		Date dateCreated = resultSet.getDate("dateCreated");
		double totalDonations = resultSet.getDouble("totalDonations");
		int totalSupporters = resultSet.getInt("totalSupporters");
		Date latestDonationDate = resultSet.getDate("latestDonationDate");
		
		return new Campaign(campaignID, title, description, 
				targetAmount, location, imgURL, startDate, 
				endDate, campaignStatus, dateCreated, totalDonations, 
				totalSupporters, latestDonationDate);
	}

}

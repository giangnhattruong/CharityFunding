package com.funix.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.funix.model.Campaign;
import com.funix.service.NullConvert;

public class CampaignRowMapper implements RowMapper<Campaign> {

	@Override
	public Campaign mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		int campaignID = resultSet.getInt("campaignID");
		String title = resultSet.getString("title");
		String description = resultSet.getString("description");
		double targetAmount = resultSet.getDouble("targetAmount");
		String location = resultSet.getString("location");
		String imgURL = resultSet.getString("imgURL");
		LocalDate startDate = NullConvert
				.toLocalDate(resultSet.getDate("startDate"));
		LocalDate endDate = NullConvert
				.toLocalDate(resultSet.getDate("endDate"));
		int status = resultSet.getInt("campaignStatus");
		boolean campaignStatus = status == 1 ? true : false;
		LocalDate dateCreated = resultSet.getDate("dateCreated").toLocalDate();
		double totalDonations = resultSet.getDouble("totalDonations");
		int totalSupporters = resultSet.getInt("totalSupporters");
		LocalDate latestDonationDate = NullConvert
				.toLocalDate(resultSet.getDate("latestDonationDate"));
		
		return new Campaign(campaignID, title, description, 
				targetAmount, location, imgURL, startDate, 
				endDate, campaignStatus, dateCreated, totalDonations, 
				totalSupporters, latestDonationDate);
	}

}

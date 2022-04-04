package com.funix.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.funix.model.DonationHistory;
import com.funix.model.DonationHistoryFilter;

public class DonationHistoryDAOImpl implements IDonationHistoryDAO {
	private JdbcTemplate jdbcTemplate;
	
	public DonationHistoryDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(DonationHistory history) {
		String SQL = "INSERT INTO donationHistoryTbl"
				+ "(userID, campaignID, donation, transactionCode) "
				+ "VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(SQL, history.getUserID(),
				history.getCampaignID(), history.getDonation(),
				history.getTransactionCode());
	}

	@Override
	public void verifyHistoryStatus(List<String> transactionCodeList) {
		String SQL = "EXECUTE updateHistoryStatus ?";
		
		for (String transactionCode: transactionCodeList) {
			jdbcTemplate.update(SQL, transactionCode);
		}
	}
	
	public List<String> getTransactionCodeList() {
		String SQL = "SELECT transactionCode FROM donationHistoryTbl";
		return jdbcTemplate.queryForList(SQL, String.class);
	}

	@Override
	public List<DonationHistory> getManyAdminHistories(
			DonationHistoryFilter filter) {
		String SQL = "SELECT * FROM dbo.getDonationHistory() "
				+ "WHERE "
				+ "(LOWER(email) LIKE ? OR LOWER(fullname) LIKE ?) AND "
				+ "(LOWER(title) LIKE ? OR LOWER(location) LIKE ?) AND "
				+ "transactionCode LIKE ? AND "
				+ "donationStatus LIKE ? "
				+ filter.getSortByFilter();
		return jdbcTemplate
				.query(SQL, new DonationHistoryRowMapper(), 
						filter.getUserKeywordFilter(),
						filter.getUserKeywordFilter(),
						filter.getCampaignKeywordFilter(),
						filter.getCampaignKeywordFilter(),
						filter.getTransactionKeywordFilter(),
						filter.getStatusFilter());
	}

	@Override
	public List<DonationHistory> getManyUserHistories(
			int userID, DonationHistoryFilter filter) {
		String SQL = "SELECT * FROM dbo.getDonationHistory() "
				+ "WHERE "
				+ "(LOWER(title) LIKE ? OR LOWER(location) LIKE ?) AND "
				+ "transactionCode LIKE ? AND "
				+ "donationStatus LIKE ? AND "
				+ "userID = ?"
				+ filter.getSortByFilter();
		return jdbcTemplate
				.query(SQL, new DonationHistoryRowMapper(), 
						filter.getCampaignKeywordFilter(),
						filter.getCampaignKeywordFilter(),
						filter.getTransactionKeywordFilter(),
						filter.getStatusFilter(), userID);
	}

}

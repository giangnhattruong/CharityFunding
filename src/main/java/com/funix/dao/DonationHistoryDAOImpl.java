/*
 * DonationHistoryDAOImpl.java    1.00    2022-04-05
 */

package com.funix.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.funix.model.DonationHistory;
import com.funix.model.DonationHistoryFilter;

/**
 * Donation History DAO to 
 * create, get donation history and execute 
 * procedures from database.
 * @author Giang_Nhat_Truong
 *
 */
public class DonationHistoryDAOImpl implements IDonationHistoryDAO {
	
	/**
	 * JdbcTemplate contains functions to execute,
	 * get and update data from database.
	 */
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * Inject dataSource from Controller.
	 * @param dataSource
	 */
	public DonationHistoryDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Create new donation/transaction record.
	 */
	@Override
	public void create(DonationHistory history) {
		String SQL = "INSERT INTO donationHistoryTbl"
				+ "(userID, campaignID, donation, transactionCode) "
				+ "VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(SQL, history.getUserID(),
				history.getCampaignID(), history.getDonation(),
				history.getTransactionCode());
	}

	/**
	 * Verify donations/transactions if they contain
	 * the same transaction codes as those in
	 * transaction code list.
	 */
	@Override
	public void verifyHistoryStatus(List<String> transactionCodeList) {
		String SQL = "EXECUTE updateHistoryStatus ?";
		
		for (String transactionCode: transactionCodeList) {
			jdbcTemplate.update(SQL, transactionCode);
		}
	}
	
	/**
	 * Get all transaction code list.
	 * @return
	 */
	public List<String> getTransactionCodeList() {
		String SQL = "SELECT transactionCode FROM donationHistoryTbl";
		return jdbcTemplate.queryForList(SQL, String.class);
	}

	/**
	 * Get donation history based on filter values taken
	 * from donation history form.
	 */
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

	/**
	 * Get donation history based on filter values taken
	 * from donation history form of a specific user.
	 */
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

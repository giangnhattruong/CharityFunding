/*
 * DonationHistoryDAOImpl.java    1.00    2022-04-05
 */

package com.funix.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
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
				+ "(userID, campaignID, donation, transactionCode, donationDate) "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		try {
			jdbcTemplate.update(SQL, history.getUserID(),
					history.getCampaignID(), history.getDonation(),
					history.getTransactionCode(), 
					history.getDonationDate().toString());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verify donations/transactions if they contain
	 * the same transaction codes as those in
	 * transaction code list.
	 */
	@Override
	public void verifyHistoryStatus(Map<String, Boolean> transactionCodeList) {
		String SQL = "EXECUTE updateHistoryStatus ?, ?";
		
		/*
		 * If a transactionCode in the map is true, 
		 * then update this transaction to 1-verified, 
		 * otherwise update this transaction to 2-denied.
		 */
		for (String transactionCode: transactionCodeList.keySet()) {
			try {
				if (transactionCodeList.get(transactionCode) == true) {
					jdbcTemplate.update(SQL, transactionCode, 1);
				} else {
					jdbcTemplate.update(SQL, transactionCode, 2);
				}
			} catch (DataAccessException e) {
				System.out.println("Transaction failed to update: " 
						+ transactionCode);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get all transaction code list.
	 * @return
	 */
	public List<String> getTransactionCodeList() {
		String SQL = "SELECT transactionCode FROM donationHistoryTbl "
				+ "WHERE donationStatus = 0";
		return jdbcTemplate.queryForList(SQL, String.class);
	}

	/**
	 * Get donation history based on filter values taken
	 * from donation history form.
	 */
	@Override
	public List<DonationHistory> getAllHistory(
			DonationHistoryFilter filter) {
		String SQL = "SELECT * FROM dbo.getDonationHistory() "
				+ "WHERE "
				+ "(LOWER(email) LIKE ? OR LOWER(fullname) LIKE ?) AND "
				+ "(LOWER(title) LIKE ? OR LOWER(location) LIKE ?) AND "
				+ "transactionCode LIKE ? AND "
				+ "donationStatus IN "
				+ filter.getStatusFilter() + " "
				+ filter.getSortByFilter();
		return jdbcTemplate
				.query(SQL, new DonationHistoryRowMapper(), 
						filter.getUserKeywordFilter(),
						filter.getUserKeywordFilter(),
						filter.getCampaignKeywordFilter(),
						filter.getCampaignKeywordFilter(),
						filter.getTransactionKeywordFilter());
	}

	/**
	 * Get donation history based on filter values taken
	 * from donation history form of a specific user.
	 */
	@Override
	public List<DonationHistory> getUserHistory(
			int userID, DonationHistoryFilter filter) {
		String SQL = "SELECT * FROM dbo.getDonationHistory() "
				+ "WHERE "
				+ "(LOWER(title) LIKE ? OR LOWER(location) LIKE ?) AND "
				+ "transactionCode LIKE ? AND "
				+ "donationStatus IN "
				+ filter.getStatusFilter() + " AND "
				+ "userID = ? "
				+ filter.getSortByFilter();
		return jdbcTemplate
				.query(SQL, new DonationHistoryRowMapper(), 
						filter.getCampaignKeywordFilter(),
						filter.getCampaignKeywordFilter(),
						filter.getTransactionKeywordFilter(),
						userID);
	}

	/**
	 * Get donation history based on filter values taken
	 * from donation history form of a specific user.
	 */
	@Override
	public List<DonationHistory> getUserHistory(
			String email, DonationHistoryFilter filter) {
		String SQL = "SELECT * FROM dbo.getDonationHistory() "
				+ "WHERE "
				+ "(LOWER(title) LIKE ? OR LOWER(location) LIKE ?) AND "
				+ "transactionCode LIKE ? AND "
				+ "donationStatus IN "
				+ filter.getStatusFilter() + " AND "
				+ "email = ? "
				+ filter.getSortByFilter();
		return jdbcTemplate
				.query(SQL, new DonationHistoryRowMapper(), 
						filter.getCampaignKeywordFilter(),
						filter.getCampaignKeywordFilter(),
						filter.getTransactionKeywordFilter(),
						email);
	}

}

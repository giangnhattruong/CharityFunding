/**
 * Interface IDonationHistoryDAO.java    1.00    2022-04-05
 */

package com.funix.dao;

import java.util.List;
import java.util.Map;

import com.funix.model.DonationHistory;
import com.funix.model.DonationHistoryFilter;

/**
 * Interface for Donation History DAO to 
 * create, get donation history and execute 
 * procedures from database.
 * @author Giang_Nhat_Truong
 *
 */
public interface IDonationHistoryDAO {
	
	/**
	 * Create a new donation/transaction record in database.
	 * @param history
	 */
	void create(DonationHistory history);
	
	/**
	 * Verify all donations/transactions which
	 * contain the same transaction codes as those in
	 * transaction code list.
	 * @param transactionCodeList
	 */
	void verifyHistoryStatus(Map<String, Boolean> transactionCodeList);
	
	/**
	 * Get all transaction code list.
	 * @return
	 */
	List<String> getTransactionCodeList();
	
	/**
	 * Get all donations history 
	 * base on search and sort filter.
	 * @param filter
	 * @return
	 */
	List<DonationHistory> getAllHistory(
			DonationHistoryFilter filter);
	
	/**
	 * Get donation history of one specific user 
	 * base on search and sort filter.
	 * @param userID
	 * @param filter
	 * @return
	 */
	List<DonationHistory> getUserHistory(
			int userID, DonationHistoryFilter filter);
	
	/**
	 * Get donation history of one specific user 
	 * base on search and sort filter.
	 * @param email
	 * @param filter
	 * @return
	 */
	List<DonationHistory> getUserHistory(
			String email, DonationHistoryFilter filter);
}

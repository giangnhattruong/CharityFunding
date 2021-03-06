/**
 * Interface ITransaction.java    1.00    2022-04-19
 */

package com.funix.banktransaction;

import java.util.List;
import java.util.Map;

/**
 * Handle bank transaction.
 * @author Giang_Nhat_Truong
 *
 */
public interface ITransaction {
	
	/**
	 * Execute a transaction.
	 */
	void send();
	
	/**
	 * Verify transaction code list.
	 * @param transactionCodeList
	 * @return
	 */
	Map<String, Boolean> verify(List<String> transactionCodeList);
	
	/**
	 * Validate transaction input.
	 * @return
	 */
	String validate();
}

/**
 * Interface ITransaction.java    1.00    2022-04-19
 */

package com.funix.banktransaction;

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
	 * Validate transaction input.
	 * @return
	 */
	String validate();
}

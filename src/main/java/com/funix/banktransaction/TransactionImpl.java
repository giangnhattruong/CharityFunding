/**
 * TransactionImpl.java    1.00    2022-04-19
 */

package com.funix.banktransaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Handle bank transaction.
 * @author Giang_Nhat_Truong
 *
 */
public class TransactionImpl implements ITransaction {
	
	/**
	 * Simulating a bank transaction success rate 
	 * on transferring (from 1-10).
	 */
	private static final double TRANSACTION_SUCCESS_RATE = 7;
	
	/**
	 * Simulating a bank transaction success rate after 
	 * transferring (from 0 - 1).
	 */
	private static final double TRANSACTION_CODE_VERIFIED_RATE = 0.3;
	
	/**
	 * Simulating a bank transaction failed rate after 
	 * transferring (from 0 - 1).
	 */
	private static final double TRANSACTION_CODE_DENIED_RATE = 0.05;
	
	/**
	 * Min amount allowed per transaction.
	 */
	private static final int MIN_AMOUNT_ALLOWED = 5;
	
	/**
	 * MAX amount allowed per transaction.
	 */
	private static final int MAX_AMOUNT_ALLOWED = 10000;
	
	/**
	 * User fullname.
	 */
	private String fullname;
	
	/**
	 * User bank number.
	 */
	private String bankNumber;
	
	/**
	 * User transfer amount.
	 */
	private double amount;
	
	/**
	 * Return transactionCode if a transaction has been sent successfully.
	 */
	private String transactionCode;
	
	/**
	 * Return true if a transaction has been sent successfully.
	 */
	private boolean transactionStatus;
	
	/**
	 * Validate transaction input.
	 * @return
	 */
	@Override
	public String validate() {
		StringBuilder messages = new StringBuilder();
		messages.append("Please fulfill all requirements below and re-submit:");
		messages.append("</br>");
		int validateErrorCount = 0;
		String numberRegex = "^\\d{8,}$";
		
		if (fullname.length() < 3) {
			messages.append(++validateErrorCount + ". ");
			messages.append("Fullname must contains at least 3 characters.");
			messages.append("</br>");
		}
		
		if (!bankNumber.matches(numberRegex)) {
			messages.append(++validateErrorCount + ". ");
			messages.append("Bank number must contains "
					+ "at least 8 digits (digits only).");
			messages.append("</br>");
		}
		
		if (amount < MIN_AMOUNT_ALLOWED || 
				amount > MAX_AMOUNT_ALLOWED) {
			messages.append(++validateErrorCount + ". ");
			messages.append("Donation amount must not less than $" 
					+ MIN_AMOUNT_ALLOWED + " or greater than $"
					+ MAX_AMOUNT_ALLOWED + ".");
			messages.append("</br>");
		}
		
		return validateErrorCount == 0 ? "success" : messages.toString();
	}

	/**
	 * Execute a transaction. Simulate a real bank transaction.
	 */
	@Override
	public void send() {
		// Simulate sending a transaction, some may be failed.
		Random random = new Random();
		int number = random.nextInt(10) + 1;
		
		if (number <= TRANSACTION_SUCCESS_RATE) {
			transactionCode = generateRandomCode(27);
			transactionStatus = true;
		} else {
			transactionStatus = false;
		}
	}
	
	/**
	 * Verify transaction code list. Simulate bank checking transactions. 
	 * @param transactionCodeList
	 * @return
	 */
	@Override
	public Map<String, Boolean> verify(List<String> transactionCodeList) {
		// Simulate verifying list of transaction codes.
		Map<String, Boolean> transactionCodeMap = new HashMap<>();
		Random random = new Random();
		int totalSize = transactionCodeList.size();
		int verifiedSize = (int) Math.ceil(totalSize * TRANSACTION_CODE_VERIFIED_RATE);
		int deniedSize = (int) Math.round(totalSize * TRANSACTION_CODE_DENIED_RATE);
		
		// Mark random list of transaction code to verified.
		for (int i = 0; i < verifiedSize; i++) {
			int randomIndex = random.nextInt(transactionCodeList.size());
			transactionCodeMap.put(transactionCodeList.get(randomIndex), true);
		}
		
		// Mark random list of transaction code to denied.
		for (int i = 0; i < deniedSize; i++) {
			int randomIndex = random.nextInt(transactionCodeList.size());
			transactionCodeMap.put(transactionCodeList.get(randomIndex), false);
		}
		
		return transactionCodeMap;
	}
	
	/**
	 * Generate random string contain uppercase letters.
	 * @return
	 */
	private String generateRandomCode(int length) {
		StringBuilder stringBuilder = 
				new StringBuilder();
		
		for (int i = 0; i < length; i++) {
			Random random = new Random();
			char randomChar = (char) (random.nextInt(26) + 65);
			stringBuilder.append(randomChar);
		}
		
		return stringBuilder.toString();
	}

	// Setter and getter
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getTransactionCode() {
		return transactionCode;
	}

	public boolean isTransactionSuccess() {
		return transactionStatus;
	}

}

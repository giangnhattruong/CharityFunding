package com.funix.dao;

import java.util.List;

import com.funix.model.DonationHistory;
import com.funix.model.DonationHistoryFilter;

public interface IDonationHistoryDAO {
	void create(DonationHistory history);
	void verifyHistoryStatus(List<String> transactionCodeList);
	List<DonationHistory> getManyAdminHistories(
			DonationHistoryFilter filter);
	List<DonationHistory> getManyUserHistories(
			int userID, DonationHistoryFilter filter);
}

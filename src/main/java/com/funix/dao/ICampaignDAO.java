package com.funix.dao;

import java.util.List;

import com.funix.model.Campaign;
import com.funix.model.CampaignFilter;

public interface ICampaignDAO {
	void create(Campaign newCampaign);
	Campaign getCampaign(int campaignID);
	void updateAllCampaignStatus();
	List<Campaign> getManyCampaigns(CampaignFilter campaignFilter);
	void update(int campaignID, Campaign newCampaign);
	void delete(String campaignIDs);
}

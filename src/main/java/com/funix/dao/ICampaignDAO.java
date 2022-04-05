/**
 * Interface ICampaignDAO.java    1.00    2022-04-05
 */

package com.funix.dao;

import java.util.List;

import com.funix.model.Campaign;
import com.funix.model.CampaignFilter;

/**
 * Interface for Campaign DAO to 
 * create, get, update, delete campaigns
 * and execute procedures from database.
 * @author Giang_Nhat_Truong
 *
 */
public interface ICampaignDAO {
	
	/**
	 * Create a new campaign in database.
	 * @param newCampaign
	 * @return
	 */
	String create(Campaign newCampaign);
	
	/**
	 * Get a campaign from database for updating.
	 * @param campaignID
	 * @return
	 */
	Campaign getCampaign(int campaignID);
	
	/**
	 * Update all campaign status, called on each
	 * getManyCampaigns request, to update all campaign
	 * status at current time.
	 */
	void updateAllCampaignStatus();
	
	/**
	 * Get many campaigns base on search and sort filter.
	 * @param campaignFilter
	 * @return
	 */
	List<Campaign> getManyCampaigns(CampaignFilter campaignFilter);
	
	/**
	 * Update an existing campaign in database.
	 * @param campaignID
	 * @param newCampaign
	 * @return
	 */
	String update(int campaignID, Campaign newCampaign);
	
	/**
	 * Delete an existing campaign in database.
	 * @param campaignIDs
	 */
	void delete(String campaignIDs);
}

/*
 * CampaignDAOImpl.java    1.00    2022-04-05
 */

package com.funix.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.funix.model.Campaign;
import com.funix.model.CampaignFilter;
import com.funix.multipart.IImageAPI;

/**
 * Campaign DAO to 
 * create, get, update, delete campaigns
 * and execute procedures from database.
 * @author Giang_Nhat_Truong
 *
 */
public class CampaignDAOImpl implements ICampaignDAO {
	
	/**
	 * JdbcTemplate contains functions to execute,
	 * get and update data from database.
	 */
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * ImageAPI help uploading, transforming and deleting
	 * images form API server.
	 */
	private IImageAPI imageAPI;
	
	/**
	 * Inject dataSource from Controller.
	 * @param dataSource
	 */
	public CampaignDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/**
	 * Inject dataSource and imageAPI from Controller.
	 * @param dataSource
	 */
	public CampaignDAOImpl(DataSource dataSource, IImageAPI imageAPI) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		this.imageAPI = imageAPI;
	}
	
	/**
	 * Create new campaign. After validating all text field of campaign
	 * successful, then the image will be uploaded to API server and result
	 * with an image URL. Then a new campaign will be created in database.
	 */
	@Override
	public String create(Campaign newCampaign) {
		String SQL = "INSERT INTO campaignTbl (title, description, "
				+ "targetAmount, location, imgURL, startDate, endDate, "
				+ "campaignStatus) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		String message = "";
		
		/*
		 * Validate all text values of a campaign first 
		 * before uploading image to API server to get image URL.
		 */
		if (newCampaign.validateTextFieldsOnly()) {
			newCampaign.setImgURL(imageAPI.uploadImage(newCampaign.getFile()));
			message = newCampaign.validate();
			
			// Validate campaign again and create new record in database.
			if (message.equals("success")) {
				jdbcTemplate.update(SQL, newCampaign.getTitle(),
						newCampaign.getDescription(), newCampaign.getTargetAmount(),
						newCampaign.getLocation(), newCampaign.getImgURL(),
						newCampaign.getStartDate(), newCampaign.getEndDate(),
						newCampaign.getCampaignStatus());
			}
		} else {
			message = newCampaign.validate();
		}
		
		return message;
	}

	/**
	 * Get a campaign to pass values for campaign updating form.
	 */
	@Override
	public Campaign getCampaign(int campaignID) {
		Campaign campaign = new Campaign();
		String SQL = "SELECT * FROM dbo.getCampaignDonationSummary()"
				+ "WHERE campaignID = ?";
		
		try {
			campaign = jdbcTemplate.queryForObject(SQL, 
					new CampaignRowMapper(), campaignID);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return campaign;
	}
	
	/**
	 * Update campaign status to closed if the campaign is
	 * out of date or campaign total donations meet the
	 * target donation amount. This function is called before
	 * getManyCampaigns to get campaign status at current time.
	 */
	@Override
	public void updateAllCampaignStatus() {
		String SQL = "EXECUTE dbo.updateCampaignStatus";
		jdbcTemplate.execute(SQL);
	}

	/**
	 * Get campaigns base on filter object taken from
	 * campaign search form.
	 */
	@Override
	public List<Campaign> getManyCampaigns(CampaignFilter campaignFilter) {
		String SQL = "SELECT * "
				+ "FROM dbo.getCampaignDonationSummary() "
				+ "WHERE (LOWER(title) LIKE ? OR "
				+ "	LOWER(description) LIKE ?) AND "
				+ "	LOWER(location) LIKE ? AND "
				+ "	campaignStatus IN "
				+ campaignFilter.getStatusFilter() + " "
				+ campaignFilter.getOrderByFilter();
		updateAllCampaignStatus();
		List<Campaign> campaignList = jdbcTemplate.query(SQL, 
				new CampaignRowMapper(), 
				campaignFilter.getKeywordFilter(),
				campaignFilter.getKeywordFilter(), 
				campaignFilter.getLocationFilter());
		return campaignList;
	}

	/**
	 * Update an existing campaign. After validating all text field of campaign
	 * successful. If a new image is uploaded, the old image will be deleted,
	 * then the new image will be uploaded to API server and result
	 * with an image URL. Then a new campaign will be created in database.
	 */
	@Override
	public String update(int campaignID, Campaign newCampaign) {
		String SQL = "UPDATE campaignTbl "
				+ "SET title = ?, description = ?, targetAmount = ?, location = ?, "
				+ "	imgURL = ?, startDate = ?, endDate = ?, campaignStatus = ? "
				+ "WHERE campaignID = ?";
		MultipartFile newFile = newCampaign.getFile();
		String message = newCampaign.validate();
		
		// Validate before deleting, uploading image and creating new data record.
		if (message.equals("success")) {
			if (newFile.getSize() != 0) {
				imageAPI.deleteImage(newCampaign.getImgURL());
				newCampaign.setImgURL(imageAPI.uploadImage(newFile));
			}
			
			jdbcTemplate.update(SQL, newCampaign.getTitle(),
					newCampaign.getDescription(), newCampaign.getTargetAmount(),
					newCampaign.getLocation(), newCampaign.getImgURL(),
					newCampaign.getStartDate(), newCampaign.getEndDate(),
					newCampaign.getCampaignStatus(), campaignID);
		}
		
		return message;
	}

	/**
	 * Delete an existing campaign.
	 */
	@Override
	public void delete(String campaignIDs) {
		String SQL = "BEGIN TRANSACTION "
				+ "DELETE campaignTbl "
				+ "WHERE campaignID IN " + campaignIDs
				+ " COMMIT TRANSACTION";
		deleteManyImages(campaignIDs);
		jdbcTemplate.update(SQL);
	}
	
	/**
	 * Delete images from API server.
	 * @param campaignIDs
	 */
	private void deleteManyImages(String campaignIDs) {
		List<String> imgURLs = getManyImgURLs(campaignIDs);
		
		for (String imgURL: imgURLs) {
			imageAPI.deleteImage(imgURL);
		}
	}
	
	/**
	 * Get image URLs from database based on campaignID list.
	 * @param campaignIDs
	 * @return
	 */
	private List<String> getManyImgURLs(String campaignIDs) {
		String SQL = "SELECT imgURL "
				+ "FROM campaignTbl "
				+ "WHERE campaignID IN " + campaignIDs;
		return jdbcTemplate.queryForList(SQL, String.class);
	}

}

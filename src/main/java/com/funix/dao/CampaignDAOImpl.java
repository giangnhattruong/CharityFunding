package com.funix.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.funix.model.Campaign;
import com.funix.model.CampaignFilter;
import com.funix.multipart.IImageAPI;

public class CampaignDAOImpl implements ICampaignDAO {
	private JdbcTemplate jdbcTemplate;
	private IImageAPI imageAPI;
	
	public CampaignDAOImpl(DataSource dataSource, IImageAPI imageAPI) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		this.imageAPI = imageAPI;
	}
	
	@Override
	public String create(Campaign newCampaign) {
		String SQL = "INSERT INTO campaignTbl (title, description, "
				+ "targetAmount, location, imgURL, startDate, endDate, "
				+ "campaignStatus) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		String message = "";
		
		if (newCampaign.validateTextFieldsOnly()) {
			newCampaign.setImgURL(imageAPI.uploadImage(newCampaign.getFile()));
			message = newCampaign.validate();
			
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

	@Override
	public Campaign getCampaign(int campaignID) {
		String SQL = "SELECT * FROM dbo.getCampaignDonationSummary()"
				+ "WHERE campaignID = ?";
		Campaign campaign = jdbcTemplate.queryForObject(SQL, 
				new CampaignRowMapper(), campaignID);
		return campaign;
	}
	
	@Override
	public void updateAllCampaignStatus() {
		String SQL = "EXECUTE dbo.updateCampaignStatus";
		jdbcTemplate.execute(SQL);
	}

	@Override
	public List<Campaign> getManyCampaigns(CampaignFilter campaignFilter) {
		String SQL = "SELECT * "
				+ "FROM dbo.getCampaignDonationSummary() "
				+ "WHERE (LOWER(title) LIKE ? OR "
				+ "	LOWER(description) LIKE ?) AND "
				+ "	LOWER(location) LIKE ? AND "
				+ "	campaignStatus LIKE ? "
				+ campaignFilter.getOrderByFilter();
		updateAllCampaignStatus();
		List<Campaign> campaignList = jdbcTemplate.query(SQL, 
				new CampaignRowMapper(), 
				campaignFilter.getKeywordFilter(),
				campaignFilter.getKeywordFilter(), 
				campaignFilter.getLocationFilter(),
				campaignFilter.getStatusFilter());
		return campaignList;
	}

	@Override
	public String update(int campaignID, Campaign newCampaign) {
		String SQL = "UPDATE campaignTbl "
				+ "SET title = ?, description = ?, targetAmount = ?, location = ?, "
				+ "	imgURL = ?, startDate = ?, endDate = ?, campaignStatus = ? "
				+ "WHERE campaignID = ?";
		MultipartFile newFile = newCampaign.getFile();
		String message = newCampaign.validate();
		
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

	@Override
	public void delete(String campaignIDs) {
		String SQL = "BEGIN TRANSACTION "
				+ "DELETE campaignTbl "
				+ "WHERE campaignID IN " + campaignIDs
				+ " COMMIT TRANSACTION";
		deleteManyImages(campaignIDs);
		jdbcTemplate.update(SQL);
	}
	
	private void deleteManyImages(String campaignIDs) {
		List<String> imgURLs = getManyImgURLs(campaignIDs);
		
		for (String imgURL: imgURLs) {
			imageAPI.deleteImage(imgURL);
		}
	}
	
	private List<String> getManyImgURLs(String campaignIDs) {
		String SQL = "SELECT imgURL "
				+ "FROM campaignTbl "
				+ "WHERE campaignID IN " + campaignIDs;
		return jdbcTemplate.queryForList(SQL, String.class);
	}

}

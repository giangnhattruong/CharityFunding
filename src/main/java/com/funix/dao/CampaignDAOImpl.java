package com.funix.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.funix.model.Campaign;
import com.funix.model.CampaignFilter;

@Component
public class CampaignDAOImpl implements ICampaignDAO {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public CampaignDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void create(Campaign newCampaign) {
		String SQL = "INSERT INTO campaignTbl (title, description, "
				+ "targetAmount, location, imgURL, startDate, endDate, "
				+ "campaignStatus) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(SQL, newCampaign.getTitle(),
				newCampaign.getDescription(), newCampaign.getTargetAmount(),
				newCampaign.getLocation(), newCampaign.getImgURL(),
				newCampaign.getStartDate(), newCampaign.getEndDate(),
				newCampaign.getCampaignStatus());
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
	public void update(int campaignID, Campaign newCampaign) {
		String SQL = "UPDATE campaignTbl "
				+ "SET title = ?, description = ?, targetAmount = ?, location = ?, "
				+ "	imgURL = ?, startDate = ?, endDate = ?, campaignStatus = ? "
				+ "WHERE campaignID = ?";
		jdbcTemplate.update(SQL, newCampaign.getTitle(),
				newCampaign.getDescription(), newCampaign.getTargetAmount(),
				newCampaign.getLocation(), newCampaign.getImgURL(),
				newCampaign.getStartDate(), newCampaign.getEndDate(),
				newCampaign.getCampaignStatus(), newCampaign.getCampaignID());
	}

	@Override
	public void delete(String campaignIDs) {
		String SQL = "BEGIN TRANSACTION "
				+ "DELETE campaignTbl "
				+ "WHERE campaignID IN " + campaignIDs
				+ " COMMIT TRANSACTION";
		jdbcTemplate.update(SQL);
	}

}

//package com.funix.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import com.funix.dao.CampaignDAOImpl;
//import com.funix.dao.ICampaignDAO;
//import com.funix.service.CampaignFilterMapping;
//import com.funix.service.DonationHistoryFilterMapping;
//import com.funix.service.IFilterMapping;
//import com.funix.service.UserDonationHistoryFilterMapping;
//import com.funix.service.UserFilterMapping;
//
//@Configuration
//public class BeanConfig {
//
//	@Bean
//	public IFilterMapping getCampaignFilterMapping() {
//		return new CampaignFilterMapping();
//	}
//
//	@Bean
//	public IFilterMapping getUserFilterMapping() {
//		return new UserFilterMapping();
//	}
//
//	@Bean
//	public IFilterMapping getDonationHistoryFilterMapping() {
//		return new DonationHistoryFilterMapping();
//	}
//
//	@Bean
//	public IFilterMapping getUserDonationHistoryFilterMapping() {
//		return new UserDonationHistoryFilterMapping();
//	}
//
//	@Bean
//	public ICampaignDAO getCampaignDAO() {
//		return new CampaignDAOImpl();
//	}
//
//}

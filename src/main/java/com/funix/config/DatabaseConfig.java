package com.funix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.funix.model.CampaignFilter;

@Configuration
public class DatabaseConfig {

	@Bean
	public DriverManagerDataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setUrl("jdbc:sqlserver://localhost:1433;databaseName=CharityFundingDB;trustServerCertificate=true");
		dataSource.setUsername("truonggn");
		dataSource.setPassword("fx13372");
		
		return dataSource;
	}
	
}

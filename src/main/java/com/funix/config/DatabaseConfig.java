/*
 * DatabaseConfig.java    1.00    2022-04-05
 */

package com.funix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.funix.model.CampaignFilter;

/**
 * Database configuration.
 * @author Giang_Nhat_Truong
 *
 */
@Configuration
public class DatabaseConfig {

	/**
	 * Connect to local SQL Server and
	 * get a DataSource instance for manipulate data.
	 * @return
	 */
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

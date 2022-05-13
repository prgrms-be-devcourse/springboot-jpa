package com.example.springjpa.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {
	@Bean
	@ConfigurationProperties("spring.datasource.test")
	public DataSourceProperties testDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource dataSource(DataSourceProperties testDataSourceProperties) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(testDataSourceProperties.getDriverClassName());
		dataSource.setUrl(testDataSourceProperties.getUrl());
		dataSource.setUsername(testDataSourceProperties.getUsername());
		dataSource.setPassword(testDataSourceProperties.getPassword());

		return dataSource;
	}
}

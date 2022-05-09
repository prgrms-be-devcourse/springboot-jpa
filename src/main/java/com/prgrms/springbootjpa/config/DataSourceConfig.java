package com.prgrms.springbootjpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

  @Bean
  public DataSource dataSource() {
    // 사용할 DataSource
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setUrl("jdbc:h2:~/test");
    dataSource.setUsername("sa");
    dataSource.setPassword("");

    return dataSource;
  }
}

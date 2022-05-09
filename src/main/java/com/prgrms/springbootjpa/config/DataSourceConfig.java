package com.prgrms.springbootjpa.config;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

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

  @Bean
  public JpaVendorAdapter jpaVendorAdapter(JpaProperties jpaProperties) {
    // Hibernate 구현체를 사용하기 위한 설정
    AbstractJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    jpaVendorAdapter.setShowSql(jpaProperties.isShowSql());
    jpaVendorAdapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
    jpaVendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());
    return jpaVendorAdapter;
  }
}

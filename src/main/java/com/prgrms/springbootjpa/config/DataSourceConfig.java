package com.prgrms.springbootjpa.config;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataSourceConfig {
  /**
   * 사용할 DataSource
   * @return DataSource
   */
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setUrl("jdbc:h2:~/test");
    dataSource.setUsername("sa");
    dataSource.setPassword("");

    return dataSource;
  }

  /**
   * Hibernate 구현체를 사용하기 위한 설정
   * @return JpaVendorAdapter
   */
  @Bean
  public JpaVendorAdapter jpaVendorAdapter(JpaProperties jpaProperties) {
    AbstractJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    jpaVendorAdapter.setShowSql(jpaProperties.isShowSql());
    jpaVendorAdapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
    jpaVendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());
    return jpaVendorAdapter;
  }

  /**
   * 테이블에 매핑되는 엔티티를 관리하는 manager
   * @return LocalContainerEntityManagerFactoryBean
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                     JpaVendorAdapter jpaVendorAdapter,
                                                                     JpaProperties jpaProperties) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("com.prgrms.springbootjpa.domain");
    em.setJpaVendorAdapter(jpaVendorAdapter);

    Properties properties = new Properties();
    properties.putAll(jpaProperties.getProperties());
    em.setJpaProperties(properties);

    return em;
  }

  /**
   * 트랜잭션 관리를 위한 manage
   * @return PlatformTransactionManager
   */
  @Bean
  public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
    // 트랜잭션 관리를 위한 빈
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
    return transactionManager;
  }
}

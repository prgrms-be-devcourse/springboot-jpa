package com.study.springbootJpa.Config;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DataSourceConfig {


    @Bean
    public DataSource dataSource() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(JpaProperties jpaProperties) {
        var adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(jpaProperties.isShowSql());
        adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl());

        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
        JpaVendorAdapter jpaVendorAdapter, JpaProperties jpaProperties) {
        var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.study.springbootJpa.domain");
        em.setJpaVendorAdapter(jpaVendorAdapter);

        var properties = new Properties();
        properties.putAll(jpaProperties.getProperties());
        em.setJpaProperties(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
        LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

        return transactionManager;
    }
}

package com.example.springjpa.config;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean // 어떤 jpa 구현체를 사용할 것인지를 명시해주는 bean
    public JpaVendorAdapter jpaVendorAdapter(JpaProperties jpaProperties){
        AbstractJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(jpaProperties.isShowSql());
        jpaVendorAdapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        jpaVendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());

        return jpaVendorAdapter;
    }

    @Bean // Jpa에서 entity는 rdb의 table과 매핑되는 객체인데 entity를 관리하는 entityManager가 있고 얘를 만들어주는 애(entityManagerFactory)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                           JpaVendorAdapter jpaVendorAdapter,
                                                                           JpaProperties jpaProperties){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.springjpa.domain", "com.example.springjpa.inherit");
        em.setJpaVendorAdapter(jpaVendorAdapter);

        Properties properties = new Properties();
        properties.putAll(jpaProperties.getProperties());
        em.setJpaProperties(properties);
        return em;
    }

    @Bean // transaction을 관리하는 Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());

        return transactionManager;
    }
}

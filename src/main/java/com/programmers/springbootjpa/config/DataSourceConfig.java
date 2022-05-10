package com.programmers.springbootjpa.config;

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
     * 어떤 dataSource를 쓸지 설정
     * @return
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
     * JPA라는 인터페이스를 구현한 여러 구현체들이 ORM frameworks
     * 그중 Hibernate를 가장 많이 씀
     * 어떤 구현체를 쓸지 결정 -> VendorAdapter
     * @param jpaProperties
     * @return
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter(JpaProperties jpaProperties) {
        AbstractJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(jpaProperties.isShowSql());
        jpaVendorAdapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        jpaVendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());

        return jpaVendorAdapter;
    }

    // jpa에서 테이블과 매핑관 entity를 관리 -> EntityManager
    // EntityManager를 만들어내는 Bean -> EntityManagerFactory

    /**
     * jpa에서 테이블과 매핑할 entity를 관리 -> EntityManager
     * EntityManager를 생성/관리 Bean -> EntityManagerFactory
     * @param dataSource
     * @param jpaVendorAdapter
     * @param jpaProperties
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter, JpaProperties jpaProperties) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        // 테이블과 매핑될 entity들을 어떤 패키지에 두고 관리할지 명시
        em.setPackagesToScan("com.programmers.springbootjpa.domain");
        em.setJpaVendorAdapter(jpaVendorAdapter);

        Properties properties = new Properties();
        properties.putAll(jpaProperties.getProperties());
        em.setJpaProperties(properties);

        return em;
    }

    /**
     * RDB의 트랜잭션을 관리하는 Bean
     * @Transactional에 대해 동작
     * @param entityManagerFactory
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

        return transactionManager;
    }
}

package com.example.mission2.config;

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

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/order-test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    // 어떤 구현체를 쓸지 설정
    // jpa 구현체들 중에 hibernate를 가장 많이 사용한다.
    @Bean
    public JpaVendorAdapter jpaVendorAdapter(JpaProperties jpaProperties) { //yaml을 통해 설정한 JpaProperties Bean에 주입됨
        AbstractJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(jpaProperties.isShowSql()); //
        jpaVendorAdapter.setDatabasePlatform(jpaProperties.getDatabasePlatform()); //
        jpaVendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());

        return jpaVendorAdapter;
    }

    // entity manager factory - 테이블과 매핑될 entity를 관리
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter,
                                                                       JpaProperties jpaProperties) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource); // 어떤 데이터 베이스를 쓸것인지
        em.setPackagesToScan("com.example.kdtjpaorder.domain"); // entity를 어떤 패키지에 두고 관리할 것인지.
        em.setJpaVendorAdapter(jpaVendorAdapter); // 위에 명시한 hibernate adapter를 사용할 것이다.

        // 추가 hibernate 튜닝
        Properties properties = new Properties();
        properties.putAll(jpaProperties.getProperties());
        em.setJpaProperties(properties);

        return em;
    }


    // transaction manger - RDB의 트랜잭션 관리
    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

        return transactionManager;
    }

}

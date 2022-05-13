package com.will.jpapractice.config;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
@EnableJpaRepositories(basePackages = "com.will.jpapractice.domain")
public class DataSourceConfig {

    // JPA 를 사용하기 위해 크게 4가지의 Bean 설정이 필요함.
    // Auto Configuration 을 사용하면 내부 적으로 아래의 빈을 자동으로 등록을 해준다.
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
     * @JpaVendorAdapter : JPA는 여러 구현체가 존재하기 때문에 구현체별 설정을 지원하기 위한 클래스이다.
     * Hibernate를 사용하기 때문에 HibernateJpaVendorAdapter를 사용한다.
     * @param jpaProperties
     * @return
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter(JpaProperties jpaProperties) {
        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(jpaProperties.isShowSql());
        adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl());

        return adapter;
    }


    /**
     * @LocalContainerEntityManagerFactoryBean :
     * EntityManagerFactoryBean을 Spring에서 사용하기 위한 클래스
     * 테이블과 매핑되는 엔티티를 관리해 줌.
     * @param dataSource
     * @param jpaVendorAdapter
     * @param jpaProperties
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter,
                                                                       JpaProperties jpaProperties) {
        LocalContainerEntityManagerFactoryBean entityManager
                = new LocalContainerEntityManagerFactoryBean();

        // 어떤 dataSource를 쓸 것인지 명시
        entityManager.setDataSource(dataSource);

        // 어떤 패키지의 엔티티를 관리를 해줄 것인지 명시
        entityManager.setPackagesToScan("com.will.jpapractice.domain");

        // 하이버네이트 구현체를 사용한다고 명시
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);

        // application.yaml에 있는 하이버네이트 튜닝 설정값을 엔티티메니저팩토리 빈에 설정.
        Properties properties = new Properties();
        properties.putAll(jpaProperties.getProperties());
        entityManager.setJpaProperties(properties);

        return entityManager;
    }


    /**
    * RDB 트랙잭션 관리해줌.
    * 트랙잭션 관리해주기 위한 Bean
    */
    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

        return transactionManager;
    }

}

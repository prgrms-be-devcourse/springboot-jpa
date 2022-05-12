package com.kdt.lecture.configuration;

import javax.sql.DataSource;
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

@Configuration
@EnableJpaRepositories(basePackages = "com.kdt.lecture")
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() { // 늘 하던 DataSource 설정
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(
        JpaProperties jpaProperties) { // AdapterPattern을 이용해 Jpa의 구현채를 어떤 것으로 할 지 설정
        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter(); // 하이버네이트 어댑터 사용
        adapter.setShowSql(jpaProperties.isShowSql()); // 로그에 SQL문을 보여줄지 말지를 설정한다.
        adapter.setDatabasePlatform(
            jpaProperties.getDatabasePlatform()); // 작업할 대상 데이터베이스의 이름을 지정한다.
        adapter.setGenerateDdl(
            jpaProperties.isGenerateDdl()); // EntityMangerFactory가 초기화 된 후에 DDL을 할지 말지 설정한다.
        return adapter; // JPA에서 쓸 어댑터 설정 완료
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
        JpaVendorAdapter jpaVendorAdapter, JpaProperties jpaProperties) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean(); //말그대로 LCEMB이다.
        em.setDataSource(dataSource); // 데이타소스 주입
        em.setPackagesToScan("com.kdt.lecture"); // 패키지 스캔
        em.setJpaVendorAdapter(jpaVendorAdapter); // 어댑터 설정
        return em; // 엔티티매니저 반환
    }

    @Bean
    public PlatformTransactionManager transactionManager(
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(); // 트랜잭션 매니저 설정
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
        return transactionManager;
    }
}

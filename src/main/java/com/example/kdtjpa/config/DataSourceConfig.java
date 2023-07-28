package com.example.kdtjpa.config;

import java.util.Properties;

import javax.sql.DataSource;

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

@Configuration
public class DataSourceConfig {

	// datasource에 대한 bean 설정
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:~/test");
		dataSource.setUsername("sa");
		dataSource.setPassword("");

		return dataSource;
	}

	// vendorAdapter : JPA의 어떤 구현체를 쓸지 설정
	@Bean
	public JpaVendorAdapter jpaVendorAdapter(JpaProperties jpaProperties) {
		AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter(); // Hiberante 구현체 사용

		//application.yml 에서 설정한 jpa 설정들이 자동으로 jpaProperties 에 주입된다.
		// 따라서 jpaProperties 를 이용해서 Adapter 에 설정을 해주는 것
		adapter.setShowSql(jpaProperties.isShowSql());
		adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
		adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
		return adapter;
	}

	// EntityManager : RDB 테이블과 매핑된 자바 Entity 를 관리
	// EntityManagerFactoryBean 이 EntityManager 를 생산하고 관리함
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter,
		JpaProperties jpaProperties) {
		LocalContainerEntityManagerFactoryBean em
			= new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource); // 어떤 datasource 쓸지 명시
		em.setPackagesToScan("com.example.kdtjpa.domain"); // 테이블과 매핑된 entity를 어떤 패키지에 두고 관리할 것인지
		em.setJpaVendorAdapter(jpaVendorAdapter); // jpa 구현체 설정(Hibernat 사용)

		// application.yml의 추가적인 Hibernat 설정들까지 반영
		Properties properties = new Properties();
		properties.putAll(jpaProperties.getProperties());
		em.setJpaProperties(properties);

		return em;
	}

	// @Transactionl 에 대해 동작하는 설정 , 해당 애노테이션은 메소드가 시작할 때 트랜잭션 열고 끝날 때 커밋
	// 그 사이에 EntityManger에 의해 Entity의 dirty checking이라던가 변경점을 감지하는 Transaction을 관리하기 위한 Bean
	@Bean
	public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

		return transactionManager;
	}
}
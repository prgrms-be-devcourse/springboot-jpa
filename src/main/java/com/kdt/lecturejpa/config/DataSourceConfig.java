package com.kdt.lecturejpa.config;

import java.util.Properties;

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
@EnableJpaRepositories(basePackages = "com.kdt.lecturejpa.domain")
public class DataSourceConfig {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:~/test");
		dataSource.setUsername("sa");
		dataSource.setPassword("");

		return dataSource;
	}

	@Bean // 구현체 결정 빈
	public JpaVendorAdapter jpaVendorAdapter(JpaProperties jpaProperties) {
		AbstractJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setShowSql(jpaProperties.isShowSql());
		jpaVendorAdapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
		jpaVendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());

		return jpaVendorAdapter;
	}

	@Bean // 엔티티 생성 관리 하는 빈
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter,
		JpaProperties jpaProperties) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

		em.setDataSource(dataSource);
		em.setPackagesToScan("com.kdt.lecturejpa.domain");
		em.setJpaVendorAdapter(jpaVendorAdapter);

		Properties properties = new Properties();
		properties.putAll(jpaProperties.getProperties());
		em.setJpaProperties(properties);

		return em;
	}

	@Bean // 트랜잭션을 관리하는 빈
	public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

		return transactionManager;
	}
}

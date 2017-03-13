package com.beanframework.multitenant.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.beanframework.multitenant.datasource.TenantDataSourceMap;
import com.beanframework.multitenant.datasource.TenantRoutingDatasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
		"com.beanframework.multitenant" }, entityManagerFactoryRef = "multitenantEntityManagerFactory", transactionManagerRef = "multitenantTransactionManager")
public class TenantPersistenceConfig {
	@Bean
	public LocalContainerEntityManagerFactoryBean multitenantEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(locationDataSource());
		em.setPackagesToScan("com.beanframework.multitenant");
		em.setPersistenceUnitName("multitenant_pu");

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());

		return em;
	}

	@Autowired
	private TenantDataSourceMap locationDataSourceMap;

	@Bean
	public DataSource locationDataSource() {
		TenantRoutingDatasource ds = new TenantRoutingDatasource();
		ds.setTargetDataSources(locationDataSourceMap);
		return ds;
	}

	@Bean
	public PlatformTransactionManager multitenantTransactionManager(
			@Qualifier("multitenantEntityManagerFactory") EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor locationExceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("hibernate.show_sql", "true");
		return properties;
	}
}

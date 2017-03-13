package com.beanframework.platform.config;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
		"com.beanframework" }, entityManagerFactoryRef = "masterEntityManagerFactory", transactionManagerRef = "masterTransactionManager")
public class PlatformPersistenceConfig {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Environment env;

	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(tenantDataSource());
		em.setPackagesToScan("com.beanframework");
		em.setPersistenceUnitName("master_pu");

		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setJpaProperties(additionalProperties());

		return em;
	}

	@Primary
	@Bean
	public DataSource tenantDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("master.tenant.driver"));
		dataSource.setUrl(env.getProperty("master.tenant.url"));
		dataSource.setUsername(env.getProperty("master.tenant.username"));
		dataSource.setPassword(env.getProperty("master.tenant.password"));

		Properties connectionProperties = new Properties();
		connectionProperties.setProperty("defaultAutoCommit", env.getProperty("master.defaultAutoCommit"));
		connectionProperties.setProperty("maxActive", env.getProperty("master.maxActive"));
		connectionProperties.setProperty("maxIdle", env.getProperty("master.maxIdle"));
		connectionProperties.setProperty("maxWait", env.getProperty("master.maxWait"));
		connectionProperties.setProperty("minIdle", env.getProperty("master.minIdle"));
		connectionProperties.setProperty("testOnBorrow", env.getProperty("master.testOnBorrow"));
		connectionProperties.setProperty("testWhileIdle", env.getProperty("master.testWhileIdle"));
		connectionProperties.setProperty("validationQuery", env.getProperty("master.validationQuery"));

		dataSource.setConnectionProperties(connectionProperties);

		if (env.getProperty("master.tenant.import.enabled", Boolean.class, true)) {
			// schema init
			try {
				PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();

				Resource[] initSql = loader.getResources(env.getProperty("master.tenant.import"));

				ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
				for (Resource resource : initSql) {
					resourceDatabasePopulator.addScript(resource);
				}

				DatabasePopulator databasePopulator = resourceDatabasePopulator;
				DatabasePopulatorUtils.execute(databasePopulator, dataSource);
			} catch (DataAccessException e) {
				logger.warn(e.getMessage());
			} catch (IOException e) {
				logger.warn(e.getMessage());
			}
		}

		return dataSource;
	}

	@Primary
	@Bean
	public PlatformTransactionManager masterTransactionManager(
			@Qualifier("masterEntityManagerFactory") EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	@Primary
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", env.getProperty("master.hibernate.dialect"));
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("master.hibernate.hbm2ddl.auto"));
		return properties;
	}
}

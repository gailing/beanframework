package com.beanframework.cronjob.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.context.AutowiringSpringBeanJobFactory;
import com.beanframework.cronjob.listener.CronjobGlobalListener;

@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class CronJobConfig {

	@Autowired
	private ApplicationContext applicationContext;

	@Value(CronjobConstants.QUARTZ_PROPERTIES_CLASSPATH)
	private String quartzPropertiesClasspath;

	@Bean
	public Properties quartzProperties() throws Exception {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource(quartzPropertiesClasspath));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	@Bean
	public CronjobGlobalListener globalJobListener() {
		return new CronjobGlobalListener();
	}

	@Bean
	public SchedulerFactoryBean schedulerFactory() throws Exception {
		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		schedulerFactory.setQuartzProperties(quartzProperties());

		// Custom job factory of spring with DI support for @Autowired
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		schedulerFactory.setJobFactory(jobFactory);
		schedulerFactory.setGlobalJobListeners(globalJobListener());

		return schedulerFactory;
	}
}

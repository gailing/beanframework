package com.beanframework.cronjob.init;

import com.beanframework.cronjob.service.CronjobService;
import com.beanframework.cronjob.utils.CronjobManager;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CronjobInit implements ApplicationListener<ApplicationReadyEvent> {


	@Autowired
	private CronjobManager cronjobManager;
	
	@Autowired
	private CronjobService cronJobService;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		try {
			logger.info("Clear all cronjob scheduler instances.");
			cronjobManager.clearAllScheduler();
			logger.info("Initialize cronjob based on job exists in database.");
			cronJobService.initCronJob();
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}

}

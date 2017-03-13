package com.beanframework.cronjob.listener;

import java.util.UUID;

import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.service.CronjobService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CronjobGlobalListener implements JobListener {

	public static final String LISTENER_NAME = "quartJobSchedulingListener";

	@Override
	public String getName() {
		return LISTENER_NAME;
	}

	@Autowired
	public CronjobService cronjobService;

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();

		UUID uuid = UUID.fromString((String) dataMap.get(Cronjob.class.getName()));
		cronjobService.updateStatus(uuid, Cronjob.Status.RUNNING, "Started Successfully");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();

		UUID uuid = UUID.fromString((String) dataMap.get(Cronjob.class.getName()));
		cronjobService.updateStatus(uuid, Cronjob.Status.ERROR, "jobExecutionVetoed");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();

		UUID uuid = UUID.fromString((String) dataMap.get(Cronjob.class.getName()));

		Cronjob cronjob = cronjobService.getCronJob(uuid);

		if (jobException != null) {
			cronjobService.updateStatus(uuid, Cronjob.Status.ERROR, jobException.getMessage());
		} else if (cronjob.getJobTrigger().equals(Cronjob.JobTrigger.RUN_ONCE)) {
			
			if(context.getResult() != null){
				cronjobService.updateStatus(uuid, Cronjob.Status.FINISHED, context.getResult().toString());
			}
			else{
				cronjobService.updateStatus(uuid, Cronjob.Status.FINISHED, null);
			}
		} else if (cronjob.getJobTrigger().equals(Cronjob.JobTrigger.START)) {
			
			if(context.getResult() != null){
				cronjobService.updateStatus(uuid, Cronjob.Status.RUNNING, context.getResult().toString());
			}
			else{
				cronjobService.updateStatus(uuid, Cronjob.Status.RUNNING, null);
			}
		}

	}
}

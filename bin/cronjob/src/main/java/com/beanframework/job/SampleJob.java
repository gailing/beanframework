package com.beanframework.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class SampleJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		context.setResult("This is sample job output.");
	}
}

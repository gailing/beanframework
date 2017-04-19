package com.beanframework.email.job;

import com.beanframework.email.service.EmailService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class EmailJob implements Job{
	
	@Autowired
	private EmailService emailService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String result = emailService.processAllEmails();
		context.setResult(result);
	}
}

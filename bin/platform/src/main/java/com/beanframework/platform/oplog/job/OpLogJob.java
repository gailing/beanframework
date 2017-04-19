package com.beanframework.platform.oplog.job;

import com.beanframework.platform.oplog.service.OpLogService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class OpLogJob implements Job {

	@Autowired
	private OpLogService logService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		int deletedLogs = logService.deleteOldLog();
		context.setResult("Cleaned "+deletedLogs+" log records");
	}
}

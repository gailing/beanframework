package com.beanframework.cronjob.utils;

import org.apache.commons.lang3.StringUtils;
import com.beanframework.cronjob.JobGroupMissingException;
import com.beanframework.cronjob.JobNameInGroupDuplicatedException;
import com.beanframework.cronjob.JobNameMissingException;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.service.CronjobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CronjobValidateConverter {

	@Autowired
	private CronjobService cronjobService;

	public void save(Cronjob cronjob) {

		if (cronjob.getUuid() == null) {
			if (StringUtils.isEmpty(cronjob.getNewJobGroup())) {
				throw new JobGroupMissingException("Job Group is required");
			}

			if (StringUtils.isEmpty(cronjob.getNewJobName())) {
				throw new JobNameMissingException("Job Name is required");
			}
		}

		// New email given
		if ((StringUtils.isNotEmpty(cronjob.getNewJobGroup()) && !cronjob.getNewJobGroup().equals(cronjob.getJobGroup()))
				|| (StringUtils.isNotEmpty(cronjob.getNewJobName())&& !cronjob.getNewJobName().equals(cronjob.getJobName()))) {

			if (cronjobService.isGroupAndNameExists(cronjob.getJobGroup(), cronjob.getJobName())) {
				throw new JobNameInGroupDuplicatedException("Job Name is existing in job group.");
			} else {
				cronjob.setJobGroup(cronjob.getNewJobGroup());
				cronjob.setJobName(cronjob.getNewJobName());
			}
		}

	}
}

package com.beanframework.cronjob.domain;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class CronjobSearchCriteria implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String PARAM_ALL = "all";
	public static final String PARAM_JOB_GROUP = "jobGroup";
	public static final String PARAM_JOB_NAME = "jobName";
	public static final String PARAM_DESCRIPTION = "description";

	private String jobName;
	private String jobGroup;
	private String description;

	public CronjobSearchCriteria() {
	}

	public CronjobSearchCriteria(String query, String keyword) {
		if (StringUtils.isNotEmpty(query) && StringUtils.isNotEmpty(keyword)) {
			if (query.equals(PARAM_ALL) || query.equals(PARAM_JOB_GROUP)) {
				this.jobGroup = keyword;
			}
			if (query.equals(PARAM_ALL) || query.equals(PARAM_JOB_NAME)) {
				this.jobName = keyword;
			}
		}
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
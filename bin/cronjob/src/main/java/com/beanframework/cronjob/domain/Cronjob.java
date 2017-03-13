package com.beanframework.cronjob.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.platform.core.base.BaseDomain;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = CronjobConstants.TABLE_CRONJOB)
public class Cronjob extends BaseDomain {
	
	public static final String MODEL = "cronjob";

	public enum JobTrigger {
		STOP("Stop"), START("Start"), RUN_ONCE("Run Once");

		private String value;

		JobTrigger(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static JobTrigger fromName(String name) {
			if (START.name().equalsIgnoreCase(name)) {
				return START;
			} else if (STOP.name().equalsIgnoreCase(name)) {
				return STOP;
			} else if (RUN_ONCE.name().equalsIgnoreCase(name)) {
				return RUN_ONCE;
			} else {
				return null;
			}
		}

		@Override
		public String toString() {
			return this.getValue();
		}
	}

	public enum Status {
		RUNNING("Running"), FINISHED("Finished"), STOPPED("Stopped"), ERROR("Error");

		private String value;

		Status(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Status fromName(String name) {
			if (RUNNING.name().equalsIgnoreCase(name)) {
				return RUNNING;
			} else if (FINISHED.name().equalsIgnoreCase(name)) {
				return FINISHED;
			} else if (STOPPED.name().equalsIgnoreCase(name)) {
				return STOPPED;
			} else if (ERROR.name().equalsIgnoreCase(name)) {
				return ERROR;
			} else {
				return null;
			}
		}

		@Override
		public String toString() {
			return this.getValue();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8939913795649500178L;
	public static final String JOB_NAME = "jobName";
	public static final String JOB_GROUP = "jobGroup";
	public static final String DESCRIPTION = "description";

	private String jobClass;

	/** Task Group */
	private String jobGroup;

	/** Task Name */
	private String jobName;

	/** Task run time expression */
	private String cronExpression;
	
	private Date startDate;

	private boolean startup;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Enumerated(EnumType.STRING)
	private JobTrigger jobTrigger;

	private Date runDate;

	private String description;

	@Lob
	@Column(length = 100000)
	private String result;
	
	@Transient
	private String newJobGroup;

	@Transient
	private String newJobName;

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isStartup() {
		return startup;
	}

	public void setStartup(boolean startup) {
		this.startup = startup;
	}

	public JobTrigger getJobTrigger() {
		return jobTrigger;
	}

	public void setJobTrigger(JobTrigger jobTrigger) {
		this.jobTrigger = jobTrigger;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getRunDate() {
		return runDate;
	}

	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getNewJobGroup() {
		return newJobGroup;
	}

	public void setNewJobGroup(String newJobGroup) {
		this.newJobGroup = newJobGroup;
	}

	public String getNewJobName() {
		return newJobName;
	}

	public void setNewJobName(String newJobName) {
		this.newJobName = newJobName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}

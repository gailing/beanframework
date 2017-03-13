package com.beanframework.platform.oplog.domain;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class OpLogSearchCriteria implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String PARAM_ALL = "all";
	public static final String PARAM_CREATED_DATE = "createdDate";
	public static final String PARAM_CREATED_BY = "createdBy";
	public static final String PARAM_IP = "ip";
	public static final String PARAM_CHANNEL = "channel";
	public static final String PARAM_OPERATE = "operate";
	public static final String PARAM_RESULT = "result";
	
	private Date createdDateFrom;
	private Date createdDateTo;
	private String createdBy;
	private String ip;
	private String channel;
	private String operate;
	private String result;

	public OpLogSearchCriteria() {
	}

	public OpLogSearchCriteria(String query, String keyword, Date dateFrom, Date dateTo) throws ParseException {
		
		if (dateFrom != null) {
			this.createdDateFrom = dateFrom;
		}
		
		if (dateTo != null) {
			this.createdDateTo = dateTo;
		}
		
		if (StringUtils.isNotEmpty(query) && keyword != null) {

			if (query.equals(PARAM_ALL) || query.equals(PARAM_CREATED_BY)) {
				this.createdBy = keyword;
			}
			if (query.equals(PARAM_ALL) || query.equals(PARAM_IP)) {
				this.ip = keyword;
			}
			if (query.equals(PARAM_ALL) || query.equals(PARAM_CHANNEL)) {
				this.channel = keyword;
			}
			if (query.equals(PARAM_ALL) || query.equals(PARAM_OPERATE)) {
				this.operate = keyword;
			}
			if (query.equals(PARAM_ALL) || query.equals(PARAM_RESULT)) {
				this.result = keyword;
			}

		}
	}

	public Date getCreatedDateFrom() {
		return createdDateFrom;
	}

	public void setCreatedDateFrom(Date createdDateFrom) {
		this.createdDateFrom = createdDateFrom;
	}

	public Date getCreatedDateTo() {
		return createdDateTo;
	}

	public void setCreatedDateTo(Date createdDateTo) {
		this.createdDateTo = createdDateTo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
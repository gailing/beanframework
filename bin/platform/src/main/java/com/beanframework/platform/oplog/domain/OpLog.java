package com.beanframework.platform.oplog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.beanframework.platform.core.base.BaseDomain;
import com.beanframework.platform.oplog.OpLogConstants;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = OpLogConstants.TABLE_LOG)
public class OpLog extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3387103539059426180L;
	public static final String MODEL = "OpLog";
	public static final String IP = "ip";
	public static final String CHANNEL = "channel";
	public static final String OPERATE = "operate";
	public static final String RESULT = "result";

	private String ip;

	private String channel;

	private String operate;

	private String result;

	@Lob
	@Column(length = 100000)
	private String exception;

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

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

}

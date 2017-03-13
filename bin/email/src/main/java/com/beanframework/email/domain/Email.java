package com.beanframework.email.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.beanframework.email.EmailConstant;
import com.beanframework.platform.core.base.BaseDomain;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = EmailConstant.TABLE_EMAIL)
public class Email extends BaseDomain {

	public enum Status {
		PENDING, PROCESSING, SENT, FAILED;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3387103539059426180L;

	private String toRecipients;

	private String bccRecipients;

	private String subject;

	@Lob
	@Column(length = 100000)
	private String content;

	@Enumerated(EnumType.STRING)
	private Status status;

	public String getToRecipients() {
		return toRecipients;
	}

	public void setToRecipients(String to) {
		this.toRecipients = to;
	}

	public String getBccRecipients() {
		return bccRecipients;
	}

	public void setBccRecipients(String bcc) {
		this.bccRecipients = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}

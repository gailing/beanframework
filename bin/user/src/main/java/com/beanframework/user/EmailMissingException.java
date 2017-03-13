package com.beanframework.user;

public class EmailMissingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailMissingException(String msg) {
		super(msg);
	}

	public EmailMissingException(String msg, Throwable t) {
		super(msg, t);
	}
}
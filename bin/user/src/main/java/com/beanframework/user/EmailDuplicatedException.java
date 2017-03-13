package com.beanframework.user;

public class EmailDuplicatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailDuplicatedException(String msg) {
		super(msg);
	}

	public EmailDuplicatedException(String msg, Throwable t) {
		super(msg, t);
	}
}
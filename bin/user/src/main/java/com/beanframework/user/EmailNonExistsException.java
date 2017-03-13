package com.beanframework.user;

public class EmailNonExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailNonExistsException(String msg) {
		super(msg);
	}

	public EmailNonExistsException(String msg, Throwable t) {
		super(msg, t);
	}
}
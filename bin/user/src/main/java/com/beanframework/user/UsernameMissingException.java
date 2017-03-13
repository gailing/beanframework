package com.beanframework.user;

public class UsernameMissingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsernameMissingException(String msg) {
		super(msg);
	}

	public UsernameMissingException(String msg, Throwable t) {
		super(msg, t);
	}
}
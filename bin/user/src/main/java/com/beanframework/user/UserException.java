package com.beanframework.user;

public class UserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserException(String msg) {
		super(msg);
	}

	public UserException(String msg, Throwable t) {
		super(msg, t);
	}
}
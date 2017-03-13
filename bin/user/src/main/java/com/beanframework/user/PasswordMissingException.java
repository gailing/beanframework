package com.beanframework.user;

public class PasswordMissingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordMissingException(String msg) {
		super(msg);
	}

	public PasswordMissingException(String msg, Throwable t) {
		super(msg, t);
	}
}
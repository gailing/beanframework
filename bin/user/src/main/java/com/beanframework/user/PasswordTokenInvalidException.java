package com.beanframework.user;

public class PasswordTokenInvalidException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordTokenInvalidException(String msg) {
		super(msg);
	}

	public PasswordTokenInvalidException(String msg, Throwable t) {
		super(msg, t);
	}
}
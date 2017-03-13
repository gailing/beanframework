package com.beanframework.user;

public class PasswordTokenExpiredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordTokenExpiredException(String msg) {
		super(msg);
	}

	public PasswordTokenExpiredException(String msg, Throwable t) {
		super(msg, t);
	}
}
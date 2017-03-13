package com.beanframework.user;

public class UsernameDuplicatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsernameDuplicatedException(String msg) {
		super(msg);
	}

	public UsernameDuplicatedException(String msg, Throwable t) {
		super(msg, t);
	}
}
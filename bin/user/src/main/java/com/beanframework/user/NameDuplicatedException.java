package com.beanframework.user;

public class NameDuplicatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameDuplicatedException(String msg) {
		super(msg);
	}

	public NameDuplicatedException(String msg, Throwable t) {
		super(msg, t);
	}
}
package com.beanframework.menu;

public class NameDuplicatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2803899934958926823L;

	public NameDuplicatedException(String msg) {
		super(msg);
	}

	public NameDuplicatedException(String msg, Throwable t) {
		super(msg, t);
	}
}
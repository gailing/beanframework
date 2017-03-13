package com.beanframework.platform.core;

import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * A sub-class of {@link SimpleMappingExceptionResolver} that can be turned on
 * and off for demonstration purposes (you wouldn't do this in a real
 * application).
 */
public class DatabaseException extends RuntimeException {

	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = 252811214506177218L;

	public DatabaseException(String msg) {
		super(msg);
	}

	public DatabaseException(String msg, Throwable t) {
		super(msg, t);
	}
}
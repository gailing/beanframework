package com.beanframework.platform.core.base;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseFacade {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void validateEmpty(String str, String property) throws Exception {
		if (StringUtils.isEmpty(str)) {
			throw new Exception(property + " must not be empty");
		}
	}

	public void validateEmpty(Object obj, String entity) throws Exception {
		if (obj == null) {
			throw new Exception(entity + " must not be empty");
		}
	}

	public void validateEmpty(UUID uuid) throws Exception {
		if (uuid == null) {
			throw new Exception("uuid must not be empty");
		}
	}
	
	public static boolean parseBoolean(String s) {
		return ((s != null) && (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("1") || s.equalsIgnoreCase("yes")));
	}

}

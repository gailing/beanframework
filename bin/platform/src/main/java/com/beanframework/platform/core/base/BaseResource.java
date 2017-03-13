package com.beanframework.platform.core.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

public class BaseResource {
	
protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected UrlPathHelper urlPathHelper = new UrlPathHelper();

}

package com.beanframework.platform.core.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

/**
 * 
 * BaseInterceptor
 *
 */
public abstract class BaseInterceptor extends HandlerInterceptorAdapter {
    protected UrlPathHelper urlPathHelper = new UrlPathHelper();
    protected final Logger logger = LoggerFactory.getLogger(getClass());
}

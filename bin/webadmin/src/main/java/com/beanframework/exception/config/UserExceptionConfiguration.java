package com.beanframework.exception.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Configuration
public class UserExceptionConfiguration {

	protected Logger logger;

	public UserExceptionConfiguration() {
		logger = LoggerFactory.getLogger(getClass());
		logger.info("Creating ExceptionConfiguration");
	}

	/**
	 * Setup the classic SimpleMappingExceptionResolver. This provides useful
	 * defaults for logging and handling exceptions. It has been part of Spring
	 * MVC since Spring V2 and you will probably find most existing Spring MVC
	 * applications are using it.
	 * <p>
	 * 
	 * @return The new resolver
	 */
	@Bean(name = "userSimpleMappingExceptionResolver")
	public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
		logger.info("Creating userSimpleMappingExceptionResolver");
		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();

		Properties mappings = new Properties();
		mappings.setProperty("InvalidCreditCardException", "creditCardError");

		r.setExceptionMappings(mappings); // None by default
		r.setExceptionAttribute("ex"); // Default is "exception"
		r.setWarnLogCategory("com.beanframework.sys.user.ExceptionLogger"); // No
																			// default
		r.setDefaultErrorView("defaultErrorPage");
		return r;
	}
}
package com.beanframework.cache.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.ehcache.CacheManager;

@Component
public class CacheInit implements InitializingBean {
	
	@Autowired
	private CacheManager cacheManager;

	@Override
	public void afterPropertiesSet() throws Exception {
		cacheManager.clearAll();
	}

}
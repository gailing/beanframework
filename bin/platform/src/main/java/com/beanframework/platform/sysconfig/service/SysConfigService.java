package com.beanframework.platform.sysconfig.service;

import com.beanframework.platform.core.base.BaseService;
import com.beanframework.platform.sysconfig.SysConfigConstants;
import com.beanframework.platform.sysconfig.domain.SysConfig;
import com.beanframework.platform.sysconfig.domain.SysConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = SysConfigConstants.CACHE_SYSCONFIGS)
public class SysConfigService extends BaseService {

	@Autowired
	private SysConfigRepository configRepository;

	@Transactional(readOnly = false)
	public SysConfig save(SysConfig sysConfig) {
		return configRepository.save(sysConfig);
	}

	public SysConfig getByName(String name) {
		return configRepository.findByName(name);
	}

//	/**
//	 * 
//	 * @param property Key
//	 * @param value Default value
//	 * @return Default value if property do not exists
//	 */
//	public SysConfig getByName(String name, String value) {
//		SysConfig sysConfig = configRepository.findByName(name);
//
//		if (sysConfig == null) {
//			sysConfig = new SysConfig();
//			sysConfig.setName(name);
//			sysConfig.setValue(value);
//			return save(sysConfig);
//		} else {
//			return sysConfig;
//		}
//	}

}

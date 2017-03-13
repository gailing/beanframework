package com.beanframework.multitenant.platform.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LocationService {
	
	@Autowired
	private LocationRepository locationRepository;

	@Transactional(readOnly = false)
	public void init() {
//		locationRepository.executeNativeQuery("create table IF NOT EXISTS test (id varchar(3) not null, name varchar(255) not null,constraint PK_location primary key (id))");
//		locationRepository.executeNativeQuery();
	}

	public Iterable<Location> getLocations() {
		return locationRepository.findAll();
	}
}

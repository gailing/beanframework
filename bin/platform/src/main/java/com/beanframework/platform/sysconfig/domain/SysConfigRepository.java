package com.beanframework.platform.sysconfig.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysConfigRepository extends CrudRepository<SysConfig, UUID>{
	
	@Query(value = "select s from SysConfig s where s.name = ?1")
	SysConfig findByName(String name);

}

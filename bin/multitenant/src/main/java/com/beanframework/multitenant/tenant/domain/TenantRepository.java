package com.beanframework.multitenant.tenant.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends CrudRepository<Tenant, String> {
	
	@Query(value = "select t from Tenant t where t.id = :id")
	Tenant findById(@Param("id") String id);

}

package com.beanframework.accountpolicy.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountpolicyRepository extends JpaRepository<Accountpolicy, UUID>, JpaSpecificationExecutor<Accountpolicy> {
	
	@Query("select count(a) > 0 from Accountpolicy a where a.uuid = :uuid")
	public boolean isAccountpolicyExists(@Param("uuid") UUID uuid);
	
	@Query("select count(a) > 0 from Accountpolicy a where a.name = :name")
	public boolean isNameExists(@Param("name") String name);
	
	@Query(value = "select a from Accountpolicy a where a.name = ?1")
	Accountpolicy findByName(String name);

	@Modifying
	@Query("delete from Accountpolicy a where a.name = :name")
	void deleteByName(@Param("name") String name);

}
package com.beanframework.user.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>, JpaSpecificationExecutor<Role> {
	
	@Query("select count(r) > 0 from Role r where r.uuid = :uuid")
	public boolean isRoleExists(@Param("uuid") UUID uuid);
	
	@Query("select count(r) > 0 from Role r where r.name = :name")
	public boolean isNameExists(@Param("name") String name);
	
	@Query(value = "select r from Role r where r.name = ?1")
	Role findByName(String name);

	@Modifying
	@Query("delete from Role r where r.name = :name")
	void deleteByName(@Param("name") String name);
}
package com.beanframework.user.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID>, JpaSpecificationExecutor<Group> {
	
	@Query("select count(g) > 0 from Group g where g.uuid = :uuid")
	public boolean isGroupExists(@Param("uuid") UUID uuid);
	
	@Query("select count(g) > 0 from Group g where g.name = :name")
	public boolean isNameExists(@Param("name") String name);
	
	@Query(value = "select g from Group g where g.name = ?1")
	Group findByName(String name);

	@Modifying
	@Query("delete from Group g where g.name = :name")
	void deleteByName(@Param("name") String name);

}
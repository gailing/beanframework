package com.beanframework.user.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID>, JpaSpecificationExecutor<Permission> {

	@Query(value = "select p from Permission p where p.name = ?1")
	Optional<Permission> findByName(String name);

	@Modifying
	@Query("delete from Permission p where p.name = :name")
	void deleteByName(@Param("name") String name);

	@Query("select p from Permission p where p.parent is null order by sort asc")
	List<Permission> findAllParent();
}
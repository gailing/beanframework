package com.beanframework.menu.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID>, JpaSpecificationExecutor<Menu> {
	
	@Query("select count(m) > 0 from Menu m where m.uuid = :uuid")
	public boolean isMenuExists(@Param("uuid") UUID uuid);
	
	@Query("select count(m) > 0 from Menu m where m.name = :name")
	public boolean isNameExists(@Param("name") String name);
	
	@Query("select count(m) > 0 from Menu m where m.path = :path")
	public boolean isPathExists(@Param("path") String path);

	@Query(value = "select m from Menu m where m.name = ?1")
	Menu findByName(String name);
	
	@Query(value = "select m from Menu m where m.path = ?1")
	Menu findByPath(String path);

	@Modifying
	@Query("delete from Menu m where m.name = :name")
	void deleteByName(@Param("name") String name);
	
	@Query("select m from Menu m where m.parent is null")
	Menu findRootMenu();
	
	@Query("select m from Menu m where m.parent.uuid = :parentId and m.enabled = 1 and m.visible = 1 order by m.sort asc")
	List<Menu> findAllEnabledVisibleMenu(@Param("parentId") UUID parentId);

	@Query("select m from Menu m where m.parent.uuid = :parentId order by m.sort asc")
	List<Menu> findAllMenu(@Param("parentId") UUID parentId);

}
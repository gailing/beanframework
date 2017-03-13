package com.beanframework.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.platform.core.base.BaseService;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.Permission;
import com.beanframework.user.domain.Role;
import com.beanframework.user.domain.RoleRepository;
import com.beanframework.user.domain.RoleSearchCriteria;
import com.beanframework.user.domain.RoleSpecification;

@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = UserConstants.CACHE_ROLES)
public class RoleService extends BaseService {

	@Autowired
	private RoleRepository roleRepository;

	public boolean isPermissionSelected(UUID uuid, UUID permissionId) {
		Role role = findByUuid(uuid);
		Hibernate.initialize(role.getPermissions());

		for (Permission permission : role.getPermissions()) {
			if (permission.getUuid().equals(permissionId)) {
				return true;
			}
		}

		return false;
	}

	public boolean isRoleExists(UUID uuid) {
		return roleRepository.isRoleExists(uuid);
	}

	public boolean isNameExists(String name) {
		return roleRepository.isNameExists(name);
	}

	public Role findByUuid(UUID uuid) {

		if (!isRoleExists(uuid)) {
			return null;
		}

		Role role = roleRepository.findOne(uuid);
		Hibernate.initialize(role.getGroups());
		Hibernate.initialize(role.getUsers());
		Hibernate.initialize(role.getPermissions());
		return role;
	}

	public List<Role> find(UUID... ids) {

		List<Role> roles = new ArrayList<Role>();

		if (ids == null) {
			return roles;
		}

		for (UUID uuid : ids) {
			roles.add(findByUuid(uuid));
		}

		return roles;
	}

	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}

	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}

	public Page<Role> findAllRoles(RoleSearchCriteria searchCriteria, Pageable pageable) {
		if (searchCriteria == null) {
			return findAll(pageable);
		} else {
			return findAll(RoleSpecification.findByCriteria(searchCriteria), pageable);
		}
	}

	private Page<Role> findAll(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}

	private Page<Role> findAll(Specification<Role> specification, Pageable pageable) {
		return roleRepository.findAll(specification, pageable);
	}

	@Transactional(readOnly = false)
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Transactional(readOnly = false)
	public void delete(UUID uuid) {
		roleRepository.delete(uuid);
	}

	@Transactional(readOnly = false)
	public void deleteByName(String name) {
		roleRepository.deleteByName(name);
	}

	@CacheEvict(allEntries = true)
	@Transactional(readOnly = false)
	public void deleteAll() {
		roleRepository.deleteAll();
	}

}

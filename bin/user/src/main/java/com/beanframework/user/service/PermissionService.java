package com.beanframework.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import com.beanframework.platform.core.base.BaseService;
import com.beanframework.user.domain.Permission;
import com.beanframework.user.domain.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PermissionService extends BaseService {

	@Autowired
	private PermissionRepository permissionRepository;

	public boolean exists(UUID uuid) {
		return permissionRepository.exists(uuid);
	}

	public Permission findOne(UUID uuid) {
		return permissionRepository.findOne(uuid);
	}
	
	public List<Permission> find(UUID... ids) {

		List<Permission> permissions = new ArrayList<Permission>();

		for (UUID uuid : ids) {
			permissions.add(findOne(uuid));
		}

		return permissions;
	}

	public Optional<Permission> findByName(String name) {
		return permissionRepository.findByName(name);
	}

	public List<Permission> findAll() {
		return permissionRepository.findAll(new Sort(Sort.Direction.ASC, Permission.SORT));
	}

	public List<Permission> findAllParent() {
		return permissionRepository.findAllParent();
	}

	public Page<Permission> findAll(Pageable pageable) {
		return permissionRepository.findAll(pageable);
	}

	public Permission save(Permission permission) {
		return permissionRepository.save(permission);
	}

	public void delete(UUID uuid) {
		permissionRepository.delete(uuid);
	}

	public void deleteByName(String name) {
		permissionRepository.deleteByName(name);
	}
}

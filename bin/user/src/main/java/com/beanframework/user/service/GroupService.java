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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.platform.core.base.BaseService;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.Group;
import com.beanframework.user.domain.GroupRepository;
import com.beanframework.user.domain.GroupSearchCriteria;
import com.beanframework.user.domain.GroupSpecification;

@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = UserConstants.CACHE_GROUPS)
public class GroupService extends BaseService {

	@Autowired
	private GroupRepository groupRepository;

	public boolean isGroupExists(UUID uuid) {
		return groupRepository.isGroupExists(uuid);
	}

	public boolean isNameExists(String name) {
		return groupRepository.isNameExists(name);
	}


	public List<Group> find(UUID... ids) {

		List<Group> groups = new ArrayList<Group>();
		
		if(ids == null){
			return groups;
		}

		for (UUID uuid : ids) {
			groups.add(findByUuid(uuid));
		}

		return groups;
	}

	public Group findByUuid(UUID uuid) {

		if (!isGroupExists(uuid)) {
			return null;
		}

		Group group = groupRepository.findOne(uuid);
		Hibernate.initialize(group.getUsers());
		Hibernate.initialize(group.getRoles());
		return group;
	}

	public Group findByName(String name) {

		if (!isNameExists(name)) {
			return null;
		}

		return groupRepository.findByName(name);
	}

	public List<Group> findAllGroups() {
		return groupRepository.findAll();
	}

	public Page<Group> findAllGroups(GroupSearchCriteria searchCriteria, Pageable pageable) {
		if (searchCriteria == null) {
			return groupRepository.findAll(pageable);
		} else {
			return groupRepository.findAll(GroupSpecification.findByCriteria(searchCriteria), pageable);
		}
	}

	@Transactional(readOnly = false)
	public Group save(Group group) {
		return groupRepository.save(group);
	}

	@Transactional(readOnly = false)
	public void delete(UUID uuid) {
		groupRepository.delete(uuid);
	}

	@Transactional(readOnly = false)
	public void deleteByName(String name) {
		groupRepository.deleteByName(name);
	}

	@Transactional(readOnly = false)
	public void removeRoleFromGroup(UUID uuid, UUID... roleIds) {
		Group group = findByUuid(uuid);
		Hibernate.initialize(group.getRoles());

		for (UUID roleUuid : roleIds) {
			for (int i = 0; i < group.getRoles().size(); i++) {
				if (group.getRoles().get(i).getUuid().equals(roleUuid)) {
					group.getRoles().remove(i);
				}
			}
		}

		save(group);
	}

	@CacheEvict(allEntries = true)
	@Transactional(readOnly = false)
	public void deleteAll() {
		groupRepository.deleteAll();
	}
}

package com.beanframework.user.service;

import java.util.Date;
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
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserRepository;
import com.beanframework.user.domain.UserSearchCriteria;
import com.beanframework.user.domain.UserSpecification;

@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = UserConstants.CACHE_USERS)
public class UserService extends BaseService {

	@Autowired
	private UserRepository userRepository;

	private void initializeUser(User user) {
		Hibernate.initialize(user.getGroups());
		for (int i = 0; i < user.getGroups().size(); i++) {
			Hibernate.initialize(user.getGroups().get(i).getRoles());
			for (int j = 0; j < user.getGroups().get(i).getRoles().size(); j++) {
				Hibernate.initialize(user.getGroups().get(i).getRoles().get(j).getPermissions());
			}
		}
		Hibernate.initialize(user.getRoles());
		for (int i = 0; i < user.getRoles().size(); i++) {
			Hibernate.initialize(user.getRoles().get(i).getPermissions());
		}
	}

	public void removeGroupFromUser(UUID uuid, UUID... groupIds) {
		User user = findByUuid(uuid);
		Hibernate.initialize(user.getGroups());

		for (UUID groupUuid : groupIds) {
			for (int i = 0; i < user.getGroups().size(); i++) {
				if (user.getGroups().get(i).getUuid().equals(groupUuid)) {
					user.getGroups().remove(i);
				}
			}
		}

		save(user);
	}

	public void removeRoleFromUser(UUID uuid, UUID... roleIds) {
		User user = findByUuid(uuid);
		Hibernate.initialize(user.getRoles());

		for (UUID roleUuid : roleIds) {
			for (int i = 0; i < user.getRoles().size(); i++) {
				if (user.getRoles().get(i).getUuid().equals(roleUuid)) {
					user.getRoles().remove(i);
				}
			}
		}

		save(user);
	}

	public boolean isUserExists(UUID uuid) {
		return userRepository.isUserExists(uuid);
	}

	public boolean isUsernameExists(String username) {
		return userRepository.isUsernameExists(username);
	}

	public boolean isEmailExists(String email) {
		return userRepository.isEmailExists(email);
	}

	public User findByUuid(UUID uuid) {

		if (!isUserExists(uuid)) {
			return null;
		}

		User user = userRepository.findOne(uuid);
		Hibernate.initialize(user.getGroups());
		Hibernate.initialize(user.getRoles());
		return user;
	}

	public User findByUsername(String username) {

		if (!isUsernameExists(username)) {
			return null;
		}

		User user = userRepository.findByUsername(username);
		initializeUser(user);
		return user;
	}

	public User findByEmail(String email) {

		if (!isEmailExists(email)) {
			return null;
		}

		User user = userRepository.findByEmail(email);
		initializeUser(user);
		return user;
	}

	public Page<User> findAllUsers(UserSearchCriteria searchCriteria, Pageable pageable) {
		if (searchCriteria == null) {
			return userRepository.findAll(pageable);
		} else {
			return userRepository.findAll(UserSpecification.findByCriteria(searchCriteria), pageable);
		}
	}

	@Transactional(readOnly = false)
	public User save(User user) {
		return userRepository.save(user);
	}

	@Transactional(readOnly = false)
	public void updateLastLogonDateById(Date lastLogonDate, UUID uuid) {
		userRepository.updateLastLogonDateById(lastLogonDate, uuid);
	}

	@Transactional(readOnly = false)
	public void delete(UUID uuid) {
		userRepository.delete(uuid);
	}

	@Transactional(readOnly = false)
	public void deleteByUsername(String username) {
		userRepository.deleteByUsername(username);
	}

	public boolean updateLoginAttemptsAndIsLocked(UUID uuid) {
		int limit = UserConstants.LIMIT_LOGIN_ATTEMPTS;

		int lastAttempts = userRepository.findAttemptsById(uuid);

		if (lastAttempts < limit) {
			int totalAttempts = lastAttempts + 1;
			userRepository.updateAttempts(uuid, totalAttempts);
			return true;
		} else {
			userRepository.setUserAccountLocked(uuid);
			return false;
		}
	}

	public void resetLoginAttempts(UUID uuid) {

		int lastAttempts = userRepository.findAttemptsById(uuid);

		if (lastAttempts != 0) {
			userRepository.updateAttempts(uuid, 0);
		}
	}

	@CacheEvict(allEntries = true)
	@Transactional(readOnly = false)
	public void deleteAll() {
		userRepository.deleteAll();
	}
}
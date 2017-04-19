package com.beanframework.accountpolicy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.accountpolicy.AccountpolicyConstants;
import com.beanframework.accountpolicy.domain.Accountpolicy;
import com.beanframework.accountpolicy.domain.AccountpolicyRepository;
import com.beanframework.accountpolicy.domain.AccountpolicySearchCriteria;
import com.beanframework.accountpolicy.domain.AccountpolicySpecification;
import com.beanframework.platform.core.base.BaseService;

@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = AccountpolicyConstants.CACHE_ACCOUNTPOLICIES)
public class AccountpolicyService extends BaseService {

	@Autowired
	private AccountpolicyRepository AccountpolicyRepository;

	public boolean isAccountpolicyExists(UUID uuid) {
		return AccountpolicyRepository.isAccountpolicyExists(uuid);
	}

	public boolean isNameExists(String name) {
		return AccountpolicyRepository.isNameExists(name);
	}


	public List<Accountpolicy> find(UUID... ids) {

		List<Accountpolicy> Accountpolicys = new ArrayList<Accountpolicy>();
		
		if(ids == null){
			return Accountpolicys;
		}

		for (UUID uuid : ids) {
			Accountpolicys.add(findByUuid(uuid));
		}

		return Accountpolicys;
	}

	public Accountpolicy findByUuid(UUID uuid) {

		if (!isAccountpolicyExists(uuid)) {
			return null;
		}

		Accountpolicy Accountpolicy = AccountpolicyRepository.findOne(uuid);
		return Accountpolicy;
	}

	public Accountpolicy findByName(String name) {

		if (!isNameExists(name)) {
			return null;
		}

		return AccountpolicyRepository.findByName(name);
	}

	public List<Accountpolicy> findAllAccountpolicys() {
		return AccountpolicyRepository.findAll();
	}

	public Page<Accountpolicy> findAllAccountpolicys(AccountpolicySearchCriteria searchCriteria, Pageable pageable) {
		if (searchCriteria == null) {
			return AccountpolicyRepository.findAll(pageable);
		} else {
			return AccountpolicyRepository.findAll(AccountpolicySpecification.findByCriteria(searchCriteria), pageable);
		}
	}

	@Transactional(readOnly = false)
	public Accountpolicy save(Accountpolicy Accountpolicy) {
		return AccountpolicyRepository.save(Accountpolicy);
	}

	@Transactional(readOnly = false)
	public void delete(UUID uuid) {
		AccountpolicyRepository.delete(uuid);
	}

	@Transactional(readOnly = false)
	public void deleteByName(String name) {
		AccountpolicyRepository.deleteByName(name);
	}

	@CacheEvict(allEntries = true)
	@Transactional(readOnly = false)
	public void deleteAll() {
		AccountpolicyRepository.deleteAll();
	}
}

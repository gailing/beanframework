package com.beanframework.cronjob.service;

import java.util.List;
import java.util.UUID;

import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobSearchCriteria;
import com.beanframework.cronjob.domain.CronjobSpecification;
import com.beanframework.cronjob.utils.CronjobManager;
import com.beanframework.platform.core.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CronjobService extends BaseService {

	@Autowired
	private CronjobRepository cronjobRepository;

	@Autowired
	private CronjobManager cronjobManager;

	public void initCronJob() {

		List<Cronjob> jobList = cronjobRepository.findStartupJob(true, Cronjob.JobTrigger.START);

		for (Cronjob cronjob : jobList) {
			try {
				cronjobManager.saveOrUpdateJob(cronjob);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isGroupAndNameExists(String jobGroup, String jobName) {
		return cronjobRepository.isGroupAndNameExists(jobGroup, jobName);
	}

	public Cronjob getCronJob(UUID uuid) {
		return cronjobRepository.getById(uuid);
	}

	public Cronjob getJobByGroupAndName(String jobGroup, String jobName) {

		if (!isGroupAndNameExists(jobGroup, jobName)) {
			return null;
		}

		return cronjobRepository.getByGroupAndName(jobGroup, jobName);
	}

	public Page<Cronjob> findAllCronJobs(CronjobSearchCriteria searchCriteria, Pageable pageable) {
		if (searchCriteria == null) {
			return findAll(pageable);
		} else {
			return findAll(CronjobSpecification.findByCriteria(searchCriteria), pageable);
		}
	}

	private Page<Cronjob> findAll(Pageable pageable) {
		return cronjobRepository.findAll(pageable);
	}

	private Page<Cronjob> findAll(Specification<Cronjob> specification, Pageable pageable) {
		return cronjobRepository.findAll(specification, pageable);
	}

	@Transactional(readOnly = false)
	public Cronjob saveCronJob(Cronjob cronjob) throws Exception {

		cronjob = cronjobRepository.save(cronjob);

		if (cronjob.getJobTrigger().equals(Cronjob.JobTrigger.START) || cronjob.getJobTrigger().equals(Cronjob.JobTrigger.RUN_ONCE)) {
			try {
				cronjobManager.saveOrUpdateJob(cronjob);

			} catch (Exception e) {
				cronjob.setStatus(Cronjob.Status.ERROR);
				cronjob.setResult(e.getMessage());
				cronjob = cronjobRepository.save(cronjob);
			}
			
		} else if (cronjob.getJobTrigger().equals(Cronjob.JobTrigger.STOP)) {
			try {
				cronjobManager.deleteJob(cronjob);
				cronjob.setStatus(Cronjob.Status.STOPPED);
				cronjob.setResult("Stopped Successfully");

			} catch (Exception e) {
				cronjob.setStatus(Cronjob.Status.ERROR);
				cronjob.setResult(e.getMessage());
			}
			cronjob = cronjobRepository.save(cronjob);
		}

		return cronjob;

	}

	@Transactional(readOnly = false)
	public void deleteCronJob(UUID uuid) throws Exception {

		Cronjob cronjob = getCronJob(uuid);

		try {
			cronjobManager.deleteJob(cronjob);
			cronjobRepository.delete(uuid);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Transactional(readOnly = false)
	public void updateStatus(UUID uuid, Cronjob.Status status, String result) {
		cronjobRepository.updateStatus(uuid, status, result);
	}
}

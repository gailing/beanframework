package com.beanframework.cronjob.service;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobSearchCriteria;
import com.beanframework.cronjob.utils.CronjobValidateConverter;
import com.beanframework.platform.core.base.BaseFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class CronjobFacade extends BaseFacade {

	@Autowired
	private CronjobService cronjobService;
	
	@Autowired
	private CronjobValidateConverter cronjobValidateConverter;

	public Cronjob createCronjob() {
		return new Cronjob();
	}
	
	public boolean isGroupAndNameExists(String jobGroup, String jobName) {
		return cronjobService.isGroupAndNameExists(jobGroup, jobName);
	}

	public Page<Cronjob> getCronjobPage(String query, String keyword, String field, String direction, int page, int pageSize) {

		// Search Criteria
		CronjobSearchCriteria searchCriteria = null;
		if (StringUtils.isNotEmpty(query) && StringUtils.isNotEmpty(keyword)) {
			searchCriteria = new CronjobSearchCriteria(query, keyword);
		}

		// Pageable
		int pageIndex = page - 1;
		PageRequest pageRequest;
		if (!StringUtils.isEmpty(field) && !StringUtils.isEmpty(direction)) {
			pageRequest = new PageRequest(pageIndex, pageSize, Sort.Direction.fromString(direction),
					field);
		} else {
			pageRequest = new PageRequest(pageIndex, pageSize, Sort.Direction.DESC,
					Cronjob.CREATED_DATE);
		}

		return cronjobService.findAllCronJobs(searchCriteria, pageRequest);
	}

	public Cronjob getCronjobById(String uuid) {
		return cronjobService.getCronJob(UUID.fromString(uuid));
	}

	public Cronjob saveCronjob(Cronjob cronjob) throws Exception {
		
		cronjobValidateConverter.save(cronjob);
		
		return cronjobService.saveCronJob(cronjob);
	}

	public Cronjob deleteCronjobById(String uuid) throws Exception {
		Cronjob cronjob = getCronjobById(uuid);		
		cronjobService.deleteCronJob(UUID.fromString(uuid));
		return cronjob;
	}

}

package com.beanframework.platform.oplog.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import com.beanframework.platform.core.base.BaseService;
import com.beanframework.platform.oplog.OpLogConstants;
import com.beanframework.platform.oplog.domain.OpLog;
import com.beanframework.platform.oplog.domain.OpLogRepository;
import com.beanframework.platform.oplog.domain.OpLogSearchCriteria;
import com.beanframework.platform.oplog.domain.OpLogSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OpLogService extends BaseService {

	@Autowired
	private OpLogRepository logRepository;

	public Page<OpLog> findAllLogs(OpLogSearchCriteria searchCriteria, Pageable pageable) {
		if (searchCriteria == null) {
			return logRepository.findAll(pageable);
		} else {
			return logRepository.findAll(OpLogSpecification.findByCriteria(searchCriteria), pageable);
		}
	}

	public OpLog saveLog(OpLog log) {
		return logRepository.save(log);
	}

	public int deleteOldLog() {

		PageRequest pageRequest = new PageRequest(0, OpLogConstants.LIMIT_LOG, Sort.Direction.DESC, OpLog.CREATED_DATE);
		List<UUID> excludedIds = logRepository.getLastestLogId(pageRequest);

		if (!excludedIds.isEmpty()) {
			return logRepository.deleteOldLogByExcludedId(excludedIds);
		}

		return 0;
	}
}

package com.beanframework.platform.oplog.service;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import com.beanframework.platform.core.utils.RequestUtils;
import com.beanframework.platform.oplog.domain.OpLog;
import com.beanframework.platform.oplog.domain.OpLogSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class OpLogFacade {

	@Autowired
	private OpLogService logService;
	
	public Page<OpLog> getLogPage(String query, String keyword, String field, String direction, Date dateFrom, Date dateTo, int page, int pageSize) throws ParseException {
		// Search Criteria
		OpLogSearchCriteria searchCriteria = null;
		if ((StringUtils.isNotEmpty(query) && StringUtils.isNotEmpty(keyword)) ||
				dateFrom != null || dateTo != null) {
			searchCriteria = new OpLogSearchCriteria(query, keyword, dateFrom, dateTo);
		}

		// Pageable
		int pageIndex = page - 1;
		PageRequest pageRequest;
		if (!StringUtils.isEmpty(field) && !StringUtils.isEmpty(direction)) {
			pageRequest = new PageRequest(pageIndex, pageSize, Sort.Direction.fromString(direction),
					field);
		} else {
			pageRequest = new PageRequest(pageIndex, pageSize, Sort.Direction.DESC,
					OpLog.CREATED_DATE);
		}

		return logService.findAllLogs(searchCriteria, pageRequest);
	}
	
	public void log(String channel, String operate, String result) {

		log(channel, operate, result, null);
	}

	public void log(String channel, String operate, Exception e) {

		log(channel, operate, null, e);
	}

	public void log(String channel, String operate, String result, Exception e) {
		OpLog log = new OpLog();
		log.setChannel(channel);
		log.setOperate(operate);
		log.setResult(result);
		log.setException(e == null? null : ExceptionUtils.getStackTrace(e));

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (attr != null) {
			HttpServletRequest request = attr.getRequest();
			log.setIp(RequestUtils.getIpAddress(request));
		}

		logService.saveLog(log);
	}

}

package com.beanframework.platform.oplog.web;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.beanframework.platform.core.base.BaseController;
import com.beanframework.platform.core.domain.PageWrapper;
import com.beanframework.platform.oplog.OpLogWebConstants;
import com.beanframework.platform.oplog.domain.OpLog;
import com.beanframework.platform.oplog.service.OpLogFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(OpLogWebConstants.PATH_ROOT)
public class OpLogController extends BaseController {
	
	@Autowired
	private OpLogFacade logFacade;

	@RequestMapping(OpLogWebConstants.PATH_LIST)
	public String list(Model model, @RequestParam Map<String, Object> allRequestParams) throws ParseException {

		// Request Query Parameters
		String query = getParamValue(PARAM_QUERY, allRequestParams);
		String keyword = getParamValue(PARAM_KEYWORD, allRequestParams);
		String field = getParamValue(PARAM_FIELD, allRequestParams);
		String direction = getParamValue(PARAM_DIRECTION, allRequestParams);
		String page = getParamValue(PARAM_PAGE, allRequestParams);
		String dateRange = getParamValue(PARAM_DATE_RANGE, allRequestParams);
		
		Date dateFrom = null;
		Date dateTo = null;
		if(StringUtils.isNotEmpty(dateRange)){
			dateFrom = dateRangeFormat.parse(dateRange.split(" - ")[0]);
			dateTo = dateRangeFormat.parse(dateRange.split(" - ")[1]);
		}
		
		
		int pageNumber = 1;

		if (StringUtils.isNotEmpty(page)) {
			pageNumber = Integer.valueOf(page);
		}

		// Result
		Page<OpLog> resultPage = logFacade.getLogPage(query, keyword, field, direction, dateFrom, dateTo, pageNumber, OpLogWebConstants.PAGE_LIST_SIZE);

		// Page
		String path = OpLogWebConstants.PATH_ROOT + OpLogWebConstants.PATH_LIST 
				+ "?" + PARAM_QUERY + "=" + query 
				+ "&" + PARAM_KEYWORD + "=" + keyword 
				+ "&" + PARAM_FIELD + "=" + field
				+ "&" + PARAM_DIRECTION + "=" + direction
				+ "&" + PARAM_DATE_RANGE + "=" + dateRange;

		model.addAttribute(MODEL_ATTRIBUTE_PAGE, new PageWrapper<OpLog>(resultPage, path));

		return OpLogWebConstants.PAGE_LIST;
	}
}
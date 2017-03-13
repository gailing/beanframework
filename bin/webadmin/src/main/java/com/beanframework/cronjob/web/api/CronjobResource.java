package com.beanframework.cronjob.web.api;

import java.util.Map;


import com.beanframework.cronjob.CronjobWebConstants;
import com.beanframework.cronjob.service.CronjobFacade;
import com.beanframework.platform.core.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CronjobWebConstants.PATH_API_ROOT)
public class CronjobResource extends BaseController {
	
	public static final String FORM_JOB_GROUP = "jobGroup";
	public static final String FORM_JOB_NAME = "jobName";

	@Autowired
	private CronjobFacade cronjobFacade;

	@RequestMapping(CronjobWebConstants.PATH_API_CHECK_NAME)
	public String checkname(Model model, @RequestParam Map<String, Object> allRequestParams) {
		String jobGroup = getParamValue(FORM_JOB_GROUP, allRequestParams);
		String jobName = getParamValue(FORM_JOB_NAME, allRequestParams);
		boolean exists = cronjobFacade.isGroupAndNameExists(jobGroup, jobName);
		return exists ? "false" : "true";
	}
}
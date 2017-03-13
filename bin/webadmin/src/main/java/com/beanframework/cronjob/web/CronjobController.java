package com.beanframework.cronjob.web;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import com.beanframework.common.AdminBaseController;
import com.beanframework.cronjob.CronjobWebConstants;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.service.CronjobFacade;
import com.beanframework.platform.core.domain.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(CronjobWebConstants.PATH_ROOT)
public class CronjobController extends AdminBaseController {

	public static final String FORM_CRONJOB = "cronjob";
	public static final String FORM_UUID = "uuid";
	public static final String FORM_JOB_CLASS = "jobClass";
	public static final String FORM_NEW_JOB_GROUP = "newJobGroup";
	public static final String FORM_NEW_JOB_NAME = "newJobName";
	public static final String FORM_CRON_EXPRESSSION = "cronExpression";
	public static final String FORM_STARTUP = "startup";
	public static final String FORM_JOB_TRIGGER = "jobTrigger";
	public static final String FORM_DESCRIPTION = "description";

	public static final String FORM_MESSAGE_SUCCESS_SAVE = "Cronjob has been successfully saved.";
	public static final String FORM_MESSAGE_JOB_NAME_AND_GROUP_EXISTS = "Job Name in use, please choose another name.";
	public static final MessageFormat FORM_MESSAGE_DELETE_SUCCESS = new MessageFormat("Cronjob [{0}] has been successfully deleted.");

	@Autowired
	private CronjobFacade cronjobFacade;

	@RequestMapping(CronjobWebConstants.PATH_LIST)
	public String list(Model model, @RequestParam Map<String, Object> allRequestParams) {

		// Request Query Parameters
		String query = getParamValue(PARAM_QUERY, allRequestParams);
		String keyword = getParamValue(PARAM_KEYWORD, allRequestParams);
		String field = getParamValue(PARAM_FIELD, allRequestParams);
		String direction = getParamValue(PARAM_DIRECTION, allRequestParams);
		String page = getParamValue(PARAM_PAGE, allRequestParams);
		int pageNumber = 1;

		if (StringUtils.isNotEmpty(page)) {
			pageNumber = Integer.valueOf(page);
		}

		// Result
		Page<Cronjob> resultPage = cronjobFacade.getCronjobPage(query, keyword, field, direction, pageNumber, CronjobWebConstants.PAGE_LIST_SIZE);

		// Page
		String path = CronjobWebConstants.PATH_ROOT + CronjobWebConstants.PATH_LIST + "?" + PARAM_QUERY + "=" + query + "&" + PARAM_KEYWORD + "=" + keyword + "&" + PARAM_FIELD + "=" + field + "&"
				+ PARAM_DIRECTION + "=" + direction;

		model.addAttribute(MODEL_ATTRIBUTE_PAGE, new PageWrapper<Cronjob>(resultPage, path));

		return page(CronjobWebConstants.PAGE_LIST, model, allRequestParams);
	}

	@RequestMapping(CronjobWebConstants.PATH_ADD)
	public String add(Model model, @RequestParam Map<String, Object> allRequestParams) {

		model.addAttribute(FORM_CRONJOB, cronjobFacade.createCronjob());

		return page(CronjobWebConstants.PAGE_FORM, model, allRequestParams);
	}

	@RequestMapping(CronjobWebConstants.PATH_EDIT)
	public String edit(Model model, @RequestParam Map<String, Object> allRequestParams) {

		String uuid = getParamValue(FORM_UUID, allRequestParams);

		Cronjob Cronjob = cronjobFacade.getCronjobById(uuid);
		model.addAttribute(FORM_CRONJOB, Cronjob);

		return page(CronjobWebConstants.PAGE_FORM, model, allRequestParams);
	}

	@RequestMapping(value = CronjobWebConstants.PATH_SAVE, method = RequestMethod.POST)
	public String save(Model model, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) {

		String uuid = getParamValue(FORM_UUID, allRequestParams);
		String jobClass = getParamValue(FORM_JOB_CLASS, allRequestParams);
		String jobGroup = getParamValue(FORM_NEW_JOB_GROUP, allRequestParams);
		String jobName = getParamValue(FORM_NEW_JOB_NAME, allRequestParams);
		String cronExpression = getParamValue(FORM_CRON_EXPRESSSION, allRequestParams);
		boolean startup = parseBoolean(getParamValue(FORM_STARTUP, allRequestParams));
		String description = getParamValue(FORM_DESCRIPTION, allRequestParams);
		String jobTrigger = getParamValue(FORM_JOB_TRIGGER, allRequestParams);

		Cronjob cronjob = null;

		try {

			if (StringUtils.isNotEmpty(uuid)) {
				// Update
				cronjob = cronjobFacade.getCronjobById(uuid);
			} else {
				// Create
				cronjob = cronjobFacade.createCronjob();
				cronjob.setNewJobGroup(jobGroup);
				cronjob.setNewJobName(jobName);
			}

			cronjob.setJobClass(jobClass);
			cronjob.setCronExpression(cronExpression);
			cronjob.setStartup(startup);
			cronjob.setJobTrigger(Cronjob.JobTrigger.fromName(jobTrigger));
			cronjob.setDescription(description);

			cronjob = cronjobFacade.saveCronjob(cronjob);
			model.addAttribute(PARAM_MESSAGE, FORM_MESSAGE_SUCCESS_SAVE);
			model.addAttribute(FORM_UUID, cronjob.getUuid().toString());
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
		}

		return redirect(CronjobWebConstants.PATH_ROOT + CronjobWebConstants.PATH_EDIT, model, redirectAttributes);
	}

	@RequestMapping(value = CronjobWebConstants.PATH_DELETE, method = RequestMethod.POST)
	public String delete(Model model, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) {

		String uuid = getParamValue(FORM_UUID, allRequestParams);

		try {
			Cronjob Cronjob = cronjobFacade.deleteCronjobById(uuid);

			model.addAttribute(PARAM_MESSAGE, FORM_MESSAGE_DELETE_SUCCESS.format(new Object[] { Cronjob.getJobName() }));

			return redirect(CronjobWebConstants.PATH_ROOT + CronjobWebConstants.PATH_LIST, model, redirectAttributes);
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
			model.addAttribute(FORM_UUID, uuid);

			return redirect(CronjobWebConstants.PATH_ROOT + CronjobWebConstants.PATH_EDIT, model, redirectAttributes);
		}
	}
}
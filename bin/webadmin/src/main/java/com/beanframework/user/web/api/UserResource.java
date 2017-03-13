package com.beanframework.user.web.api;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.beanframework.platform.core.base.BaseController;
import com.beanframework.user.service.UserFacade;
import com.beanframework.user.web.UserWebConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserWebConstants.PATH_API_ROOT)
public class UserResource extends BaseController {
	
	public static final String FORM_USERNAME = "username";
	public static final String FORM_OLD_EMAIL = "oldEmail";
	public static final String FORM_EMAIL = "email";

	@Autowired
	private UserFacade userFacade;

	@RequestMapping(UserWebConstants.PATH_API_CHECK_USERNAME)
	public String checkUsername(Model model, @RequestParam Map<String, Object> allRequestParams) {
		String username = getParamValue(FORM_USERNAME, allRequestParams);
		boolean exists = userFacade.isUsernameExists(username);
		return exists ? "false" : "true";
	}

	@RequestMapping(UserWebConstants.PATH_API_CHECK_EMAIL)
	public String checkEmail(Model model, @RequestParam Map<String, Object> allRequestParams) {
		String oldEmail = getParamValue(FORM_OLD_EMAIL, allRequestParams);
		String email = getParamValue(FORM_EMAIL, allRequestParams);

		if (StringUtils.isNotEmpty(oldEmail) && StringUtils.isNotEmpty(email) && oldEmail.equals(email)) {
			return "true";
		}

		boolean exists = userFacade.isEmailExists(email);
		return exists ? "false" : "true";
	}
}
package com.beanframework.user.web.api;

import java.util.Map;


import com.beanframework.platform.core.base.BaseController;
import com.beanframework.user.GroupWebConstants;
import com.beanframework.user.service.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(GroupWebConstants.PATH_API_ROOT)
public class GroupResource extends BaseController {
	
	public static final String FORM_NAME = "name";

	@Autowired
	private UserFacade userFacade;

	@RequestMapping(GroupWebConstants.PATH_API_CHECK_NAME)
	public String checkname(Model model, @RequestParam Map<String, Object> allRequestParams) {
		String name = getParamValue(FORM_NAME, allRequestParams);
		boolean exists = userFacade.isGroupNameExists(name);
		return exists ? "false" : "true";
	}
}
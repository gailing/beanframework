package com.beanframework.accountpolicy.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.beanframework.common.AdminBaseController;

@Controller
@RequestMapping(AccountpolicyWebConstants.PATH_ROOT)
public class AccountpolicyController extends AdminBaseController{

	@RequestMapping
	public String list(Model model, @RequestParam Map<String, Object> allRequestParams) throws Exception {

		return page(AccountpolicyWebConstants.PAGE, model, allRequestParams);
	}
}

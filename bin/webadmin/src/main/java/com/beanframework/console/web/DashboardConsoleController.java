package com.beanframework.console.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.beanframework.common.AdminBaseController;
import com.beanframework.console.ConsoleWebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(ConsoleWebConstants.PATH_ROOT)
public class DashboardConsoleController extends AdminBaseController {
	
	@RequestMapping(value = { ConsoleWebConstants.PATH_DASHBOARD }, method = { RequestMethod.GET, RequestMethod.POST })
	public String dashboard(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return ConsoleWebConstants.PAGE_DASHBOARD;
	}
}

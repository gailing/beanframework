package com.beanframework.console.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.beanframework.common.AdminBaseController;
import com.beanframework.console.ConsoleWebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(ConsoleWebConstants.PATH_ROOT)
public class MainConsoleController extends AdminBaseController {

	@Autowired
	private LicenseConsoleController consoleLicense;

	@Value("${console.license.skip:true}")
	private boolean skipLicense;

	@RequestMapping(value = { "" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String console(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!skipLicense && !consoleLicense.isLicenseAccepted()) {
			return redirect(ConsoleWebConstants.PATH_ROOT + ConsoleWebConstants.PATH_LICENSE, model, redirectAttributes);
		} else {
			return redirect(ConsoleWebConstants.PATH_ROOT + ConsoleWebConstants.PATH_DASHBOARD, model, redirectAttributes);
		}
	}
}

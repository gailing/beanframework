package com.beanframework.console.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.beanframework.common.AdminBaseController;
import com.beanframework.platform.sysconfig.domain.SysConfig;
import com.beanframework.platform.sysconfig.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(ConsoleWebConstants.PATH_ROOT)
public class LicenseConsoleController extends AdminBaseController {

	@Autowired
	private SysConfigService sysConfigService;

	public static final String FORM_ACCEPT = "accept";

	public static final String LICENSE_ACCEPTED = "license_accepted";

	public boolean isLicenseAccepted() {
		SysConfig sysConfig = sysConfigService.getByName(LICENSE_ACCEPTED);

		if (sysConfig == null) {
			return false;
		} else if (parseBoolean(sysConfig.getValue())) {
			return true;
		} else {
			return false;
		}
	}
	
	@RequestMapping(value = ConsoleWebConstants.PATH_LICENSE, method = { RequestMethod.GET, RequestMethod.POST })
	public String license(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (request.getMethod().equalsIgnoreCase("POST")) {
			String accepted = parseBoolean(FORM_ACCEPT, allRequestParams) ? "1" : "0";

			SysConfig sysConfig = sysConfigService.getByName(LICENSE_ACCEPTED);

			if(sysConfig == null){
				sysConfig = new SysConfig();
				sysConfig.setName(LICENSE_ACCEPTED);
			}

			try {
				sysConfig.setValue(accepted);
				sysConfig = sysConfigService.save(sysConfig);
				model.addAttribute(PARAM_MESSAGE, "License has been accepted.");
			} catch (Exception e) {
				model.addAttribute(PARAM_ERROR, e.getMessage());
			}
			
			model.addAttribute("accepted", sysConfig.getValue().equals("0") ? false : true);
		} else {
			SysConfig sysConfig = sysConfigService.getByName(LICENSE_ACCEPTED);
			if(sysConfig == null){
				model.addAttribute("accepted", false);
			}
			else{
				model.addAttribute("accepted", sysConfig.getValue().equals("0") ? false : true);
			}
		}

		return ConsoleWebConstants.PAGE_LICENSE;
	}
}

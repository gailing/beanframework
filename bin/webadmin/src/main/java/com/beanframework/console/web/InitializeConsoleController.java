package com.beanframework.console.web;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.common.AdminBaseController;
import com.beanframework.menu.init.MenuInit;
import com.beanframework.user.init.UserInit;

@Controller
@RequestMapping(ConsoleWebConstants.PATH_ROOT)
public class InitializeConsoleController extends AdminBaseController {

	@Autowired
	private MenuInit menuInit;
	
	@Autowired
	private UserInit userInit;
	
	@Value(ConsoleWebConstants.VAR_DIR)
	private String VAR_DIR;

	@RequestMapping(value = ConsoleWebConstants.PATH_INITIALIZE, method = { RequestMethod.GET, RequestMethod.POST })
	public String menu(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (request.getMethod().equalsIgnoreCase("POST")) {

			StringBuilder sb = new StringBuilder();

			try {
				
				boolean menu = parseBoolean("menu", allRequestParams);
				if(menu){
					menuInit.init();
					sb.append("Menu was initialized successfully. \n");
				}
				
				boolean user = parseBoolean("user", allRequestParams);
				if(user){
					userInit.init();
					sb.append("User was initialized successfully. \n");
				}
				
				boolean log = parseBoolean("log", allRequestParams);
				if(log){
					FileUtils.cleanDirectory(new File(VAR_DIR+"/log"));
					sb.append("Log was initialized successfully. \n");
				}
				
				model.addAttribute(PARAM_MESSAGE, sb.toString());
			} catch (Exception e) {
				model.addAttribute(PARAM_MESSAGE, sb.toString());
				model.addAttribute(PARAM_ERROR, e.getMessage());
			}
			return redirect(ConsoleWebConstants.PATH_ROOT + ConsoleWebConstants.PATH_INITIALIZE, model, redirectAttributes);
		}

		return page(ConsoleWebConstants.PAGE_INITIALIZE, model, allRequestParams);
	}

}

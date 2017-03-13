package com.beanframework.filemanager.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.beanframework.common.AdminBaseController;
import com.beanframework.theme.ThemeManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/filemanager")
public class FilemanagerController extends AdminBaseController {
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public String filemanager(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return ThemeManager.getInstance().getAdminThemePath() + "/filemanager/filemanager";
	}
	
	@RequestMapping(value="templates/{page}", method = { RequestMethod.GET, RequestMethod.POST })
	public String template(@PathVariable("page") String page, Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
				
		return ThemeManager.getInstance().getAdminThemePath() + "/filemanager/templates/"+page;
	}
	
	@RequestMapping(value="angularfilemanager", method = { RequestMethod.GET, RequestMethod.POST })
	public String angularfilemanager(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
				
		return ThemeManager.getInstance().getAdminThemePath() + "/filemanager/angularfilemanager";
	}
}

package com.beanframework.filemanager.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.common.AdminBaseController;

@Controller
@RequestMapping(FilemanagerWebConstants.PATH_ROOT)
public class FilemanagerController extends AdminBaseController {

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public String filemanager(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return page(FilemanagerWebConstants.PAGE_CONTAINER, model, allRequestParams);
	}

	@RequestMapping(value = FilemanagerWebConstants.PATH_TEMPLATES, method = { RequestMethod.GET, RequestMethod.POST })
	public String template(@PathVariable("page") String page, Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		return page(FilemanagerWebConstants.PAGE_TEMPLATES + "/" + page, model, allRequestParams);
	}

	@RequestMapping(value = FilemanagerWebConstants.PATH_ANGULARFILEMANAGER, method = { RequestMethod.GET, RequestMethod.POST })
	public String angularfilemanager(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		return page(FilemanagerWebConstants.PAGE_ANGULARFILEMANAGER, model, allRequestParams);
	}
}

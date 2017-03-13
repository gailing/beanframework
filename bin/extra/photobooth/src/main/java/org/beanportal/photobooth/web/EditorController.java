package com.beanframework.photobooth.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/editor")
public class EditorController extends CommonController{

	@RequestMapping(method = RequestMethod.GET)
	public String editor(Model model, HttpServletRequest request) {
		
		if(!isFacebookConnected()){
			return "redirect:/connect/facebook";
		}
		
		String src = request.getParameter("src");
		
		model.addAttribute("selectedPhoto", FilenameUtils.getName(src));

		return THEME_PAGE + "/editor";
	}
}

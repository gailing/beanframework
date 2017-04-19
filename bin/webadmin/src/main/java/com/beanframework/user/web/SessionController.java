package com.beanframework.user.web;

import java.text.MessageFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.common.AdminBaseController;
import com.beanframework.user.utils.UserManager;

@Controller
@RequestMapping(UserWebConstants.PATH_ROOT)
public class SessionController extends AdminBaseController {

	public static final String FORM_USERNAME = "username";
	public static final MessageFormat FORM_MESSAGE_EXPIRE_USER = new MessageFormat("User [{0}] sessions has been successfully expired.");

	@Autowired
	private UserManager userManager;

	@RequestMapping(UserWebConstants.PATH_SESSION)
	public String list(Model model, @RequestParam Map<String, Object> allRequestParams) {

		model.addAttribute(MODEL_ATTRIBUTE_PAGE, userManager.getAllUserSession());

		return UserWebConstants.PAGE_SESSION;
	}

	@RequestMapping(value = UserWebConstants.PATH_EXPIRE_USER, method = RequestMethod.POST)
	public String remove(Model model, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) {

		String username = getParamValue(FORM_USERNAME, allRequestParams);

		userManager.expireAllSessionsByUsername(username);

		model.addAttribute(PARAM_MESSAGE, FORM_MESSAGE_EXPIRE_USER.format(new Object[] { username }));

		return redirect(UserWebConstants.PATH_ROOT + UserWebConstants.PATH_SESSION, model, redirectAttributes);
	}

}

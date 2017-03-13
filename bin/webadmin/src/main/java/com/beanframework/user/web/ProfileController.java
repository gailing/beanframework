package com.beanframework.user.web;

import java.util.Map;

import com.beanframework.common.AdminBaseController;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserFacade;
import com.beanframework.user.utils.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(UserWebConstants.PATH_ROOT_PROFILE)
public class ProfileController extends AdminBaseController {

	public static final String FORM_USER = "user";
	public static final String FORM_FIRSTNAME = "firstName";
	public static final String FORM_LASTNAME = "lastName";
	public static final String FORM_EMAIL = "email";
	public static final String FORM_PASSWORD = "password";

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private UserManager userManager;

	@RequestMapping
	public String profile(Model model, @RequestParam Map<String, Object> allRequestParams) throws Exception {

		model.addAttribute(FORM_USER, userManager.getCurrentUser());

		return page(UserWebConstants.PAGE_PROFILE, model, allRequestParams);
	}

	@RequestMapping(value = UserWebConstants.PATH_PROFILE_SAVE, method = RequestMethod.POST)
	public String profile(Model model, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) throws Exception {

		String firstName = getParamValue(FORM_FIRSTNAME, allRequestParams);
		String lastName = getParamValue(FORM_LASTNAME, allRequestParams);
		String email = getParamValue(FORM_EMAIL, allRequestParams);
		String password = getParamValue(FORM_PASSWORD, allRequestParams);

		User user = userManager.getCurrentUser();

		user.setEmail(email);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);

		user = userFacade.saveUser(user);
		model.addAttribute(PARAM_MESSAGE, "Your profile has been successfully updated.");

		return redirect(UserWebConstants.PATH_ROOT_PROFILE, model, redirectAttributes);
	}

}

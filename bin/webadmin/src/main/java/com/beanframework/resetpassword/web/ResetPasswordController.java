package com.beanframework.resetpassword.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.common.AdminBaseController;
import com.beanframework.resetpassword.service.ResetPasswordService;
import com.beanframework.user.EmailNonExistsException;

@Controller
@RequestMapping(ResetPasswordWebConstants.PATH_ROOT_RESET_PASSWORD)
public class ResetPasswordController extends AdminBaseController {

	public static final String FORM_EMAIL = "email";
	public static final String FORM_SENT = "sent";
	public static final String FORM_RESET = "reset";
	
	@Autowired
	private ResetPasswordService resetPasswordService;

	@RequestMapping
	public String view(Model model, @RequestParam Map<String, Object> allRequestParams) throws Exception {

		String formSent = getParamValue(FORM_SENT, allRequestParams);

		if (StringUtils.isEmpty(formSent)) {
			model.addAttribute(FORM_SENT, false);
		} else {
			model.addAttribute(FORM_SENT, true);
		}

		return ResetPasswordWebConstants.PAGE_RESET_PASSWORD;
	}

	@RequestMapping(value = ResetPasswordWebConstants.PATH_SEND, method = RequestMethod.POST)
	public String send(HttpServletRequest request, Model model, HttpServletRequest req, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes)
			throws Exception {

		String email = getParamValue(FORM_EMAIL, allRequestParams);

		try {

			String domain = 80 == request.getServerPort() ? request.getServerName() : request.getServerName() + ":" + request.getServerPort();
			resetPasswordService.sendResetPasswordToken(email, request.getServerName(), domain);
			model.addAttribute(PARAM_MESSAGE, "We've sent a password reset link to your email address.");
			model.addAttribute(FORM_SENT, true);
		} catch (EmailNonExistsException e) {
			model.addAttribute(PARAM_ERROR, "No account found with that email address.");
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
		}

		return redirect(ResetPasswordWebConstants.PATH_ROOT_RESET_PASSWORD, model, redirectAttributes);
	}

	@RequestMapping(value = ResetPasswordWebConstants.PATH_RESET)
	public String resetForm(Model model, HttpServletRequest req, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) throws Exception {

		String formSent = getParamValue(FORM_SENT, allRequestParams);

		if (StringUtils.isEmpty(formSent)) {
			model.addAttribute(FORM_RESET, false);
		} else {
			model.addAttribute(FORM_RESET, true);
		}

		return redirect(ResetPasswordWebConstants.PAGE_RESET_PASSWORD_TOKEN, model, redirectAttributes);
	}

	@RequestMapping(value = ResetPasswordWebConstants.PATH_RESET, method = RequestMethod.POST)
	public String reset(Model model, HttpServletRequest req, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) throws Exception {

		model.addAttribute(FORM_RESET, true);

		return redirect(ResetPasswordWebConstants.PAGE_RESET_PASSWORD_TOKEN, model, redirectAttributes);
	}

}

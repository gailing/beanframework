package com.beanframework.user.service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.beanframework.common.AdminBaseService;
import com.beanframework.email.domain.Email;
import com.beanframework.email.service.EmailService;
import com.beanframework.platform.oplog.service.OpLogFacade;
import com.beanframework.user.EmailNonExistsException;
import com.beanframework.user.PasswordTokenInvalidException;
import com.beanframework.user.domain.PasswordToken;
import com.beanframework.user.domain.User;
import com.beanframework.user.web.ResetPasswordWebConstants;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

@Component
public class ResetPasswordService extends AdminBaseService{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordTokenService passwordTokenService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private OpLogFacade logFacade;
	
	public void sendResetPasswordToken(String email, String site, String domain) throws Exception {
		if (userService.isEmailExists(email)) {
			User user = userService.findByEmail(email);

			PasswordToken passwordToken = new PasswordToken();
			passwordToken.setUser(user);
			passwordToken.setToken(UUID.randomUUID());
			passwordToken = passwordTokenService.save(passwordToken);

			// prepare data
			Map<String, String> data = new HashMap<>();
			data.put("productName", site);
			data.put("name", user.getFirstName() + " " + user.getLastName());
			data.put("actionUrl", domain + ResetPasswordWebConstants.PATH_RESET_PASSWORD + "?token=" + passwordToken.getToken().toString());

			// get template
			// Create your Configuration instance, and specify if up to what
			// FreeMarker
			// version (here 2.3.25) do you want to apply the fixes that are not
			// 100%
			// backward-compatible. See the Configuration JavaDoc for details.
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_24);

			// Specify the source where the template files come from. Here I set
			// a
			// plain directory for it, but non-file-system sources are possible
			// too:
			// cfg.setDirectoryForTemplateLoading(new
			// File("/where/you/store/templates"));
			cfg.setClassForTemplateLoading(this.getClass(), "/templates");

			// Set the preferred charset template files are stored in. UTF-8 is
			// a good choice in most applications:
			cfg.setDefaultEncoding("UTF-8");

			// Sets how errors will appear.
			// During web page *development*
			// TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

			// False if Don't log exceptions inside FreeMarker that it will
			// thrown at you anyway:
			cfg.setLogTemplateExceptions(true);

			Template t = cfg.getTemplate(ResetPasswordWebConstants.RESET_PASSWORD_TEMPLATE);

			String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(t, data);

			Email emailToSend = new Email();
			emailToSend.setToRecipients(user.getEmail());
			emailToSend.setSubject(ResetPasswordWebConstants.RESET_PASSWORD_EMAIL_SUBJECT);
			emailToSend.setContent(emailContent);

			emailService.sendEmail(emailToSend);

			logFacade.log(ResetPasswordWebConstants.SYSTEM_RESET_PASSWORD_CHANNEL, ResetPasswordWebConstants.SYSTEM_RESET_PASSWORD_OPERATE,
					new MessageFormat(ResetPasswordWebConstants.SYSTEM_RESET_PASSWORD_EMAIL_QUEUE).format(new Object[] { user.getEmail() }));
		} else {
			logFacade.log(ResetPasswordWebConstants.SYSTEM_RESET_PASSWORD_CHANNEL, ResetPasswordWebConstants.SYSTEM_RESET_PASSWORD_OPERATE,
					new MessageFormat(ResetPasswordWebConstants.SYSTEM_RESET_PASSWORD_EMAIL_NOT_EXISTS).format(new Object[] { email }));
			throw new EmailNonExistsException("Email not exits.");
		}
	}

	public void resetPassword(String token, String password) {
		PasswordToken passwordToken = passwordTokenService.findByToken(UUID.fromString(token));

		if (passwordToken == null) {
			throw new PasswordTokenInvalidException("Invalid password token");
		}

	}

}

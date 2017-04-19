package com.beanframework.user.security;

import java.text.MessageFormat;
import java.util.Date;

import com.beanframework.platform.oplog.service.OpLogFacade;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;
import com.beanframework.user.utils.PasswordUtils;
import com.beanframework.user.utils.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class UserAuthProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Autowired
	private OpLogFacade logFacade;

	@Autowired
	private UserManager userManager;
	
	@Autowired
	private UserAuthorityService userAuthorityService;

	private static final String channel = UserConstants.SYSTEM_SECURITY_CHANNEL;
	private static final String operate = UserConstants.SYSTEM_SECURITY_OPERATE_LOGIN;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		User user = userService.findByUsername(username);

		if (user == null) {
			logFacade.log(channel, operate, new MessageFormat(UserConstants.SYSTEM_SECURITY_USERNAME_NOT_FOUND).format(new Object[] { username }));
			throw new BadCredentialsException(UserConstants.MESSAGE_WRONG_USERNAME);
		}
		
		if(password.isEmpty()){
			throw new BadCredentialsException(UserConstants.MESSAGE_BLANK_PASSWORD);
		}

		if (!PasswordUtils.isMatch(password, user.getPassword())) {

				if (userService.updateLoginAttemptsAndIsLocked(user.getUuid())) {
					logFacade.log(channel, operate, new MessageFormat(UserConstants.SYSTEM_SECURITY_ACCOUNT_NON_LOCKED).format(new Object[] { username }));
					throw new DisabledException(UserConstants.MESSAGE_ACCOUNT_NON_LOCKED);
				}

			logFacade.log(channel, operate, new MessageFormat(UserConstants.SYSTEM_SECURITY_PASSWORD_WRONG).format(new Object[] { username }));
			throw new BadCredentialsException(UserConstants.MESSAGE_WRONG_PASSWORD);
		}
		
		if (!user.isEnabled()) {
			logFacade.log(channel, operate, new MessageFormat(UserConstants.SYSTEM_SECURITY_ACCOUNT_DISABLED).format(new Object[] { username }));
			throw new DisabledException(UserConstants.MESSAGE_ACCOUNT_DISABLED);
		}

		if (!user.isAccountNonExpired()) {
			logFacade.log(channel, operate, new MessageFormat(UserConstants.SYSTEM_SECURITY_ACCOUNT_NON_EXPIRED).format(new Object[] { username }));
			throw new DisabledException(UserConstants.MESSAGE_ACCOUNT_NON_EXPIRED);
		}

		if (!user.isAccountNonLocked()) {
			logFacade.log(channel, operate, new MessageFormat(UserConstants.SYSTEM_SECURITY_ACCOUNT_NON_LOCKED).format(new Object[] { username }));
			throw new DisabledException(UserConstants.MESSAGE_ACCOUNT_NON_LOCKED);
		}

		if (!user.isCredentialsNonExpired()) {
			logFacade.log(channel, operate, new MessageFormat(UserConstants.SYSTEM_SECURITY_ACCOUNT_CREDENTIALS_NON_EXPIRED).format(new Object[] { username }));
			throw new DisabledException(UserConstants.MESSAGE_ACCOUNT_DISABLED);
		}

		userManager.validateMaxUserSession(user);
		userService.resetLoginAttempts(user.getUuid());

		userService.updateLastLogonDateById(new Date(), user.getUuid());

		logFacade.log(channel, operate, new MessageFormat(UserConstants.SYSTEM_SECURITY_LOGIN_SUCCESS).format(new Object[] { username }));

		user.setAuthorities(userAuthorityService.getUserAuthorities(user));
		return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	}

	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}

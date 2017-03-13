package com.beanframework.user.security;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.AdminBaseConstants;
import com.beanframework.user.config.AdminSecurityConfig;
import com.beanframework.user.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class SuperAdminAuthProvider implements AuthenticationProvider {

	@Value(AdminBaseConstants.SUPER_ADMIN_DEFAULT_USERNAME)
	private String DEFAULT_SUPER_ADMIN_USERNAME;

	@Value(AdminBaseConstants.SUPER_ADMIN_DEFAULT_PASSWORD)
	private String DEFAULT_SUPER_ADMIN_PASSWORD;
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		if (!username.equals(DEFAULT_SUPER_ADMIN_USERNAME) || !password.equals(DEFAULT_SUPER_ADMIN_PASSWORD)) {
			throw new BadCredentialsException("Wrong username or password.");
		}

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);
		authorities.add(new SimpleGrantedAuthority(AdminSecurityConfig.SUPER_ADMIN_ROLE));

		User user = new User();
		user.setFirstName("Super Admin");
		user.setUsername(username);

		user.setAuthorities(authorities);
		return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	}

	public boolean supports(Class<? extends Object> authentication) {
		return true;
	}
}

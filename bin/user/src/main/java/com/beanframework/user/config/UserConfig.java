package com.beanframework.user.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableJpaAuditing
@EnableCaching
@EnableAsync
public class UserConfig {
	
	@Bean
	public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}
	
	@Bean
	public static SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public AuditorAware<String> createAuditorProvider() {
		return new SecurityAuditor();
	}

	@Bean
	public AuditingEntityListener createAuditingListener() {
		return new AuditingEntityListener();
	}

	public static class SecurityAuditor implements AuditorAware<String> {
		@Override
		public String getCurrentAuditor() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (auth != null && auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {

				org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) auth
						.getPrincipal();
				String username = userDetails.getUsername();

				return username;
			}

			return null;

		}
	}

}

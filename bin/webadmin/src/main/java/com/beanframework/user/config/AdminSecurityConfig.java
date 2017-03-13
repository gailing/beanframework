package com.beanframework.user.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.beanframework.common.AdminBaseConstants;
import com.beanframework.user.security.AdminSuccessHandler;
import com.beanframework.user.security.ConsoleSuccessHandler;
import com.beanframework.user.security.CsrfHeaderFilter;
import com.beanframework.user.security.SuperAdminAuthProvider;
import com.beanframework.user.security.UserAuthProvider;

@Configuration
@EnableWebSecurity
public class AdminSecurityConfig{
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String REMEMBER_ME_COOKIE_NAME = "REMEMBER_ME";
	
	public static final String ADMIN_ROLE = "ADMIN";
	
	public static final String SUPER_ADMIN_ROLE = "SUPERADMIN";
	
	public static CsrfHeaderFilter csrfHeaderFilter(String path){
    	CsrfHeaderFilter csrfHeaderFilter = new CsrfHeaderFilter();
    	csrfHeaderFilter.setPath(path);
    	return csrfHeaderFilter;
    }
    
	public static CsrfTokenRepository csrfTokenRepository() {
    	HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    	repository.setHeaderName("X-XSRF-TOKEN");
    	return repository;
    }
	
	@Configuration
    @Order(1)                                                        
    public static class DefaultSuperAdminWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		
		@Value(AdminBaseConstants.PATH_CONSOLE)
		private String PATH_ADMIN_CONSOLE;
		
		@Autowired
		private SuperAdminAuthProvider defaultSuperAdminAuthProvider;
		
		@Autowired
		private UserAuthProvider userAuthProvider;
		
		@Autowired
		private ConsoleSuccessHandler successHandler;
		
		@Autowired
		private SessionRegistry sessionRegistry;
		
        protected void configure(HttpSecurity http) throws Exception {
        	http
        		.antMatcher(PATH_ADMIN_CONSOLE+"/**")
    	        .authorizeRequests()
    	        	.antMatchers(PATH_ADMIN_CONSOLE+"/**").authenticated()
    	        	.antMatchers(PATH_ADMIN_CONSOLE+"/**").hasAnyAuthority(SUPER_ADMIN_ROLE)
    	        	.and()
    	        .addFilterAfter(csrfHeaderFilter(PATH_ADMIN_CONSOLE), CsrfFilter.class)
    	        .csrf().csrfTokenRepository(csrfTokenRepository())
		        	.and()
    	        .formLogin()
            		.loginPage(PATH_ADMIN_CONSOLE+"/login")
	                .successHandler(successHandler)
	                .failureUrl(PATH_ADMIN_CONSOLE+"/login?error")
	                .usernameParameter("username")
	                .passwordParameter("password")
	                .permitAll()
	                .and()
	            .logout()
	            	.logoutUrl(PATH_ADMIN_CONSOLE+"/logout")
	            	.logoutSuccessUrl(PATH_ADMIN_CONSOLE+"/login?logout")
	            	.invalidateHttpSession(true)
	            	.deleteCookies(REMEMBER_ME_COOKIE_NAME)
	               	.permitAll()
	               	.and()
	            .exceptionHandling()
	        		.accessDeniedPage(PATH_ADMIN_CONSOLE+"/login?denied")
	        		.and()
	            .rememberMe()
	        		.rememberMeParameter("rememberme")
	        		.rememberMeCookieName(REMEMBER_ME_COOKIE_NAME)
	        		.tokenValiditySeconds(60*60*24*30)
	        		.and()
	        	.sessionManagement()
	        		.maximumSessions(-1)
	        		.sessionRegistry(sessionRegistry)
	        		.maxSessionsPreventsLogin(true)
	        		.expiredUrl(PATH_ADMIN_CONSOLE+"/login?expired");
        }
        
        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
    		auth.authenticationProvider(defaultSuperAdminAuthProvider);
    		auth.authenticationProvider(userAuthProvider);
        }
    }
	
	
	@Configuration
    @Order(2)                                                        
    public static class AdminWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		
		@Value(AdminBaseConstants.PATH_ADMIN)
		private String PATH_ADMIN;
		
		@Autowired
		private SuperAdminAuthProvider defaultSuperAdminAuthProvider;
		
		@Autowired
		private UserAuthProvider userAuthProvider;
		
		@Autowired
		private AdminSuccessHandler successHandler;
		
		@Autowired
		private SessionRegistry sessionRegistry;
		
        protected void configure(HttpSecurity http) throws Exception {
        	http
        		.antMatcher(PATH_ADMIN+"/**")
		        .authorizeRequests()
		        	.antMatchers(PATH_ADMIN+"/login/**").permitAll()
		        	.antMatchers(PATH_ADMIN+"/logout/**").permitAll()
		        	.antMatchers(PATH_ADMIN+"/resetpassword/**").permitAll()
		        	.antMatchers(PATH_ADMIN+"/register/**").permitAll()
		        	.antMatchers(PATH_ADMIN+"/**").authenticated()
		        	.antMatchers(PATH_ADMIN+"/**").hasAnyAuthority(ADMIN_ROLE, SUPER_ADMIN_ROLE)
		        	.and()
		        .addFilterAfter(csrfHeaderFilter(PATH_ADMIN), CsrfFilter.class)
		        .csrf().csrfTokenRepository(csrfTokenRepository())
		        	.and()
		        .formLogin()
	                .loginPage(PATH_ADMIN+"/login")
	                .successHandler(successHandler)
	                .failureUrl(PATH_ADMIN+"/login?error")
	                .usernameParameter("username")
	                .passwordParameter("password")
	                .permitAll()
	                .and()
	            .logout()
	            	.logoutUrl(PATH_ADMIN+"/logout")
	            	.logoutSuccessUrl(PATH_ADMIN+"/login?logout") 
	            	.invalidateHttpSession(true)
	            	.deleteCookies(REMEMBER_ME_COOKIE_NAME)
	               	.permitAll()
	        		.and()
	        	.exceptionHandling()
	        		.accessDeniedPage(PATH_ADMIN+"/login?denied")
	        		.and()
	        	.rememberMe()
	        		.rememberMeParameter("rememberme")
	        		.rememberMeCookieName(REMEMBER_ME_COOKIE_NAME)
	        		.tokenValiditySeconds(60*60*24*30)
	        		.and()
	        	.sessionManagement()
	        		.maximumSessions(-1)
	        		.sessionRegistry(sessionRegistry)
	        		.maxSessionsPreventsLogin(true)
	        		.expiredUrl(PATH_ADMIN+"/login?expired");
        	http.headers().frameOptions().sameOrigin();
        }
        
        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
        	auth.authenticationProvider(defaultSuperAdminAuthProvider);
    		auth.authenticationProvider(userAuthProvider);
        }
    }
}
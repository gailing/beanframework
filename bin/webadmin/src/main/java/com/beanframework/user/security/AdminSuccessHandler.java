package com.beanframework.user.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beanframework.common.AdminBaseConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

@Component
public class AdminSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Value(AdminBaseConstants.PATH_ADMIN)
	private String PATH_ADMIN;
	
	private RequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		
		DefaultSavedRequest savedRequest = (DefaultSavedRequest) requestCache.getRequest(request, response);

		if(savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET")){
			super.onAuthenticationSuccess(request, response, authentication);
		}
		else{
			getRedirectStrategy().sendRedirect(request, response, PATH_ADMIN);
		}
	}
}

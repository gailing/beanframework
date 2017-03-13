package com.beanframework.exception.handler;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import com.beanframework.platform.core.base.BaseController;
import com.beanframework.theme.ThemeManager;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Controller
class DefaultExceptionHandler extends BaseController{
	private static final String PATH = "/error";
	public static final String DEFAULT_ERROR_VIEW = ThemeManager.getInstance().getAdminThemePath()+"/error";

	@RequestMapping(value = PATH)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e) throws Exception {
		// If the exception is annotated with @ResponseStatus rethrow it and let
		// the framework handle it - like the OrderNotFoundException example
		// at the start of this post.
		// AnnotationUtils is a Spring Framework utility class.
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;

		// Otherwise setup and send the user to a default error-view.
		ModelAndView mav = new ModelAndView();
		
		String trace = (String) getErrorAttributes(req).get("trace");
		String url = req.getRequestURL().toString();
		Date timestamp = new Date();
		int status = res.getStatus();
		String error = (String) getErrorAttributes(req).get("error");
		String message = (String) getErrorAttributes(req).get("message");
		
		if(StringUtils.isNotEmpty(trace)){
			logger.info(trace);
		}
		
		mav.addObject("exception", e);
		mav.addObject("url", url);
		mav.addObject("timestamp", timestamp);
		mav.addObject("status", status);
		mav.addObject("error", error);
		mav.addObject("message", message);
		mav.addObject("trace", trace);
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}

	// Total control - setup a model and return the view name yourself. Or consider
	// subclassing ExceptionHandlerExceptionResolver (see below).
	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest req, HttpServletResponse res, Exception e) {
		logger.error("Request: " + req.getRequestURL() + " raised " + e, e);

		ModelAndView mav = new ModelAndView();
		
		String trace = (String) getErrorAttributes(req).get("trace");
		String url = req.getRequestURL().toString();
		Date timestamp = new Date();
		int status = res.getStatus();
		String error = (String) getErrorAttributes(req).get("error");
		String message = (String) getErrorAttributes(req).get("message");
		
		if(StringUtils.isNotEmpty(trace)){
			logger.info(trace);
		}
		
		mav.addObject("exception", e);
		mav.addObject("url", url);
		mav.addObject("timestamp", timestamp);
		mav.addObject("status", status);
		mav.addObject("error", error);
		mav.addObject("message", message);
		mav.addObject("trace", trace);
		
		return mav;
	}


}
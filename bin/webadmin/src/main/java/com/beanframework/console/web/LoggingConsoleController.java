package com.beanframework.console.web;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.common.AdminBaseController;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

@Controller
@RequestMapping(ConsoleWebConstants.PATH_ROOT)
public class LoggingConsoleController extends AdminBaseController{

	@Value(ConsoleWebConstants.VAR_DIR)
	private String VAR_DIR;

	@RequestMapping(value = ConsoleWebConstants.PATH_LOGGING, method = { RequestMethod.GET, RequestMethod.POST })
	public String logging(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return redirect(ConsoleWebConstants.PATH_ROOT+ConsoleWebConstants.PATH_LOGGING_TAIL+"?level=all", model, redirectAttributes);
	}

	@RequestMapping(value = ConsoleWebConstants.PATH_LOGGING_TAIL, method = { RequestMethod.GET, RequestMethod.POST })
	public String tail(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return page(ConsoleWebConstants.PAGE_LOGGING_TAIL, model, allRequestParams);
	}

	@RequestMapping(value = ConsoleWebConstants.PATH_LOGGING_DOWNLOAD, method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<byte[]> download(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) {

		String level = request.getParameter("level");

		HttpHeaders headers = new HttpHeaders();

		try {
			String fileName = level + ".log";
			headers.setContentDispositionFormData(fileName, fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			// 获取物理路径
			File file = new File(VAR_DIR + "/log/" + fileName);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = ConsoleWebConstants.PATH_LOGGING_LEVEL, method = { RequestMethod.GET, RequestMethod.POST })
	public String level(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
		List<Logger> loggers = loggerContext.getLoggerList();
		
		model.addAttribute("loggers", loggers);

		return ConsoleWebConstants.PAGE_LOGGING_LEVEL;
	}

//	List<String> findNamesOfConfiguredAppenders() {
//		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//		List<String> strList = new ArrayList<String>();
//		for (ch.qos.logback.classic.Logger log : lc.getLoggerList()) {
//			if (log.getLevel() != null || hasAppenders(log)) {
//				strList.add(log.getName());
//			}
//		}
//		return strList;
//	}
//
//	boolean hasAppenders(ch.qos.logback.classic.Logger logger) {
//		Iterator<Appender<ILoggingEvent>> it = logger.iteratorForAppenders();
//		return it.hasNext();
//	}
}

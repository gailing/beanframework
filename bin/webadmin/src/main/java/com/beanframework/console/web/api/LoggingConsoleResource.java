package com.beanframework.console.web.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.beanframework.console.web.ConsoleWebConstants;
import com.beanframework.platform.core.base.BaseResource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

@RestController
@RequestMapping(ConsoleWebConstants.PATH_API_ROOT)
public class LoggingConsoleResource extends BaseResource {

	@Value(ConsoleWebConstants.VAR_DIR)
	private String VAR_DIR;

	@RequestMapping(ConsoleWebConstants.PATH_API_LOGGING_TAIL)
	public LogFile tail(Model model, HttpServletRequest request) throws Exception {

		String totalLine = request.getParameter("totalLine");
		String creationTime = request.getParameter("creationTime");

		String level = request.getParameter("level");

		String logFilePath;

		if (StringUtils.isEmpty(level)) {
			logFilePath = VAR_DIR + "/log/all.log";
		} else {
			logFilePath = VAR_DIR + "/log/" + level + ".log";
		}

		Path path = Paths.get(logFilePath);
		BasicFileAttributes attr;
		FileTime ft = null;
		int count = 0;

		StringBuffer sb = new StringBuffer();

		BufferedReader br = null;

		String content = "";

		try {
			attr = Files.readAttributes(path, BasicFileAttributes.class);
			ft = attr.creationTime();

			String sCurrentLine;
			br = new BufferedReader(new FileReader(logFilePath));

			List<String> lines = new ArrayList<String>();
			while ((sCurrentLine = br.readLine()) != null) {
				count++;

				if (StringUtils.isNotEmpty(totalLine) && StringUtils.isNotEmpty(creationTime)) {
					int lineStart = Integer.valueOf(totalLine);
					if (count > lineStart && creationTime.equals(ft.toString())) {
						sb.append(sCurrentLine + System.getProperty("line.separator"));
					}
				} else {
					lines.add(sCurrentLine);
				}
			}

			if (lines.isEmpty()) {
				content = sb.toString();
			} else {
				int line = Integer.valueOf(request.getParameter("line"));
				for (int i = 1; i <= line; i++) {
					content = lines.get(lines.size() - i) + System.getProperty("line.separator") + content;
				}
			}

			br.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			if (br != null) {
				br.close();
			}
		}

		return new LogFile(StringEscapeUtils.escapeHtml4(content), count, ft.toString());
	}

	public class LogFile {
		private String content;
		private int totalLine;
		private String creationTime;

		public LogFile(String content, int totalLine, String creationTime) {
			super();
			this.content = content;
			this.totalLine = totalLine;
			this.creationTime = creationTime;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getTotalLine() {
			return totalLine;
		}

		public void setTotalLine(int totalLine) {
			this.totalLine = totalLine;
		}

		public String getCreationTime() {
			return creationTime;
		}

		public void setCreationTime(String creationTime) {
			this.creationTime = creationTime;
		}
	}
	
	@RequestMapping(ConsoleWebConstants.PATH_API_LOGGING_LEVEL)
	public void level(Model model, HttpServletRequest request) throws Exception {
		String loggerName = request.getParameter("logger");
		String level = request.getParameter("level");
		
		LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
		Logger logger = loggerContext.getLogger(loggerName);
		logger.setLevel(Level.toLevel(level));
	}
}

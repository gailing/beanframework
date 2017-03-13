package com.beanframework.platform.core.base;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UrlPathHelper;

public class BaseController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected UrlPathHelper urlPathHelper = new UrlPathHelper();

	public static final SimpleDateFormat dateRangeFormat = new SimpleDateFormat("yyyy-MM-dd, hh:mm a");

	public static final String PARAM_PAGE = "p";
	public static final String PARAM_QUERY = "q";
	public static final String PARAM_QUERY_ALL = "all";
	public static final String PARAM_KEYWORD = "k";
	public static final String PARAM_DIRECTION = "d";
	public static final String PARAM_FIELD = "f";
	public static final String PARAM_MESSAGE = "message";
	public static final String PARAM_ERROR = "error";
	public static final String PARAM_SUCCESS = "success";
	public static final String MODEL_ATTRIBUTE_PAGE = "page";
	public static final String PARAM_DELIMITER = ",";
	public static final String PARAM_DATE_RANGE = "dr";

	@Autowired
	private ErrorAttributes errorAttributes;

	public String getPathValue(HttpServletRequest request) {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

		AntPathMatcher apm = new AntPathMatcher();
		String finalPath = apm.extractPathWithinPattern(bestMatchPattern, path);

		return finalPath;
	}

	protected Map<String, Object> getErrorAttributes(HttpServletRequest request) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return errorAttributes.getErrorAttributes(requestAttributes, true);
	}

	protected boolean isEmpty(String field, String fieldName, Model model) {

		if (field.isEmpty()) {
			model.addAttribute(PARAM_ERROR, fieldName + " cannot be empty");
			return true;
		} else {
			return false;
		}
	}

	protected boolean isNotMatch(String fieldSource, String fieldTarget, String fieldSourceName, String fieldTargetName, Model model) {

		if (fieldSource.equals(fieldTarget)) {
			return false;
		} else {
			model.addAttribute(PARAM_ERROR, fieldSourceName + " is not match with " + fieldTargetName);
			return true;
		}
	}

	public boolean parseBoolean(String s) {
		return ((s != null) && (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("1") || s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("on")));
	}

	public boolean parseBoolean(String name, Map<String, Object> allRequestParams) {
		String s = getParamValue(name, allRequestParams);
		return ((s != null) && (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("1") || s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("on")));
	}

	protected void convertModelToRedirectAttributes(Model model, RedirectAttributes redirectAttributes) {
		Iterator<Entry<String, Object>> iterator = model.asMap().entrySet().iterator();
		while (iterator.hasNext()) {
			try {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
				if (entry.getValue() instanceof String || entry.getValue() instanceof Integer) {
					redirectAttributes.addAttribute(entry.getKey(), entry.getValue());
				}
			} catch (Exception e) {
				// Do nothing
			}
		}
	}

	public void getAlertMessage(Model model, Map<String, Object> allRequestParams) {
		String message = getParamValue(PARAM_MESSAGE, allRequestParams);
		if (!StringUtils.isEmpty(message)) {
			model.addAttribute(PARAM_MESSAGE, message);
		}

		String error = getParamValue(PARAM_ERROR, allRequestParams);
		if (!StringUtils.isEmpty(error)) {
			model.addAttribute(PARAM_ERROR, error);
		}
	}

	public String getParamValue(String name, Map<String, Object> allRequestParams) {

		for (String key : allRequestParams.keySet()) {
			if (key.equals(name)) {

				Object obj = allRequestParams.get(key);

				if (obj instanceof String[]) {
					String[] vals = (String[]) obj;
					return String.join(PARAM_DELIMITER, vals);
				}

				if (obj instanceof String) {
					String val = (String) obj;

					if (StringUtils.isEmpty(val)) {
						return null;
					} else {
						return val.trim();
					}
				}

			}
		}

		return null;
	}

	public UUID[] convertToUUID(String[] ids) {

		if (ids == null || ids.length == 0) {
			return null;
		}

		UUID[] uuids = new UUID[ids.length];

		for (int i = 0; i < uuids.length; i++) {
			uuids[i] = UUID.fromString(ids[i]);
		}

		return uuids;
	}

	@Autowired
	private Environment env;

	public String redirect(String path, Model model, RedirectAttributes redirectAttributes) {

		convertModelToRedirectAttributes(model, redirectAttributes);

		String rx = "\\$\\{([^}]*)\\}";

		StringBuffer formattedPath = new StringBuffer();
		Pattern p = Pattern.compile(rx);
		Matcher m = p.matcher(path);

		while (m.find()) {
			// Avoids throwing a NullPointerException in the case that you
			// Don't have a replacement defined in the map for the match
			String repString = env.getProperty(m.group(1));
			if (repString != null)
				m.appendReplacement(formattedPath, repString);
		}
		m.appendTail(formattedPath);

		return "redirect:" + formattedPath.toString();
	}

	public String forward(String path, Model model) {

		String rx = "\\$\\{([^}]*)\\}";

		StringBuffer formattedPath = new StringBuffer();
		Pattern p = Pattern.compile(rx);
		Matcher m = p.matcher(path);

		while (m.find()) {
			// Avoids throwing a NullPointerException in the case that you
			// Don't have a replacement defined in the map for the match
			String repString = env.getProperty(m.group(1));
			if (repString != null)
				m.appendReplacement(formattedPath, repString);
		}
		m.appendTail(formattedPath);

		return "forward:" + formattedPath.toString();
	}

	public String page(String path, Model model, Map<String, Object> allRequestParams) {
		String page = getParamValue(PARAM_PAGE, allRequestParams);
		if (StringUtils.isEmpty(page)) {
			model.addAttribute(PARAM_PAGE, "");
		} else {
			model.addAttribute(PARAM_PAGE, page);
		}

		String query = getParamValue(PARAM_QUERY, allRequestParams);
		if (StringUtils.isEmpty(query)) {
			model.addAttribute(PARAM_QUERY, "");
		} else {
			model.addAttribute(PARAM_QUERY, query);
		}

		String keyword = getParamValue(PARAM_KEYWORD, allRequestParams);
		if (StringUtils.isEmpty(keyword)) {
			model.addAttribute(PARAM_KEYWORD, "");
		} else {
			model.addAttribute(PARAM_KEYWORD, keyword);
		}

		String direction = getParamValue(PARAM_DIRECTION, allRequestParams);
		if (StringUtils.isEmpty(direction)) {
			model.addAttribute(PARAM_DIRECTION, "");
		} else {
			model.addAttribute(PARAM_DIRECTION, direction);
		}

		String field = getParamValue(PARAM_FIELD, allRequestParams);
		if (StringUtils.isEmpty(field)) {
			model.addAttribute(PARAM_FIELD, "");
		} else {
			model.addAttribute(PARAM_FIELD, field);
		}

		String dateRange = getParamValue(PARAM_DATE_RANGE, allRequestParams);
		if (StringUtils.isEmpty(dateRange)) {
			model.addAttribute(PARAM_DATE_RANGE, "");
		} else {
			model.addAttribute(PARAM_DATE_RANGE, dateRange);
		}

		String message = getParamValue(PARAM_MESSAGE, allRequestParams);
		if (!StringUtils.isEmpty(message)) {
			model.addAttribute(PARAM_MESSAGE, message);
		}

		String error = getParamValue(PARAM_ERROR, allRequestParams);
		if (!StringUtils.isEmpty(error)) {
			model.addAttribute(PARAM_ERROR, error);
		}

		return path;
	}
}

package com.beanframework.email.web;
//package com.beanframework.sys.email.web;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.beanframework.common.base.BaseController;
//import com.beanframework.sys.email.constant.EmailConstant;
//import com.beanframework.sys.email.domain.Email;
//import com.beanframework.sys.email.service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(EmailConstant.PATH_API_ROOT)
//public class EmailRestController extends BaseController {
//
//	@Autowired
//	private EmailService emailService;
//
//	@RequestMapping(EmailConstant.PATH_API_LIST)
//	public List<Email> list(HttpServletRequest request, HttpServletResponse response,
//			@RequestParam Map<String, Object> allRequestParams) {
//
//		int pageIndex = 0;
//		
//		String page = getParamValue(PARAM_PAGE, allRequestParams);
//
//		if (!StringUtils.isEmpty(allRequestParams.get(PARAM_PAGE))) {
//			pageIndex = Integer.valueOf(page) - 1;
//		}
//		PageRequest pageRequest = new PageRequest(pageIndex, EmailConstant.API_LIST_SIZE, Sort.Direction.DESC,
//				"createdDate");
//
//		Page<Email> emails = emailService.findAll(pageRequest);
//
//		return emails.getContent();
//	}
//
//	@RequestMapping(EmailConstant.PATH_API_SAVE)
//	public String save(HttpServletRequest request, HttpServletResponse response,
//			@RequestParam Map<String, Object> allRequestParams) {
//
//		String to = getParamValue("to", allRequestParams);
//		String bcc = getParamValue("bcc", allRequestParams);
//		String subject = getParamValue("subject", allRequestParams);
//		String content = getParamValue("content", allRequestParams);
//
//		Email email = new Email();
//		email.setToRecipients(to);
//		email.setBccRecipients(bcc);
//		email.setSubject(subject);
//		email.setContent(content);
//
//		emailService.sendEmail(email);
//
//		return "true";
//	}
//
//}

package com.beanframework.email.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.beanframework.email.EmailConstant;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailRepository;
import com.beanframework.platform.core.base.BaseService;
import com.beanframework.platform.oplog.service.OpLogFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmailService extends BaseService {
	
	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private OpLogFacade logFacade;

	public Page<Email> findAll(Pageable pageable) {
		return emailRepository.findAll(pageable);
	}

	public Email sendEmail(Email email) {
		email.setStatus(Email.Status.PENDING);
		email.setCreatedDate(new Date());
		return emailRepository.save(email);
	}

	public String processAllEmails() {

		// Count old processing email
		int count = emailRepository.countByStatus(Email.Status.PROCESSING).intValue();

		int numberOfPendingEmails = 0;

		if (count < EmailConstant.numberOfEmailToProcess) {
			numberOfPendingEmails = EmailConstant.numberOfEmailToProcess - count;
		}

		if (numberOfPendingEmails > 0) {
			Pageable pageable = new PageRequest(0, numberOfPendingEmails, Sort.Direction.ASC, "createdDate");

			// Change pending email to processing email
			List<Email> pendingEmails = emailRepository.findByStatus(Email.Status.PENDING, pageable);

			for (Email email : pendingEmails) {
				email.setStatus(Email.Status.PROCESSING);
				emailRepository.save(email);
			}
		}

		// Get all processing email
		Pageable pageable = new PageRequest(0, EmailConstant.numberOfEmailToProcess, Sort.Direction.ASC, "createdDate");
		List<Email> processingEmails = emailRepository.findByStatus(Email.Status.PROCESSING, pageable);

		int sentEmail = 0;
		int failedEmail = 0;
		String result = null;

		if (processingEmails.size() > 0) {

			// Change processing email to sent email
			for (Email email : processingEmails) {

				try {
//					sendEmail(new String[] { email.getToRecipients() }, null, email.getSubject(), null, email.getContent());
					email.setStatus(Email.Status.SENT);
					emailRepository.save(email);
					sentEmail++;
				} catch (Exception e) {
					email.setStatus(Email.Status.FAILED);
					emailRepository.save(email);
					failedEmail++;
					logFacade.log(EmailConstant.SYSTEM_CHANNEL, EmailConstant.SYSTEM_OPERATE_SEND_EMAIL, e);
				}
			}
		}
		
		result = new MessageFormat(EmailConstant.SYSTEM_OPERATE_SEND_EMAIL_RESULT).format(new Object[] { sentEmail, failedEmail });
		logFacade.log(EmailConstant.SYSTEM_CHANNEL, EmailConstant.SYSTEM_OPERATE_SEND_EMAIL, result);

		return result;
	}
	
//	@Autowired
//	private org.springframework.mail.javamail.JavaMailSender javaMailSender;
//
//	@Value("${spring.mail.from.name}")
//	private String fromName;
//	
//	@Value("${spring.mail.from.email}")
//	private String fromEmail;
//
//	private void sendEmail(String[] to, String[] bcc, String subject, String text, String html) throws Exception {
//		javaMailSender.send(new MimeMessagePreparator() {
//			public void prepare(MimeMessage mimeMessage) throws Exception {
//				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//				message.setFrom(new InternetAddress(fromEmail, fromName));
//				if(to != null && to.length > 0){
//					message.setTo(to);
//				}
//				if(bcc != null && bcc.length > 0){
//					message.setTo(bcc);
//				}
//				message.setSubject(subject);
//				message.setText(html, html);
//			}
//		});
//	}

}

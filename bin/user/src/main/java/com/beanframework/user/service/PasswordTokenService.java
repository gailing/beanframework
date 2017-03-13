package com.beanframework.user.service;

import java.util.UUID;

import com.beanframework.platform.core.base.BaseService;
import com.beanframework.user.domain.PasswordToken;
import com.beanframework.user.domain.PasswordTokenRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PasswordTokenService extends BaseService {

	@Autowired
	private PasswordTokenRepository passwordTokenRepository;

	public boolean isPasswordTokenExists(UUID token) {
		return passwordTokenRepository.isPasswordTokenExists(token);
	}

	public PasswordToken findByToken(UUID token) {

		if (!isPasswordTokenExists(token)) {
			return null;
		}

		PasswordToken passwordToken = passwordTokenRepository.findByToken(token);
		Hibernate.initialize(passwordToken.getUser());

		return passwordToken;
	}

	public PasswordToken save(PasswordToken passwordToken) {
		return passwordTokenRepository.save(passwordToken);
	}

}

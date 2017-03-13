package com.beanframework.user.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, UUID>, JpaSpecificationExecutor<PasswordToken> {

	@Query(value = "select p from PasswordToken p where p.token = :token")
	PasswordToken findByToken(@Param("token") UUID token);

	@Query("select count(p) > 0 from PasswordToken p where p.token = :token")
	boolean isPasswordTokenExists(@Param("token") UUID token);
	

}
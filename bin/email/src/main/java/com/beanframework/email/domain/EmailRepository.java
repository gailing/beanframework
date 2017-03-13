package com.beanframework.email.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, UUID> {
	
	@Query(value = "select e from Email e where e.status = :status")
	List<Email> findByStatus(@Param("status") Email.Status status, Pageable pageable);
	
	@Query("select count(e) from Email e where e.status=:status")
    Long countByStatus(@Param("status") Email.Status status);
}
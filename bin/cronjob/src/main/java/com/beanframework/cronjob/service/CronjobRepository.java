package com.beanframework.cronjob.service;

import java.util.List;
import java.util.UUID;

import com.beanframework.cronjob.domain.Cronjob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CronjobRepository extends JpaRepository<Cronjob, UUID>, JpaSpecificationExecutor<Cronjob> {

	@Query("select count(c) > 0 from Cronjob c where c.jobGroup = :jobGroup and c.jobName = :jobName")
	boolean isGroupAndNameExists(@Param("jobGroup") String jobGroup, @Param("jobName") String jobName);

	@Query("select c from Cronjob c where c.jobGroup = :jobGroup and c.jobName = :jobName order by c.createdDate desc")
	Cronjob getByGroupAndName(@Param("jobGroup") String jobGroup, @Param("jobName") String jobName);

	@Query("select c from Cronjob c where c.uuid = :uuid")
	Cronjob getById(@Param("uuid") UUID uuid);

	@Query("select c from Cronjob c where c.startup = :startup and c.jobTrigger = :jobTrigger order by c.createdDate desc")
	List<Cronjob> findStartupJob(@Param("startup") boolean startup, @Param("jobTrigger") Cronjob.JobTrigger jobTrigger);

	@Modifying
	@Query("update Cronjob c set c.status = :status, c.result = :result where c.uuid = :uuid")
	int updateStatus(@Param("uuid") UUID uuid, @Param("status") Cronjob.Status status, @Param("result") String result);

}
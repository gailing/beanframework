package com.beanframework.platform.oplog.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OpLogRepository extends JpaRepository<OpLog, UUID>, JpaSpecificationExecutor<OpLog> {
	
	@Query("select l.uuid from "+OpLog.MODEL+" l")
	List<UUID> getLastestLogId(Pageable pageable);

	@Modifying
    @Query("delete from "+OpLog.MODEL+" l where l.uuid not in (:uuidList)")
	int deleteOldLogByExcludedId(@Param("uuidList") List<UUID> idList);
	
}
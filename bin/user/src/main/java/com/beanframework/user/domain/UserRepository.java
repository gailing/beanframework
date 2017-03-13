package com.beanframework.user.domain;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

	@Query("select count(u) > 0 from User u where u.uuid = :uuid")
	public boolean isUserExists(@Param("uuid") UUID uuid);
	
	@Query("select count(u) > 0 from User u where u.username = :username")
	public boolean isUsernameExists(@Param("username") String username);
	
	@Query("select count(u) > 0 from User u where u.email = :email")
	public boolean isEmailExists(@Param("email") String email);
	
	@Query(value = "select u.uuid from User u where u.username = ?1")
	UUID findIdByUsername(String username);
	
	@Query(value = "select u.password from User u where u.uuid = ?1")
	String findPasswordByUsername(UUID uuid);

	@Query(value = "select u from User u where u.username = ?1")
	User findByUsername(String username);

	@Query(value = "select u from User u where u.email = ?1")
	User findByEmail(String email);

//	@Query(value = "select u from User u where u.uuid = ?1")
//	User findByUuid(UUID uuid);

	@SuppressWarnings("unchecked")
	User save(User user);

	@Modifying
	@Query("delete from User u where u.uuid = :uuid")
	void delete(@Param("uuid") UUID uuid);

	@Modifying
	@Query("delete from User u where u.username = :username")
	void deleteByUsername(@Param("username") String username);

	@Modifying
	@Query("update User u set u.password = :password where u.uuid = :uuid")
	int updatePasswordById(@Param("uuid") UUID uuid, @Param("password") String password);
	
	@Modifying
	@Query("update User u set u.lastLogonDate = :date where u.uuid = :uuid")
	int updateLastLogonDateById(@Param("date") Date date, @Param("uuid") UUID uuid);
	
	@Query(value = "select u.attempts from User u where u.uuid = :uuid")
	int findAttemptsById(@Param("uuid") UUID uuid);
	
	@Modifying
	@Query("update User u set u.attempts = :attempts where u.uuid = :uuid")
	int updateAttempts(@Param("uuid") UUID uuid, @Param("attempts") int attempts);

	@Modifying
	@Query("update User u set u.accountNonLocked = 0 where u.uuid = :uuid")
	public void setUserAccountLocked(@Param("uuid") UUID uuid);
}
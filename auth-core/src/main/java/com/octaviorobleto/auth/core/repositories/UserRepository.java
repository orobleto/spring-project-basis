package com.octaviorobleto.auth.core.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.octaviorobleto.auth.core.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	@Query("SELECT u.active FROM User u WHERE u.email = :email")
	boolean findActiveByEmail(@Param("email") String email);

	List<User> findUsersByRolesId(Long id);

	@Transactional
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE User u SET u.failedAttempts = u.failedAttempts + 1, u.active = CASE WHEN (u.failedAttempts) >= 3 THEN 0 ELSE u.active END, u.modified = :modified WHERE u.email = :email")
	void incrementFailedAttempts(@Param("email") String email, @Param("modified") LocalDateTime modified);

	@Transactional
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE User u SET u.failedAttempts = :failedAttempts, u.modified = :modified WHERE u.email = :email")
	void updateFailedAttempts(@Param("email") String email, @Param("failedAttempts") int failedAttempts,
			@Param("modified") LocalDateTime modified);

	@Transactional
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE User u SET u.lastLogin = :lastLogin WHERE u.email = :email")
	void updateLastLogin(@Param("email") String email, @Param("lastLogin") LocalDateTime lastLogin);

}

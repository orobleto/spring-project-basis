package com.octaviorobleto.auth.core.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octaviorobleto.auth.core.entities.User;
import com.octaviorobleto.auth.core.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public boolean isActive(String email) {
		return userRepository.findActiveByEmail(email);
	}

	public void incrementFailedAttempts(String email) {
		userRepository.incrementFailedAttempts(email, LocalDateTime.now());
	}

	public void updateLastLogin(String email) {
		userRepository.updateLastLogin(email, LocalDateTime.now());
	}

	public boolean incrementFailedAttemptsAndValidateUserStatus(String email) {
		if (findByEmail(email).isPresent()) {
			incrementFailedAttempts(email);
			return isActive(email);
		}

		return true;
	}

	public boolean updateLastLoginAndValidateUserStatus(String email) {
		boolean active = isActive(email);
		if (active) {
			updateLastLogin(email);
		}
		return active;
	}

}

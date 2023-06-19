package com.octaviorobleto.auth.core.settings;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.octaviorobleto.auth.core.entities.Role;
import com.octaviorobleto.auth.core.entities.User;
import com.octaviorobleto.auth.core.repositories.RoleRepository;
import com.octaviorobleto.auth.core.repositories.UserRepository;

@Component
public class DataInitialize implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public void run(String... args) throws Exception {
		roles();
		users();
	}

	private void roles() {
		roleRepository.save(Role.builder().description("ADMIN").build());
		roleRepository.save(Role.builder().description("USER").build());
		roleRepository.save(Role.builder().description("SUP").build());
	}

	private void users() {
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByDescription("ADMIN"));

		userRepository.save(User.builder().email("user1@octaviorobleto.com")
				.password(passwordEncoder.encode("User11234")).active(true).roles(roles).build());
		roles.add(roleRepository.findByDescription("USER"));
		userRepository.save(User.builder().email("user2@octaviorobleto.com")
				.password(passwordEncoder.encode("User21234")).active(true).roles(roles).build());
		roles.add(roleRepository.findByDescription("SUP"));
		userRepository.save(User.builder().email("user3@octaviorobleto.com")
				.password(passwordEncoder.encode("User31234")).active(true).roles(roles).build());
		userRepository.save(User.builder().email("user4@octaviorobleto.com")
				.password(passwordEncoder.encode("User41234")).active(true).roles(roles).build());
		;
	}

}

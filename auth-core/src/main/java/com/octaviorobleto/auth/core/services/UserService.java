package com.octaviorobleto.auth.core.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.octaviorobleto.auth.core.entities.User;
import com.octaviorobleto.auth.core.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	private static Logger logger = LogManager.getLogger();
	@Autowired
	private UserRepository userRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info(username);
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Credenciales Incorrectas"));
		List<GrantedAuthority> roles = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getDescription())).collect(Collectors.toList());
		logger.info(user);

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
	}

}

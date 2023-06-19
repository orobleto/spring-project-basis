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

@Service
public class UserLoginService implements UserDetailsService {
	private static Logger logger = LogManager.getLogger();
	@Autowired
	private UserService userService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug(username);
		User user = userService.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Credenciales Incorrectas"));
		List<GrantedAuthority> roles = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getDescription())).collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
	}

}

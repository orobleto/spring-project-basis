package com.octaviorobleto.auth.core.services.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.octaviorobleto.auth.core.services.UserLoginService;
import com.octaviorobleto.commons.utilities.text.StringUtils;


public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private UserLoginService userService;

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getToken(request);
		if (!StringUtils.isEmpty(token)) {
			String username = jwtProvider.getUserName(token);
			UserDetails userDetails = userService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
		filterChain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		if (StringUtils.isEmpty(bearer) || !bearer.startsWith("Bearer")) {// null || ==0
			return null;
		}
		return bearer.replace("Bearer ", "");
	}

}

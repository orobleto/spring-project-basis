package com.octaviorobleto.auth.core.services.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

	private static Logger logger = LogManager.getLogger();

	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error(HttpServletResponse.SC_UNAUTHORIZED + " Acceso No Autorizado");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acceso No Autorizado");

	}

}

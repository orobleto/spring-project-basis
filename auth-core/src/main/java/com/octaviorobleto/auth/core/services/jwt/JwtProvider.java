package com.octaviorobleto.auth.core.services.jwt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {
	private static Logger logger = LogManager.getLogger();
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private Long expiration;

	public String generate(Authentication authentication) {
		String username = authentication.getName();
		Date creationDate = new Date();
		Date expirationDate = new Date(creationDate.getTime() + expiration);
		List<String> roles = authentication.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", roles);

		return Jwts.builder().setClaims(claims).setIssuedAt(creationDate).setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, secret).compact();

	}

	public String getUserName(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validate(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			logger.error("Token Expirado: " + e);
		} catch (UnsupportedJwtException e) {
			logger.error("Token No soportado: " + e);
		} catch (MalformedJwtException e) {
			logger.error("Token mal formado: " + e);
		} catch (Exception e) {
			logger.error(e);
		}
		return false;
	}

}

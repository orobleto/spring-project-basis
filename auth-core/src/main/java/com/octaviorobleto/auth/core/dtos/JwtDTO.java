package com.octaviorobleto.auth.core.dtos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.octaviorobleto.auth.core.utilities.examples.JsonExamples;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "jwt-dto", example = JsonExamples.JWT_DTO)
public class JwtDTO {
	@JsonProperty(value = "access_token", required = true)
	private String accessToken;
	@JsonProperty(value = "expires_in", required = true)
	@Value("${jwt.expiration}")
	private Long expiresIn;
	@JsonProperty(value = "token_type", required = true)
	@Value("${jwt.type}")
	private String tokenType;
}

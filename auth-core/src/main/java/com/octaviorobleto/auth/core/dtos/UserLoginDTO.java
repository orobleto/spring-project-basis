package com.octaviorobleto.auth.core.dtos;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.octaviorobleto.auth.core.utilities.examples.JsonExamples;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "user-dto", example = JsonExamples.USER_DTO)
@Component
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

	@NotEmpty(message = "Debe enviar un correo electronico [email]")
	@JsonProperty(value = "email", required = true)
	private String email;
	@NotEmpty(message = "Debe enviar la clave [password]")
	@JsonProperty(value = "password", required = true)
	private String password;
}

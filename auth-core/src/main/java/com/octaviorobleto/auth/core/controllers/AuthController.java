package com.octaviorobleto.auth.core.controllers;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.octaviorobleto.auth.core.dtos.JwtDTO;
import com.octaviorobleto.auth.core.dtos.UserLoginDTO;
import com.octaviorobleto.auth.core.services.UserService;
import com.octaviorobleto.auth.core.services.jwt.JwtProvider;
import com.octaviorobleto.auth.core.utilities.examples.JsonExamples;
import com.octaviorobleto.commons.utilities.text.StringUtils;
import com.octaviorobleto.utilities.core.dtos.ErrorMessageDTO;
import com.octaviorobleto.utilities.core.enums.ErrorMessageTypes;
import com.octaviorobleto.utilities.core.services.ErrorMessageService;
import com.octaviorobleto.utilities.core.services.ValidateDataTypesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path = { "/v1/auth" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public class AuthController {
	private static Logger logger = LogManager.getLogger();
	@Autowired
	private JwtDTO jwtDTO;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private ErrorMessageService errorMessageService;
	@Autowired
	private ValidateDataTypesService validateDataTypesService;
	@Autowired
	private UserService userService;

	@Operation(tags = "Auth", summary = "Inicia Sesion del usuario")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = JwtDTO.class), examples = @ExampleObject(summary = "Ejemplo OK", value = JsonExamples.JWT_DTO)) }),

			@ApiResponse(responseCode = "400", description = "Solicitud no Valida", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDTO.class), examples = @ExampleObject(summary = "Ejemplo 400", value = com.octaviorobleto.utilities.core.examples.JsonExamples.ERROR_MESSAGE_DTO)) }),
			@ApiResponse(responseCode = "401", description = "Sin Autorizacion", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDTO.class), examples = @ExampleObject(summary = "Ejemplo 401", value = JsonExamples.AUTH_RESPONSE_401)) }),
			@ApiResponse(responseCode = "409", description = "Conflicto", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDTO.class), examples = @ExampleObject(summary = "Ejemplo 409", value = JsonExamples.AUTH_RESPONSE_409)) })

	})

	@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = UserLoginDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
			@ExampleObject(name = "Ejemplo", value = JsonExamples.USER_DTO) }))

	@PostMapping(path = { "/login" })
	public ResponseEntity<?> login(@RequestBody @Valid UserLoginDTO userLoginDTO, BindingResult bindingResult) {

		ErrorMessageDTO errorMessageDTO = validateUserLogin(userLoginDTO, bindingResult);

		if (null != errorMessageDTO) {
			return ResponseEntity.status(400).body(errorMessageDTO);
		}

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			jwtDTO.setAccessToken(jwtProvider.generate(authentication));
		} catch (BadCredentialsException e) {
			logger.error(e);

			// validamos que el usuario este habilitado (activo)
			if (!userService.incrementFailedAttemptsAndValidateUserStatus(userLoginDTO.getEmail())) {
				return ResponseEntity.status(409).body(ErrorMessageDTO.builder().code(409)
						.message("Usuario [" + userLoginDTO.getEmail() + "] Bloqueado").build());
			}

			return ResponseEntity.status(401)
					.body(ErrorMessageDTO.builder().code(401).message("Acceso No Autorizado").build());
		}

		if (!userService.updateLastLoginAndValidateUserStatus(userLoginDTO.getEmail())) {
			return ResponseEntity.status(409).body(ErrorMessageDTO.builder().code(409)
					.message("Usuario [" + userLoginDTO.getEmail() + "] Bloqueado").build());
		}

		return ResponseEntity.ok(jwtDTO);
	}

	private ErrorMessageDTO validateUserLogin(UserLoginDTO userLoginDTO, BindingResult bindingResult) {
		String validate = "";
		String validateEmail = validateDataTypesService.validateEmail(userLoginDTO.getEmail());
		String validatePassword = validateDataTypesService.validatePassword(userLoginDTO.getPassword());

		if (bindingResult.hasErrors()) {
			return errorMessageService.getErrorMessage(ErrorMessageTypes.VALIDATION_ERROR, bindingResult);
		}

		validate += (StringUtils.isEmpty(validateEmail) ? "" : validateEmail);
		
		if (!StringUtils.isEmpty(validate)) {
			
		}
		
		validate += (StringUtils.isEmpty(validatePassword) ? ""
				: (StringUtils.isEmpty(validate)) ? validatePassword : "; " + validatePassword);

		if (!StringUtils.isEmpty(validate)) {
			return errorMessageService.getErrorMessage(ErrorMessageTypes.DATA_TYPE_ERROR, validate);
		}

		return null;
	}

}

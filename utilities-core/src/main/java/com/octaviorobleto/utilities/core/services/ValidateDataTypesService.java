package com.octaviorobleto.utilities.core.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.octaviorobleto.commons.utilities.text.StringUtils;

/**
 * 
 * Proporciona un servicio para validar el tipo de dato
 * 
 * @author <a href="https://octaviorobleto.com" target="_blank">Octavio
 *         Robleto</a>
 * @version 1.0
 * @date 2023-06-16
 * @class ValidateDataTypesService
 */
@Service
public final class ValidateDataTypesService {
	@Value("${regex.password.value}")
	private String passwordREGEX;
	@Value("${regex.password.message}")
	private String passwordMessage;
	@Value("${regex.email.value}")
	private String emailREGEX;
	@Value("${regex.email.message}")
	private String emailMesssage;

	/**
	 * Verifica si una cadena es un correo electrocio valido
	 * 
	 * @param email {@link String} Puede proporcionar {@code null}
	 * @return emailMesssage que contiene advertencia si no cumple que con las
	 *         expresion regular emailREGEX, {@code null} si cumple
	 */
	public String validateEmail(final String email) {
		if (StringUtils.isEmpty(email) || !email.matches(emailREGEX)) {
			return emailMesssage;
		}
		return null;
	}

	/**
	 * Verifica si una cadena es un patron valido para claves
	 * 
	 * @param password {@link String} Puede proporcionar {@code null}
	 * @return passwordMessage que contiene advertencia si no cumple que con las
	 *         expresion regular passwordREGEX, {@code null} si cumple
	 */
	public String validatePassword(final String password) {
		if (StringUtils.isEmpty(password) || !password.matches(passwordREGEX)) {
			return passwordMessage;
		}
		return null;
	}
}

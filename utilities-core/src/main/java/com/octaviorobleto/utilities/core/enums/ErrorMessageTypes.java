package com.octaviorobleto.utilities.core.enums;

import lombok.Getter;

@Getter
public enum ErrorMessageTypes {

	GENERIC_ERROR(100, "Error Generico: "), NO_ELEMENTS(200, "No hay elementos que mostrar con: "),
	VALIDATION_ERROR(300, "Error de validación: "), DATA_TYPE_ERROR(400, "No es el tipo de dato requerido: ");

	private String message;
	private int code;

	private ErrorMessageTypes(int code, String message) {
		this.code = code;
		this.message = message;
	}

}

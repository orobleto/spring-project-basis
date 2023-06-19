package com.octaviorobleto.auth.core.utilities.examples;

public final class JsonExamples {

	public static final String JWT_DTO = "{\"access_token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c\",\"expires_in\":50000,\"token_type\":\"bearer\"}";
	public static final String USER_DTO = "{   \"email\": \"user@dominio.ext\",   \"password\": \"1234\" }";
	public static final String AUTH_RESPONSE_401 = "{   \"code\": 401,   \"message\": \"Acceso No Autorizado\" }";
	public static final String AUTH_RESPONSE_409 = "{   \"code\": 409,   \"message\": \"Usuario [user@dominio.ext] Bloqueado\" }";
	private JsonExamples() {

	}
}

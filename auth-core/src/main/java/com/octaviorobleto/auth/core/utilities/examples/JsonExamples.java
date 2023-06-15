package com.octaviorobleto.auth.core.utilities.examples;

public final class JsonExamples {
	public static final String JWT_DTO = "{\"access_token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c\",\"expires_in\":50000,\"token_type\":\"bearer\"}";
	public static final String AUTH_RESPONSE_401 = "{ \"timestamp\": \"2023-06-12T19:53:45.737\",   \"status\": 401,   \"error\": \"Acceso No Autorizado\",   \"path\": \"/v1/auth/login\" }";
	public static final String USER_DTO = "{   \"email\": \"user@dominio.ext\",   \"key\": \"1234\" }";
}

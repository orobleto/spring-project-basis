package com.octaviorobleto.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
				title = "Spring Project Basis",
				version = "1.0.0",
				description = "Proyecto Base para Proyectos de Spring",
				contact = @Contact(name = "Octavio Robleto", url = "https://octaviorobleto.com",email= "octavio.robleto@gmail.com")
		)
)
@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}

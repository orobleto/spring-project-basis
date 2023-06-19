package com.octaviorobleto.utilities.core.dtos;

import org.springframework.stereotype.Component;

import com.octaviorobleto.utilities.core.examples.JsonExamples;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "error-message-dto", example = JsonExamples.ERROR_MESSAGE_DTO)
@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageDTO {
	private int code;
	private String message;
}

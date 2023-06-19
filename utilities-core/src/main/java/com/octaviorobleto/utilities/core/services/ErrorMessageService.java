package com.octaviorobleto.utilities.core.services;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.octaviorobleto.utilities.core.dtos.ErrorMessageDTO;
import com.octaviorobleto.utilities.core.enums.ErrorMessageTypes;

@Service
public class ErrorMessageService {
	private static Logger logger = LogManager.getLogger();

	public ErrorMessageDTO getErrorMessage(ErrorMessageTypes errorMessageTypes, Object object) {
		String message = null;
		if (object instanceof String) {
			message = (String) object;
		} else if (object instanceof BindingResult) {
			BindingResult bindingResult = (BindingResult) object;
			message = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage())
					.collect(Collectors.joining("; "));
		}
		ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder().code(errorMessageTypes.getCode())
				.message(errorMessageTypes.getMessage().concat(message)).build();
		logger.debug(errorMessageDTO);
		return errorMessageDTO;

	}
}

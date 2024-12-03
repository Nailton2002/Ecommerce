package com.api.restfull.ecommerce.application.validation;

import com.api.restfull.ecommerce.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ResourceExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(BusinessRuleException.class)
	public ResponseEntity<Object> handleBusinessRuleException(BusinessRuleException e, HttpServletRequest request) {
		String error = "Business Rule Exception";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(BusinessRuleExceptionLogger.class)
	public ResponseEntity<String> handleBusinessRuleException(BusinessRuleExceptionLogger ex) {
		logger.warn("Violação de regra de negócio: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(ResourceNotFoundExceptionLogger.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundExceptionLogger ex) {
		logger.error("Erro: {}", ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(ExceptionLogger.class)
	public ResponseEntity<String> handleGeneralException(ExceptionLogger ex) {
		logger.error("Erro inesperado: {}", ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor.");
	}

	@ExceptionHandler(DataIntegrityValidationException.class)
	public ResponseEntity<StandardError> resourceNotFound(DataIntegrityValidationException e, HttpServletRequest request) {
		String error = "Data Integrity Violation Exception";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}


}

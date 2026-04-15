package com.ncm.hrms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// 1. Handle Bad Request Exceptions together
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), 400);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// 2. Custom Exception
	@ExceptionHandler(IpNotAllowedException.class)
	public ResponseEntity<ErrorResponse> handleIpNotAllowed(IpNotAllowedException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), 403);
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), 400);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// 3. Generic Runtime Exception
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), 400);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// 4. Catch-all (last)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
		ErrorResponse error = new ErrorResponse("Something went wrong", 500);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

package com.qadah.demo.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * * Handle all exceptions and java bean validation errors
 * for all endpoints income data that use the @Valid annotation
 *
 * @author Ehab Qadah
 */
@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String ACCESS_DENIED = "Access denied!";
	public static final String INVALID_REQUEST = "Invalid request";
	public static final String ERROR_MESSAGE_TEMPLATE = "message: %s %n requested uri: %s";
	public static final String LIST_JOIN_DELIMITER = ",";
	private static final Logger local_logger = LoggerFactory.getLogger(GeneralExceptionHandler.class);
	private static final String ERRORS_FOR_PATH = "errors {} for path {}";
	private static final String PATH = "path";
	private static final String ERRORS = "error";
	private static final String STATUS = "status";
	private static final String MESSAGE = "message";
	private static final String TIMESTAMP = "timestamp";

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception,
			HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		List<String> validationErrors = exception.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.toList());
		return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, request, validationErrors);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		return getExceptionResponseEntity(status, request,
				Collections.singletonList(ex.getLocalizedMessage()));
	}

	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<Object> handleConstraintViolation(
			ConstraintViolationException exception, WebRequest request) {
		List<String> validationErrors = exception.getConstraintViolations().stream().
				map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
				.collect(Collectors.toList());
		return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, request, validationErrors);
	}

	private ResponseEntity<Object> getExceptionResponseEntity(
			final HttpStatus status,
			WebRequest request, List<String> errors) {
		final Map<String, Object> body = new LinkedHashMap<>();

		final String errorsMessage = CollectionUtils.isNotEmpty(errors) ?
				errors.stream().filter(StringUtils::isNotEmpty).collect(Collectors.joining(LIST_JOIN_DELIMITER))
				:status.getReasonPhrase();
		final String path = request.getDescription(false);
		body.put(TIMESTAMP, Instant.now());
		body.put(STATUS, status.value());
		body.put(ERRORS, errorsMessage);
		body.put(PATH, path);
		body.put(MESSAGE, getMessageForStatus(status));
		local_logger.error(ERRORS_FOR_PATH, errorsMessage, path);
		return new ResponseEntity<>(body, status);
	}

	private String getMessageForStatus(HttpStatus status) {
		switch (status) {
			case UNAUTHORIZED:
				return ACCESS_DENIED;
			case BAD_REQUEST:
				return INVALID_REQUEST;
			default:
				return status.getReasonPhrase();
		}
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
		ResponseStatus responseStatus =
				exception.getClass().getAnnotation(ResponseStatus.class);
		final HttpStatus status =
				responseStatus!=null ? responseStatus.value():HttpStatus.INTERNAL_SERVER_ERROR;
		final String localizedMessage = exception.getLocalizedMessage();
		final String path = request.getDescription(false);
		String message = (StringUtils.isNotEmpty(localizedMessage) ? localizedMessage:status.getReasonPhrase());
		logger.error(String.format(ERROR_MESSAGE_TEMPLATE, message, path), exception);
		return getExceptionResponseEntity(status, request, Collections.singletonList(message));
	}
}
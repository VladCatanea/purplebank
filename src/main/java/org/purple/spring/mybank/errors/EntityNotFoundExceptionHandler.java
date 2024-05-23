package org.purple.spring.mybank.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EntityNotFoundExceptionHandler extends ResponseEntityExceptionHandler{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler (value = {EntityNotFoundException.class})
	protected ResponseEntity<Object> handleError(
		      EntityNotFoundException ex, WebRequest request)
	{
		logger.error("Could not find entity of type {} and id {}", ex.getType(), ex.getId());
		String bodyOfResponse = "Could not find entity of type " + ex.getType() + " and id " + ex.getId();
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
}

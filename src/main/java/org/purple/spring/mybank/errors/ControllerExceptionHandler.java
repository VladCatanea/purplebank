package org.purple.spring.mybank.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler (value = {EntityNotFoundException.class})
	protected ResponseEntity<Object> handleEntityNotFoundError(
		      EntityNotFoundException ex, WebRequest request)
	{
		logger.error("Could not find entity of type {} and id {}", ex.getType(), ex.getId(), ex);
        return handleExceptionInternal(ex, null, 
          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler (value = {AccessDeniedException.class})
	protected ResponseEntity<Object> handleAccessDeniedError(
		      AccessDeniedException ex, WebRequest request)
	{
		logger.error("Not Authorized", ex);
        return handleExceptionInternal(ex, null, 
          new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}
	
	@ExceptionHandler (value = {TransactionException.class})
	protected ResponseEntity<Object> handleTransactionError(
		      TransactionException ex, WebRequest request)
	{
		logger.error("Transaction exception: ", ex);
        return handleExceptionInternal(ex, null, 
          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
}

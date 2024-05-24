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
public class AccessDeniedExceptionHandler extends ResponseEntityExceptionHandler{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler (value = {AccessDeniedException.class})
	protected ResponseEntity<Object> handleError(
		      AccessDeniedException ex, WebRequest request)
	{
		logger.error("No Authorized", ex);
        return handleExceptionInternal(ex, null, 
          new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}
}


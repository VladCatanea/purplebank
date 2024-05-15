package org.purple.spring.mybank.deposit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public EntityNotFoundException(Long id, String type) {
	    super("Could not find entity of type " + type + " and id " + id);
	    logger.error("Could not find entity of type "+ type + " and id " + id);
	  }
	}

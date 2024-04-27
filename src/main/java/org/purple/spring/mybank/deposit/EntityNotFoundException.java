package org.purple.spring.mybank.deposit;

public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException(Long id) {
	    super("Could not find entity " + id);
	  }
	}

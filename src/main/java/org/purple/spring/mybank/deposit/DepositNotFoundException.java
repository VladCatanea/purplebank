package org.purple.spring.mybank.deposit;

public class DepositNotFoundException extends RuntimeException {

	  public DepositNotFoundException(Long id) {
	    super("Could not find deposit " + id);
	  }
	}

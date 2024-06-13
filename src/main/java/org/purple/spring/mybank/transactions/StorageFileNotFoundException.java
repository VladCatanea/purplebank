package org.purple.spring.mybank.transactions;

import org.purple.spring.mybank.errors.TransactionException;

public class StorageFileNotFoundException extends TransactionException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}

package com.penguineering.pim.imapindex.store;

public class StoreException extends Exception {
	private static final long serialVersionUID = 5482502846553432640L;

	public StoreException() {
	}

	public StoreException(String message) {
		super(message);
	}

	public StoreException(Throwable cause) {
		super(cause);
	}

	public StoreException(String message, Throwable cause) {
		super(message, cause);
	}

}

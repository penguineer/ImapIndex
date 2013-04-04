package com.penguineering.pim.imapindex.index;

public class IndexAccessException extends Exception {
	private static final long serialVersionUID = 5443629753227914238L;

	public IndexAccessException() {
	}

	public IndexAccessException(String message) {
		super(message);
	}

	public IndexAccessException(Throwable cause) {
		super(cause);
	}

	public IndexAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}

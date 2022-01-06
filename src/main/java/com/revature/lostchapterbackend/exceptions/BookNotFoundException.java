package com.revature.lostchapterbackend.exceptions;

public class BookNotFoundException extends Exception {

	public BookNotFoundException() {
		super();

	}

	public BookNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public BookNotFoundException(String message, Throwable cause) {
		super(message, cause);

		// TODO Auto-generated constructor stub

	}

	public BookNotFoundException(String message) {
		super(message);

	}

	public BookNotFoundException(Throwable cause) {
		super(cause);

	}

	
}

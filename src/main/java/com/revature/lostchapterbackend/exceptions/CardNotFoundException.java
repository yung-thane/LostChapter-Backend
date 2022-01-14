package com.revature.lostchapterbackend.exceptions;

public class CardNotFoundException extends Exception {

	public CardNotFoundException() {
		super();
	}

	public CardNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		}

	public CardNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CardNotFoundException(String message) {
		super(message);
	}

	public CardNotFoundException(Throwable cause) {
		super(cause);
	}

	
	
}

package com.revature.lostchapterbackend.exceptions;

public class SynopsisInputException extends Exception {

	public SynopsisInputException() {
		super();
	}

	public SynopsisInputException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SynopsisInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public SynopsisInputException(String message) {
		super(message);
	}

	public SynopsisInputException(Throwable cause) {
		super(cause);
	}

}

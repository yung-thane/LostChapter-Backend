package com.revature.lostchapterbackend.exceptions;

public class SaleDiscountRateException extends Exception {

	public SaleDiscountRateException() {
		super();
	}

	public SaleDiscountRateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SaleDiscountRateException(String message, Throwable cause) {
		super(message, cause);
	}

	public SaleDiscountRateException(String message) {
		super(message);
	}

	public SaleDiscountRateException(Throwable cause) {
		super(cause);
	}

}

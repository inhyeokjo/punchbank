package com.kkb.punchbank.global.exception;

public class BaseCustomException extends RuntimeException {

	protected BaseCustomException(String msg) {
		super(msg);
	}

	public BaseCustomException(String message, Throwable cause) {
		super(message, cause);
	}
}

package com.shippable.exception;


public class ClientException extends ShippableException {

	private static final long serialVersionUID = 1L;

	public ClientException(ErrorCode errorCode, Object... args) {
		super(errorCode, args);
	}

	public ClientException(ErrorCode errorCode, Throwable underlyingException, Object... args) {
		super(errorCode, underlyingException, args);
	}

}

package com.Exception;

public class InvalidQueryStringException extends RuntimeException {

	public InvalidQueryStringException() {
		super();
	}
	public InvalidQueryStringException(String str) {
		super(str);
	}
}

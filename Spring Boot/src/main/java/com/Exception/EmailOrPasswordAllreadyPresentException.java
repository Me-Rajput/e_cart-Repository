package com.Exception;

public class EmailOrPasswordAllreadyPresentException extends RuntimeException {

	public EmailOrPasswordAllreadyPresentException() {
		super();
	}
	public EmailOrPasswordAllreadyPresentException(String str) {
		super(str);
	}
}

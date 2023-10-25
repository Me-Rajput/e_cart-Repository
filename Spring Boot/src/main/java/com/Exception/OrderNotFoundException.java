package com.Exception;

public class OrderNotFoundException extends RuntimeException{
	
	public OrderNotFoundException() {
		super();
	}
	public OrderNotFoundException(String str) {
		super(str);
	}
}

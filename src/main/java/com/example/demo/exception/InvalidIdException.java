package com.example.demo.exception;

public class InvalidIdException extends Exception{
	
	private static final long serialVersionUID =1L;
	

	private final String message;


	public InvalidIdException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}

package com.example.demo.exception;

public class InvalidIdException extends Exception{
	
	private static final long serialVersionUID =1L;
	
<<<<<<< HEAD
	private final String message;
=======
	private String message;
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada

	public InvalidIdException(String message) {
		super();
		this.message = message;
	}
	
<<<<<<< HEAD
	@Override
=======
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
	public String getMessage() {
		return message;
	}

}

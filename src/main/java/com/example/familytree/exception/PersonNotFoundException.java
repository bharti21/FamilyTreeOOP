package com.example.familytree.exception;

import java.io.Serializable;

public class PersonNotFoundException extends RuntimeException implements Serializable{

	private static final long serialVersionUID = 1L;

	public PersonNotFoundException() {
		super();
	}

	public PersonNotFoundException(String message) {
		super(message);
	}
	
	
}

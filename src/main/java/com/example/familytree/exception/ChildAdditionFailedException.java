package com.example.familytree.exception;

import java.io.Serializable;

public class ChildAdditionFailedException extends RuntimeException implements Serializable{

	private static final long serialVersionUID = 1L;

	public ChildAdditionFailedException() {
		super();
	}

	public ChildAdditionFailedException(String message) {
		super(message);
	}
	
}

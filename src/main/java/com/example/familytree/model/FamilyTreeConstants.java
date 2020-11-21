package com.example.familytree.model;

public class FamilyTreeConstants {

	private FamilyTreeConstants() throws InstantiationException {
		throw new InstantiationException("Instantiation of this class from outside not allowed");
	}
	
	public static final String PERSON_NOT_FOUND_MESSAGE = "PERSON_NOT_FOUND";
	public static final String CHILD_ADDITION_FAILED_MESSAGE = "CHILD_ADDITION_FAILED";
	public static final String CHILD_ADDITION_SUCCEEDED_MESSAGE = "CHILD_ADDITION_SUCCEEDED";
	public static final String ADD_CHILD_MESSAGE = "ADD_CHILD";
	public static final String GET_RELATIONSHIP_MESSAGE = "GET_RELATIONSHIP";
	
}

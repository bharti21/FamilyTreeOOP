package com.example.familytree.model;

public enum Gender {

	MALE("Male"),FEMALE("Female");
	
	private String gender;

	private Gender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}
}

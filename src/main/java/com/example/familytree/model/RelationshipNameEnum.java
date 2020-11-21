package com.example.familytree.model;

public enum RelationshipNameEnum {

	FATHER("father"), MOTHER("mother"), SIBLINGS("siblings"), SON("son"), DAUGHTER("daughter"), PATERNAL_UNCLE(
			"paternal-uncle"), MATERNAL_UNCLE("maternal-uncle"), PATERNAL_AUNT("paternal-aunt"), MATERNAL_AUNT(
					"maternal-aunt"), SISTER_IN_LAW("sister-in-law"), BROTHER_IN_LAW("brother-in-law"),BROTHER("brother"),SISTER("sister"),SPOUSE("spouse"),CHILD("child");
	private String relation;

	private RelationshipNameEnum(String relation) {
		this.relation = relation;
	}

	public String getRelation() {
		return relation;
	}

	public static RelationshipNameEnum getRelationshipNameEnum(String value){
		return RelationshipNameEnum.valueOf(value);
	}
}

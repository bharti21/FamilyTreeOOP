/**
 * 
 */
package com.example.familytree.model;

import static com.example.familytree.model.FamilyTreeConstants.CHILD_ADDITION_FAILED_MESSAGE;
import static com.example.familytree.model.FamilyTreeConstants.PERSON_NOT_FOUND_MESSAGE;
import static com.example.familytree.model.Gender.MALE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.familytree.exception.ChildAdditionFailedException;
import com.example.familytree.exception.PersonNotFoundException;

public class Family {

	public static List<Person> familyTree = new ArrayList<>();

	public void addPerson(Person person) {
		familyTree.add(person);
	}

	public void initFamilyTree() {
		for (Person person : familyTree) {
			person.addSiblings();
		}
	}
	public void addChild(String motherName, String childName, String gender) {
		Person child = new Person(childName, gender);
		Person person = getFamilyMember(motherName);
		if (isPersonEligibleToAddChild(person)) {
			person.addChild(child);
		}
	}

	public List<String> getRelationship(String name, String relationship) {
		Person person = getFamilyMember(name);
		List<String> names = new ArrayList<String>();
		List<Person> relatives = new ArrayList<>();
		RelationshipNameEnum relationshipNameEnum = getRelationshipEnumFromValue(relationship);
		switch (relationshipNameEnum) {
		case SON:
			relatives = person.getSons();
			break;

		case DAUGHTER:
			relatives = person.getDaughters();
			break;
		case SIBLINGS:
			relatives = person.getSiblings();
			break;
		case PATERNAL_AUNT:
			relatives = person.getPaternalAunt();
			break;
		case PATERNAL_UNCLE:
			relatives = person.getPaternalUncle();
			break;
		case MATERNAL_AUNT:
			relatives = person.getMaternalAunt();
			break;
		case MATERNAL_UNCLE:
			relatives = person.getMaternalUncle();
			break;
		case SISTER_IN_LAW:
			relatives = person.getSisterInLaw();
			break;
		case BROTHER_IN_LAW:
			relatives = person.getBrotherInLaw();
			break;
		default:
			break;
		}
		names = getNamesOfRelatives(relatives);
		return names;
	}

	
	public Person getFamilyMember(String personName) {
		Person member = findFamilyMember(personName);
		return member;
	}

	private Person findFamilyMember(String personName) {
		Person member = null;
		List<Person> members = familyTree.stream().filter(p -> personName.equalsIgnoreCase(p.getName()))
				.collect(Collectors.toList());
		if (null == members || members.isEmpty()) {
			throw new PersonNotFoundException(PERSON_NOT_FOUND_MESSAGE);
		}
		member = members.get(0);
		return member;
	}

	private boolean isPersonEligibleToAddChild(Person person) {
		if (person.getGender().equalsIgnoreCase(MALE.getGender())) {
			throw new ChildAdditionFailedException(CHILD_ADDITION_FAILED_MESSAGE);
		}
		return true;
	}

	private RelationshipNameEnum getRelationshipEnumFromValue(String relation) {

		String relationShipEnumValue = relation.toUpperCase().replace('-', '_');
		RelationshipNameEnum relationshipNameEnum = RelationshipNameEnum.getRelationshipNameEnum(relationShipEnumValue);
		return relationshipNameEnum;
	}


	private List<String> getNamesOfRelatives(List<Person> relatives) {
		List<String> names = new ArrayList<String>();
		if (null != relatives && !relatives.isEmpty()) {
			for (Person relative : relatives) {
				names.add(relative.getName());
			}
		} else {
			names.add("NONE");
		}
		return names;
	}
}

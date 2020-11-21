package com.example.familytree;

import static com.example.familytree.model.Gender.FEMALE;
import static com.example.familytree.model.Gender.MALE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.familytree.exception.ChildAdditionFailedException;
import com.example.familytree.exception.PersonNotFoundException;
import com.example.familytree.model.Family;
import com.example.familytree.model.Person;

@RunWith(MockitoJUnitRunner.class)
public class FamilyTest {
	@InjectMocks
	private Family family;
	@Mock
	private Person person;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		setMockFamilyMembers();
	}

	@Test(expected = PersonNotFoundException.class)
	public void testAddChildThrowsPersonNotFoundExceptionIfGivenPersonIsNotPresentInFamily()  {
		family.addChild("Sailee", "Pihu", FEMALE.getGender());
	}

	@Test(expected = ChildAdditionFailedException.class)
	public void testAddChildThrowsChildAdditionFailedExceptionIfGivenPersonIsMale()  {
		family.addChild("Dhruv", "Vatsa", MALE.getGender());
	}

	@Test
	public void testGivenPersonsChildAdditionIsSuccessful() {
		family.addChild("Asva", "Vani", "female");
		family.initFamilyTree();
		assertNotNull(family.getFamilyMember("Vani"));
	}

	@Test(expected = PersonNotFoundException.class)
	public void testGetRelationshipThrowsPersonNotFoundExceptionIfGivenPersonIsNotPresentInFamily() {
		family.getRelationship("Pihu", "mother");
	}

	@Test
	public void testGetRelationshipReturnNoneIfNoRelativesFoundOfGivenPerson() {
		assertEquals(family.getRelationship("Asva", "siblings").get(0), "NONE");
	}

	@Test
	public void testGetRelationShipReturnsGivenPersonsRelativesNames() {
		family.addChild("Asva", "Chit", "male");
		family.initFamilyTree();
		assertEquals(family.getRelationship("Asva", "son").get(0), "Chit");
		assertEquals(family.getRelationship("Dhruv", "son").get(0), "Chit");
	}

	private void setMockFamilyMembers() {
		Person person1 = new Person("Dhruv", "male");
		Person person2 = new Person("Asva", "female");
		person1.addSpouse(person2);
		Family.familyTree.add(person1);
	}

}

package com.example.familytree.model;

import static com.example.familytree.model.Gender.FEMALE;
import static com.example.familytree.model.Gender.MALE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Person {

	private String name;
	private String gender;
	private Person father;
	private Person mother;
	private Person spouse;
	private List<Person> siblings = new ArrayList<>();
	private List<Person> children = new ArrayList<>();

	public Person(String name, String gender) {
		super();
		this.name = name;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Person getFather() {
		return father;
	}

	public void setFather(Person father) {
		this.father = father;
	}

	public Person getMother() {
		return mother;
	}

	public void setMother(Person mother) {
		this.mother = mother;
	}

	public List<Person> getSiblings() {
		return siblings;
	}

	public void setSiblings(List<Person> siblings) {
		this.siblings = siblings;
	}

	public Person getSpouse() {
		return spouse;
	}

	public void setSpouse(Person spouse) {
		this.spouse = spouse;
	}

	public List<Person> getChildren() {
		return children;
	}

	public void setChildren(List<Person> children) {
		this.children = children;
	}

	public void addParents(Person father, Person mother) {
		this.setFather(father);
		this.setMother(mother);
	}

	public void addChild(Person child) {
		this.addChildToParent(child);
		Person father = this.getSpouse();
		father.addChildToParent(child);
		child.setFather(father);
		child.setMother(this);
		Family.familyTree.add(child);

	}

	private void addChildToParent(Person child) {
		List<Person> children = this.getChildren();
		children.add(child);
		this.setChildren(children);
	}

	public void addSpouse(Person spouse) {
		this.setSpouse(spouse);
		spouse.setSpouse(this);
		Family.familyTree.add(spouse);
	}

	public void addSiblings() {
		Person father = this.getFather();
		if (null != father && null != father.getChildren()) {
			List<Person> siblings = father.getChildren().stream()
					.filter(p -> !p.getName().equalsIgnoreCase(this.getName())).collect(Collectors.toList());
			this.setSiblings(siblings);
		}
	}

	public List<Person> getSons() {
		List<Person> sons = new ArrayList<>();
		sons = this.getChildren().stream().filter(p -> MALE.getGender().equalsIgnoreCase(p.getGender()))
				.collect(Collectors.toList());
		return sons;
	}

	public List<Person> getDaughters() {
		List<Person> daughters = new ArrayList<>();
		daughters = this.getChildren().stream().filter(p -> FEMALE.getGender().equalsIgnoreCase(p.getGender()))
				.collect(Collectors.toList());
		return daughters;
	}

	public List<Person> getBrotherInLaw() {
		List<Person> brotherInLaws = new ArrayList<>();
		List<Person> sisters = this.getSisters();
		List<Person> sisterSpouse = getSiblingsSpouse(sisters);
		if (!sisterSpouse.isEmpty()) {
			brotherInLaws.addAll(sisterSpouse);
		}
		if (null != spouse) {
			List<Person> spouseBrothers = spouse.getBrothers();
			if (null != spouseBrothers) {
				brotherInLaws.addAll(spouseBrothers);
			}
		}
		return brotherInLaws;
	}

	private List<Person> getSiblingsSpouse(List<Person> siblings) {
		List<Person> spouses = new ArrayList<Person>();
		if (null != siblings && !siblings.isEmpty()) {
			for (Person sister : siblings) {
				Person spouse = sister.getSpouse();
				if (null != spouse) {
					spouses.add(spouse);
				}
			}
		}
		return spouses;
	}

	private List<Person> getSisters() {
		List<Person> sisters = new ArrayList<>();
		List<Person> siblings = this.getSiblings();
		if (null != siblings) {
			sisters = siblings.stream().filter(p -> FEMALE.getGender().equalsIgnoreCase(p.getGender()))
					.collect(Collectors.toList());
		}
		return sisters;
	}

	private List<Person> getBrothers() {
		List<Person> brothers = new ArrayList<>();
		List<Person> siblings = this.getSiblings();
		if (null != siblings) {
			brothers = siblings.stream().filter(p -> MALE.getGender().equalsIgnoreCase(p.getGender()))
					.collect(Collectors.toList());
		}
		return brothers;
	}

	public List<Person> getSisterInLaw() {
		List<Person> brothers = this.getBrothers();
		List<Person> sisterInLaws = new ArrayList<>();
		List<Person> brotherSpouse = getSiblingsSpouse(brothers);
		if (!brotherSpouse.isEmpty()) {
			sisterInLaws.addAll(brotherSpouse);
		}
		if (null != spouse) {
			List<Person> spouseSisters = spouse.getSisters();
			if (null != spouseSisters) {
				sisterInLaws.addAll(spouseSisters);
			}
		}

		return sisterInLaws;
	}

	public List<Person> getMaternalUncle() {
		List<Person> maternalUncles = new ArrayList<>();
		Person mother = this.getMother();
		if (null != mother.getBrothers()) {
			maternalUncles.addAll(mother.getBrothers());
		}
		return maternalUncles;
	}

	public List<Person> getMaternalAunt() {
		List<Person> maternalAunts = new ArrayList<>();
		Person mother = this.getMother();
		if (null != mother.getSisters()) {
			maternalAunts.addAll(mother.getSisters());
		}
		return maternalAunts;
	}

	public List<Person> getPaternalUncle() {
		List<Person> paternalUncles = new ArrayList<>();
		Person father = this.getFather();
		if (null != father.getBrothers()) {
			paternalUncles.addAll(father.getBrothers());
		}
		return paternalUncles;
	}

	public List<Person> getPaternalAunt() {
		List<Person> paternalAunts = new ArrayList<>();
		Person father = this.getFather();
		if (null != father.getSisters()) {
			paternalAunts.addAll(father.getSisters());
		}
		return paternalAunts;
	}

}

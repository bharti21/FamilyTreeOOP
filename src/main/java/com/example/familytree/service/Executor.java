package com.example.familytree.service;

import static com.example.familytree.model.FamilyTreeConstants.ADD_CHILD_MESSAGE;
import static com.example.familytree.model.FamilyTreeConstants.CHILD_ADDITION_SUCCEEDED_MESSAGE;
import static com.example.familytree.model.FamilyTreeConstants.GET_RELATIONSHIP_MESSAGE;
import static com.example.familytree.model.Gender.FEMALE;
import static com.example.familytree.model.RelationshipNameEnum.SPOUSE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.example.familytree.exception.ChildAdditionFailedException;
import com.example.familytree.exception.PersonNotFoundException;
import com.example.familytree.model.Family;
import com.example.familytree.model.Person;

public class Executor {

	public static void execute(String filePath) throws IOException {
		Family family = new Family();
		try {

			initFamilyTree(family);
			processInput(family, filePath);
		} catch (PersonNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (ChildAdditionFailedException e) {
			System.out.println(e.getMessage());
		}

	}

	private static void processInput(Family family, String filePath) throws IOException {
		File file = new File(filePath);

		BufferedReader br = new BufferedReader(new FileReader(file));
		String input;
		while ((input = br.readLine()) != null)
			if (input.startsWith(ADD_CHILD_MESSAGE)) {
				processAddChild(family, input);
			} else if (input.startsWith(GET_RELATIONSHIP_MESSAGE)) {
				processGetRelationship(family, input);
			} else {
				System.out.println("Please provide valid operation");
			}
	}

	private static void processGetRelationship(Family family, String input) {
		String[] splittedInput = input.split(" ");
		try {
			String personName = splittedInput[1];
			String relationshipName = splittedInput[2];
			List<String> relatives = family.getRelationship(personName, relationshipName);
			for (String relative : relatives) {
				System.out.print(relative + " ");
			}
			System.out.println();
		} catch (PersonNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void processAddChild(Family family, String input) {
		String[] splittedInput = input.split(" ");
		try {
			String motherName = splittedInput[1];
			String childName = splittedInput[2];
			String childGender = splittedInput[3];
			family.addChild(motherName, childName, childGender);
			System.out.println(CHILD_ADDITION_SUCCEEDED_MESSAGE);
		} catch (PersonNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (ChildAdditionFailedException e) {
			System.out.println(e.getMessage());
		}
		family.initFamilyTree();
	}

	private static void initFamilyTree(Family family) {
		Executor executor = new Executor();
		String fileName = "FamilyMembers.json";
		InputStream inputStream = executor.getFileFromResourceAsStream(fileName);
		File file = new File("Members.json");
		try {
			copyInputStreamToFile(inputStream, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initializeFamilyTreeFromJson(file, family);

		family.initFamilyTree();
	}

	public static void initializeFamilyTreeFromJson(File file, Family family) {
		JSONParser parser = new JSONParser();

		try {

			Object obj = parser.parse(new FileReader(file));
			JSONArray personList = (JSONArray) obj;
			addMembersToFamily(personList, family);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static void addMembersToFamily(JSONArray personList, Family family) {

		for (int i = 0; i < personList.size(); i++) {
			JSONObject childJSONObject = (JSONObject) personList.get(i);

			Person emperor = getPersonFromJsonObject(childJSONObject);
			Family.familyTree.add(emperor);

			JSONObject queenJSONObject = (JSONObject) childJSONObject.get(SPOUSE.getRelation());
			Person queen = getPersonFromJsonObject(queenJSONObject);

			emperor.addSpouse(queen);
			List<JSONObject> childrenJSONObject = (List<JSONObject>) childJSONObject.get("children");

			for (int j = 0; j < childrenJSONObject.size(); j++) {
				JSONObject child = childrenJSONObject.get(j);
				addIndividualToFamilyTree(child, queen);
			}

		}
	}

	private static void addIndividualToFamilyTree(JSONObject personJSONObject, Person mother) {
		Person member = getPersonFromJsonObject(personJSONObject);
		mother.addChild(member);

		JSONObject spouseJSONObject;

		if (!personJSONObject.get(SPOUSE.getRelation()).equals("")) {
			spouseJSONObject = (JSONObject) personJSONObject.get(SPOUSE.getRelation());

			Person spouse = getPersonFromJsonObject(spouseJSONObject);

			member.addSpouse(spouse);

			List<JSONObject> childrenJSONObject = (List<JSONObject>) personJSONObject.get("children");
			for (int i = 0; i < childrenJSONObject.size(); i++) {
				JSONObject child = childrenJSONObject.get(i);
				if (FEMALE.getGender().equalsIgnoreCase(member.getGender()))
					addIndividualToFamilyTree(child, member);
				else
					addIndividualToFamilyTree(child, spouse);
			}
		}
	}

	private static Person getPersonFromJsonObject(JSONObject personJSONObject) {
		String name = (String) personJSONObject.get("name");
		String gender = (String) personJSONObject.get("gender");
		Person member = new Person(name, gender);
		return member;
	}

	private InputStream getFileFromResourceAsStream(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }
	private static void copyInputStreamToFile(InputStream inputStream, File file) 
	        throws IOException {

	        try (FileOutputStream outputStream = new FileOutputStream(file)) {

	            int read;
	            byte[] bytes = new byte[1024];

	            while ((read = inputStream.read(bytes)) != -1) {
	                outputStream.write(bytes, 0, read);
	            }


	        }

	    }

	}

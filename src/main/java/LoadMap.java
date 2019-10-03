package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Temporary file responsible for interacting with the user and loading the map.
 * 
 * @author Smeet
 *
 */
public class LoadMap {
	private static GameMap map;	
	private static HashMap<Integer, Country> listOfCountries; //temporary HashMap to facilitate reading .map files
	
	public static void main(String[] args){
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the name of the map:");
		String mapName = in.nextLine();
		map = new GameMap(mapName);
		listOfCountries = new HashMap<Integer, Country>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(mapName));
			String s;
			while ((s = reader.readLine()) != null) {
				System.out.println(s); 
				if(s.equals("[continents]"))
					reader = readContinents(reader);
				if(s.equals("[countries]"))
					reader = readCountries(reader);
				if(s.equals("[borders]"))
					reader = readBorders(reader);
			}   
			//reader.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			System.out.println(e.getMessage());
		}
		catch(IOException e) {
			System.out.println("IOException");
			System.out.println(e.getMessage());
		}
		in.close();
		printMap();
	}
	
	static BufferedReader readContinents(BufferedReader reader) {
		String s;
		boolean continentExists = false;
		try {
			while(!((s = reader.readLine()).equals(""))) {
				String[] continentString = s.split("\\s+");
				//Check if continent already exists in the map
				ArrayList<Continent> continents = map.getContinentList();
				Iterator<Continent> itr = continents.iterator();
				while(itr.hasNext()) {
					Continent tempContinent = itr.next();
					if(tempContinent.getContinentName().equalsIgnoreCase(continentString[0]))
						continentExists = true;
				}
				if(!continentExists) {
					continents.add(new Continent(continentString[0], continentString[1], continentString[2]) );
					map.setContinentList(continents);
				} 
				else {
					//terminate the program if continent already exists
					System.out.println("Error reading the file.");
					System.out.println("Two continents of same name exists.");
					System.exit(-1);
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return reader;
	}
	
	static BufferedReader readCountries(BufferedReader reader) {
		String s;
		try {
			while(!((s = reader.readLine()).equals(""))) {
				String[] countryString = s.split("\\s+");
				Country newCountry = new Country(countryString[0], countryString[1], countryString[2], countryString[3], countryString[4], map);
				addToContinentMap(newCountry);	//Add country to the appropriate continent in the map. Terminate if duplicate entry.
				listOfCountries.put(newCountry.getIndex(), newCountry);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return reader;
	}
	
	static BufferedReader readBorders(BufferedReader reader) {
		String s;
		try {
			while((s = reader.readLine()) != null) {
				System.out.println(s);
				if(!s.equals("")) {
					String[] borderString = s.split("\\s+");
					Country argumentCountry = new Country();
					try {
						argumentCountry = listOfCountries.get(Integer.parseInt(borderString[0]));
					}
					catch(IndexOutOfBoundsException e){
						System.out.println("Error reading the file.");
						System.out.println("Neighbour does not exist.");
						System.exit(-1);
					}
					
					for(int i=1; i<borderString.length; i++) {
						addNeighbour(argumentCountry, borderString[i]);
					}
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return reader;
	}

	static void addToContinentMap(Country newCountry) {
		Continent tempContinent = map.getContinent(newCountry.getContinentName());
		HashMap<String, Country> tempMap = tempContinent.getCountries();
		if(!tempMap.containsKey(newCountry.getCountryName())) {
			tempMap.put(newCountry.getCountryName(), newCountry);
		}
		else {
			//terminate the program if same name country exists in the continent
			System.out.println("Error reading the file.");
			System.out.println("Two countries of same name exists in the same continent.");
			System.exit(-1);
		}
	}
	
	static void addNeighbour(Country argumentCountry, String stringIndex) {
		int borderIndex = Integer.parseInt(stringIndex);
		Country neighbourCountry = listOfCountries.get(borderIndex);
		ArrayList<Country> neighbourList = argumentCountry.getNeighbours();
		if(!notANeighbour(neighbourList, neighbourCountry.getCountryName()))
			neighbourList.add(neighbourCountry);
		else
			System.out.println("Neighbourhood already defined.");
	}
	
	static boolean notANeighbour(ArrayList<Country> neighbourList, String country) {
		Iterator<Country> itr = neighbourList.iterator();
		while(itr.hasNext()) {
			Country tempCountry = itr.next();
			if(tempCountry.getCountryName().equals(country))
				return true;
		}
		return false;
	}
	
	static void printMap() {
		System.out.println("Map name: " + map.getMapName());
		for(int i=1;i<=map.getContinentList().size();i++) {
			Continent tempContinent = map.getContinent(i);
			System.out.println("Continent name: " + tempContinent.getContinentName());
			HashMap<String, Country> tempMap = tempContinent.getCountries();
			for(Country c: tempMap.values()) {
				System.out.println("\t" + c.getCountryName());
				System.out.println("\tNeighbours:");
				Iterator<Country> itr = c.getNeighbours().iterator();
				while(itr.hasNext()) {
					Country tempCountry = itr.next();
					System.out.print("\t" + tempCountry.getCountryName());
				}
				System.out.println("");
			}
		}
	}
}
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
	private static HashMap<Integer, Country> listOfCountries; //temporary hashmap to map index with Country
	
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
		try {
			while(!((s = reader.readLine()).equals(""))) {
				String[] continentString = s.split("\\s+");
				map.addContinent(new Continent(continentString[0], continentString[1], continentString[2]));
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
				if(addToContinentMap(newCountry))
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
					Country argumentCountry = listOfCountries.get(Integer.parseInt(borderString[0]));
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

	static boolean addToContinentMap(Country newCountry) {
		Continent tempContinent = map.getContinent(newCountry.getContinentName());
		HashMap<String, Country> tempMap = tempContinent.getCountries();
		if(!tempMap.containsKey(newCountry.getCountryName())) {
			tempMap.put(newCountry.getCountryName(), newCountry);
			return true;
		}
		else
			System.out.println("Country already exists.");
		return false;
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
		//map.printContinentsSize();
		for(int i=1;i<=map.getNumberOfContinents();i++) {
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
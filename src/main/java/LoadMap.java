package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Responsible for loading map from ".map" file.
 * Map is loaded as GameMap object.
 * 
 * @author Smeet
 *
 */
public class LoadMap {
	/**
	 * Tracks the index value of continents, new or exisiting, to later facilitate writing them to
	 * map files following domaination's conventions.
	 */
	public static int inMapIndex = 1;	
	
	private GameMap map;	
	private HashMap<Integer, Country> listOfCountries; //temporary HashMap to facilitate reading .map files
	
	
	//temporary main method for testing purpose.
	public static void main(String[] args){
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the name of the map:");
		String mapName = in.nextLine();
		LoadMap loadedMap = new LoadMap();
		loadedMap.readMap(mapName);
		in.close();
		loadedMap.printMap();
	}
	
	/**
	 * Getter method for getting the game map.
	 * @return GameMap object that is created.
	 */
	public GameMap getMap() {
		return this.map;
	}
	
	/**
	 * Setter method for setting the game map.
	 * @param map GameMap object to be set.
	 */
	public void setMap(GameMap map) {
		this.map = map;
	}
	/**
	 * Reads the ".map" file and creates a GameMap object accordingly.
	 * Performs basic validation checks too.
	 * @param mapName Name of the map file to be read
	 * @return GameMap object representing the map just read
	 * @throws FileNotFoundException, IOException
	 */
	public GameMap readMap(String mapName) {
		map = new GameMap(mapName);
		listOfCountries = new HashMap<Integer, Country>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(mapName));
			String s;
			while ((s = reader.readLine()) != null) {
				if(s.equals("[continents]"))
					reader = readContinents(reader);
				if(s.equals("[countries]"))
					reader = readCountries(reader);
				if(s.equals("[borders]"))
					reader = readBorders(reader);
			}   
			reader.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			System.out.println(e.getMessage());
		}
		catch(IOException e) {
			System.out.println("IOException");
			System.out.println(e.getMessage());
		}
		return map;
	}
	
	/**
	 * Reads the continents from the ".map" files.
	 * Exits the program if error of duplicate continents is found.
	 * @param reader Stream starting from continents section of ".map" file
	 * @return BufferedReader stream at the point where it has finished reading continents
	 * @throws IOException
	 */
	private BufferedReader readContinents(BufferedReader reader) {
		String s;
		boolean continentExists = false;
		try {
			while(!((s = reader.readLine()).equals(""))) {
				String[] continentString = s.split("\\s+");
				
				//Check if continent already exists in the map
				continentExists = MapValidator.doesContinentExist(map, continentString[0]);
				if(!continentExists) {
					map.getContinents().put(continentString[0].toLowerCase(), new Continent(continentString[0], continentString[1], continentString[2]));
					inMapIndex++;
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
	
	/**
	 * Reads the countries from the ".map" files.
	 * Exits the program if duplicate country or in non-existent country.
	 * @param reader Stream starting from countries section of ".map" file
	 * @return BufferedReader stream at the point where it has finished reading countries
	 * @throws IOException
	 */
	private BufferedReader readCountries(BufferedReader reader) {
		String s;
		try {
			while(!((s = reader.readLine()).equals(""))) {
				String[] countryString = s.split("\\s+");
				Country newCountry = new Country(countryString[0], countryString[1], countryString[2], countryString[3], countryString[4], map);
				if(newCountry.getInContinent()==null)
				{
					System.out.println("Country: " + newCountry.getCountryName() + " continent name: " + newCountry.getInContinent());
					System.out.println("Error reading the file.");
					System.out.println("This continent does not exist.");
					System.exit(-1);
				}
				addToContinentMap(newCountry);	//Add country to the appropriate continent in the map. Terminate if duplicate entry.
				listOfCountries.put(newCountry.getIndex(), newCountry);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return reader;
	}
	
	/**
	 * Reads the borders from the ".map" files.
	 * Exits the programming error if attempted to add invalid neighbor or to an invalid country.
	 * @param reader Stream starting from borders section of ".map" file
	 * @return BufferedReader stream at the end of file
	 * @throws IOException
	 */
	private BufferedReader readBorders(BufferedReader reader) {
		String s;
		try {
			while((s = reader.readLine()) != null) {
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

	/**
	 * Registers this new country as part of its continent.
	 * If duplicate country, exits the program throwing error.
	 * @param newCountry Country to be registered with the corresponding continent
	 */
	private void addToContinentMap(Country newCountry) {
		
		if(!MapValidator.doesCountryExist(map, newCountry.getCountryName())) {
			Continent argumentContinent = map.getContinents().get(newCountry.getInContinent());
			argumentContinent.getCountries().put(newCountry.getCountryName().toLowerCase(), newCountry);
			map.getCountries().put(newCountry.getCountryName().toLowerCase(), newCountry);
		}
		else {
			//terminate the program if same name country exists in the continent
			System.out.println("Error reading the file.");
			System.out.println("Two countries of same name exists in the same continent.");
			System.exit(-1);
		}
	}
	
	/**
	 * Registers the country at argument 'stringIndex' with the argumentCountry.
	 * Exits the programming throwing error if invalid neighbor is found.
	 * @param argumentCountry Country to which neighbor is to be registered.
	 * @param stringIndex Index of the country to be added as a neighbor to the argument country
	 */
	private void addNeighbour(Country argumentCountry, String stringIndex) {
		int borderIndex = Integer.parseInt(stringIndex);
		Country neighbourCountry = new Country();
		try {
			neighbourCountry = listOfCountries.get(borderIndex);
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("Error reading the file.");
			System.out.println("Neighbour labelled " + borderIndex + " does not exist.");
			System.exit(-1);
		}
		if(!argumentCountry.getNeighbours().containsKey(neighbourCountry.getCountryName().toLowerCase()))
			argumentCountry.getNeighbours().put(neighbourCountry.getCountryName().toLowerCase(), neighbourCountry);
	}
	
	/**
	 * Prints the map listing all continents, its respective countries, and their respective neighbors
	 */
	public void printMap() {
		System.out.println("Map name: " + map.getMapName());
		for(Continent continent : map.getContinents().values()) {
			System.out.println("Continent name: " + continent.getContinentName());
			for(Country country : continent.getCountries().values()) {
				System.out.println("\tCountry: " + country.getCountryName());
				System.out.println("\tNeighbours:");
				for(Country neighbour : country.getNeighbours().values())
					System.out.print("\t" + neighbour.getCountryName());
				System.out.println("");
			}
		}
	}
}
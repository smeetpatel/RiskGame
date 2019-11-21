package main.java.model;

import java.io.*;
import java.util.HashMap;

/**
 * Responsible for loading map from ".map" file.
 * Map is loaded as GameMap object.
 * 
 * @author Smeet
 */
public class LoadMap {
	/**
	 * Tracks the index value of continents, new or existing, to later facilitate writing them to
	 * map files following domaination's conventions.
	 */
	//public static int inMapIndex = 1;
	private int inMapIndex = 1;

	/**
	 * Represents the game map.
	 */
	private GameMap map;

	/**
	 * Stores list of countries in the map.
	 * Facilitates reading neighbors of a country written following the rules of ".map" files of domination file format
	 * HashMap has
	 * 1) Key: integer value representing the index of the country in ".map" file of domination file format
	 * 2) Value: corresponding Country object
	 */
	private HashMap<Integer, Country> listOfCountries; //temporary HashMap to facilitate reading .map files

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
	 * Gets the inMapIndex value for the continent
	 * @return returns index value of the continent in the map
	 */
	public int getInMapIndex() {
		return inMapIndex;
	}

	/**
	 * Sets the in map index value for the continent
	 * @param inMapIndex index value of the continent in the map
	 */
	public void setInMapIndex(int inMapIndex) {
		this.inMapIndex = inMapIndex;
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
				if(!continentExists && Integer.parseInt(continentString[1])>=0) {
					map.getContinents().put(continentString[0].toLowerCase(), new Continent(continentString[0], continentString[1], continentString[2], this.inMapIndex));
					this.inMapIndex++;
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
		this.inMapIndex = 1;
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
				try {
					if(newCountry.getInContinent()==null)
					{
						//System.out.println("continent: " + countryString[2]);
						//System.out.println("Country: " + newCountry.getCountryName() + " continent name: " + newCountry.getInContinent());
						System.out.println("Error reading the file.");
						System.out.println("This continent does not exist.");
						System.exit(-1);
					}
					addToContinentMap(newCountry);	//Add country to the appropriate continent in the map. Terminate if duplicate entry.
					listOfCountries.put(newCountry.getIndex(), newCountry);
				}
				catch(NullPointerException e) {
					e.printStackTrace();
				}
				
				
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
		
		if(!MapValidation.doesCountryExist(map, newCountry.getCountryName())) {
			//newCountry.printCountry();
			Continent argumentContinent = map.getContinents().get(newCountry.getInContinent().toLowerCase());
			//System.out.println("Fetched continent: " + argumentContinent.getContinentName());
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
	 * Saves GameMap object as ".map" file following Domination game format
	 *
	 * @param map      GameMap object representing the map to be saved
	 * @param fileName Name with which map file is to be saved
	 * @return true if successful, else false indicating invalid command
	 * @throws IOException
	 */
	public boolean saveMap(GameMap map, String fileName) {
		//Check if map is valid or not
		GameActions gameActions = new GameActions();
		if (gameActions.validateMap(map) == MapValidityStatus.VALIDMAP) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/maps/" + fileName + ".map"));
				int continentIndex = 1;    //to track continent index in "map" file
				int countryIndex = 1; //to track country index in "map" file
				HashMap<Integer, String> indexToCountry = new HashMap<Integer, String>(); //to get in country name corresponding to in map index to be in compliance with Domination format
				HashMap<String, Integer> countryToIndex = new HashMap<String, Integer>(); //to get in map index to be in compliance with Domination format

				//write preliminary basic information
				writer.write("name " + fileName + " Map");
				writer.newLine();
				writer.newLine();
				writer.write("[files]");
				writer.newLine();
				writer.newLine();
				//writer.newLine();
				writer.flush();

				//write information about all the continents
				writer.write("[continents]");
				writer.newLine();
				for (Continent continent : map.getContinents().values()) {
					writer.write(continent.getContinentName() + " " + Integer.toString(continent.getControlValue()) + " " + continent.getColorCode());
					writer.newLine();
					writer.flush();
					continent.setInMapIndex(continentIndex);
					continentIndex++;
				}
				writer.newLine();

				//write information about all the countries
				writer.write("[countries]");
				writer.newLine();
				for (Country country : map.getCountries().values()) {
					writer.write(Integer.toString(countryIndex) + " " + country.getCountryName() + " " + Integer.toString(map.getContinents().get(country.getInContinent().toLowerCase()).getInMapIndex()) + " " + country.getxCoOrdinate() + " " + country.getyCoOrdinate());
					writer.newLine();
					writer.flush();
					indexToCountry.put(countryIndex, country.getCountryName().toLowerCase());
					countryToIndex.put(country.getCountryName().toLowerCase(), countryIndex);
					countryIndex++;
				}
				writer.newLine();
				//countryIndex = 1;

				//write information about all the borders
				writer.write("[borders]");
				writer.newLine();
				//writer.newLine();
				writer.flush();
				for (int i = 1; i < countryIndex; i++) {
					String countryName = indexToCountry.get(i);
					Country c = map.getCountries().get(countryName.toLowerCase());
					writer.write(Integer.toString(i) + " ");
					for (Country neighbor : c.getNeighbours().values()) {
						writer.write(Integer.toString(countryToIndex.get(neighbor.getCountryName().toLowerCase())) + " ");
						writer.flush();
					}
					writer.newLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		} else {
			return false;
		}
	}
}
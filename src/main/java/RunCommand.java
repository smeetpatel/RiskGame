package main.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class containing logic for executing commands
 * @author Smeet
 *
 */
public class RunCommand {

	/**
	 * Loads map as GameMap object for editing.
	 * If the map file does not exist, creates a new GameMap object to add information.
	 * @param mapName Name of the map to be searched/created
	 * @return map object representing the existing map or null value if new one is to be created
	 */
	public GameMap editMap(String mapName) {
		//Check if file exists
		String filePath = "maps/" + mapName;
		GameMap map;
		File f = new File(filePath);
		if(f.exists()) {
			LoadMap lm = new LoadMap();
			map = lm.readMap(filePath);
		}
		else {
			System.out.println(mapName + " does not exist.");
			System.out.println("Creating a new map named " + mapName);
			map = new GameMap(mapName);
		}
		return map;
	}
	
	/**
	 * Loads map as GameMap object for playing the game.
	 * If map file does not exist, it reflects the command as invalid command.
	 * @param mapName name of the map to be used for playing the game
	 * @return map representing the existing map to be used for game play
	 */
	public GameMap loadMap(String mapName) {
		//check if file exists
		String filePath = "maps/" + mapName;
		GameMap map;
		File f = new File(filePath);
		if(f.exists()) {
			LoadMap lm = new LoadMap();
			map = lm.readMap(filePath);
			if(validateMap(map)) {
				//System.out.println("Valid in loading map");
				map.setValid(true);
			}
			else {
				System.out.println("Map not suitable for game play. Correct the map to continue with this map or load another map from the existing maps. ");
				map.setValid(false);
			}
		}
		else {
			//error
			System.out.println("Map " + mapName + " does not exist. Try again or use 'editmap' if you want to create new map.");
			return null;
		}
		return map;
	}
	
	/**
	 * Add continent with given name and control value if it is valid entry.
	 * Invalid if continent already exists.
	 * @param map GameMap representing the map to which continent is to be added
	 * @param continentName Name of the continent to be added
	 * @param controlValue Control value of the continent to be added
	 * @return true if valid entry, else false indicating invalid command
	 */
	public boolean addContinent(GameMap map, String continentName, int controlValue) {
		//check if continent already exists
		if(!(MapValidator.doesContinentExist(map, continentName))) {
			Continent continent = new Continent(continentName, controlValue);
			map.getContinents().put(continentName.toLowerCase(), continent);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Removes the continent and all the countries present in that continent.
	 * Removes neighboring node for all the countries being removed.
	 * @param map GameMap object representing the map being edited
	 * @param continentName Name of the continent to be removed
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean removeContinent(GameMap map, String continentName) {
		//check if argument continent exists or not
		if(map.getContinents().containsKey(continentName.toLowerCase())) {
			Continent continent = map.getContinents().get(continentName.toLowerCase());
			//remove each country of the continent
			for(Country c : continent.getCountries().values()) {
				if(!removeCountry(map, c.getCountryName()))
						return false;
			}
			map.getContinents().remove(continentName.toLowerCase());
			return true;
		}
		else {
			System.out.println(continentName + " does not exist.");
			return false;
		}
	}
	
	/**
	 * Add country to the map as per the argument parameters if it is a valid entry.
	 * Invalid if country already exists.
	 * @param map GameMap representing the map to which country is to be added
	 * @param countryName Name of the country which is to be added
	 * @param continentName Name of the continent to which this new country belongs
	 * @return true if valid entry, else false indicating invalid command
	 */
	public boolean addCountry(GameMap map, String countryName, String continentName) {
		//check if argument country exists or not
		if(!MapValidator.doesCountryExist(map, countryName)) {
			Country country = new Country(countryName, continentName);
			Continent continent = map.getContinents().get(continentName.toLowerCase());
			continent.getCountries().put(countryName.toLowerCase(), country);
			map.getCountries().put(countryName.toLowerCase(), country);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Removes the country and removes link to all its neighbors.
	 * @param map GameMap object representing the map being edited
	 * @param countryName Name of the country to be removed
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean removeCountry(GameMap map, String countryName) {
		//check if argument country exists or not
		if(map.getCountries().containsKey(countryName.toLowerCase())) {
			Country country = map.getCountries().get(countryName.toLowerCase());
			//remove each neighbor link of this country
			for(Country neighbor : country.getNeighbours().values()) {
				if(!removeNeighbor(map, country.getCountryName(), neighbor.getCountryName()))
					return false;
			}
			map.getCountries().remove(countryName.toLowerCase());
			return true;
		}
		else {
			System.out.println(countryName + " does not exist.");
			return false;
		}
		
	}
	
	/**
	 * Adds link between the two argument countries to indicate their neighborhood.
	 * @param map GameMap object representing the map being edited
	 * @param country1 Name of one of the participating country
	 * @param country2 Name of another participating country
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean addNeighbor(GameMap map, String country1, String country2) {
		//Check if both the country exists
		if(map.getCountries().containsKey(country1.toLowerCase()) && map.getCountries().containsKey(country2.toLowerCase())) {
			Country c1 = map.getCountries().get(country1.toLowerCase());
			Country c2 = map.getCountries().get(country2.toLowerCase());
			if(!c1.getNeighbours().containsKey(c2.getCountryName().toLowerCase()))
				c1.getNeighbours().put(country2.toLowerCase(), c2);
			if(!c2.getNeighbours().containsKey(c1.getCountryName().toLowerCase()))
				c2.getNeighbours().put(country1.toLowerCase(), c1);
			return true;
		}
		else {
			if(!map.getCountries().containsKey(country1.toLowerCase()) && !map.getCountries().containsKey(country2.toLowerCase()))
				System.out.println(country1 + " and " + country2 + "  does not exist. Create country first and then set their neighbors.");
			else if(!map.getCountries().containsKey(country1.toLowerCase()))
				System.out.println(country1 + " does not exist. Create country first and then set its neighbors.");
			else
				System.out.println(country2 + " does not exist. Create country first and then set its neighbors.");
			return false;
		}
	}
	
	/**
	 * Removes the link between the two neighboring Country objects
	 * @param map GameMap object representing the map being edited
	 * @param country1 One of the argument countries
	 * @param country2 One of the argument countries
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean removeNeighbor(GameMap map, String country1, String country2) {
		//Check if both the country exists
		if(map.getCountries().containsKey(country1.toLowerCase()) && map.getCountries().containsKey(country2.toLowerCase())) {
			Country c1 = map.getCountries().get(country1.toLowerCase());
			Country c2 = map.getCountries().get(country2.toLowerCase());
			c1.getNeighbours().remove(country2.toLowerCase());
			c2.getNeighbours().remove(country1.toLowerCase());
			return true;
		}
		else {
			if(!map.getCountries().containsKey(country1.toLowerCase()) && !map.getCountries().containsKey(country2.toLowerCase()))
				System.out.println(country1 + " and " + country2 + "  does not exist.");
			else if(!map.getCountries().containsKey(country1.toLowerCase()))
				System.out.println(country1 + " does not exist.");
			else
				System.out.println(country2 + " does not exist.");
			return false;
		}
	}
	
	/**
	 * Prints the continents, countries, and neighbors for each country present in the map.
	 * @param map GameMap object representing the map to be shown.
	 */
	public void showMap(GameMap map) {
		LoadMap l = new LoadMap();
		l.setMap(map);
		l.printMap();
	}
	
	/**
	 * Saves GameMap object as ".map" file following Domination game format
	 * @param map GameMap object representing the map to be saved
	 * @return true if succesfful, else false indicating invalid command
	 * @throws IOException
	 */
	public boolean saveMap(GameMap map, String fileName) {
		//Check if map is valid or not 
		if(validateMap(map)) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("maps/"+fileName+".map"));
				int continentIndex = 1;	//to track continent index in "map" file
				int countryIndex = 1; //to track country index in "map" file
				HashMap<String, Integer> countryToIndex = new HashMap<String, Integer>(); //to get in map index to be in compliance with Domination format
				ArrayList<Country> borderWritingList = new ArrayList<Country>(); //to write borders in order to be in compliance with Domination format
				
				//write preliminary basic information
				writer.write("name " + map.getMapName() + " Map");
				writer.newLine();
				writer.write("[files]");
				writer.newLine();
				
				//write information about all the continents
				writer.write("[continents]");
				writer.newLine();
				for(Continent continent : map.getContinents().values()) {
					writer.write(continent.getContinentName() + " " + Integer.toString(continent.getControlValue()) + " " + continent.getColorCode());
					writer.newLine();
					continent.setInMapIndex(continentIndex);
					continentIndex++;
				}
				
				//write information about all the countries
				writer.write("[countries]");
				writer.newLine();
				for(Country country : map.getCountries().values()) {
					writer.write(Integer.toString(countryIndex) + " " + country.getCountryName() + " " + map.getContinents().get(country.getInContinent()).getInMapIndex() + " " + country.getxCoOrdinate() + " " + country.getyCoOrdinate());
					writer.newLine();
					countryToIndex.put(country.getCountryName().toLowerCase(), countryIndex);
					countryIndex++;
				}
				countryIndex = 1;
				
				//write information about all the borders
				writer.write("[borders]");
				writer.newLine();
				Iterator<Country> itr = borderWritingList.listIterator();
				while(itr.hasNext()) {
					Country country = itr.next();
					writer.write(countryIndex + " ");
					for(Country c : country.getNeighbours().values()) {
						writer.write(Integer.toString(countryToIndex.get(c.getCountryName().toLowerCase())) + " ");
					}
					writer.newLine();
					writer.close();
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		else
		{
			System.out.println("Map not suitable for game play. Correct the map to continue with the new map or load a map from the existing maps.");
			return false;
		}
	}
	
	/**
	 * Runs various validity checks to ensure that map is suitable for playing the game.
	 * Checks:
	 * 	1) If any empty continent is present, i.e. continent without any country
	 * 	2) If each continent is a connected sub-graph
	 * 	3) If map for the game is connected graph or not
	 * @param map GameMap representing the map
	 * @return returns true if valid map, else false indicating invalid map
	 */
	public boolean validateMap(GameMap map) {
		MapValidator mv = new MapValidator();
		if(!mv.notEmptyContinent(map)) {
			System.out.println("Invalid map - emtpy continent present.");
			return false;
		}
		else if(!mv.isGraphConnected(mv.createGraph(map))) {
			System.out.println("Invalid map - not a connected graph");
			return false;
		}
		else if(!mv.continentConnectivityCheck(map)) {
			System.out.println("Invalid map - one of the continent is not a connected sub-graph");
			return false;
		}
		
		return true;
	}
}
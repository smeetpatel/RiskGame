package main.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class containing logic for executing map related commands
 * @author Smeet
 */
public class MapEditor {

	/**
	 * Loads map as GameMap object for editing.
	 * If the map file does not exist, creates a new GameMap object to add information.
	 * @param mapName Name of the map to be searched/created
	 * @return map object representing the existing map or null value if new one is to be created
	 */
	public void editMap(GameData game, String mapName) {
		//Check if file exists
		String filePath = "maps/" + mapName;
		GameMap map;
		File f = new File(filePath);
		if(f.exists()) {
			LoadMap lm = new LoadMap();
			map = lm.readMap(filePath);
			map.setMapName(mapName);
		}
		else {
			map = new GameMap(mapName);
		}
		game.setMap(map);
		game.setGamePhase(Phase.EDITMAP);
	}
	
	/**
	 * Loads map as GameMap object for playing the game.
	 * If map file does not exist, it reflects the command as invalid command.
	 * @param mapName name of the map to be used for playing the game
	 * @return map representing the existing map to be used for game play
	 */
	public boolean loadMap(GameData game, String mapName) {
		//check if file exists
		String filePath = "maps/" + mapName;
		GameMap map;
		File f = new File(filePath);
		if(f.exists()) {
			LoadMap lm = new LoadMap();
			map = lm.readMap(filePath);
			map.setMapName(mapName);
			game.setMap(map);
			if(validateMap(map)==MapValidityStatus.VALIDMAP) {
				map.setValid(true);
				game.setGamePhase(Phase.STARTUP);
			}
			else {
				map.setValid(false);
				game.setGamePhase(Phase.NULL);
			}
		}
		else {
			game.setGamePhase(Phase.NULL);
			return false;
		}
		return true;
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
			if(controlValue<0){
				return false;
			}
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
			ArrayList<Country> tempList = new ArrayList<Country>();
			for(Country c : continent.getCountries().values()) {
				tempList.add(c);
			}
			Iterator<Country> itr = tempList.listIterator();
			while(itr.hasNext()) {
				Country c = itr.next();
				if(!removeCountry(map, c.getCountryName()))
					return false;
			}
			map.getContinents().remove(continentName.toLowerCase());
			return true;
		}
		else {
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
			if(!map.getContinents().containsKey(continentName.toLowerCase())) {
				return false;
			}
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
			ArrayList<Country> tempList = new ArrayList<Country>();
			
			//remove each neighbor link of this country
			for(Country neighbor : country.getNeighbours().values()) {
				tempList.add(neighbor);
			}
			Iterator<Country> itr = tempList.listIterator();
			while(itr.hasNext()) {
				Country neighbor = itr.next();
				if(!removeNeighbor(map, country.getCountryName(), neighbor.getCountryName()))
					return false;
			}
			map.getCountries().remove(countryName.toLowerCase());
			map.getContinents().get(country.getInContinent().toLowerCase()).getCountries().remove(countryName.toLowerCase());
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * Adds link between the two argument countries to indicate their neighborhood.
	 * @param map GameMap object representing the map being edited
	 * @param countryName Name of one of the participating country
	 * @param neighborCountryName Name of another participating country
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean addNeighbor(GameMap map, String countryName, String neighborCountryName) {
		//Check if both the country exists
		if(map.getCountries().containsKey(countryName.toLowerCase()) && map.getCountries().containsKey(neighborCountryName.toLowerCase())) {
			Country c1 = map.getCountries().get(countryName.toLowerCase());
			Country c2 = map.getCountries().get(neighborCountryName.toLowerCase());
			if(!c1.getNeighbours().containsKey(c2.getCountryName().toLowerCase()))
				c1.getNeighbours().put(neighborCountryName.toLowerCase(), c2);
			if(!c2.getNeighbours().containsKey(c1.getCountryName().toLowerCase()))
				c2.getNeighbours().put(countryName.toLowerCase(), c1);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Removes the link between the two neighboring Country objects
	 * @param map GameMap object representing the map being edited
	 * @param countryName One of the argument countries
	 * @param neighborCountryName One of the argument countries
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean removeNeighbor(GameMap map, String countryName, String neighborCountryName) {
		//Check if both the country exists
		if(map.getCountries().containsKey(countryName.toLowerCase()) && map.getCountries().containsKey(neighborCountryName.toLowerCase())) {
			Country c1 = map.getCountries().get(countryName.toLowerCase());
			Country c2 = map.getCountries().get(neighborCountryName.toLowerCase());
			c1.getNeighbours().remove(neighborCountryName.toLowerCase());
			c2.getNeighbours().remove(countryName.toLowerCase());
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Saves GameMap object as ".map" file following Domination game format
	 * @param map GameMap object representing the map to be saved
	 * @return true if successful, else false indicating invalid command
	 * @throws IOException
	 */
	public boolean saveMap(GameMap map, String fileName) {
		//Check if map is valid or not 
		if(validateMap(map)==MapValidityStatus.VALIDMAP) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("maps/"+fileName+".map"));
				int continentIndex = 1;	//to track continent index in "map" file
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
				for(Continent continent : map.getContinents().values()) {
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
				for(Country country : map.getCountries().values()) {
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
				for(int i=1;i<countryIndex;i++) {
					String countryName = indexToCountry.get(i);
					Country c = map.getCountries().get(countryName.toLowerCase());
					writer.write(Integer.toString(i) + " ");
					for(Country neighbor : c.getNeighbours().values()) {
						writer.write(Integer.toString(countryToIndex.get(neighbor.getCountryName().toLowerCase())) + " ");
						writer.flush();
					}
					writer.newLine();
				}
			}
			catch(IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		else
		{
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
	public MapValidityStatus validateMap(GameMap map) {
		MapValidator mv = new MapValidator();
		if(!mv.notEmptyContinent(map)) {

			return MapValidityStatus.EMPTYCONTINENT;
		}
		else if(!mv.isGraphConnected(mv.createGraph(map))) {

			return MapValidityStatus.UNCONNECTEDGRAPH;
		}
		else if(!mv.continentConnectivityCheck(map)) {
			return MapValidityStatus.UNCONNECTEDCONTINENT;
		}
		
		return MapValidityStatus.VALIDMAP;
	}
}
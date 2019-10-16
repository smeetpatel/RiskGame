package main.java;

import java.util.HashMap;

/**
 * GameMap holds the map of the Risk game. This map can be directly used or edited as
 * per the allowed commands.
 * 
 * Holds HashMaps to access countries and continents of the map with their names.
 * Note that the keys for both HashMaps are stored in lower case. 
 * @author Smeet
 *
 */
public class GameMap {
	private String mapName;
	private HashMap<String, Continent> continents;
	private HashMap<String, Country> countries;
	private boolean valid;
	
	/**
	 * Creates a GameMap object without naming the map.
	 */
	GameMap(){
		this.mapName = "";
		this.continents = new HashMap<String, Continent>();
		this.countries = new HashMap<String, Country>();
		this.valid = false;
	}
	
	/**
	 * Registers the name of the map.
	 * Initializes HashMaps for maintaining continents and countries.
	 * @param mapName name of the map
	 */
	public GameMap(String mapName){
		this.mapName = mapName;
		this.continents = new HashMap<String, Continent>();
		this.countries = new HashMap<String, Country>();
		this.valid = false;
	}

	/**
	 * Returns the name of the map.
	 * @return returns the name of the map
	 */
	public String getMapName() {
		return this.mapName;
	}
	
	/**
	 * Set name of the map to the given name.
	 * @param mapName Name of the map
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	/**
	 * Returns the HashMap maintaining list of continents in the map.
	 * @return returns the HashMap maintaining list of continents in the map.
	 */
	public HashMap<String, Continent> getContinents(){
		return this.continents;
	}
	
	/**
	 * Sets the continents HashMap to the argument HashMap.
	 * @param continents Altered HashMap for continents 
	 */
	public void setContinents(HashMap<String, Continent> continents) {
		this.continents = continents;
	}
	
	/**
	 * Returns the HashMap maintaining list of continents in the map.
	 * @return returns the HashMap maintaining list of countries in the map.
	 */
	public HashMap<String, Country> getCountries(){
		return this.countries;
	}
	
	/**
	 * Sets the countries HashMap to the argument HashMap.
	 * @param countries Altered HashMap for countries 
	 */
	public void setCountries(HashMap<String, Country> countries) {
		this.countries = countries;
	}
	
	/**
	 * Getter method to fetch valid variable
	 * @return returns whether the map is valid for game play or not
	 */
	public boolean getValid() {
		return this.valid;
	}
	
	/**
	 * Setter method to set status for validity of the map
	 * @param valid Indicates whether the map is valid for game play or not
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
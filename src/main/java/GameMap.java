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
	
	/**
	 * Registers the name of the map.
	 * Initializes HashMaps for maintaining continents and countries.
	 * @param mapName name of the map
	 */
	GameMap(String mapName){
		this.mapName = mapName;
		this.continents = new HashMap<String, Continent>();
		this.countries = new HashMap<String, Country>();
	}
	
	/**
	 * Returns the name of the map.
	 * @return returns the name of the map
	 */
	String getMapName() {
		return this.mapName;
	}
	
	/**
	 * Returns the HashMap maintaining list of continents in the map.
	 * @return returns the HashMap maintaining list of continents in the map.
	 */
	HashMap<String, Continent> getContinents(){
		return this.continents;
	}
	
	/**
	 * Sets the continents HashMap to the argument HashMap.
	 * @param continents Altered HashMap for continents 
	 */
	void setContinents(HashMap<String, Continent> continents) {
		this.continents = continents;
	}
	
	/**
	 * Returns the HashMap maintaining list of continents in the map.
	 * @return returns the HashMap maintaining list of countries in the map.
	 */
	HashMap<String, Country> getCountries(){
		return this.countries;
	}
	
	/**
	 * Sets the countries HashMap to the argument HashMap.
	 * @param countries Altered HashMap for countries 
	 */
	void setCountries(HashMap<String, Country> countries) {
		this.countries = countries;
	}
}
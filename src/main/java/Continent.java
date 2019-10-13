package main.java;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Continent class holds the details about continents like its name, control value, color code,
 * and the countries present in this continent.
 * 
 * The countries in a particular continent are stored as a HashMap with country name as the 
 * key and related Country object references as the value.
 * Note that the keys for HashMaps are stored in lower case. 
 * @author Smeet
 *
 */
public class Continent {
	private String continentName;
	private int inMapIndex;	//index to further assist in writing files in ".map" format
	private int controlValue;
	private String colorCode;
	private HashMap<String, Country> countries;
	
	/**
	 * Creates a Continent object based only on it's name and control value.
	 * Will be used when user defined continents are to be created.
	 * @param continentName Name of the continent
	 * @param controlValue Control value for this continent
	 */
	Continent(String continentName, int controlValue){
		this.continentName = continentName;
		this.inMapIndex = LoadMap.inMapIndex;
		LoadMap.inMapIndex++;
		this.controlValue = controlValue;
		this.colorCode = "";
		this.countries = new HashMap<String, Country>();
	}	
	
	/**
	 * Creates a Continent object based on argument parameters.
	 * Will be used when reading from the ".map" files. 
	 * @param continentName Name of the continent
	 * @param controlValue Control value for this continent
	 * @param colorCode Color code in case using GUI
	 */
	Continent(String continentName, String controlValue, String colorCode){
		this.continentName = continentName;
		this.inMapIndex = LoadMap.inMapIndex;
		this.controlValue = Integer.parseInt(controlValue);
		this.colorCode = colorCode;
		this.countries = new HashMap<String, Country>();
	}
	
	/**
	 * Returns the name of the continent.
	 * @return returns the name of the continent
	 */
	String getContinentName() {
		return this.continentName;
	}
	
	/**
	 * Returns the index for this continent when saved  in ".map" file following domination's rules
	 * @return returns index of the continent
	 */
	int getInMapIndex() {
		return this.inMapIndex;
	}
	
	/**
	 * Returns the HashMap containing countries belonging to this continent
	 * @return returns the HashMap containing countries belonging to this continent
	 */
	HashMap<String, Country> getCountries() {
		return countries;
	}
}
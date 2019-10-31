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
	/**
	 * Represents the name of the continent.
	 */
	private String continentName;

	/**
	 * Index value of the continent when saving to ".map" file of domination game format.
	 */
	private int inMapIndex;

	/**
	 * Control value of the continent.
	 */
	private int controlValue;

	/**
	 * Color code for the continent for GUI uses.
	 */
	private String colorCode;

	/**
	 * Stores the countries belonging to this continent.
	 * HashMap has
	 * 1) Key: name of the country stored in lower case
	 * 2) Value: corresponding Country object
	 */
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
		this.controlValue = controlValue;
		this.colorCode = "000";
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
	public String getContinentName() {

		return this.continentName;
	}
	
	/**
	 * Returns the index for this continent when saved  in ".map" file following domination's rules
	 * @return returns index of the continent
	 */
	public int getInMapIndex() {
		return this.inMapIndex;
	}

	/**
	 * Sets the index of the continent as per domination file format.
	 * @param inMapIndex Index value of the continent in ".map" file of domination game format
	 */
	public void setInMapIndex(int inMapIndex) {
		this.inMapIndex = inMapIndex;
	}
	
	/**
	 * Getter method to fetch the control value of the continent
	 * @return returns the control value of the continent
	 */
	public int getControlValue() {
		return this.controlValue;
	}

	/**
	 * Setter method to set the control value of the continent
	 * @param controlValue set control value of the continent to this value
	 */
	public void setControlValue(int controlValue) {
		this.controlValue = controlValue;
	}
	
	/**
	 * Getter method to fetch the color code of the continent
	 * @return returns the color code of the continent
	 */
	public String getColorCode() {
		return this.colorCode;
	}

	/**
	 * Setter method to set the color code of the continent
	 * @param colorCode set color code of the continent to this value
	 */
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	/**
	 * Returns the HashMap containing countries belonging to this continent
	 * @return returns the HashMap containing countries belonging to this continent
	 */
	public HashMap<String, Country> getCountries() {
		return countries;
	}
}
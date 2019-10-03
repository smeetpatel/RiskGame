package main.java;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Continent class holds the details about continents like its name, control value, color code,
 * and the countries present in this continent.
 * 
 * The countries in a particular continent are stored as a HashMap with country name as the 
 * key and related Country object as the key.
 * @author Smeet
 *
 */
public class Continent {
	private String continentName;
	private int controlValue;
	private String colorCode;
	private HashMap<String, Country> countries;
	
	Continent(String continentName, int controlValue){
		this.continentName = continentName;
		this.controlValue = controlValue;
		this.colorCode = "";
		this.countries = new HashMap<String, Country>();
	}	
	
	Continent(String continentName, String controlValue, String colorCode){
		this.continentName = continentName;
		this.controlValue = Integer.parseInt(controlValue);
		this.colorCode = colorCode;
		this.countries = new HashMap<String, Country>();
	}
	
	String getContinentName() {
		return this.continentName;
	}
	
	HashMap<String, Country> getCountries() {
		return countries;
	}
}
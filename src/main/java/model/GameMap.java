package main.java.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * GameMap holds the map of the Risk game. This map can be directly used or edited as
 * per the allowed commands.
 * 
 * Holds HashMaps to access countries and continents of the map with their names.
 * Note that the keys for both HashMaps are stored in lower case. 
 * @author Smeet
 *
 */
public class GameMap implements Serializable {
	/**
	 * Name of the game map.
	 */
	private String mapName;

	/**
	 * Stores the continents present in the map.
	 * HashMap has
	 * 1) Key: name of the continent in lower case
	 * 2) Value: corresponding Continent object
	 */
	private HashMap<String, Continent> continents;

	/**
	 * Stores the countries present in the map.
	 * HashMap has
	 * 1) Key: name of the country in lower case
	 * 2) Value: corresponding Country object
	 */
	private HashMap<String, Country> countries;

	/**
	 * Boolean indicating whether the map is valid for Risk game play or not.
	 */
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
	 * Returns the HashMap maintaining list of countries in the map.
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
	 * Getter method to fetch valid status of the map
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

	/**
	 * Add continent with given name and control value if it is valid entry.
	 * Invalid if continent already exists.
	 * @param continentName Name of the continent to be added
	 * @param controlValue Control value of the continent to be added
	 * @return true if valid entry, else false indicating invalid command
	 */
	public boolean addContinent(String continentName, int controlValue) {
		//check if continent already exists
		if(!(MapValidation.doesContinentExist(this, continentName))) {
			if(controlValue<0){
				return false;
			}
			Continent continent = new Continent(continentName, controlValue);
			this.continents.put(continentName.toLowerCase(), continent);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Removes the continent and all the countries present in that continent.
	 * Removes neighboring node for all the countries being removed.
	 * @param continentName Name of the continent to be removed
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean removeContinent(String continentName) {
		//check if argument continent exists or not
		if(this.continents.containsKey(continentName.toLowerCase())) {
			Continent continent = this.continents.get(continentName.toLowerCase());

			//remove each country of the continent
			ArrayList<Country> tempList = new ArrayList<Country>();
			for(Country c : continent.getCountries().values()) {
				tempList.add(c);
			}
			Iterator<Country> itr = tempList.listIterator();
			while(itr.hasNext()) {
				Country c = itr.next();
				if(!removeCountry(c.getCountryName()))
					return false;
			}
			this.continents.remove(continentName.toLowerCase());
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Add country to the map as per the argument parameters if it is a valid entry.
	 * Invalid if country already exists.
	 * @param countryName Name of the country which is to be added
	 * @param continentName Name of the continent to which this new country belongs
	 * @return true if valid entry, else false indicating invalid command
	 */
	public boolean addCountry(String countryName, String continentName) {
		//check if argument country exists or not
		if(!MapValidation.doesCountryExist(this, countryName)) {
			if(!this.continents.containsKey(continentName.toLowerCase())) {
				return false;
			}
			Country country = new Country(countryName, continentName);
			Continent continent = this.continents.get(continentName.toLowerCase());
			continent.getCountries().put(countryName.toLowerCase(), country);
			this.countries.put(countryName.toLowerCase(), country);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Removes the country and removes link to all its neighbors.
	 * @param countryName Name of the country to be removed
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean removeCountry(String countryName) {
		//check if argument country exists or not
		if(this.countries.containsKey(countryName.toLowerCase())) {

			Country country = this.countries.get(countryName.toLowerCase());
			ArrayList<Country> tempList = new ArrayList<Country>();

			//remove each neighbor link of this country
			for(Country neighbor : country.getNeighbours().values()) {
				tempList.add(neighbor);
			}
			Iterator<Country> itr = tempList.listIterator();
			while(itr.hasNext()) {
				Country neighbor = itr.next();
				if(!removeNeighbor(country.getCountryName(), neighbor.getCountryName()))
					return false;
			}
			this.countries.remove(countryName.toLowerCase());
			this.continents.get(country.getInContinent().toLowerCase()).getCountries().remove(countryName.toLowerCase());
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds link between the two argument countries to indicate their neighborhood.
	 * @param countryName Name of one of the participating country
	 * @param neighborCountryName Name of another participating country
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean addNeighbor(String countryName, String neighborCountryName) {
		//Check if both the country exists
		if(this.countries.containsKey(countryName.toLowerCase()) && this.countries.containsKey(neighborCountryName.toLowerCase())) {
			Country c1 = this.countries.get(countryName.toLowerCase());
			Country c2 = this.countries.get(neighborCountryName.toLowerCase());
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
	 * @param countryName One of the argument countries
	 * @param neighborCountryName One of the argument countries
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean removeNeighbor(String countryName, String neighborCountryName) {
		//Check if both the country exists
		if(this.countries.containsKey(countryName.toLowerCase()) && this.countries.containsKey(neighborCountryName.toLowerCase())) {
			Country c1 = this.countries.get(countryName.toLowerCase());
			Country c2 = this.countries.get(neighborCountryName.toLowerCase());
			c1.getNeighbours().remove(neighborCountryName.toLowerCase());
			c2.getNeighbours().remove(countryName.toLowerCase());
			return true;
		}
		else {
			return false;
		}
	}
}
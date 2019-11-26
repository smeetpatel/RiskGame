package main.java.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Country class holds the details related to any country present in the map.
 * It also maintains a list of it's neighbour 'Country' objects.
 * 
 * @author Smeet
 *
 */

public class Country implements Serializable {
	/**
	 * Index value of the country when saving to ".map" file of domination game format.
	 */
	private int index;

	/**
	 * Represents the name of the country.
	 */
	private String countryName;

	/**
	 * Represents the name of the continent to which this country belongs.
	 */
	private String inContinent;

	/**
	 * Stores the neighboring countries.
	 * 	 * HashMap has
	 * 	 * 1) Key: name of the country stored in lower case
	 * 	 * 2) Value: corresponding Country object
	 */
	private HashMap<String, Country> neighbours;

	/**
	 * X co-ordinate value of the country for GUI uses.
	 */
	private int xCoOrdinate;

	/**
	 * Y co-ordinate value of the country for GUI uses.
	 */
	private int yCoOrdinate;

	/**
	 * Number of armies in this country.
	 */
	private int numberOfArmies;

	/**
	 * Represents the player who currently owns the country.
	 */
	private Player ownerPlayer;

	/**
	 * Create Country object with default values.
	 */
	public Country(){}

	/**
	 * Create country object with values in argument parameters and set defaults for the rest.
	 * @param countryName Name of the country
	 * @param inContinent Name of the continent in which this country belongs
	 */
	public Country(String countryName, String inContinent){
		this.countryName = countryName;
		this.inContinent = inContinent;
		this.neighbours = new HashMap<String, Country>();
		this.numberOfArmies = 0;
		this.ownerPlayer = null;
	}
	
	/**
	 * Creates country object as per the argument parameters.
	 * Used when reading from ".map" files.
	 * @param index index in the ".map" file as per Domination's conventions
	 * @param countryName name of the country
	 * @param continentIndex index of the continent this country belongs to
	 * @param xCoOrdinate x-co-ordinate on GUI map
	 * @param yCoOrdinate y-co-ordinate on GUI map
	 * @param map GameMap in which this country resides
	 */
	Country(String index, String countryName, String continentIndex, String xCoOrdinate, String yCoOrdinate, GameMap map){
		this.index = Integer.parseInt(index);
		this.countryName = countryName;
		for(Continent c : map.getContinents().values()) {
			if(c.getInMapIndex()==Integer.parseInt(continentIndex)) {
				this.inContinent = c.getContinentName();
				//break;
			}	
		}	
		this.neighbours = new HashMap<String, Country>();
		this.xCoOrdinate = Integer.parseInt(xCoOrdinate);
		this.yCoOrdinate = Integer.parseInt(yCoOrdinate);
		this.numberOfArmies = 0;
		this.ownerPlayer = null;
	}

	/**
	 * Creates country object as per the argument parameters.
	 * Used when reading from ".map" files.
	 * @param countryName Name of the country
	 * @param xCoOrdinate x-co-ordinate on GUI map
	 * @param yCoOrdinate y-co-ordinate on GUI map
	 * @param inContinent Represents the name of the continent the country belongs to
	 */
	public Country(String countryName, String xCoOrdinate, String yCoOrdinate, String inContinent){
		this.index = 0;
		this.countryName = countryName;
		this.inContinent = inContinent;
		this.neighbours = new HashMap<String, Country>();
		this.xCoOrdinate = Integer.parseInt(xCoOrdinate);
		this.yCoOrdinate = Integer.parseInt(yCoOrdinate);
		this.numberOfArmies = 0;
		this.ownerPlayer = null;
	}
	/**
	 * Returns the index of this country in the ".map" file as per Domination's conventions
	 * @return returns the index of this country in the ".map" file as per Domination's conventions
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Returns the name of the continent in which this country belongs
	 * @return returns the name of the continent in which this country belongs
	 */
	public String getInContinent() {
		return this.inContinent;
	}
	
	/**
	 * Returns the name of the country
	 * @return returns the name of the country
	 */
	public String getCountryName() {
		return countryName;
	}
	
	/**
	 * Returns the HashMap holding all the neighbors with their names in lower case as keys and their 
	 * Country object references as values.
	 * @return returns neighbors of this country
	 */
	public HashMap<String, Country> getNeighbours(){
		return this.neighbours;
	}

	/**
	 * Getter method to fetch x-co-ordinate value
	 * @return returns x-co-ordinate
	 */
	public int getxCoOrdinate() {
		return this.xCoOrdinate;
	}
	
	/**
	 * Setter method to set x-co-ordinate
	 * @param xCoOrdinate argument x-co-ordinate value to be set
	 */
	public void setxCoOrdinate(int xCoOrdinate) {
		this.xCoOrdinate = xCoOrdinate;
	}
	
	/**
	 * Getter method to fetch y-co-ordinate value
	 * @return returns y-co-ordinate
	 */
	public int getyCoOrdinate() {
		return this.yCoOrdinate;
	}

	/**
	 * Setter method to set y-co-ordinate
	 * @param yCoOrdinate argument y-co-ordinate value to be set
	 */
	public void setyCoOrdinate(int yCoOrdinate) {
		this.yCoOrdinate = yCoOrdinate;
	}

	/**
	 * Getter method to get number of armies in the country.
	 * @return returns number of armies in the player
	 */
	public int getNumberOfArmies() {
		return this.numberOfArmies;
	}

	/**
	 * Set number of armies in the country
	 * @param numberOfArmies number of armies to be set in the country
	 */
	public void setNumberOfArmies(int numberOfArmies) {
		this.numberOfArmies = numberOfArmies;
	}

	/**
	 * Gets the player owning the country currently.
	 * @return Player owning this country.
	 */
	public Player getOwnerPlayer() {
		return ownerPlayer;
	}

	/**
	 * Sets the player owning this country currently
	 * @param ownerPlayer Player owning this country.
	 */
	public void setOwnerPlayer(Player ownerPlayer) {
		this.ownerPlayer = ownerPlayer;
	}
}
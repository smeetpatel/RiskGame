package main.java;

import java.util.HashMap;

/**
 * Country class holds the details related to any country present in the map.
 * It also maintains a list of it's neighbour 'Country' objects.
 * 
 * @author Smeet
 *
 */

public class Country {
	private int index;
	private String countryName;
	private String inContinent;
	private HashMap<String, Country> neighbours;
	private int xCoOrdinate;
	private int yCoOrdinate;
	
	/**
	 * Create Country object with default values.
	 */
	Country(){}
	
	/**
	 * Create country object with values in argument parameters and set defaults for the rest.
	 * @param countryName Name of the country
	 * @param inContinent Name of the continent in which this country belongs
	 */
	Country(String countryName, String inContinent){
		this.countryName = countryName;
		this.inContinent = inContinent;
		this.neighbours = new HashMap<String, Country>();
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
	 * Setter method to fetch x-co-ordinate
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
	 * Setter method to fetch y-co-ordinate
	 * @param xCoOrdinate argument y-co-ordinate value to be set
	 */
	public void setyCoOrdinate(int yCoOrdinate) {
		this.yCoOrdinate = yCoOrdinate;
	}
	
	public void printCountry(){
		System.out.println("index: " + index);
		System.out.println("countryName: " + countryName);
		System.out.println("inContinent: " + inContinent);
		//System.out.println("index: " + index);
		//System.out.println("index: " + index);
	}
	
	
}
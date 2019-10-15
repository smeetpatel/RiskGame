package main.java;
import java.util.*;

/**
 * This class assign player attributes and maintain HashMaps for countries
 * and continents owned by them.
 * @author Jasmine
 *
 */
public class Player {
	private String playerName;
	private int ownedArmies;
	private HashMap<String, Country> ownedCountries;
	private HashMap<String, Continent> ownedContinents;
	/**
	 * This constructor will assign name to the players.
	 *
	 * @param playerName name of player
	 */
	 public Player(String playerName){
		this.playerName = playerName;
		this.ownedArmies = 0;
		this.ownedCountries = new HashMap<String, Country>();
		this.ownedContinents = new HashMap<String, Continent>();
	}
	/**
	 * Getter method to return player name entered by user
	 * @return playerName
	 */
	public String getPlayerName() {
		return this.playerName;
	}
	/**
	 * Method to set player name
	 * @param playerName Name of the player
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	/**
	 * This function gets the number of initial armies
	 * @return ownedArmies
	 */
	public int getOwnedArmies() {
		return ownedArmies;
	}
	/**
	 * This function sets the number of initial armies
	 * @param ownedArmies number of armies owned by players
	 */
	public void setOwnedArmies(int ownedArmies) {
		this.ownedArmies = ownedArmies;
	}
	/**
	 * This method returns the countries owned by players
	 * @return ownedCountries
	 */
	public HashMap<String, Country> getOwnedCountries(){
		return this.ownedCountries;
	}
	/**
	 * This method sets the countries owned by players as a Hash map
	 * @param ownedCountries number of countries owned by players
	 */
	public void setOwnedCountries(HashMap<String, Country> ownedCountries){
		this.ownedCountries = ownedCountries;
	}
	/**
	 * This method returns the continents owned by players
	 * @return ownedContinents
	 */
	public HashMap<String, Continent> getOwnedContinents(){
		return this.ownedContinents;
	}
	/**
	 * This method sets the continents owned by players as a Hash map
	 * @param ownedContinents number of continents owned by players
	 */
	public void setOwnedContinents(HashMap<String, Continent> ownedContinents) {
		this.ownedContinents = ownedContinents;
	}
}


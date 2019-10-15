package main.java;
import java.util.*;
import java.io.*;

/**
 * This class assign player attributes.
 *
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

	public String getPlayerName() {
		return this.playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getOwnedArmies() {
		return ownedArmies;
	}

	public void setOwnedArmies(int ownedArmies) {
		this.ownedArmies = ownedArmies;
	}

	public HashMap<String, Country> getOwnedCountries(){
		return this.ownedCountries;
	}

	public void setOwnedCountries(HashMap<String, Country> ownedCountries){
		this.ownedCountries = ownedCountries;
	}

	public HashMap<String, Continent> getOwnedContinents(){
		return this.ownedContinents;
	}

	public void setOwnedContinents(HashMap<String, Continent> ownedContinents) {
		this.ownedContinents = ownedContinents;
	}
}

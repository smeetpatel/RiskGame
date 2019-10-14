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
	 private int playerNum;
   // public Player current_player;

    public List<Country> countries_assigned;
	public List<Player> player_list;
    
    public int initial_armies;
	public int reinforce_armies;
    

    /**
     * This constructor will assign name to the players.
     *
     */
    public Player(){
    	countries_assigned = new ArrayList<Country>();
    }
	/**
	 * method to return name of player.
	 * 
	 * @return the playerName
	 */
	public String getPlayerName() {
		this.playerName = playerName;
		return playerName;
	}
	/**
	 * method to assign name to player.
	 * 
	 * @param playerName the name to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	/**
	 * method to remove a player from Player_List.
	 * 
	 * @param playerName 
	 */
	public void removePlayer(String playerName) {
	if(player_list.contains(playerName)){
		player_list.remove(playerName);}
		else
	System.out.println("Player name" + playerName + "does not exist"+"!");
	}
	
	
    /**
     * method to identify Player turn number
     * 
	 * @return the playerNum
	 */
	public int getPlayerNum() {
		return playerNum;
	}
	/**
	 * setter method to assign turn value to player.
	 * 
	 * @param playerNum the player number to set
	 */
	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}
	
	
	
	/**
	 * getter method for player list.
	 * 
	 * @return the player_list
	 */
	public List<Player> getPlayerList() {
		return player_list;
	}
	/**
	 * setter method to assign player list.
	 * 
	 * @param player_list the playerList to set
	 */
	public void setPlayerList(List<Player> player_list) {
		this.player_list = player_list;
	}
	
	/**
	 * getter method for countries.
	 * 
	 * @return the countries_assigned
	 */
	public List<Country> getAssignedCountries() {
		return countries_assigned;
	}
	/**
	 * setter method to assign countries.
	 * 
	 * @param countries_assigned the assignedCountries to set
	 */
	public void setAssignedCountries(List<Country> countries_assigned) {
		this.countries_assigned = countries_assigned;
	}
	/**
	 * Add country to the countries_assigned list
	 *
	 * @param country
	 */
	public void addCountry(Country country) {
		this.countries_assigned.add(country);
	}
    /**
     * getter method for initial armies.
     * 
	 * @return the intial_armies
	 */
	public int getInitialArmies() {
		return initial_armies;
	}
	/**
	 * setter method to assign initial armies.
	 * 
	 * @param intial_armies the intial_armies to set
	 */
	public void setInitialArmies(int intial_armies) {
		this.initial_armies = intial_armies;
	}
	/**
	 * getter method for reinforce armies.
	 * 
	 * @return the reinforce_armies
	 */
	public int getReinforceArmies() {
		return reinforce_armies;
	}
	/**
	 * setter method to assign reinforce armies.
	 * 
	 * @param reinforce_armies the reinforce_armies to set
	 */
	public void setReinforceArmies(int reinforce_armies) {
		this.reinforce_armies = reinforce_armies;
	}
	/**
	 * this method will discard reinforce armies.
	 * 
	 * @param armies_selected subtract this amount
	 */
	public void subReinforceArmies(int armies_selected) {
		reinforce_armies-=armies_selected;
	} 

 }
 
package main.java;
import java.util.*;

/**
 * This class assign player attributes and maintain HashMaps for countries
 * and continents owned by them.
 * @author Jasmine
 *
 */
public class Player {
	/**
	 * Name of the player
	 */
	private String playerName;

	/**
	 * Number of armies owned by this player.
	 */
	private int ownedArmies;

	/**
	 * Stores countries owned by the player.
	 * HashMap has
	 * 1) Key: name of the country in lower case
	 * 2) Value: corresponding Country object
	 */
	private HashMap<String, Country> ownedCountries;

	/**
	 * Stores continents owned by the player.
	 * HashMap has
	 * 1) Key: name of the continent in lower case
	 * 2) Value: corresponding Continent object
	 */
	private HashMap<String, Continent> ownedContinents;

	/**
	 * Stores cards owned by the player.
	 */
	private ArrayList<Card> ownedCards;

	/**
	 * Creates a player with the argument player name and sets default value for rest of the player fields.
	 * @param playerName name of player
	 */
	 public Player(String playerName){
		this.playerName = playerName;
		this.ownedArmies = 0;
		this.ownedCountries = new HashMap<String, Country>();
		this.ownedContinents = new HashMap<String, Continent>();
		this.ownedCards = new ArrayList<Card>();
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

	/**
	 * This function allow player to place armies
	 * @param game Represents the state of the game
	 * @param countryName Reinforce armies here
	 * @param num Reinforce this many armies
	 * @return true if successful, else false
	 */
	public boolean reinforce(GameData game, String countryName, int num)
	{
		if(this.ownedCountries.containsKey(countryName.toLowerCase()))
		{
			if(this.ownedArmies >= num)
			{
				Country c= this.ownedCountries.get(countryName.toLowerCase());
				int existingArmies = c.getNumberOfArmies();
				existingArmies += num;
				c.setNumberOfArmies(existingArmies);
				this.setOwnedArmies(this.ownedArmies-num);
				if(this.ownedArmies==0) {
					game.setGamePhase(Phase.ATTACK);
				}
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	//TODO: Add attack method
	//TODO: Add fortification method
	public FortificationCheck fortify(GameData game, String fromCountry, String toCountry, int num)
	{
		MapValidation mv = new MapValidation();
		if(this.ownedCountries.containsKey(fromCountry.toLowerCase()))
		{
			if(this.ownedCountries.containsKey(toCountry.toLowerCase()))
			{
				if((this.ownedCountries.get(fromCountry.toLowerCase()).getNumberOfArmies()- num)>=1)
				{
					if(mv.fortificationConnectivityCheck(this, fromCountry, toCountry))
					{
						int fromArmies = this.ownedCountries.get(fromCountry.toLowerCase()).getNumberOfArmies();
						fromArmies -= num;
						this.ownedCountries.get(fromCountry.toLowerCase()).setNumberOfArmies(fromArmies);
						int toArmies = this.ownedCountries.get(toCountry.toLowerCase()).getNumberOfArmies();
						toArmies += num;
						this.ownedCountries.get(toCountry.toLowerCase()).setNumberOfArmies(toArmies);
						game.setGamePhase(Phase.TURNEND);
						return FortificationCheck.FORTIFICATIONSUCCESS;
					} else {
						return FortificationCheck.PATHFAIL;
					}
				} else {
					return FortificationCheck.ARMYCOUNTFAIL;
				}
			} else {
				return FortificationCheck.TOCOUNTRYFAIL;
			}
		}
		else
		{
			return FortificationCheck.FROMCOUNTRYFAIL;
		}
	}

	/**
	 * Used to mark end of a player's turn when player decides to not fortify.
	 * @param game Represents current state of the game.
	 */
	public void fortify(GameData game){
		game.setGamePhase((Phase.TURNEND));
	}

	/**
	 * This method returns the cards owned by player.
	 * @return ownedCards number of cards owned by player
	 */
	public ArrayList<Card> getOwnedCards(){
		return this.ownedCards;
	}

	/**
	 * This method adds the card earned by the player.
	 * @param card object of Card
	 */
	public void setOwnedCards(Card card){
		this.ownedCards.add(card);
	}

	/**
	 * This method removes the card from owned cards after trade in process.
	 * @param card object of Card
	 */
	public void removeOwnedCards(Card card){ this.ownedCards.remove(card); }
}


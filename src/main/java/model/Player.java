package main.java.model;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class assign player attributes and maintain HashMaps for countries
 * and continents owned by them.
 * @author Jasmine
 *
 */
public class Player extends Observable{
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
	 * Represents the percentage of map controlled.
	 */
	private double mapControlled;
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
		this.mapControlled = 0.0f;
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
		notifyObservers(this);
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
		//TODO: Notify observers of the cards removed
		notifyObservers(this);
	}

	/**
	 * This method removes the card from owned cards after trade in process.
	 * @param card object of Card
	 */
	public void removeOwnedCards(Card card){
		this.ownedCards.remove(card);
		//TODO: Notify observers of the cards added
		notifyObservers(this);
	}

	/**
	 * Returns the percentage of map controlled by the player.
	 * @return returns the percentage of map controlled by the player.
	 */
	public double getMapControlled(){
		return this.mapControlled;
	}

	/**
	 * Sets the percentage of the map controlled by the player.
	 * @param mapControlled current percentage of the map controlled by the player.
	 */
	public void setMapControlled(double mapControlled){
		this.mapControlled = mapControlled;
		notifyObservers(this);
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
		game.setActivePlayer(this);
		if(this.ownedCountries.containsKey(countryName.toLowerCase()))
		{
			if(this.ownedArmies >= num)
			{
				Country c= this.ownedCountries.get(countryName.toLowerCase());
				int existingArmies = c.getNumberOfArmies();
				existingArmies += num;
				c.setNumberOfArmies(existingArmies);
				this.setOwnedArmies(this.ownedArmies-num);
				notifyObservers(Integer.toString(num) + " armies reinforced at " + countryName +". Remaining reinforcement armies: " + Integer.toString(this.ownedArmies) + "\n");
				if(this.ownedArmies==0) {
					game.setGamePhase(Phase.ATTACK);
				}
				return true;
			}
			else
			{
				notifyObservers(this.playerName + " doesn't have " + num + " armies to reinforce. Invalid command.");
				return false;
			}
		}
		else
		{
			notifyObservers(countryName + " not owned by " + this.playerName +". Invalid command.\n");
			return false;
		}
	}

	/**
	 * Attacks the argument country.
	 * @param game Represents the state of the game.
	 * @param countryFrom Country doing the attack
	 * @param countryTo Country that is defending
	 * @param numberOfDice Number of dice attacker wishes to roll
	 * @param defendDice Number of dice defender wishes to roll
	 * @param defendingPlayer Player owning the defending country
	 * @return true if successful in conquering, else false.
	 */
	public boolean attack(GameData game, String countryFrom, String countryTo, int numberOfDice, int defendDice, Player defendingPlayer){
		Country attackingCountry = game.getMap().getCountries().get(countryFrom.toLowerCase());
		Country defendingCountry = game.getMap().getCountries().get(countryTo.toLowerCase());
		int[] attackerDiceRolls = new int[numberOfDice];
		int[] defenderDiceRolls = new int[defendDice];
		int attackerArmiesLost = 0;
		int defenderArmiesLost = 0;
		//roll the dices
		for(int i = 0; i<numberOfDice; i++){
			int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
			attackerDiceRolls[i] = randomNum;
		}
		for(int i = 0; i<defendDice; i++){
			int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
			defenderDiceRolls[i] = randomNum;
		}

		//sort the dice roll result
		Arrays.sort(attackerDiceRolls);
		Arrays.sort(defenderDiceRolls);

		//compare dice results
		for(int i=1; i<=defendDice; i++){
			if(defenderDiceRolls[defenderDiceRolls.length-i]>=attackerDiceRolls[attackerDiceRolls.length-i]){
				attackingCountry.setNumberOfArmies(attackingCountry.getNumberOfArmies()-1);
				attackerArmiesLost++;
			} else {
				defendingCountry.setNumberOfArmies(defendingCountry.getNumberOfArmies()-1);
				defenderArmiesLost++;
			}
			if(defendingCountry.getNumberOfArmies()==0){
				if(attackerArmiesLost>0){
					notifyObservers(this.playerName + " lost " + attackerArmiesLost + " army at " + countryFrom + ".\n");
				}
				if(defenderArmiesLost>0){
					notifyObservers(defendingPlayer.getPlayerName() + " lost " + defenderArmiesLost + " army at " + countryTo + ".\n");
				}
				this.ownedCountries.put(countryTo.toLowerCase(), defendingCountry);
				defendingPlayer.getOwnedCountries().remove(countryTo.toLowerCase());
				notifyObservers(this.playerName + " conquered " + countryTo + ".\n");
				if(defendingPlayer.getOwnedCountries().size()==0){
					notifyObservers(defendingPlayer.getPlayerName() + " lost his/her last country. Hence, out of the game. " + this.playerName + " gets all his/her cards.");
				}
				return true;
			}
			if(i>=numberOfDice){
				break;
			}
		}
		if(attackerArmiesLost>0){
			notifyObservers(this.playerName + " lost " + attackerArmiesLost + " army at " + countryFrom + ".\n");
		}
		if(defenderArmiesLost>0){
			notifyObservers(defendingPlayer.getPlayerName() + " lost " + defenderArmiesLost + " army at " + countryTo + ".\n");
		}
		return false;
	}

	/**
	 * check whether the attack is possible or not
	 * @return true if attack is possible otherwise false
	 */
	public boolean isAttackPossible() {
		boolean attackPossible = false;
		for(Country country : this.ownedCountries.values()) {
			for(Country neighbor : country.getNeighbours().values()){
				if(country.getNumberOfArmies()>1 && !(this.ownedCountries.containsKey(neighbor.getCountryName().toLowerCase()))){
					attackPossible = true;
				}
			}
		}
		return attackPossible;
	}

	/**
	 * Method to move army to the conquered territory.
	 * @param game Represents the state of the game.
	 * @param fromCountry Attacking country
	 * @param toCountry Conquered country
	 * @param numberOfDice Number of dice rolled when conquering the territory
	 * @param numberOfArmies Number of armies to transfer to newly conquered territory.
	 * @return true if successful, else false.
	 */
	public boolean moveArmy(GameData game, String fromCountry, String toCountry, int numberOfDice, int numberOfArmies) {
		Country attackingCountry = game.getMap().getCountries().get(fromCountry.toLowerCase());
		Country defendingCountry = game.getMap().getCountries().get(toCountry.toLowerCase());
		if(numberOfArmies<numberOfDice){
			return false;
		}
		if(numberOfArmies>=attackingCountry.getNumberOfArmies()){
			return false;
		}
		attackingCountry.setNumberOfArmies(attackingCountry.getNumberOfArmies()-numberOfArmies);
		defendingCountry.setNumberOfArmies(defendingCountry.getNumberOfArmies()+numberOfArmies);
		notifyObservers(this.playerName + " moved " + numberOfArmies + " armies from " + fromCountry + " to " + toCountry + ".");
		return true;
	}

	/**
	 * perform the fortification operation of the game
	 * @param game represents state of the game
	 * @param fromCountry country from armies send
	 * @param toCountry country to armies placed
	 * @param num total number of armies to send from one country to another country
	 * @return status of the fortification phase
	 */
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
						notifyObservers(this.playerName + " fortified " + toCountry + " with " + num + " armies from " + fromCountry +". " + this.playerName + "'s turn ends now.");
						game.setGamePhase(Phase.TURNEND);
						return FortificationCheck.FORTIFICATIONSUCCESS;
					} else {
						notifyObservers("No path from " + fromCountry + " to " + toCountry + ". Fortification failed.");
						return FortificationCheck.PATHFAIL;
					}
				} else {
					notifyObservers("Not enough armies in " + fromCountry + " to fortify " + toCountry + " with " + num + " armies. Fortification failed.");
					return FortificationCheck.ARMYCOUNTFAIL;
				}
			} else {
				notifyObservers(this.playerName + " does not own " + toCountry + ". Fortification failed.");
				return FortificationCheck.TOCOUNTRYFAIL;
			}
		}
		else
		{
			notifyObservers(this.playerName + " does not own  " + fromCountry + ". Fortification failed.");
			return FortificationCheck.FROMCOUNTRYFAIL;
		}
	}

	/**
	 * Used to mark end of a player's turn when player decides to not fortify.
	 * @param game Represents current state of the game.
	 */
	public void fortify(GameData game){
		notifyObservers(this.playerName + " decided to not fortify any country. " + this.playerName + "'s turn ends now.");
		game.setGamePhase((Phase.TURNEND));
	}

	/**
	 * Exchanges cards.
	 */
	public boolean cardExchange(GameData game, ArrayList<Integer> cardIndex) {
		int numberOfArmies = 0;
		for(int i : cardIndex){
			if(i<=0 || i>this.ownedCards.size()){
				return false;
			}
		}
		if (cardIndex.get(0) != cardIndex.get(1) && cardIndex.get(1) != cardIndex.get(2) && cardIndex.get(2) != cardIndex.get(0)){
			if ((this.getOwnedCards().get(cardIndex.get(0) - 1).getCardType().equals(this.getOwnedCards().get(cardIndex.get(1) - 1).getCardType()) &&
					this.getOwnedCards().get(cardIndex.get(0) - 1).getCardType().equals(this.getOwnedCards().get(cardIndex.get(2) - 1).getCardType())) ||
					(!this.getOwnedCards().get(cardIndex.get(0) - 1).getCardType().equals(this.getOwnedCards().get(cardIndex.get(1) - 1).getCardType()) &&
					!this.getOwnedCards().get(cardIndex.get(0) - 1).getCardType().equals(this.getOwnedCards().get(cardIndex.get(2) - 1).getCardType()) &&
					!this.getOwnedCards().get(cardIndex.get(1) - 1).getCardType().equals(this.getOwnedCards().get(cardIndex.get(2) - 1).getCardType())) ||
					((this.getOwnedCards().get(cardIndex.get(0) - 1).getCardType().equals(this.getOwnedCards().get(cardIndex.get(1) - 1).getCardType()) && this.getOwnedCards().get(cardIndex.get(2) - 1).getCardType().equals("WildCard")) ||
							(this.getOwnedCards().get(cardIndex.get(1) - 1).getCardType().equals(this.getOwnedCards().get(cardIndex.get(2) - 1).getCardType()) && this.getOwnedCards().get(cardIndex.get(0) - 1).getCardType().equals("WildCard"))	||
							(this.getOwnedCards().get(cardIndex.get(0) - 1).getCardType().equals(this.getOwnedCards().get(cardIndex.get(2) - 1).getCardType()) && this.getOwnedCards().get(cardIndex.get(1) - 1).getCardType().equals("WildCard")))) {

				numberOfArmies = checkForOwnedCardCountry(cardIndex, numberOfArmies);
				game.setCardsDealt(game.getCardsDealt()+1);
				numberOfArmies += (game.getCardsDealt()*5);
				this.setOwnedArmies(this.getOwnedArmies() + numberOfArmies);
				for (int i = 0; i < cardIndex.size(); i++) {
					game.getDeck().addCard(this.getOwnedCards().get(cardIndex.get(i) - 1));
				}
				for (int i = cardIndex.size() - 1; i >= 0; i--) {
					this.removeOwnedCards(this.getOwnedCards().get(cardIndex.get(i) - 1));
				}
				if(this.ownedCards.size()<3){
					game.setGamePhase(Phase.REINFORCEMENT);
				}
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * in card exchange, checks that player own the country which indicated on the card
	 * @param cardIndex indexes of the selected cards
	 * @param numberOfArmies number of armies player will get if match found
	 * @return number of armies after check of match for country
	 */
	public int checkForOwnedCardCountry(ArrayList<Integer> cardIndex, int numberOfArmies){
		boolean countryFound = false;
		for (int i = 0; i < 3; i++) {
			if (!countryFound) {
				Country country = this.getOwnedCards().get(cardIndex.get(i) - 1).getCardCountry();
				if(country!=null){
					if(this.ownedCountries.containsKey(country.getCountryName().toLowerCase())){
						return numberOfArmies+2;
					}
				}
			}
		}
		return numberOfArmies;
	}
}
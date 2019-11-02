package main.java;

import java.util.*;
/**
 * 
 * Manage's task related to start up phase of the game.
 *
 */
public class StartUp {

	/**
	 * Adds player to the game.
	 * Restricts number of players to 6.
	 * @param players List of players in the game
	 * @param playerName Name of the player
	 * @return true if successful in adding the player, else false
	 */
	public boolean addPlayer(ArrayList<Player> players, String playerName){
		if(players.size()==6) {
			return false;
		}
		Iterator<Player> itr = players.listIterator();
		while(itr.hasNext()){
			if(itr.next().getPlayerName().equalsIgnoreCase(playerName)) {
				return false;
			}
		}
		players.add(new Player(playerName));
		return true;
	}
	
	/**
	 * Removes player from the game.
	 * @param players List of players in the game
	 * @param playerName Name of the player
	 * @return true if successful in removing the player, else false
	 */
	public boolean removePlayer(ArrayList<Player> players, String playerName){
		Iterator<Player> itr = players.listIterator();
		while(itr.hasNext()) {
			Player p = itr.next();
			if(p.getPlayerName().equals(playerName)) {
				players.remove(p);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Responsible for distributing countries amongst players and assigning initial armies.
	 * @param map Game map 
	 * @param players List of players in the game
	 * @return true if successful, else false
	 */
	public boolean populateCountries(GameData game, ArrayList<Player> players) {
		int numberOfPlayers = players.size();
		if(players.size()<2) {
			return false;
		}
		int counter = 0;
		for(Country c : game.getMap().getCountries().values()) {
			Player p = players.get(counter);
			p.getOwnedCountries().put(c.getCountryName().toLowerCase(), c);
			if(counter>=numberOfPlayers-1)
				counter = 0;
			else
				counter++;
		}
		assignInitialArmies(players);
		game.setGamePhase(Phase.ARMYALLOCATION);
		return true;
	}
	
	/**
	 * Assigns initial armies to each player depending on the number of players.
	 * @param players List of players in the game
	 */
	public void assignInitialArmies(ArrayList<Player> players) {
		int numberOfPlayers = players.size();
		int numberOfArmies = 0;
		if(numberOfPlayers==2) {
			numberOfArmies = 40;
		}
		else if(numberOfPlayers==3) {
			numberOfArmies = 35;
		}
		else if(numberOfPlayers==4) {
			numberOfArmies = 30;
		}
		else if(numberOfPlayers==5) {
			numberOfArmies = 25;
		}
		else if(numberOfPlayers==6) {
			numberOfArmies = 20;
		}
		Iterator<Player> itr = players.listIterator();
		while(itr.hasNext()) {
			Player p = itr.next();
			p.setOwnedArmies(numberOfArmies);
		}
	}
	
	/**
	 * Place army at the argument country if it is in compliance with game rules.
	 * @param player Player assigning the army
	 * @param countryName Country where army is to be placed
	 * @return true if successful, else false
	 */
	public boolean placeArmy(Player player, String countryName) {
		if(player.getOwnedArmies()>0) {
			if(player.getOwnedCountries().containsKey(countryName.toLowerCase())) {
				int existingArmy = player.getOwnedCountries().get(countryName.toLowerCase()).getNumberOfArmies();
				existingArmy++;
				player.getOwnedCountries().get(countryName.toLowerCase()).setNumberOfArmies(existingArmy);
				player.setOwnedArmies(player.getOwnedArmies()-1);
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
		return true;
	}
	
	/**
	 * Place army of all players randomly.
	 * @param players List of players in the game
	 * @return true if successful, else false
	 */
	public boolean placeAll(GameData game) {
		Iterator<Player> itr = game.getPlayers().listIterator();
		while(itr.hasNext()) {
			Player p = itr.next();
			while(p.getOwnedArmies()>0) {
				for(Country country : p.getOwnedCountries().values()) {
					int newArmy = country.getNumberOfArmies();
					newArmy += 1;
					country.setNumberOfArmies(newArmy);
					p.setOwnedArmies(p.getOwnedArmies()-1);
					if(p.getOwnedArmies()<=0)
						break;
				}
			}
		}
		game.setGamePhase(Phase.REINFORCEMENT);
		return true;
	}
	
	/**
	 * Checks if all armies of every player is allocated or not.
	 * @param players List of players in the game
	 * @return true if successful, else false
	 */
	public void isAllArmyPlaced(GameData game) {
		Iterator<Player> itr = game.getPlayers().listIterator();
		while(itr.hasNext()) {
			Player p = itr.next();
			if(p.getOwnedArmies()>0){
				return;
			}
		}
		game.setGamePhase(Phase.REINFORCEMENT);
	}
	
	/**
	 * Responsible for managing distribution of initial armies.
	 * @param players List of players in the game
	 * @param cmd Command object that maintains game state
	 * @param gamePhase Current game phase
	 */
	public void armyDistribution(GameData game, Command cmd){
		Scanner sc = new Scanner(System.in);
		int numberOfPlayers = game.getPlayers().size();
		int playerTraversal = 0;
		while(game.getGamePhase()!=Phase.CARDEXCHANGE) {
			while(game.getGamePhase()!=Phase.CARDEXCHANGE) {
				Player p = game.getPlayers().get(playerTraversal);
				int originalArmies = p.getOwnedArmies();
				System.out.println(p.getPlayerName() + "'s turn");
				String command = sc.nextLine();
				game.setGamePhase(cmd.parseCommand(p, command));
				if(!command.contentEquals("showmap") && originalArmies>p.getOwnedArmies()) {
					playerTraversal++;
					if(playerTraversal>=numberOfPlayers) {
						playerTraversal = 0;
					}
				}
			}
		}
		game.setGamePhase(Phase.CARDEXCHANGE);
	}
}
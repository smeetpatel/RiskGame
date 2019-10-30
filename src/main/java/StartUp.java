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
			System.out.println("Can not add any more player. Maximum 6 players are allowed.");
			return false;
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
	public boolean populateCountries(GameMap map, ArrayList<Player> players) {
		int numberOfPlayers = players.size();
		if(players.size()<2) {
			System.out.println("Minimum two players are required to play the game.");
			return false;
		}
		int counter = 0;
		for(Country c : map.getCountries().values()) {
			Player p = players.get(counter);
			p.getOwnedCountries().put(c.getCountryName().toLowerCase(), c);
			if(counter>=numberOfPlayers-1)
				counter = 0;
			else
				counter++;
		}
		assignInitialArmies(players);
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
				System.out.println("You don't own this country. Please allocate army in your country.");
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
	public boolean placeAll(ArrayList<Player> players) {
		Iterator<Player> itr = players.listIterator();
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
		return true;
	}
	
	/**
	 * Checks if all armies of every player is allocated or not.
	 * @param players List of players in the game
	 * @return true if successful, else false
	 */
	public boolean isAllArmyPlaced(ArrayList<Player> players) {
		Iterator<Player> itr = players.listIterator();
		while(itr.hasNext()) {
			Player p = itr.next();
			if(p.getOwnedArmies()>0)
				return false;
		}
		return true;
	}
	
	/**
	 * Responsible for managing distribution of initial armies.
	 * @param players List of players in the game
	 * @param cmd Command object that maintains game state
	 * @param gamePhase Current game phase
	 */
	public void armyDistribution(ArrayList<Player> players, Command cmd, Command.Phase gamePhase){
		Scanner sc = new Scanner(System.in);
		int numberOfPlayers = players.size();
		int playerTraversal = 0;
		while(gamePhase!=Command.Phase.REINFORCEMENT) {
			while(gamePhase!=Command.Phase.REINFORCEMENT) {
				Player p = players.get(playerTraversal);
				int originalArmies = p.getOwnedArmies();
				System.out.println(p.getPlayerName() + "'s turn");
				String command = sc.nextLine();
				gamePhase = cmd.parseCommand(p, command); 
				if(!command.contentEquals("showmap") && originalArmies>p.getOwnedArmies()) {
					playerTraversal++;
					if(playerTraversal>=numberOfPlayers) {
						playerTraversal = 0;
					}
				}
			}
		}
	}
	
	/**
	 * Shows map in tabular form.
	 * @param players List of players in the game
	 * @param map Game map
	 */
	public void showMap(ArrayList<Player> players, GameMap map) {
		if(map==null)
			return;
		if(players.size()==0 || players.get(0).getOwnedCountries().size()==0) {
			MapEditor rc = new MapEditor();
			rc.showMap(map);
			return;
		}
		System.out.format("%25s%25s%35s%25s%10s\n", "Owner", "Country", "Neighbors", "Continent", "#Armies");
		System.out.format("%85s\n", "---------------------------------------------------------------------------------------------------------------------------");
		boolean printPlayerName = true;
		boolean printContinentName = true;
		boolean printCountryName = true;
		boolean printNumberOfArmies = true;
		
		for(int i=0; i<players.size(); i++){
			Player p = players.get(i);
			for(Country country : p.getOwnedCountries().values()) {
				for(Country neighbor : country.getNeighbours().values()) {
					if(printPlayerName && printContinentName && printCountryName) {
						System.out.format("\n%25s%25s%35s%25s%10d\n", p.getPlayerName(), country.getCountryName(), neighbor.getCountryName(), country.getInContinent(), country.getNumberOfArmies());
						printPlayerName = false;
						printContinentName = false;
						printCountryName = false;
						printNumberOfArmies = false;
					}
					else if(printContinentName && printCountryName && printNumberOfArmies) {
						System.out.format("\n%25s%25s%35s%25s%10d\n", "", country.getCountryName(), neighbor.getCountryName(), country.getInContinent(), country.getNumberOfArmies());
						printContinentName = false;
						printCountryName = false;
						printNumberOfArmies = false;
					}
					else {
						System.out.format("\n%25s%25s%35s%25s%10s\n", "", "", neighbor.getCountryName(), "", "");
					}
				}
				printContinentName = true;
				printCountryName = true;
				printNumberOfArmies = true;
			}
			printPlayerName = true;
			printContinentName = true;
			printCountryName = true;
			printNumberOfArmies = true;
		}
	}
}
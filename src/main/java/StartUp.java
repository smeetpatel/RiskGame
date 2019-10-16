package main.java;

import java.util.*;

public class StartUp {

	public boolean addPlayer(ArrayList<Player> players, String playerName){
		if(players.size()==6)
			return false;
		players.add(new Player(playerName));
		return true;
	}
	
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
	
	public boolean populateCountries(GameMap map, ArrayList<Player> players) {
		int numberOfPlayers = players.size();
		if(players.size()==1) {
			System.out.println("Add at least one more player");
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
		return true;
	}
	
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
	
	public void placeAll(ArrayList<Player> players) {
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
		//return true;
	}
	
	public boolean isAllArmyPlaced(ArrayList<Player> players) {
		Iterator<Player> itr = players.listIterator();
		while(itr.hasNext()) {
			Player p = itr.next();
			if(p.getOwnedArmies()>0)
				return false;
		}
		return true;
	}
	
	public void armyDistribution(ArrayList<Player> players, Command.Phase gamePhase) {
		Scanner sc = new Scanner(System.in);
		while(gamePhase!=Command.Phase.REINFORCEMENT) {
			String command = sc.nextLine();
			Command cmd = new Command();
			gamePhase = cmd.parseCommand(null, command);
		}
		sc.close();
	}
}
package main.java;
public class StartUp {

	public boolean addPlayer(ArrayList<Player> players, String playerName){
		if(players.size()==6)
			return false;
		players.add(new Player(playerName));
		return players;
	}
	
	public boolean removePlayer(ArrayList<Player> players, String playerName){
		Iterator<PLayer> itr = players.listIterator();
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
			p.getOwnedCountries.put(c.getCountryName().toLowerCase(), c);
			if(counter>=numberOfPlayers-1)
				counter = 0;
			else
				counter++;
		}
		return false;
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
		else {
			numberOfArmies = 15;
		}		
	}
	
	public boolean placeArmy(Player player, String countryName) {
		
	}
	
	public boolean placeAll(ArrayList<Player> players, Player callingPlayer) {
		
	}
}

package main.java;
import java.util.*;
import java.io.*;

import main.java.LoadMap;
import main.java.GameMap;
import main.java.Player;
import main.java.Country;
import main.java.Continent;



public class GamePlay {
	
	static Player current_player;
	static int playerCount;
	static StringBuilder stb = new StringBuilder();
    private static final int min_players = 2;
	private static final int max_players = 6;
	public List<Player> player_list;
	
	/**
	 * method to distribute countries among players.
	 * 
	 * @param player_list list of players
	 * @param countries list of countries
	 * @return string value
	 */
	public static String assignCountries(List<Player> player_list,HashMap<String, Country> countries){
		stb.append("-----USER STARTS PUTTING ARMIES-----\n\n");

		playerCount = player_list.size();
		// Collections.shuffle(countries);
		StringBuilder sb = new StringBuilder();
		
		int playerNum = 0;
		for(Player p:player_list){
			playerNum++;
			p.setPlayerNum(playerNum);
		}
		while(countries.size() > 0){
			for(Player p:player_list){
				List<Country> new_list = p.getAssignedCountries();
				if(countries.size()>0){					
					Country cn = countries.get(0);
					sb.append(p.getPlayerName()+" gets "+cn.getCountryName()+".\n");
					stb.append(p.getPlayerName()+" placed 1 army on "+cn.getCountryName()+".\n");
					new_list.add(cn);
					//countries.remove(0);
					p.setAssignedCountries(new_list);
				}
				else
					break;
			}
		}
		sb.append("-----COUNTRIES ASSIGNED-----\n\n");
		return sb.toString();

	}
	
		/**
	 * this method will allocate initial armies according to player numbers.
	 * 
	 * @param player_list maintained list of players 
	 */
	public static void setInitialArmies(List<Player> player_list){
		int armies=0;
		int num_of_players = player_list.size();
		
		if(num_of_players==2)
			armies=40;
		else if(num_of_players==3)
			armies=35;
		else if(num_of_players==4)
			armies=30;
		else if(num_of_players==5)
			armies=25;
		else if(num_of_players==6)
			armies=20;
		
		for(Player p:player_list){
			p.setInitialArmies(armies);
			
		}
		
		for(Player p:player_list){
			int deployed_armies=armies-p.getAssignedCountries().size();
			p.setInitialArmies(deployed_armies);			
		}
	}
	
	//////////////////////////////////////////////////////////////////
		public GamePlay( int playerNum, BufferedReader reader) throws IOException {
		player_list = new ArrayList<Player>();
		Scanner input = new Scanner(System.in);
    	System.out.println("Enter the Number of the Players");
    	playerNum = input.nextInt();
    	Player p = new Player();

		p.setPlayerNum(playerNum);
		System.out.println("Enter the Name of the Player(s)");
		for (int i = 0; i < playerNum; i++) {

			String playerName = null;
			
			if (reader!= null && (playerName = reader.readLine()) != null) {
				p.setPlayerName(playerName);
			}
			player_list.add(p);

		}
	}
	/**
	 * Method to distribute countries among players.
	 */
	public void assignCountries() {

		int integer = 0;
		for (String key : Continent.getCountries().keySet()) {
			integer = integer % player_list.size();
			Player player = player_list.get(integer);
			player.addCountry(Continent.getCountries().get(key));
			integer++;
		}
	}
	//////////////////////////////////////////////////////////////////
	
	/**
	 * this method will get current player.
	 * 
	 * @param player_list list of players
	 * @param playerNum turn number of player
	 * @return current player
	 */
	public Player getCurrentPlayer(List<Player> player_list,int playerNum){
		for(Player p:player_list){
			if(playerNum == p.getPlayerNum()){
				current_player = p;
				break;
			}
		}
		return current_player;
	}
	

	
    /**
     * This method will place initial armies on the countries in a round robin fashion.
     * 
     * @param player_list list of players
     * @return string value
     */
    public String placeArmies(List<Player> player_list) {
        int a = 0;
        int lastPlayer = player_list.size();
        while (lastPlayer > 0) {
        	
            if (player_list.get(a % playerCount).getInitialArmies() > 0) {
            	
                List<Country> playerCountryList = player_list.get(a % playerCount).getAssignedCountries();
                Country anyCountry = playerCountryList.get(new Random().nextInt(playerCountryList.size()));
                
       // implement later         anyCountry.addArmy(1);
                player_list.get(a % playerCount).setInitialArmies(player_list.get(a % playerCount).getInitialArmies()- 1);
                stb.append(player_list.get(a % playerCount).getPlayerName() + " put 1 army on "+ anyCountry.getCountryName()+".\n");
            } else {
                lastPlayer--;
            }
            a++;
        }
        stb.append("\n\n==Allocating armies as well as country is done===");
        return stb.toString();
    }


}
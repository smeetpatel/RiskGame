package main.java;

import java.io.File;
import java.util.Scanner;

/**
 * Responsible for playing the game.
 * Covers tasks ranging from 'map editing' to 'actual game play'.
 * Responsible for only interacting with the user and passing the user command for appropriate action.
 */
public class PlayRisk {

	/**
	 * Responsible for playing the game.
	 * Covers tasks ranging from 'map editing' to 'actual game play'.
	 * Responsible for only interacting with the user and passing the user command for appropriate action.
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		int numberOfPlayers;
		int traversalCounter;
		String command;
		Phase gamePhase;
		Player player;
		PlayRisk game = new PlayRisk();
		CardExchange ce = new CardExchange();

		System.out.println("Welcome to Risk Game");
		System.out.println("To continue, select a map from the below mentioned existing maps or create a new one.");
		game.printMapNames();

		//read first command
		Command cmd = new Command();
		Scanner read = new Scanner(System.in);
		do{
			command = read.nextLine();
			gamePhase = cmd.parseCommand(null, command);
		}while(gamePhase!=Phase.ARMYALLOCATION);

		//start the game by looping through the players
        numberOfPlayers = cmd.game.getPlayers().size();
        traversalCounter = 0;
		while(gamePhase!=Phase.CARDEXCHANGE){
            player = cmd.game.getPlayers().get(traversalCounter);
            System.out.println(player.getPlayerName() + "'s turn to place army");
            int originalArmies = player.getOwnedArmies();
            command = read.nextLine();
            gamePhase = cmd.parseCommand(player, command);
            if (!command.equalsIgnoreCase("showmap") && originalArmies > player.getOwnedArmies()) {
                traversalCounter++;
                if (traversalCounter >= numberOfPlayers) {
                    traversalCounter = 0;
                }
            }
        }

		traversalCounter = 0;	//reset traversal counter
		while(true){
			player = cmd.game.getPlayers().get(traversalCounter);
			System.out.println(player.getPlayerName() + "'s turn");
			System.out.println("Reinforcement phase");
			cmd.playerChangeEvent(player);
			while (player.getOwnedCards().size()>2) {
				while (player.getOwnedCards().size() > 4) {
					System.out.println("You have 5 cards or more, you have to exchange cards");
					ce.printPlayersCards(player);
					command = read.nextLine();
					gamePhase = cmd.parseCommand(player, command);
				}
				System.out.println("Would you like to exchange your card to get more armies?");
				ce.printPlayersCards(player);
				command = read.nextLine();
				gamePhase = cmd.parseCommand(player,command);
			}
			command = "exchangecards -none";
			gamePhase = cmd.parseCommand(player,command);

			while(gamePhase!=Phase.TURNEND){
				if(gamePhase==Phase.REINFORCEMENT){
					System.out.println("Reinforcement armies: " + player.getOwnedArmies());
				} else if(gamePhase==Phase.ATTACK) {
					System.out.println("Attack phase");
				}
				else if(gamePhase==Phase.ATTACKCARDEXCHANGE){
				    System.out.println("You have to exchange cards till you have 4 or fewer cards. Use command 'exchangecards num num num' only.");
                    System.out.println("You will be redirected to move armies to conquered country once you have 4 or less cards.");
                }
				command = read.nextLine();
				gamePhase = cmd.parseCommand(player, command);
			}
			cmd.turnEndEvent();
			traversalCounter++;
			if (traversalCounter >= cmd.game.getPlayers().size()) {
				traversalCounter = 0;
			}
		}
	}
	
	/**
	 * Prints names of existing map files in maps folder.
	 */
	private void printMapNames() {
		File folder = new File("src/main/resources/maps/");
		File[] files = folder.listFiles();
		
		for(int i=0; i<files.length; i++) {
			if(files[i].isFile())
				System.out.print(files[i].getName() + " ");
		}
		System.out.println();
	}
}
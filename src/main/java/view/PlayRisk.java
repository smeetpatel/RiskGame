package main.java.view;

import main.java.controller.Controller;
import main.java.controller.MapController;
import main.java.controller.StartUpController;
import main.java.model.Card;
import main.java.model.Phase;
import main.java.model.Player;
import main.java.controller.Command;

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
	/*public static void main(String[] args) {
		int numberOfPlayers;
		int traversalCounter;
		String command;
		Phase gamePhase;
		Player player;
		PlayRisk game = new PlayRisk();

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
		int index = 1;
		while(true){
			player = cmd.game.getPlayers().get(traversalCounter);
			System.out.println(player.getPlayerName() + "'s turn");
			System.out.println("Reinforcement phase");
			cmd.playerChangeEvent(player);
			while (player.getOwnedCards().size()>2) {
				while (player.getOwnedCards().size() > 4) {
					System.out.println("You have 5 cards or more, you have to exchange cards");
					for(Card card : player.getOwnedCards()){
						System.out.println(index + ". " + card.getCardCountry() + " - " + card.getCardType());
						index++;
					}
					index = 1;
					command = read.nextLine();
					gamePhase = cmd.parseCommand(player,command);
				}
				System.out.println("Would you like to exchange your card to get more armies?");
				for(Card card : player.getOwnedCards()){
					System.out.println(index + ". " + card.getCardCountry() + " - " + card.getCardType());
					index++;
				}
				index = 1;
				command = read.nextLine();
				gamePhase = cmd.parseCommand(player, command);
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
				if(gamePhase==Phase.QUIT){
					System.out.println(player.getPlayerName() + " won the game.\n");
					System.exit(0);
				}
			}
			cmd.turnEndEvent();
			traversalCounter++;
			if (traversalCounter >= cmd.game.getPlayers().size()) {
				traversalCounter = 0;
			}
		}
	}*/
	public static void main(String[] args) {
		int numberOfPlayers;
		int traversalCounter;

		String command;
		String message;

		boolean validCommand = false;
		Player player;
		Controller controller;
		PlayRisk game = new PlayRisk();
		Scanner read = new Scanner(System.in);

		//Select game mode
		while(!validCommand){
			System.out.println("Enter 1 to play single-game mode or 2 to play tournament mode.");
			command = read.nextLine();
			if(command.equals("1")){

				//select user-choice to load game or play new game
				while(!validCommand){
					System.out.println("Enter 1 to load a saved game or 2 to edit/load map for new game.");
					command = read.nextLine();
					if(command.equals("1")){

						validCommand = true;
						//TODO: code for loading a saved game goes here

					} else if(command.equals("2")){

						validCommand = true;
						controller = new MapController();
						System.out.println("To continue, select a map from the below mentioned existing maps or create a new one.");
						game.printMapNames();

						//interact with user until a map is loaded
						do{
							command = read.nextLine();
							message = controller.parseCommand(null, command);
							System.out.println(message);
						}while(controller.getGame().getGamePhase()!=Phase.STARTUP);

						//now add game players and populate countries
						controller = new StartUpController(controller.getGame());
						do{
							command = read.nextLine();
							message = controller.parseCommand(null, command);
							System.out.println(message);
						}while(controller.getGame().getGamePhase()!=Phase.ARMYALLOCATION);

						//allow players to place initial armies
						numberOfPlayers = controller.getGame().getPlayers().size();
						traversalCounter = 0;
						while(controller.getGame().getGamePhase()!=Phase.CARDEXCHANGE){
							player = controller.getGame().getPlayers().get(traversalCounter);
							//TODO: execute following code only if player is human
							System.out.println(player.getPlayerName() + "'s turn to place army");
							int originalArmies = player.getOwnedArmies();
							command = read.nextLine();
							message = controller.parseCommand(player, command);
							System.out.println(message);
							if (!command.equalsIgnoreCase("showmap") && originalArmies > player.getOwnedArmies()) {
								traversalCounter++;
								if (traversalCounter >= numberOfPlayers) {
									traversalCounter = 0;
								}
							}
						}


					} else {

						System.out.println("Invalid choice");

					}
				}

			} else if (command.equals("2")){
				//TODO: code for tournament mode goes here
				validCommand = true;
			} else {
				System.out.println("Invalid choice");
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
package main.java.view;

import main.java.controller.*;
import main.java.model.*;

import java.io.File;
import java.util.Scanner;

/**
 * Responsible for playing the game.
 * Covers tasks ranging from 'map editing' to 'actual game play'.
 * Responsible for only interacting with the user and passing the user command for appropriate action.
 */
public class PlayRisk {

	/**
	 * Runs the game.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		int numberOfPlayers;
		int traversalCounter;
		int index = 1;

		String command;
		String message;

		boolean validCommand = false;
		boolean loadGame = false;

		Player player = null;
		Controller controller;

		PlayRisk game = new PlayRisk();
		Scanner read = new Scanner(System.in);

		//Select game mode
		while (!validCommand) {
			System.out.println("Enter 1 to play single-game mode or 2 to play tournament mode.");
			command = read.nextLine();
			if (command.equals("1")) {
				//single-game mode
				//select user-choice to load game or play new game
				while (!validCommand) {
					System.out.println("Enter 1 to load a saved game or 2 to edit/load map for new game.");
					command = read.nextLine();

					if (command.equals("1")) {

						//loads game
						validCommand = true;
						loadGame = true;
						controller = new LoadGameController();
						System.out.println("To continue, select a game to load from the existing save games.");
						game.printSavedGames();
						do {
							command = read.nextLine();
							message = controller.parseCommand(null, command);
						} while (!message.equals("Loaded successfully"));

						//set traversal counter by finding appropriate player
						traversalCounter = -1;
						for (Player player1 : controller.getGame().getPlayers()) {
							traversalCounter++;
							if (player1 == controller.getGame().getActivePlayer()) {
								break;
							}
						}

						//set controller to turn controller to continue playing the loaded game
						controller = new TurnController(controller.getGame());

					} else if (command.equals("2")) {

						//loads/edits a map
						validCommand = true;
						controller = new MapController();
						System.out.println("To continue, select a map from the below mentioned existing maps or create a new one.");
						game.printMapNames();

						//interact with user until a map is loaded
						do {
							command = read.nextLine();
							message = controller.parseCommand(null, command);
						} while (controller.getGame().getGamePhase() != Phase.STARTUP);

						//now add game players and populate countries
						controller = new StartUpController(controller.getGame());
						do {
							command = read.nextLine();
							message = controller.parseCommand(null, command);
						} while (controller.getGame().getGamePhase() != Phase.ARMYALLOCATION);

						//allow players to place all the initial armies in start-up phase
						numberOfPlayers = controller.getGame().getPlayers().size();
						traversalCounter = 0;
						while (controller.getGame().getGamePhase() != Phase.CARDEXCHANGE) {
							player = controller.getGame().getPlayers().get(traversalCounter);
							System.out.println(player.getPlayerName() + "'s turn to place army");
							int originalArmies = player.getOwnedArmies();
							if (player instanceof HumanPlayer) {
								command = read.nextLine();
							} else {
								command = "placeall";
							}
							message = controller.parseCommand(player, command);
							if (!command.equalsIgnoreCase("showmap") && originalArmies > player.getOwnedArmies()) {
								traversalCounter++;
								if (traversalCounter >= numberOfPlayers) {
									traversalCounter = 0;
								}
							}
						}

						//let each player play all three phases of the game in round-robin fashion.
						controller = new TurnController(controller.getGame());
						traversalCounter = 0;    //reset traversal counter

					} else {
						System.out.println("Invalid choice. Enter 1 to loading game and 2 to load/edit/create a map and play the game.");
						continue;
					}

					while (controller.getGame().getGamePhase() != Phase.QUIT) {

						//get the player
						player = controller.getGame().getPlayers().get(traversalCounter);

						//If human player carry out user interaction, else allow bots to take turns.
						if (player instanceof HumanPlayer) {
							System.out.println(player.getPlayerName() + "'s turn");
							if (controller.getGame().getGamePhase() == Phase.CARDEXCHANGE) {
								System.out.println("Reinforcement phase");

								//if game was loaded in reinforcement phase, reinforcement armies are already calculated before saving the game
								if (loadGame) {
									loadGame = false;
								} else {
									((TurnController) controller).playerChangeEvent(player);
								}

								//card exchange takes place
								while (player.getOwnedCards().size() > 2) {
									while (player.getOwnedCards().size() > 4) {
										System.out.println("You have 5 cards or more, you have to exchange cards");
										for (Card card : player.getOwnedCards()) {
											System.out.println(index + ". " + card.getCardCountry() + " - " + card.getCardType());
											index++;
										}
										index = 1;
										command = read.nextLine();
										message = controller.parseCommand(player, command);
										System.out.println(message);
									}
									System.out.println("Would you like to exchange your card to get more armies?");
									for (Card card : player.getOwnedCards()) {
										System.out.println(index + ". " + card.getCardCountry() + " - " + card.getCardType());
										index++;
									}
									index = 1;
									command = read.nextLine();
									message = controller.parseCommand(player, command);
								}
							}
							command = "exchangecards -none";
							message = controller.parseCommand(player, command);
							if(loadGame){
								loadGame = false;
							}

							//loop through all three phases of the player
							while (controller.getGame().getGamePhase() != Phase.TURNEND) {
								if (controller.getGame().getGamePhase() == Phase.REINFORCEMENT) {
									System.out.println("Reinforcement armies: " + player.getOwnedArmies());
								} else if (controller.getGame().getGamePhase() == Phase.ATTACK) {
									System.out.println("Attack phase");
								} else if (controller.getGame().getGamePhase() == Phase.ATTACKCARDEXCHANGE) {
									System.out.println("You have to exchange cards till you have 4 or fewer cards. Use command 'exchangecards num num num' only.");
									System.out.println("You will be redirected to move armies to conquered country once you have 4 or less cards.");
								}
								command = read.nextLine();
								message = controller.parseCommand(player, command);
								if (controller.getGame().getGamePhase() == Phase.QUIT) {
									System.out.println(player.getPlayerName() + " won the game.\n");
									System.exit(0);
								}
							}
						} else {
							((TurnController) controller).botTurn(player);
							if (controller.getGame().getGamePhase() == Phase.QUIT) {
								System.out.println(player.getPlayerName() + " won the game.\n");
								System.exit(0);
							}
						}
							((TurnController) controller).turnEndEvent();
							traversalCounter++;
							if (traversalCounter >= controller.getGame().getPlayers().size()) {
								traversalCounter = 0;
							}
					}
				}
			} else if (command.equals("2")) {
					//tournament code

					controller = new TournamentController();
					do {
						System.out.println("Enter a valid tournament command of the form 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'");
						command = read.nextLine();
						message = controller.parseCommand(null, command);
					} while (!message.equals("success"));
					validCommand = true;
				} else {
					System.out.println("Invalid choice. Enter 1 for single-game mode and 2 for tournament mode.");
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

	/**
	 * Prints names of existing game files in maps folder.
	 */
	private void printSavedGames(){
        File folder = new File("src/main/resources/game/");
        File[] files = folder.listFiles();

        for(int i=0; i<files.length; i++) {
            if(files[i].isFile())
                System.out.print(files[i].getName() + " ");
        }
        System.out.println();
    }
}
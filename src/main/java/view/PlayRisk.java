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
                //single-game mode
				//select user-choice to load game or play new game
				while(!validCommand){
					System.out.println("Enter 1 to load a saved game or 2 to edit/load map for new game.");
					command = read.nextLine();
					if(command.equals("1")){
                        //loads game
						validCommand = true;
						//TODO: code for loading a saved game goes here

					} else if(command.equals("2")){
                        //loads/edits a map
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

						//allow players to place all the initial armies in start-up phase
						numberOfPlayers = controller.getGame().getPlayers().size();
						traversalCounter = 0;
						while(controller.getGame().getGamePhase()!=Phase.CARDEXCHANGE){
							player = controller.getGame().getPlayers().get(traversalCounter);
							System.out.println(player.getPlayerName() + "'s turn to place army");
							int originalArmies = player.getOwnedArmies();
                            if(player instanceof HumanPlayer){
                                command = read.nextLine();
                            } else {
                                command = "placeall";
                            }
                            message = controller.parseCommand(player, command);
                            System.out.println(message);
							if (!command.equalsIgnoreCase("showmap") && originalArmies > player.getOwnedArmies()) {
								traversalCounter++;
								if (traversalCounter >= numberOfPlayers) {
									traversalCounter = 0;
								}
							}
						}

						//let each player play all three phases of the game in round-robin fashion.
						controller = new TurnController(controller.getGame());
						traversalCounter = 0;	//reset traversal counter
						int index = 1;
						while(controller.getGame().getGamePhase()!=Phase.QUIT){
							player = controller.getGame().getPlayers().get(traversalCounter);
                            //If human player carry out user interaction, else allow bots to take turns.
                            if(player instanceof HumanPlayer){
                                System.out.println(player.getPlayerName() + "'s turn");
                                System.out.println("Reinforcement phase");
                                ((TurnController) controller).playerChangeEvent(player);

                                //card exchange takes place
                                while (player.getOwnedCards().size()>2) {
                                    while (player.getOwnedCards().size() > 4) {
                                        System.out.println("You have 5 cards or more, you have to exchange cards");
                                        for(Card card : player.getOwnedCards()){
                                            System.out.println(index + ". " + card.getCardCountry() + " - " + card.getCardType());
                                            index++;
                                        }
                                        index = 1;
                                        command = read.nextLine();
                                        message = controller.parseCommand(player,command);
                                        System.out.println(message);
                                    }
                                    System.out.println("Would you like to exchange your card to get more armies?");
                                    for(Card card : player.getOwnedCards()){
                                        System.out.println(index + ". " + card.getCardCountry() + " - " + card.getCardType());
                                        index++;
                                    }
                                    index = 1;
                                    command = read.nextLine();
                                    message = controller.parseCommand(player, command);
                                }
                                command = "exchangecards -none";
                                message = controller.parseCommand(player,command);
                                System.out.println(message);

                                //loop through all three phases of the player
                                while(controller.getGame().getGamePhase()!=Phase.TURNEND){
                                    if(controller.getGame().getGamePhase()==Phase.REINFORCEMENT){
                                        System.out.println("Reinforcement armies: " + player.getOwnedArmies());
                                    } else if(controller.getGame().getGamePhase()==Phase.ATTACK) {
                                        System.out.println("Attack phase");
                                    }
                                    else if(controller.getGame().getGamePhase()==Phase.ATTACKCARDEXCHANGE){
                                        System.out.println("You have to exchange cards till you have 4 or fewer cards. Use command 'exchangecards num num num' only.");
                                        System.out.println("You will be redirected to move armies to conquered country once you have 4 or less cards.");
                                    }
                                    command = read.nextLine();
                                    message = controller.parseCommand(player, command);
                                    if(controller.getGame().getGamePhase()==Phase.QUIT){
                                        System.out.println(player.getPlayerName() + " won the game.\n");
                                        System.exit(0);
                                    }
                                }
                            } else {
                                ((TurnController) controller).botTurn(player);
                            }

							((TurnController) controller).turnEndEvent();
							traversalCounter++;
							if (traversalCounter >= controller.getGame().getPlayers().size()) {
								traversalCounter = 0;
							}
						}
					} else {
						System.out.println("Invalid choice");

					}
				}
			} else if (command.equals("2")){
			    //tournament code
				//TODO: code for tournament mode goes here
				//TODO: call tournament controller here
                controller = new TournamentController();
                do{
                    System.out.println("Enter a valid tournament command of the form 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'");
                    command = read.nextLine();
                    message = controller.parseCommand(null, command);
					System.out.println(message); // For debugging purpose **** It prints the message which shows any error or success message.
                } while(!message.equals("success"));
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
package main.java;

import java.io.File;
import java.util.Scanner;

/**
 * Responsible for playing the game.
 * Covers tasks ranging from 'map editing' to 'actual game play'.
 * Responsible for only interacting with the user and calling appropriate methods for further actions.
 */
public class PlayRisk {

	public static void main(String[] args) {
		PlayRisk game = new PlayRisk();
		System.out.println("Welcome to Risk Game");
		System.out.println("To continue, select a map from the below mentioned existing maps or create a new one.");
		game.printMapNames();

		//read first command
		String command;
		Phase gamePhase;
		Command cmd = new Command();
		Scanner read = new Scanner(System.in);
		do{
			command = read.nextLine();
			gamePhase = cmd.parseCommand(null, command);
		}while(gamePhase!=Phase.REINFORCEMENT);
		
		//start the game by looping through the players
		int numberOfPlayers = cmd.game.getPlayers().size();
		int traversalCounter = 0;
		while(true) {
			while(traversalCounter<numberOfPlayers) {
				Player p = cmd.game.getPlayers().get(traversalCounter);
				Reinforcement.assignReinforcementArmies(p);
				System.out.println(p.getPlayerName() + "'s turn");

				while(gamePhase!=Phase.TURNEND) {
					if(gamePhase==Phase.REINFORCEMENT) {
						System.out.println("Reinforcement armies: " + p.getOwnedArmies());
					}
					command = read.nextLine();
					gamePhase = cmd.parseCommand(p, command);
				}
				gamePhase = Phase.REINFORCEMENT;
				cmd.game.setGamePhase(gamePhase);
				traversalCounter++;
			}
			traversalCounter = 0;
		}		
	}
	
	/**
	 * Prints names of existing map files in maps folder.
	 */
	private void printMapNames() {
		File folder = new File("maps/");
		File[] files = folder.listFiles();
		
		for(int i=0; i<files.length; i++) {
			if(files[i].isFile())
				System.out.print(files[i].getName() + " ");
		}
		System.out.println();
	}
}
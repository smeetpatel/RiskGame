package main.java;

import java.io.File;
import java.util.Scanner;

/**
 * Responsible for playing the game.
 * Covers tasks ranging from 'map editing' to 'actual gameplay'.
 * Responsible for only interacting with the user and calling apporpriate methods for further
 * actions.
 * 
 * @author Tirth & Smeet
 *
 */

public class PlayRisk {

	public static void main(String[] args) {
		PlayRisk game = new PlayRisk();
		System.out.println("Welcome to Risk Game");
		System.out.println("To continue, select a map from the below mentioned existing maps or create a new one.");
		game.printMapNames();

		//read first command
		Scanner read = new Scanner(System.in);
		String command = read.nextLine();
		Command cmd = new Command();
		Command.Phase gamePhase = Command.Phase.NULL;
		gamePhase = cmd.parseCommand(command);
		while(gamePhase!= Command.Phase.REINFORCEMENT) {
			command = read.nextLine();
			gamePhase = cmd.parseCommand(command);
		}
		
		int numberOfPlayers = cmd.players.size();
		int traversalCounter = 0;
		//start the game by looping through the players
		while(true) {
			while(traversalCounter<numberOfPlayers) {
				Player p = cmd.players.get(traversalCounter);
				Reinforcement.assignReinforcementArmies(p);
				while(gamePhase!=Command.Phase.TURNEND) {
					command = read.nextLine();
					gamePhase = cmd.parseCommand(command);
				}
			}
		}
	}
	
	//prints names of existing map files
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
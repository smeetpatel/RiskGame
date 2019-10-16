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
		gamePhase = cmd.parseCommand(null, command);
		while(gamePhase!= Command.Phase.REINFORCEMENT) {
			command = read.nextLine();
			gamePhase = cmd.parseCommand(null, command);
		}
		
		int numberOfPlayers = cmd.players.size();
		int traversalCounter = 0;
		//start the game by looping through the players
		while(true) {
			while(traversalCounter<numberOfPlayers) {
				Player p = cmd.players.get(traversalCounter);
				Reinforcement.assignReinforcementArmies(p);
				System.out.println(p.getPlayerName() + "'s turn");
				//System.out.println("Own's countries: ");
				//for(Country c : p.getOwnedCountries().values())
				//	System.out.println(c.getCountryName());
				System.out.println("Owned armies: " + p.getOwnedArmies());

				while(gamePhase!=Command.Phase.TURNEND) {
					command = read.nextLine();
					gamePhase = cmd.parseCommand(p, command);
				}
				gamePhase = Command.Phase.REINFORCEMENT;
				cmd.setGamePhase(gamePhase);
				traversalCounter++;
			}
			traversalCounter = 0;
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
package main.java;

import java.io.File;
import java.util.Scanner;

/**
 * Responsible for playing the game.
 * Covers tasks ranging from 'map editing' to 'actual gameplay'.
 * Responsible for only interacting with the user and calling apporpriate methods for further
 * actions.
 * 
 * @author Smeet
 *
 */

public class PlayRisk {

	public enum Phase{NULL, EDITMAP, STARTUP, REINFORCEMENT, FORTIFICATION, QUIT};
	
	public static void main(String[] args) {
		PlayRisk game = new PlayRisk();
		System.out.println("Welcome to Risk Game");
		System.out.println("To continue, select a map from the below mentioned existing maps or create a new one.");
		game.printMapNames();

		//read first command
		Scanner read = new Scanner(System.in);
		String command = read.nextLine();
		Command cmd = new Command();
		Phase gamePhase = Phase.NULL;
		//gamePhase = cmd.parseCommand(gamePhase, command);
		cmd.parseCommand(command);
		while(gamePhase!=Phase.QUIT) {
			command = read.nextLine();
			//gamePhase = cmd.parseCommand(gamePhase, command);
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
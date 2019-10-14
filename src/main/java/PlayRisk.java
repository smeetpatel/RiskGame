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

	//public enum Phase{NULL, EDITMAP, STARTUP, REINFORCEMENT, FORTIFICATION, QUIT};
	
	public static void main(String[] args) {
		PlayRisk game = new PlayRisk();
		System.out.println("Welcome to Risk Game");
		System.out.println("To continue, select a map from the below mentioned existing maps or create a new one.");
		game.printMapNames();

		//read first command
		Scanner read = new Scanner(System.in);
		System.out.println("Enter: ");
		String command = read.nextLine();
		Command cmd = new Command();
		Command.Phase gamePhase = Command.Phase.NULL;
		//gamePhase = cmd.parseCommand(gamePhase, command);
		gamePhase = cmd.parseCommand(command);
		while(!gamePhase.equals(Command.Phase.QUIT)) {
			System.out.println("Enter Command: ");
			command = read.nextLine();
			gamePhase = cmd.parseCommand(command);
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
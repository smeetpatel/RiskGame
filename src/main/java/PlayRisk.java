package main.java;

import java.io.File;
import java.util.Scanner;

/**
 * Responsible for playing the game.
 * Covers tasks ranging from 'map editing' to 'actual gameplay'.
 * Responsible for only interacting with the user and calling apporpriate methods for furhter
 * actions.
 * 
 * @author Smeet
 *
 */

public class PlayRisk {

	public static enum Phase{NULL, EDITMAP, STARTUP, REINFORCEMENT, FORTIFICATION};
	
	public static void main(String[] args) {
		System.out.println("Welcome to Risk Game");
		System.out.println("To continue, select a map from the below mentioned existing maps or create a new one.");
		printMapNames();

		//read first command
		Scanner read = new Scanner(System.in);
		String command = read.nextLine();
		Command cmd = new Command();
		Phase gamePhase = Phase.NULL;
		gamePhase = cmd.parseCommand(gamephase, command);
		
		
		
		
		
		

	}
	
	//prints names of existing map files
	private static void printMapNames() {
		File folder = new File("maps/");
		File[] files = folder.listFiles();
		
		for(int i=0; i<files.length; i++) {
			if(files[i].isFile())
				System.out.print(files[i].getName() + " ");
		}
		System.out.println();
	}
}
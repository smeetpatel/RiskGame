package main.java;

import java.util.Scanner;

public class Dummy {
    public static void main(String[] args){

        Command.Phase gamePhase;
        String command;
        Player player = new Player(null);
        Scanner sc = new Scanner(System.in);
        command = sc.nextLine();
        gamePhase = Command.Phase.REINFORCEMENT;
        Command ob1 = new Command();
        gamePhase = ob1.parseCommand(player,command);

    }
}

package main.java.view;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Displays the result of the tournament.
 */
public class TournamentResultView {

    /**
     * Displays the result of the tournament
     * @param winner HashMap representing the winner of the game indexed based on the number of the game.
     * @param maps List of maps used to play the tournament.
     */
    public void displayTournamentResult(HashMap<Integer, String> winner, ArrayList<String> maps){
        int mapChangeAfter = winner.size()/maps.size();
        int index = 0;
        int changeIndex = 1;
        System.out.format("%25s%25s%35s\n", "Game number", "Game map", "Winner");
        System.out.format("%85s\n", "-------------------------------------------------------------------------------------------");
        String mapName = maps.get(index);
        for(int i = 1; i<=winner.size(); i++){
            if(changeIndex>mapChangeAfter){
                index++;
                mapName = maps.get(index);
                changeIndex = 0;
            }
            changeIndex++;
            System.out.format("\n%25s%25s%25s\n", i, mapName, winner.get(i));
        }
    }
}
package main.java.controller;

import main.java.model.GameData;
import main.java.model.Player;
import main.java.view.PhaseView;
import main.java.view.PlayerDominationView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages tournament related commands.
 */
public class TournamentController extends Controller {

    /**
     * Creates a tournament controller object.
     */
    public TournamentController(){
        super();
    }

    /**
     * {@inheritDoc}
     * @param player Player playing the move
     * @param newCommand Command to be interpreted
     * @return winner of the tournament
     */
    public String parseCommand(Player player, String newCommand){
        //TODO: Return string "success" if valid tournament command
        //TODO Validation 1: Check if map files exist or not by calling 'isMapExists' method of GameActions class
        //TODO Validation 2: Check that number of map files in the argument are between 1 to 5
        //TODO Validation 3: Check if that number of player strategies in the command are between 2 to 4
        //TODO Validation 4: Check that the mentioned player strategies are all of different types
        //TODO Validation 5: Check that number of games mentioned in the argument are between 1 to 5
        //TODO Validation 6: Check that number of turns mentioned in the argument are between 10 to 50
        //TODO return appropriate string message in response to any failure. Make sure message makes reflect the occuring error.
        //TODO if command is valid, call playTournament method of TournamentController with appropriate arguments

    }

    /**
     * Get game data.
     */
    public GameData getGame(){
        return game;
    }

    /**
     * Conducts the tournament as per the mentioned arguments.
     * @param mapFiles  List of map files to play on
     * @param strategies    List of player strategies
     * @param numberOfGames Number of games to play
     * @param numberOfTurns Number of turns to play
     */
    public void playTournament(ArrayList<String> mapFiles, ArrayList<String> strategies, int numberOfGames, int numberOfTurns){
        int turnNumber = 0;
        PhaseView phaseView;
        PlayerDominationView playerDominationView;
        HashMap<Integer, String> winner = new HashMap<Integer, String>();

        //Create player objects
        for(String strategy : strategies){
            gameAction.addPlayer(game.getPlayers(), strategy, strategy);
        }

        //Start playing the each game
        for(String mapName : mapFiles)
    }

}

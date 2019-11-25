package main.java.controller;

import main.java.model.GameActions;
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

    GameActions gameActions;

    /**
     * Ensures map name is valid.
     *
     * @param s input string
     * @return true if valid match, else false
     */
    public boolean isMapNameValid(String s) {

        return s != null && s.matches("^[a-zA-Z.]*$");
    }

    public boolean isPlayerStrategyValid(String s){

        String[] array = new String[]{"aggresive", "benevolent", "random", "cheater"};
        for(int i=0; i<4; i++){
            if(s.equals(array[i])){
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerStrategyDistinct(ArrayList<String> list){

        for(int i=0;i<list.size();i++){
            for(int j = i+1; j<list.size(); j++){
                if(list.get(i).equals(list.get(j))){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean allMapExists(ArrayList<String> list){
        int counter=0;
        for(String s:list){
            if(gameActions.isMapExists(s)){
                counter++;
            }
        }
        if(counter == list.size()){
            return true;
        }else{
            return false;
        }

    }
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

        String[] data = newCommand.split("\\s+");
        String commandName = data[0];
        int noOfMaps;
        int noOfGames;
        int noOfTurns;
        ArrayList<String> maps = new ArrayList<String>();
        ArrayList<String> strategies = new ArrayList<String>();


        if (commandName.equals("tournament")) {

            try {
                if (data[1].equals("-M")) {
                    int i = 2;
                    while (!data[i].equals("-P")) {
                        if (i < data.length) {
                            if (isMapNameValid(data[i])) {
                                maps.add(data[i]);
                            } else {
                                return "map name is not valid";
                            }

                        } else {
                            return "there is nothing after -P";
                        }
                        i++;
                    }

                    if(maps.size()>=1 && maps.size()<=5 && allMapExists(maps)) {

                        //System.out.println(maps);


                        if (data[i].equals("-P")) {

                            int indexNew = i + 1;

                            while (!data[indexNew].equals("-G")) {
                                if (indexNew < data.length) {
                                    if (isPlayerStrategyValid(data[indexNew])) {
                                        strategies.add(data[indexNew]);
                                    } else {
                                        return "player strategy is not valid";
                                    }

                                } else {
                                    return "there is nothing after -P";
                                }
                                indexNew++;
                            }

                            if(strategies.size()>=2 && strategies.size()<=5 && isPlayerStrategyDistinct(strategies)) {
                                //System.out.println(strategies);

                                if (data[indexNew].equals("-G")) {
                                    if (indexNew + 1 < data.length) {
                                        if (data[indexNew + 1].matches("[1-5]")) {
                                            noOfGames = Integer.parseInt(data[indexNew + 1]);
                                            //System.out.println("Number of Games:" + noOfGames);
                                        } else {
                                            return "number of game is not valid";
                                        }
                                    } else {
                                        return "nothing after -G";
                                    }

                                    int newIndex = indexNew + 2;

                                    if (data[newIndex].equals("-D")) {


                                        if (newIndex + 1 < data.length) {

                                            int n = Integer.parseInt(data[newIndex + 1]);

                                            if (n >= 10 && n <= 50) {
                                                noOfTurns = Integer.parseInt(data[newIndex + 1]);

                                                //System.out.println("Number of turns: " + noOfTurns);
                                                return "success";
                                            } else {
                                                return "number of turns invalid";
                                            }
                                        } else {
                                            return "nothin after -G";
                                        }
                                    } else {
                                        return "there has to be -D";
                                    }
                                } else {
                                    return "there has to be -G";
                                }
                            }else{
                                return "number of strategies is not valid";
                            }
                        } else {
                            return "there has to be -P";
                        }
                    }else {
                        return "numbee of maps is invalid.";
                    }
                } else {
                    return "there has to be -M";
                }
            }catch (ArrayIndexOutOfBoundsException e){
                String message = "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                return message;
            }
        } else {
            return "Command has to be start with 'tournamnet'";
        }

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
        for(String mapName : mapFiles){

        }
    }

}

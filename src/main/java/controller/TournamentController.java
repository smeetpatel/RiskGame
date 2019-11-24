package main.java.controller;

import main.java.model.GameData;
import main.java.model.Phase;
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
        return "";
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
        int numberOfPlayers = strategies.size();
        int traversalCounter = 0;
        int gameNumber = 0;

        PhaseView phaseView;
        PlayerDominationView playerDominationView;
        HashMap<Integer, String> winner = new HashMap<Integer, String>();
        TurnController turnController = new TurnController(game);

        //Start playing on each map
        for(String mapName : mapFiles){
            //Start playing each game
            for(int i = 1; i<=numberOfGames; i++){

                gameNumber++;

                //load the map
                gameAction.loadMap(game, mapName);

                //Create player objects
                for(String strategy : strategies){
                    gameAction.addPlayer(game.getPlayers(), strategy, strategy);
                }

                //populate countries amongst players and create player domination view
                gameAction.populateCountries(game, game.getPlayers());
                System.out.println("Game " + 1 + " on " + mapName + ": Countries allocated amongst players");
                playerDominationView = new PlayerDominationView();
                playerDominationView.setVisible(true);
                playerDominationView.setSize(600, 600);
                playerDominationView.setDefaultCloseOperation(3);
                attachToPlayers(playerDominationView);
                gameAction.initalizaMapContolValue(game);

                //place all initial armies and create phase view
                gameAction.placeAll(game);
                phaseView = new PhaseView();
                phaseView.setVisible(true);
                phaseView.setSize(600, 600);
                phaseView.setDefaultCloseOperation(3);
                for(Player p : game.getPlayers()){
                    p.attach(phaseView);
                }
                game.attach(phaseView);

                //let each player play one by one
                for(int j=1; j<=numberOfTurns; j++){
                    Player player = getGame().getPlayers().get(traversalCounter);
                    turnController.botTurn(player);
                    if(game.getGamePhase()== Phase.QUIT){
                        winner.put(gameNumber, player.getPlayerName());
                        break;
                    }
                    turnController.turnEndEvent();
                    traversalCounter++;
                    if (traversalCounter >= numberOfPlayers) {
                        traversalCounter = 0;
                    }
                }
                winner.put(gameNumber, "No winner");

                //detach model objects from views, close of the views, and reset game data
                game.detach(phaseView);
                for(Player player: game.getPlayers()){
                    player.detach(phaseView);
                    player.detach(playerDominationView);
                }
                phaseView.setVisible(false);
                phaseView.dispose();
                playerDominationView.setVisible(false);
                playerDominationView.dispose();
                game.resetGameData();
            }
        }
        //return winner;
    }

    /**
     * Stores the player for the domination view
     */
    private void attachToPlayers(PlayerDominationView playerDominationView) {
        for(Player player : game.getPlayers()){
            player.attach(playerDominationView);
        }
    }
}

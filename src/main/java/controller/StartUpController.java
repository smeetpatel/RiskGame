package main.java.controller;

import main.java.model.GameActions;
import main.java.model.GameData;
import main.java.model.Phase;
import main.java.model.Player;
import main.java.view.MapView;
import main.java.view.PlayerDominationView;

/**
 * Manages start-up related commands for the game.
 */
public class StartUpController implements  Controller{

    /**
     * Helps access methods to view map.
     */
    public MapView mapView;

    /**
     * Represents the 'Player domination view'
     */
    public PlayerDominationView playerDominationView;

    /**
     * Intializes required class variables.
     */
    public StartUpController(GameData game){
        this.game = game;
        gameAction = new GameActions();
        mapView = new MapView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String parseCommand(Player player, String newCommand) {
        String message = "";
        String playerName;
        String[] data = newCommand.split("\\s+");
        String commandName = data[0];

        if (game.getGamePhase().equals(Phase.STARTUP)) {
            switch (commandName) {
                case "gameplayer":
                    try {
                        for (int i = 1; i < data.length; i++) {
                            if (data[i].equals("-add")) {
                                if (data[i + 1].matches("[a-zA-Z0-9]+")) {
                                    playerName = data[i + 1];
                                    boolean check = gameAction.addPlayer(game.getPlayers(), playerName);
                                    if (check) {
                                        message = "Player " + playerName + " successfully added";
                                    } else {
                                        if (game.getPlayers().size() == 6) {
                                            message = "Can not add any more player. Maximum 6 players can play.";
                                        } else {
                                            message = "Player with same name already exists. Add player with different name.";
                                        }
                                    }
                                } else {
                                    message = "Invalid player name";
                                }
                            } else if (data[i].equals("-remove")) {
                                if (data[i + 1].matches("[a-zA-Z0-9]+")) {
                                    playerName = data[i + 1];
                                    boolean check = gameAction.removePlayer(game.getPlayers(), playerName);
                                    if (check){
                                        message = "Player " + playerName + " successfully removed";
                                    }
                                    else {
                                        message = "Player " + playerName + " does not exist";
                                    }
                                } else{
                                    message = "Invalid player name";
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'gameplayer -add playername -remove playername'";
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'gameplayer -add playername -remove playername'";
                    }
                    break;

                case "populatecountries":
                    boolean check = gameAction.populateCountries(game, game.getPlayers());
                    if (check) {
                        message = "Countries allocated amongst the players";
                        playerDominationView = new PlayerDominationView();
                        playerDominationView.setVisible(true);
                        playerDominationView.setSize(600, 600);
                        playerDominationView.setDefaultCloseOperation(3);
                        attachToPlayers();
                        gameAction.initalizaMapContolValue(game);
                    } else {
                        message = "Minimum two players are required to play the game. Maximum six players.";
                    }
                    break;

                case "showmap":
                    mapView.showMap(game.getMap(), game.getPlayers());
                    break;

                default:
                    message = "Invalid command - use gameplayer/populatecountries/placearmy/placeall/showmap commands in start up phase.";
                    break;
            }
            return message;
        }
    }

    /**
     * Stores the player for the domination view
     */
    private void attachToPlayers() {
        for(Player player : game.getPlayers()){
            player.attach(playerDominationView);
        }
    }
}
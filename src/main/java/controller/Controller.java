package main.java.controller;

import main.java.model.GameActions;
import main.java.model.GameData;
import main.java.model.Player;

/**
 * Represents the essential qualities each controller will have.
 */
public interface Controller {

    /**
     * Holds the data related to the game.
     */
    GameData game;

    /**
     * Helps access methods related to background operations of the game such as loading/editing map, calculating the number of reinforcement armies, etc.
     */
    GameActions gameAction;

    /**
     * Function responsible for parsing user command and calling appropriate method.
     *
     * @param player Player playing the move
     * @param newCommand Command to be interpreted
     * @return appropriate response message
     */
    String parseCommand(Player player, String newCommand){};

    /**
     * Get game data.
     */
    GameData getGame(){};
}

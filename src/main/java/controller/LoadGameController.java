package main.java.controller;

import main.java.model.GameDataBuilder;
import main.java.model.Player;

/**
 * Manages game loading related commands for the game.
 */
public class LoadGameController extends Controller{

    /**
     * Function responsible for parsing user command and calling appropriate method.
     *
     * @param player Player playing the move
     * @param newCommand Command to be interpreted
     * @return appropriate response message
     */
    @Override
    public String parseCommand(Player player, String newCommand){

        return "";
    }

    /**
     * Creates a game data object.
     * @param gameDataBuilder Helps build GameData object.
     */
    public void createGameData(GameDataBuilder gameDataBuilder){
        this.game = gameDataBuilder.buildGameData();
    }
}
package main.java.controller;

import main.java.model.GameActions;
import main.java.model.GameData;
import main.java.model.Player;
import main.java.view.CardExchangeView;
import main.java.view.MapView;

/**
 * Manages commands related to three phases of each turn of the game.
 */
public class TurnController implements Controller{

    /**
     * Helps access methods to view map.
     */
    public MapView mapView;

    /**
     * Represents the 'Card exchange view'
     */
    public CardExchangeView cardExchangeView;

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
    public String parseCommand(Player player, String newCommand){
        String message = "";

        return message;
    }


    /**
     * event occur when player's turn changed
     * @param player object of player
     */
    public void playerChangeEvent(Player player) {
        gameAction.assignReinforcementArmies(game, player);
        cardExchangeView = new CardExchangeView();
        cardExchangeView.setVisible(true);
        cardExchangeView.setSize(600, 600);
        player.attach(cardExchangeView);
        gameAction.initializeCEV(player);
    }

}

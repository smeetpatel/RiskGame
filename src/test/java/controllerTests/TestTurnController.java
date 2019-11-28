package test.java.controllerTests;

import main.java.controller.Controller;
import main.java.controller.StartUpController;
import main.java.controller.TurnController;
import main.java.model.GameActions;
import main.java.model.GameData;
import main.java.model.Phase;
import main.java.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests if a valid turn related commands are executed or not.
 */
public class TestTurnController {
    /**
     * Represents the state of the game
     */
    GameData game;

    /**
     * To help load the map.
     */
    GameActions gameActions;

    /**
     * Refers to controller
     */
    Controller controller;

    /**
     * List of players in the game.
     */
    ArrayList<Player> players;

    /**
     * Sets up the context for test.
     */
    @Before
    public void before(){
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();
        players = new ArrayList<Player>();

        //load the map
        gameActions.loadMap(game, "finaltest.map");

        //set up players
        controller = new StartUpController(game);
        controller.parseCommand(null, "gameplayer -add s human -add t human");
        controller.parseCommand(null, "populatecountries");
        controller.parseCommand(game.getPlayers().get(0), "placeall");
        controller = new TurnController(game);
    }

    /**
     * Tests if more number of armies mentioned in reinforce command is handled or not.
     */
    @Test
    public void testReinforce(){
        Player player = game.getPlayers().get(0);
        game.setActivePlayer(player);
        game.setGamePhase(Phase.REINFORCEMENT);
        gameActions.assignReinforcementArmies(game, player);
        int armies = player.getOwnedArmies();
        String command = "reinforce india " + (armies+1);
        String message = controller.parseCommand(player, command);
        Assert.assertEquals("You don't have enough armies", message);
    }

    /**
     * Tests if more country mentioned in reinforce command is handled or not.
     */
    @Test
    public void testReinforce2(){
        Player player = game.getPlayers().get(0);
        game.setActivePlayer(player);
        game.setGamePhase(Phase.REINFORCEMENT);
        gameActions.assignReinforcementArmies(game, player);
        int armies = player.getOwnedArmies();
        String command = "reinforce us " + armies;
        String message = controller.parseCommand(player, command);
        Assert.assertEquals("You don't own this country", message);
    }

}

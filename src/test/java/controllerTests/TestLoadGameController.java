package test.java.controllerTests;

import main.java.controller.Controller;
import main.java.controller.LoadGameController;
import main.java.controller.StartUpController;
import main.java.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests if a valid game loading takes place or not.
 */
public class TestLoadGameController {

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
     * Sets up the context for test.
     */
    @Before
    public void before(){
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();
        controller = new LoadGameController();
    }

    /**
     * Tests if valid game loading command is accepted or not.
     */
    @Test
    public void testLoadGame(){
        String message = controller.parseCommand(null, "loadgame gameone");
        Assert.assertEquals("Loaded successfully", message);
    }

    /**
     * Tests if valid game phase is maintained or not.
     */
    @Test
    public void testLoadGame2(){
        controller.parseCommand(null, "loadgame gameone");
        Assert.assertEquals(Phase.FORTIFICATION, controller.getGame().getGamePhase());
        controller.parseCommand(null, "loadgame gametwo");
        Assert.assertEquals(Phase.REINFORCEMENT, controller.getGame().getGamePhase());

    }

    /**
     * Tests if valid game phase is maintained or not.
     */
    @Test
    public void testLoadGame3(){
        controller.parseCommand(null, "loadgame gameone");
        Assert.assertEquals("s", controller.getGame().getActivePlayer().getPlayerName().toLowerCase());
    }
}
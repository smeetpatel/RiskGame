package test.java.controllerTests;

import main.java.controller.Controller;
import main.java.controller.LoadGameController;
import main.java.controller.TournamentController;
import main.java.model.GameActions;
import main.java.model.GameData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests tournament related commands.
 */
public class TestTournamentController {

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
        controller = new TournamentController();
    }

    /**
     * Test if invalid tournament commands are detected or not.
     */
    @Test
    public void testTorunamentCommand(){

        String message = controller.parseCommand(null, "tournament -M -P player  -G 3 -D 5");
        Assert.assertEquals("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", message);

       message = controller.parseCommand(null, "tournament -M finaltest.map ameroki.map ConquestCliff.map eurasien.map uk.map world.map -P player  -G 3 -D 5");
        Assert.assertEquals("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", message);

        message = controller.parseCommand(null, "tournament -M finaltest.map ameroki.map -P human  -G 3 -D 15");
        Assert.assertEquals("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", message);

        message = controller.parseCommand(null, "tournament -M finaltest.map ameroki.map -P benevolent benevolent  -G 3 -D 15");
        Assert.assertEquals("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", message);

        message = controller.parseCommand(null, "tournament -M finaltest.map ameroki.map -P benevolent random cheater aggressive random  -G 3 -D 15");
        Assert.assertEquals("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", message);

        message = controller.parseCommand(null, "tournament -M finaltest.map ameroki.map -P benevolent random  -G 3 -D 5");
        Assert.assertEquals("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", message);

        message = controller.parseCommand(null, "tournament -M finaltest.map ameroki.map -P benevolent random  -G 3 -D 55");
        Assert.assertEquals("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", message);

        message = controller.parseCommand(null, "tournament -M finaltest.map ameroki.map -P benevolent random  -G 3 -D 15");
        Assert.assertEquals("success", message);
    }


}

package test.java.modelTests;

import main.java.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests GameActions class.
 */
public class TestGameActions {

    /**
     * Represents the state of the game
     */
    GameData game;

    /**
     * To help load the map.
     */
    GameActions gameActions;

    /**
     * MapValidation object.
     */
    MapValidation mapValidation;

    /**
     * Represents player.
     */
    Player player;

    /**
     * Sets up the context for test.
     */
    @Before
    public void before(){
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();
        //load the map
        gameActions.loadMap(game, "finaltest.map");
    }

    /**
     * Tests various validity checks to ensure that map is suitable for playing the game.
     */
    @Test
    public void testValidateMap(){
        MapValidityStatus mapValidityStatus = gameActions.validateMap(game.getMap());
        Assert.assertEquals(MapValidityStatus.VALIDMAP, mapValidityStatus);
    }

    /**
     * Tests various validity checks to ensure that map is suitable for playing the game.
     */
    @Test
    public void testValidateMap2(){
        game.getMap().addContinent("new", 9);
        MapValidityStatus mapValidityStatus = gameActions.validateMap(game.getMap());
        Assert.assertEquals(MapValidityStatus.EMPTYCONTINENT, mapValidityStatus);
    }

    /**
     * Tests various validity checks to ensure that map is suitable for playing the game.
     */
    @Test
    public void testValidateMap3(){
        game.getMap().addContinent("new", 9);
        game.getMap().addCountry("newCountry", "new");
        MapValidityStatus mapValidityStatus = gameActions.validateMap(game.getMap());
        Assert.assertEquals(MapValidityStatus.UNCONNECTEDGRAPH, mapValidityStatus);
    }
}
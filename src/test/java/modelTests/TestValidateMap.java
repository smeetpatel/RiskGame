package test.java.modelTests;

import main.java.model.GameActions;
import main.java.model.GameData;
import main.java.model.GameMap;

import main.java.model.MapValidityStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if map gets validated correctly or not
 *
 */
public class TestValidateMap {
    /**
     * Represents the state of the game
     */
    GameData game;

    /**
     * To help load the map.
     */
    GameActions gameActions;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();
        //load the map
        gameActions.loadMap(game, "testmapthird.map");
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testValidateMap() {
        MapValidityStatus mapValidityStatus = gameActions.validateMap(game.getMap());
        Assert.assertEquals(MapValidityStatus.VALIDMAP, mapValidityStatus);
    }
}

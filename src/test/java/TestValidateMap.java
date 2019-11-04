package test.java;

import main.java.GameActions;
import main.java.GameData;
import main.java.GameMap;

import main.java.MapValidityStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

/**
 * Tests if map gets validated correctly or not
 *
 */
public class TestValidateMap {
   // MapEditor rcmd;
    GameMap map;
    GameActions gameActions;
    MapValidityStatus mapValidityStatus;
    GameData game;
    /**
     * Set up the context
     */
    @Before
    public void before(){

        //rcmd = new MapEditor();
        //map = new GameMap("createdMap.map");
        gameActions = new GameActions();
        game = new GameData();
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testValidateMap() {

        map = new GameMap("createdMap.map");
        gameActions.loadMap(game, "luca.map");
        mapValidityStatus = gameActions.validateMap(map);
        Assert.assertEquals(MapValidityStatus.VALIDMAP, mapValidityStatus);

    }
}

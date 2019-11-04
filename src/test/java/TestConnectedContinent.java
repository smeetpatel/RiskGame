package test.java;

import main.java.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if connected continent are being rightly identified or not.
 *
 */
public class TestConnectedContinent {
    MapValidation mvr;
    GameMap map;
    GameData game;
    GameActions gameActions;

    /**
     * Set up the context
     * Initialize the objects
     */
    @Before
    public void before(){
        mvr = new MapValidation();
        map = new GameMap("createdMap.map");
        game = new GameData();
        gameActions = new GameActions();
    }

    /**
     * This test method tests that continents are connected or not
     */
    @Test
    public void testConnectedContinent(){
        gameActions.editMap(game,"world.map");
        boolean check = mvr.continentConnectivityCheck(map);
        Assert.assertEquals(true,check);

    }
}
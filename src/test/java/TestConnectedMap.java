package test.java;

import main.java.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if connected maps are being rightly identified or not.
 *
 */
public class TestConnectedMap {

    /**
     * Helps access methods of MapValidation class.
     */
    MapValidation mvr;

    /**
     * To help load the map.
     */
    GameActions gameActions;

    /**
     * Represents the state of the game.
     */
    GameData game;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        mvr = new MapValidation();
        gameActions = new GameActions();
        game = new GameData();
    }

    /**
     * Tests if correctly identifies valid map or not.
     */
    @Test
    public void testConnectedMap1(){
        gameActions.loadMap(game, "world.map");
        boolean check = mvr.isGraphConnected(mvr.createGraph(game.getMap()));
        Assert.assertEquals(true,check);
    }

    /**
     * Tests if correctly identifies invalid map or not.
     */
    @Test
    public void testConnectedMap(){
        gameActions.loadMap(game, "world.map");
        Continent continent = new Continent("dummy", "9", "000");
        game.getMap().getContinents().put("dummy", continent);
        Country country = new Country("dummysecond", "dummy");
        game.getMap().getContinents().get("dummy").getCountries().put("dummysecond", country);
        game.getMap().getCountries().put("dummysecond", country);
        boolean check = mvr.isGraphConnected(mvr.createGraph(game.getMap()));
        Assert.assertEquals(false,check);
    }
}

package test.java;

import main.java.Continent;
import main.java.GameActions;
import main.java.GameData;
import main.java.MapValidation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if empty continents are being detected or not
 *
 */
public class TestEmptyContinent {
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
     * Tests if correctly identifies non-empty continents or not.
     */
    @Test
    public void testEmptyContinent1(){
        gameActions.loadMap(game, "world.map");
        boolean check = mvr.notEmptyContinent(game.getMap());
        Assert.assertEquals(true,check);
    }

    /**
     * Tests if correctly identifies empty continent or not.
     */
    @Test
    public void testEmptyContinent2(){
        gameActions.loadMap(game, "world.map");
        Continent continent = new Continent("dummy", 9);
        game.getMap().getContinents().put("dummy", continent);
        boolean check = mvr.notEmptyContinent(game.getMap());
        Assert.assertEquals(false,check);
    }
}

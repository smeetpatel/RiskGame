package test.java;

import main.java.GameActions;
import main.java.GameData;
import main.java.GameMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests if country are being removed properly or not
 *
 */
public class TestCountry {

    GameMap map;
    String countryName;
    GameData game;
    GameActions gameActions;


    /**
     * Set up the context
     */
    @Before
    public void before(){
        map = new GameMap("createdMap.map");
        countryName = "india";
        game = new GameData();
        gameActions = new GameActions();
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testCountry(){

        gameActions.editMap(game, "createdMap.map");
        boolean check = game.getMap().removeCountry(countryName);
        Assert.assertEquals(true,check);
    }
}
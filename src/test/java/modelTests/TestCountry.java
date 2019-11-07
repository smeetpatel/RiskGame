package test.java.modelTests;

import main.java.model.GameActions;
import main.java.model.GameData;
import main.java.model.GameMap;
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
     * Intialize the objects and parameters
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
     * This test method checks that given country is present in given graph or not for remove
     */
    @Test
    public void testCountry(){

        gameActions.editMap(game, "createdMap.map");
        boolean check = game.getMap().removeCountry(countryName);
        Assert.assertEquals(true,check);
    }
}
package test.java;

import main.java.GameActions;
import main.java.GameData;
import main.java.GameMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if continents are being removed properly or not
 *
 */
public class TestContinent {

    GameMap map;
    String continentName;
    int controlValue;
    GameActions gameActions;
    GameData game;

    /**
     * Set up the context
     * Initialize the objects and parameters
     */
    @Before
    public void before(){
        map = new GameMap("ameroki.map");
        continentName = "azio";
        gameActions = new GameActions();
        game = new GameData();
    }

    /**
     * Test if tests are rightly identified or not
     * This test method checks that given continent is present in given graph or not for remove
     */
    @Test
    public void testContinent(){

        gameActions.editMap(game, "ameroki.map");
        boolean check = game.getMap().removeContinent(continentName);
        Assert.assertEquals(true,check);
    }
}

package test.java.modelTests;

import main.java.model.GameActions;
import main.java.model.GameData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if continents are being removed properly or not
 *
 */
public class TestContinent {

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
     * Initialize the objects and parameters
     */
    @Before
    public void before(){
        gameActions = new GameActions();
        game = new GameData();
        gameActions.loadMap(game, "ameroki.map");
    }

    /**
     * Tests if continent is rightly removed or not
     */
    @Test
    public void testContinent1(){
        String continentName = "azio";
        game.getMap().removeContinent(continentName);
        Assert.assertFalse(game.getMap().getContinents().containsKey(continentName.toLowerCase()));
    }

    /**
     * Tests if continent is rightly added or not
     */
    @Test
    public void testContinent2(){
        String continentName = "newcontinent";
        game.getMap().addContinent(continentName, 9);
        Assert.assertTrue(game.getMap().getContinents().containsKey(continentName.toLowerCase()));
    }
}

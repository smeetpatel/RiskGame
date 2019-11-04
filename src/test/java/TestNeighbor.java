package test.java;

import main.java.GameActions;
import main.java.GameData;
import main.java.GameMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests if neighbors are being removed properly or not
 *
 */
public class TestNeighbor {

    GameMap map;
    String countryName;
    String neighborCountrynName;
    GameActions gameActions;
    GameData game;
    //MapEditor rcmd;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        map = new GameMap("createdMap.map");
        //rcmd = new MapEditor();
        countryName = "china";
        neighborCountrynName = "india";
        game = new GameData();
        gameActions = new GameActions();
    }

    /**
     * Test if tests are rightly identified or not
     * This test method checks that given country is present in given graph or not for remove
     */
    @Test
    public void testNeighbor(){

        gameActions.editMap(game, "createdMap.map");
        boolean check = game.getMap().removeNeighbor(countryName, neighborCountrynName);
        Assert.assertEquals(true,check);

    }
}

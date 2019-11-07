package test.java.modelTests;

import main.java.model.Country;
import main.java.model.GameActions;
import main.java.model.GameData;
import main.java.model.MapValidation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if connected continent are being rightly identified or not.
 *
 */
public class TestConnectedContinent {
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
     * Initialize the objects
     */
    @Before
    public void before(){
        mvr = new MapValidation();
        gameActions = new GameActions();
        game = new GameData();
    }

    /**
     * Tests if correctly identifies connected continents to be connected sub-graph or not.
     */
    @Test
    public void testConnectedContinent1(){
        gameActions.loadMap(game, "world.map");
        boolean check = mvr.continentConnectivityCheck(game.getMap());
        Assert.assertEquals(true,check);
    }

    /**
     * Tests if correctly identifies unconnected continents to be unconnected sub-graph or not.
     */
    @Test
    public void testConnectedContinent2(){
        gameActions.loadMap(game, "world.map");
        Country country = new Country("dummy", "Asia");
        game.getMap().getContinents().get("asia").getCountries().put("dummy", country);
        boolean check = mvr.continentConnectivityCheck(game.getMap());
        Assert.assertEquals(false,check);
    }
}
package test.java.modelTests;

import main.java.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests MapValidation class.
 */
public class TestMapValidation {

    /**
     * Represents the state of the game
     */
    GameData game;

    /**
     * To help load the map.
     */
    GameActions gameActions;

    /**
     * MapValidation object.
     */
    MapValidation mapValidation;

    /**
     * Represents player.
     */
    Player player;

    /**
     * Sets up the context for test.
     */
    @Before
    public void before(){
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();
        mapValidation = new MapValidation();
        //load the map
        gameActions.loadMap(game, "finaltest.map");

        //set up countries
        Country India = new Country("India", "Asia");
        Country China = new Country("China", "Asia");
        Country US = new Country("US", "NorthAmerica");

        //set up neighbors
        India.getNeighbours().put("china", China);
        China.getNeighbours().put("india", India);

        //set up player
        player = new HumanPlayer("Smeet");
        player.getOwnedCountries().put("india", India);
        player.getOwnedCountries().put("china", China);
        player.getOwnedCountries().put("us", US);
    }

    /**
     * Tests if all continents are connected sub-graphs or not
     */
    @Test
    public void testContinentConnectivityCheck(){
        Assert.assertTrue(mapValidation.continentConnectivityCheck(game.getMap()));
    }

    /**
     * Tests if there is a path of countries between argument fromCountry and argument toCountry such that all the countries in the path are owned by the argument player.
     */
    @Test
    public void testFortificationConnectivityCheck(){
        Assert.assertTrue(mapValidation.fortificationConnectivityCheck(this.player, "india", "china"));
        Assert.assertFalse(mapValidation.fortificationConnectivityCheck(this.player, "india", "us"));
    }
}

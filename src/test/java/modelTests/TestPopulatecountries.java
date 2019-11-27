package test.java.modelTests;

/**
 * Tests if countries are being populated properly or not
 *
 */

import java.util.ArrayList;

import main.java.model.GameActions;
import main.java.model.GameData;
import main.java.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPopulatecountries {

    /**
     * Represents the state of the game
     */
    GameData game;

    /**
     * To help load the map.
     */
    GameActions gameActions;

    /**
     * Player 1
     */
    Player player1;

    /**
     * Player 2
     */
    Player player2;

    /**
     * List of players in the game.
     */
    ArrayList<Player> players;

    /**
     * Set up the context
     */
    /*
    @Before
    public void before() {
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();

        //load the map
        gameActions.loadMap(game, "createdMap.map");

        //create players and them to the map
        players = new ArrayList<Player>();
        player1 = new Player("Smeet");
        player2 = new Player("Tirth");
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);
    }*/

    /**
     * Test if tests are rightly identified or not
     * This test method checks the number of countries player gets in populatecountry method
     */
    /*
    @Test
    public void testPopulateCountries() {
        boolean check = gameActions.populateCountries(game,players);
        Assert.assertEquals(2,player1.getOwnedCountries().size());
    }*/
}


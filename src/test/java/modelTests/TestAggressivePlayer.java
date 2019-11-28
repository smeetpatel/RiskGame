package test.java.modelTests;

import main.java.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests aggressive player.
 */
public class TestAggressivePlayer {

    /**
     * Represents the state of the game
     */
    GameData game;

    /**
     * To help load the map.
     */
    GameActions gameActions;

    /**
     * Represents player 1.
     */
    Player player1;

    /**
     * Represents player 2.
     */
    Player player2;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();

        //load the map
        gameActions.loadMap(game, "finaltest.map");

        //create player and add him/her to the game
        player1 = new AggressivePlayer("s");
        game.getPlayers().add(player1);
        player2 = new AggressivePlayer("t");
        game.getPlayers().add(player2);

        //assign countries and initial armies.
        player1.getOwnedCountries().put("india", game.getMap().getCountries().get("india"));
        player1.getOwnedCountries().put("china", game.getMap().getCountries().get("china"));
        player1.getOwnedCountries().get("india").setNumberOfArmies(9);
        player1.getOwnedCountries().get("china").setNumberOfArmies(8);

        player2.getOwnedCountries().put("us", game.getMap().getCountries().get("us"));
        player2.getOwnedCountries().put("aussie", game.getMap().getCountries().get("aussie"));
        player2.getOwnedCountries().get("us").setNumberOfArmies(8);
        player2.getOwnedCountries().get("aussie").setNumberOfArmies(9);
        player1.getOwnedCountries().get("india").getNeighbours().put("us", player2.getOwnedCountries().get("us"));
        player1.getOwnedCountries().get("india").getNeighbours().put("aussie", player2.getOwnedCountries().get("aussie"));
    }

    /**
     * Tests if reinforced correctly or not.
     */
    @Test
    public void testReinforce(){
        player1.reinforce(game, "", 0);
        Assert.assertEquals(12, player1.getOwnedCountries().get("india").getNumberOfArmies());
    }

    /**
     * Tests if aggressive player attack with strongest country or not.
     */
    @Test
    public void testAttack(){
        game.setActivePlayer(player1);
        Country country = ((AggressivePlayer) player1).findStrongestCountry();
        Assert.assertEquals("india", country.getCountryName().toLowerCase());
    }

    /**
     * Tests if fortifies correctly or not.
     */
    @Test
    public void testFortify(){
        game.setActivePlayer(player1);
        FortificationCheck fortificationCheck = player1.fortify(game, "", "", 0);
        Assert.assertEquals(16, player1.getOwnedCountries().get("india").getNumberOfArmies());
    }


}

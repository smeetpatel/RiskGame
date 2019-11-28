package test.java.modelTests;

import main.java.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests cheater player.
 */
public class TestCheaterPlayer {

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
        player1 = new CheaterPlayer("s");
        game.getPlayers().add(player1);
        player2 = new CheaterPlayer("t");
        game.getPlayers().add(player2);

        //assign countries and initial armies.
        player1.getOwnedCountries().put("india", game.getMap().getCountries().get("india"));
        player1.getOwnedCountries().put("china", game.getMap().getCountries().get("china"));
        player1.getOwnedCountries().put("nz", game.getMap().getCountries().get("nz"));
        player1.getOwnedCountries().put("canada", game.getMap().getCountries().get("canada"));
        player1.getOwnedCountries().get("india").setNumberOfArmies(9);
        player1.getOwnedCountries().get("china").setNumberOfArmies(8);
        player1.getOwnedCountries().get("nz").setNumberOfArmies(5);
        player1.getOwnedCountries().get("canada").setNumberOfArmies(5);
        player1.getOwnedCountries().get("india").setOwnerPlayer(player1);
        player1.getOwnedCountries().get("china").setOwnerPlayer(player1);
        player1.getOwnedCountries().get("nz").setOwnerPlayer(player1);
        player1.getOwnedCountries().get("canada").setOwnerPlayer(player1);

        player2.getOwnedCountries().put("us", game.getMap().getCountries().get("us"));
        player2.getOwnedCountries().put("aussie", game.getMap().getCountries().get("aussie"));
        player2.getOwnedCountries().get("us").setNumberOfArmies(8);
        player2.getOwnedCountries().get("aussie").setNumberOfArmies(9);
        player2.getOwnedCountries().get("us").getNeighbours().put("us", player2.getOwnedCountries().get("us"));
        player2.getOwnedCountries().get("aussie").getNeighbours().put("aussie", player2.getOwnedCountries().get("aussie"));
        player2.getOwnedCountries().get("us").setOwnerPlayer(player2);
        player2.getOwnedCountries().get("aussie").setOwnerPlayer(player2);
    }

    /**
     * Tests if reinforced correctly or not.
     */
    @Test
    public void testReinforce(){
        player1.reinforce(game, "", 0);
        Assert.assertEquals(18, player1.getOwnedCountries().get("india").getNumberOfArmies());
        Assert.assertEquals(16, player1.getOwnedCountries().get("china").getNumberOfArmies());
    }

    /**
     * Tests if cheater player get all opposing neighboring countries or not.
     */
    @Test
    public void testAttack(){
        player1.attack(game, "", "", 0, 0, null);
        Assert.assertTrue(player1.getOwnedCountries().containsKey("us"));
        Assert.assertTrue(player1.getOwnedCountries().containsKey("aussie"));
    }

    /**
     * Tests if fortifies correctly or not.
     */
    @Test
    public void testFortify(){
        game.setActivePlayer(player1);
        FortificationCheck fortificationCheck = player1.fortify(game, "", "", 0);
        Assert.assertEquals(18, player1.getOwnedCountries().get("india").getNumberOfArmies());
        Assert.assertEquals(8, player1.getOwnedCountries().get("china").getNumberOfArmies());
        Assert.assertEquals(10, player1.getOwnedCountries().get("nz").getNumberOfArmies());
        Assert.assertEquals(10, player1.getOwnedCountries().get("canada").getNumberOfArmies());
    }
}

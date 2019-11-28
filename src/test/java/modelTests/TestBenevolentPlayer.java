package test.java.modelTests;

import main.java.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests benevolent player.
 */
public class TestBenevolentPlayer {
    /**
     * Represents the state of the game
     */
    GameData game;

    /**
     * To help load the map.
     */
    GameActions gameActions;

    /**
     * Represents a player.
     */
    Player player;

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
        player = new BenevolentPlayer("s");
        game.getPlayers().add(player);

        //assign countries and initial armies.
        player.getOwnedCountries().put("india", game.getMap().getCountries().get("india"));
        player.getOwnedCountries().put("china", game.getMap().getCountries().get("china"));
        player.getOwnedCountries().get("india").setNumberOfArmies(8);
        player.getOwnedCountries().get("china").setNumberOfArmies(9);
    }

    /**
     * Tests if reinforced correctly or not.
     */
    @Test
    public void testReinforce(){
        player.reinforce(game, "", 0);
        Assert.assertEquals(11, player.getOwnedCountries().get("india").getNumberOfArmies());

        player.reinforce(game, "", 0);
        Assert.assertEquals(12, player.getOwnedCountries().get("china").getNumberOfArmies());

        player.reinforce(game, "", 0);
        Assert.assertEquals(14, player.getOwnedCountries().get("india").getNumberOfArmies());
    }

    /**
     * Tests if benevolent player doesn't attack or not.
     */
    @Test
    public void testAttack(){
        game.setActivePlayer(player);
        player.attack(game, "", "", 0, 0, null);
        Assert.assertEquals(Phase.FORTIFICATION, game.getGamePhase());
    }

    /**
     * Tests if fortifies correctly or not.
     */
    @Test
    public void testFortify(){
        game.setActivePlayer(player);
        FortificationCheck fortificationCheck = player.fortify(game, "", "", 0);
        Assert.assertEquals(FortificationCheck.FORTIFICATIONSUCCESS, fortificationCheck);
        Assert.assertEquals(16, player.getOwnedCountries().get("india").getNumberOfArmies());
    }
}
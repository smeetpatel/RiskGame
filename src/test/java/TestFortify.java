package test.java;

import main.java.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TestFortify {

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
     * Set up the context
     * Initialize the objects and parameters
     */
    @Before
    public void before(){
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();

        //load the map
        gameActions.loadMap(game, "testmapthird.map");

        //create players and them to the map
        ArrayList<Player> players = new ArrayList<Player>();
        player1 = new Player("Smeet");
        player2 = new Player("Tirth");
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);

        //set correct phase for calling fortify method
        game.setGamePhase(Phase.FORTIFICATION);

        //assign countries between players
        player1.getOwnedCountries().put("india", game.getMap().getCountries().get("india"));
        player1.getOwnedCountries().put("china", game.getMap().getCountries().get("china"));
        player1.getOwnedCountries().put("canada", game.getMap().getCountries().get("canada"));
        player1.getOwnedCountries().put("nz", game.getMap().getCountries().get("nz"));
        player2.getOwnedCountries().put("aussie", game.getMap().getCountries().get("aussie"));

        //assign number of armies in each country
        game.getMap().getCountries().get("india").setNumberOfArmies(9);
        game.getMap().getCountries().get("china").setNumberOfArmies(3);
        game.getMap().getCountries().get("canada").setNumberOfArmies(1);
    }

    /**
     * Tests if correctly identifies that player does not own origin country for fortification or not.
     */
    @Test
    public void testFortify1(){
        int numberOfArmies = 1;
        FortificationCheck fortificationCheck = player1.fortify(game, "usa", "china", numberOfArmies);
        Assert.assertEquals(fortificationCheck.FROMCOUNTRYFAIL, fortificationCheck);
    }

    /**
     * Tests if correctly identifies that player does not own destination country for fortification or not.
     */
    @Test
    public void testFortify2(){
        int numberOfArmies = 1;
        FortificationCheck fortificationCheck = player1.fortify(game, "india", "usa", numberOfArmies);
        Assert.assertEquals(fortificationCheck.TOCOUNTRYFAIL, fortificationCheck);
    }

    /**
     * Tests if correctly identifies that player does not own have enough armies for reinforcement or not.
     */
    @Test
    public void testFortify3(){
        int numberOfArmies = 9;
        FortificationCheck fortificationCheck = player1.fortify(game, "india", "china", numberOfArmies);
        Assert.assertEquals(fortificationCheck.ARMYCOUNTFAIL, fortificationCheck);
        numberOfArmies = 1;
        fortificationCheck = player1.fortify(game, "canada", "india", numberOfArmies);
        Assert.assertEquals(fortificationCheck.ARMYCOUNTFAIL, fortificationCheck);
    }

    /**
     * Tests if correctly identifies that there is no path between origin and destination country made of armies owned by player.
     */
    @Test
    public void testFortify4(){
        int numberOfArmies = 1;
        FortificationCheck fortificationCheck = player1.fortify(game, "india", "nz", numberOfArmies);
        Assert.assertEquals(fortificationCheck.PATHFAIL, fortificationCheck);
    }

    /**
     * Tests if correctly identifies successful fortification move or not.
     */
    @Test
    public void testFortify5(){
        int numberOfArmies = 1;
        FortificationCheck fortificationCheck = player1.fortify(game, "india", "canada", numberOfArmies);
        Assert.assertEquals(fortificationCheck.FORTIFICATIONSUCCESS, fortificationCheck);
    }
}

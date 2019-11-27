package test.java.modelTests;

import main.java.model.GameActions;
import main.java.model.GameData;
import main.java.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Test class for reinforcement phase of the game
 */
public class TestReinforcement {

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
     * Set up the context for the test
     * initialize the objects
     */
    /*
    @Before
    public void before(){
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();

        //load the map
        gameActions.loadMap(game, "testmapthird.map");

        //create players and them to the map
        players = new ArrayList<Player>();
        player1 = new Player("Smeet");
        player2 = new Player("Tirth");
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);

        //populate countries
        gameActions.populateCountries(game, players);

        //place all armies
        gameActions.placeAll(game);
    }*/

    /**
     * This test method checks that reinforcement operation occures successfully or not
     */
    /*
    @Test
    public void testReinforcement1(){
        gameActions.populateCountries(game,players);
        boolean check = player1.reinforce(game,"india",5);
        Assert.assertEquals(true,check);
    }*/

    /**
     * This test method checks that country specified in reinforcement command is owned by that player or not for successful
     * reinforcement
     */
    /*
    @Test
    public void testReinforcement2(){
        gameActions.editMap(game,"createdMap.map");
        gameActions.populateCountries(game,players);
        boolean check = player1.reinforce(game,"USA",5);
        Assert.assertEquals(true,check);
    }*/
}

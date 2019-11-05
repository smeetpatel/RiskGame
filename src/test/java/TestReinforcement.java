package test.java;

import main.java.GameActions;
import main.java.GameData;
import main.java.GameMap;
import main.java.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Test class for reinforcement phase of the game
 */
public class TestReinforcement {

    GameData game;
    Player player1;
    Player player2;
    GameMap map;
    GameActions gameActions;
    ArrayList<Player> players;

    /**
     * Set up the context for the test
     * initialize the objects
     */
    @Before
    public void before(){
        player1 = new Player("tirth");
        player2 = new Player("smeet");
        map = new GameMap("createdMap.map");
        game = new GameData();
        players = new ArrayList<Player>();
        gameActions = new GameActions();
        players.add(player1);
        players.add(player2);

    }

    /**
     * This test method checks that reinforcement operation occures successfully or not
     */
    @Test
    public void testReinforcement1(){
        gameActions.editMap(game,"createdMap.map");
        gameActions.populateCountries(game,players);
        System.out.println(player1.getOwnedArmies());
        System.out.println(player1.getOwnedCountries().size());
        boolean check = player1.reinforce(game,"india",5);
        System.out.println(player1.getOwnedArmies());
        Assert.assertEquals(true,check);
    }

    /**
     * This test method checks that country specified in reinforcement command owend by that player or not for successful
     * reinforcement
     */
    @Test
    public void testReinforcement2(){
        gameActions.editMap(game,"createdMap.map");
        gameActions.populateCountries(game,players);
        System.out.println(player1.getOwnedArmies());
        System.out.println(player1.getOwnedCountries().size());
        boolean check = player1.reinforce(game,"USA",5);
        System.out.println(player1.getOwnedArmies());
        Assert.assertEquals(false,check);
    }
}

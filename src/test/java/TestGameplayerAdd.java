package test.java;

import static  org.junit.Assert.*;

import main.java.GameActions;
import main.java.GameData;
import main.java.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests if adding game player works correctly or not.
 *
 */
public class TestGameplayerAdd {

    /**
     * List of players
     */
    ArrayList<Player> players;

    /**
     * Name of a player to be added
     */
    String playerName;

    /**
     * Object of GameAction class to access it's methods
     */
    GameActions gameActions;

    /**
     * Set up the context
     */
    @Before
    public void before(){

        players = new ArrayList<Player>();
        playerName = "Tirth";
        gameActions = new GameActions();
    }

    /**
     * Test if tests are rightly identified or not
     * Test whether player successfully added or not
     */
    @Test
    public void testGameplayerAdd(){

        boolean check = gameActions.addPlayer(players, playerName);
        System.out.println(players.get(0).getPlayerName());
        Assert.assertEquals(players.get(0).getPlayerName(), playerName);
    }
}

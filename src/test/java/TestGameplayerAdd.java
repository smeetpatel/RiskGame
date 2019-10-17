package test.java;

import static  org.junit.Assert.*;

import main.java.Player;
import main.java.StartUp;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests if adding game player works correctly or not.
 *
 */
public class TestGameplayerAdd {

    StartUp stp;
    ArrayList<Player> players;
    String playerName;
    
    /**
     * Set up the context
     */
    @Before
    public void before(){
        stp = new StartUp();
        players = new ArrayList<Player>();
        playerName = "Tirth";
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testGameplayerAdd(){
        boolean check = stp.addPlayer(players, playerName);
        System.out.println(players.get(0).getPlayerName());
        assertEquals(players.get(0).getPlayerName(), playerName);
    }
}

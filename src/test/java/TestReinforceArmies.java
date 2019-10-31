package test.java;

import static org.junit.Assert.*;

import main.java.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests if reinforcement armies are being calculated properly or not.
 *
 */
public class TestReinforceArmies {

    Player player1;
    Player player2;
    GameMap map;
    ArrayList<Player> players;
    StartUp stp;
    Phase gamePhase;
    Command cmd;
    Reinforcement rfc;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        player1 = new Player("tirth");
        player2 = new Player("smeet");
        map = new GameMap("world.map");
        players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);
        gamePhase = Phase.STARTUP;

    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testReinforceArmies(){
        stp = new StartUp();
        cmd = new Command();
        boolean checkagain = false;
       // boolean check = stp.populateCountries(map, players);
        /*if(check){
            checkagain = Reinforcement.assignReinforcementArmies(player1);
        }

        assertEquals(true,checkagain);*/
    }
}


package test.java;

import static org.junit.Assert.*;

import main.java.*;
import org.junit.Assert;
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
    //StartUp stp;
    Phase gamePhase;
    Command cmd;
    GameActions gameActions;
    GameData game;
    //Reinforcement rfc;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        player1 = new Player("tirth");
        player2 = new Player("smeet");
        map = new GameMap("ameroki.map");
        players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);
        gamePhase = Phase.STARTUP;
        gameActions = new GameActions();
        game = new GameData();
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testReinforceArmies(){
        cmd = new Command();
        boolean checkagain,check;
        gameActions.editMap(game, "ameroki.map");
        check = gameActions.populateCountries(game,players);
        checkagain = gameActions.assignReinforcementArmies(game, player2);
        int totalReinforcementArmies = (int) (player2.getOwnedCountries().size() / 3);
        assertEquals(totalReinforcementArmies,player2.getOwnedArmies());
    }
}


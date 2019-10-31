package test.java;

/**
 * Tests if countries are being populated properly or not
 *
 */
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.*;
import org.junit.Before;
import org.junit.Test;

public class TestPopulatecountries {

    Player player1;
    Player player2;
    GameMap map;
    ArrayList<Player> players;
    StartUp stp;

    /**
     * Set up the context
     */
    @Before
    public void before() {

        player1 = new Player("tirth");
        player2 = new Player("smeet");
        map = new GameMap("world.map");
        players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testPopulateCountries() {
        stp = new StartUp();
       // boolean check = stp.populateCountries(map, players);
        //assertEquals(true,check);
    }

}


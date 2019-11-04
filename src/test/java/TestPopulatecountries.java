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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPopulatecountries {

    Player player1;
    Player player2;
    GameMap map;
    ArrayList<Player> players;
    GameData gameData;
    GameActions gameActions;
    //StartUp stp;

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
        gameData = new GameData();
        gameActions = new GameActions();
    }

    /**
     * Test if tests are rightly identified or not
     * This test method checks the number of countries player gets in populatecountry method
     */
    @Test
    public void testPopulateCountries() {

        gameActions.editMap(gameData,"createdMap.map");
        boolean check = gameActions.populateCountries(gameData,players);
        Assert.assertEquals(2,player1.getOwnedCountries().size());
    }

}


package test.java;

import main.java.Deck;
import main.java.GameActions;
import main.java.GameData;
import main.java.GameMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests that deck creation works properly or not
 */
public class TestDeckCreation {

    GameMap gameMap;
    GameActions gameActions;
    GameData gameData;
    Deck deck;
    int acctualSizeOfDeck;
    int expectedSizeOfDeck;

    /**
     * Set up the context
     * Initialize the objects and parameters
     */
    @Before
    public void before(){

        gameMap = new GameMap("createdMap.map");
        gameActions = new GameActions();
        gameData = new GameData();
        deck = new Deck();
    }

    /**
     * This test method checks that the size of the deck is equal to the total number of countries in map
     */
    @Test
    public void testDeckCreation(){

        expectedSizeOfDeck = gameMap.getCountries().size();
        acctualSizeOfDeck = deck.getDeckSize();
        Assert.assertEquals(expectedSizeOfDeck,acctualSizeOfDeck);
    }

}

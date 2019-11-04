package test.java;

import main.java.Deck;
import main.java.GameActions;
import main.java.GameData;
import main.java.GameMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestDeckCreation {

    GameMap gameMap;
    GameActions gameActions;
    GameData gameData;
    Deck deck;
    int acctualSizeOfDeck;
    int expectedSizeOfDeck;

    @Before
    public void before(){

        gameMap = new GameMap("createdMap.map");
        gameActions = new GameActions();
        gameData = new GameData();
        deck = new Deck();
    }

    @Test
    public void testDeckCreation(){

        expectedSizeOfDeck = gameMap.getCountries().size();
        acctualSizeOfDeck = deck.getDeckSize();
        Assert.assertEquals(expectedSizeOfDeck,acctualSizeOfDeck);
    }

}

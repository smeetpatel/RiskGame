package test.java.modelTests;

import main.java.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Tests GameActions class.
 */
public class TestGameActions {

    /**
     * Represents the state of the game
     */
    GameData game;

    /**
     * To help load the map.
     */
    GameActions gameActions;

    /**
     * MapValidation object.
     */
    MapValidation mapValidation;

    /**
     * List of players in the game.
     */
    ArrayList<Player> players;

    /**
     * Player 1
     */
    Player player1;

    /**
     * Player 2
     */
    Player player2;

    /**
     * Card 1
     */
    Card c1;

    /**
     * Card 2
     */
    Card c2;

    /**
     * Card 3
     */
    Card c3;

    /**
     * Card 4
     */
    Card c4;

    /**
     * Card 5
     */
    Card c5;

    /**
     * Card 6
     */
    Card c6;

    /**
     * Sets up the context for test.
     */
    @Before
    public void before(){
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();
        players = new ArrayList<Player>();

        //load the map
        gameActions.loadMap(game, "finaltest.map");

        //create players and them to the map
        player1 = new HumanPlayer("s");
        player2 = new HumanPlayer("t");
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);
        game.setGamePhase(Phase.CARDEXCHANGE);

        //assign countries between players
        player1.getOwnedCountries().put("india", game.getMap().getCountries().get("india"));
        player1.getOwnedCountries().put("china", game.getMap().getCountries().get("china"));
        player1.getOwnedCountries().put("canada", game.getMap().getCountries().get("canada"));
        player2.getOwnedCountries().put("aussie", game.getMap().getCountries().get("aussie"));

        //create cards
        c1 = new Card("Infantry", game.getMap().getCountries().get("india"));
        c2 = new Card("Infantry", game.getMap().getCountries().get("russia"));
        c3 = new Card("Artillery", game.getMap().getCountries().get("aussie"));
        c4 = new Card("Infantry", game.getMap().getCountries().get("us"));
        c5 = new Card("Cavalary", game.getMap().getCountries().get("nz"));
        c6 = new Card("WildCard", null);

        //distribute cards
        player1.setOwnedCards(c1);
        player1.setOwnedCards(c2);
        player1.setOwnedCards(c3);
        player1.setOwnedCards(c4);
        player1.setOwnedCards(c5);
        player1.setOwnedCards(c6);
    }

    /**
     * Tests various validity checks to ensure that map is suitable for playing the game.
     */
    @Test
    public void testValidateMap(){
        MapValidityStatus mapValidityStatus = gameActions.validateMap(game.getMap());
        Assert.assertEquals(MapValidityStatus.VALIDMAP, mapValidityStatus);
    }

    /**
     * Tests various validity checks to ensure that map is suitable for playing the game.
     */
    @Test
    public void testValidateMap2(){
        game.getMap().addContinent("new", 9);
        MapValidityStatus mapValidityStatus = gameActions.validateMap(game.getMap());
        Assert.assertEquals(MapValidityStatus.EMPTYCONTINENT, mapValidityStatus);
    }

    /**
     * Tests various validity checks to ensure that map is suitable for playing the game.
     */
    @Test
    public void testValidateMap3(){
        game.getMap().addContinent("new", 9);
        game.getMap().addCountry("newCountry", "new");
        MapValidityStatus mapValidityStatus = gameActions.validateMap(game.getMap());
        Assert.assertEquals(MapValidityStatus.UNCONNECTEDGRAPH, mapValidityStatus);
    }

    /**
     * Tests if correctly calculates reinforcement armies or not.
     */
    @Test
    public void testAssignReinforcementArmies(){
        gameActions.assignReinforcementArmies(game, player1);
        Assert.assertEquals(3,player1.getOwnedArmies());

        Country c1 = new Country();
        Country c2 = new Country();
        Country c3 = new Country();
        Country c4 = new Country();
        Country c5 = new Country();
        Country c6 = new Country();

        player1.getOwnedCountries().put("usa", game.getMap().getCountries().get("usa"));
        player1.getOwnedCountries().put("russia", game.getMap().getCountries().get("russia"));
        player1.getOwnedCountries().put("nz", game.getMap().getCountries().get("nz"));
        player1.getOwnedCountries().put("a", c1);
        player1.getOwnedCountries().put("aa", c2);
        player1.getOwnedCountries().put("aaa", c3);
        player1.getOwnedCountries().put("aaaa", c4);
        player1.getOwnedCountries().put("aaaaaa", c5);
        player1.getOwnedCountries().put("aaaaaaa", c6);
        player1.setOwnedArmies(0);

        gameActions.assignReinforcementArmies(game, player1);
        Assert.assertEquals(4,player1.getOwnedArmies());
    }

    /**
     * Tests if extra armies are given or not based on the ownership of the card.
     */
    @Test
    public void testReinforceArmies3(){
        ArrayList<Integer> cardIndex = new ArrayList<Integer>();
        cardIndex.add(5);
        cardIndex.add(3);
        cardIndex.add(1);
        Collections.sort(cardIndex);
        player1.cardExchange(game, cardIndex);
        Assert.assertEquals(7, player1.getOwnedArmies());
    }

    /**
     * Tests if card exchange occurs correctly or not.
     */
    @Test
    public void testCardExchange(){
        ArrayList<Integer> cardIndex = new ArrayList<Integer>();
        cardIndex.add(5);
        cardIndex.add(3);
        cardIndex.add(2);
        Collections.sort(cardIndex);
        player1.cardExchange(game, cardIndex);
        Assert.assertEquals(5, player1.getOwnedArmies());
    }

    /**
     * Tests if invalid card combination is detected or not.
     */
    @Test
    public void testCardExchange2(){
        ArrayList<Integer> cardIndex = new ArrayList<Integer>();
        cardIndex.add(5);
        cardIndex.add(1);
        cardIndex.add(2);
        Collections.sort(cardIndex);
        boolean check = player1.cardExchange(game, cardIndex);
        Assert.assertEquals(false, check);
    }

    /**
     * Test if wildcard combination is correctly detected or not.
     */
    @Test
    public void testCardExchange3(){
        ArrayList<Integer> cardIndex = new ArrayList<Integer>();
        cardIndex.add(6);
        cardIndex.add(1);
        cardIndex.add(2);
        Collections.sort(cardIndex);
        boolean check = player1.cardExchange(game, cardIndex);
        Assert.assertEquals(true, check);
    }

    /**
     * Test if invalid card indexes are detected or not.
     */
    @Test
    public void testCardExchange4(){
        ArrayList<Integer> cardIndex = new ArrayList<Integer>();
        cardIndex.add(7);
        cardIndex.add(1);
        cardIndex.add(2);
        Collections.sort(cardIndex);
        boolean check = player1.cardExchange(game, cardIndex);
        Assert.assertEquals(false, check);
    }

    /**
     * Test if invalid card indexes are detected or not.
     */
    @Test
    public void testCardExchange5(){
        ArrayList<Integer> cardIndex = new ArrayList<Integer>();
        cardIndex.add(5);
        cardIndex.add(1);
        cardIndex.add(6);
        Collections.sort(cardIndex);
        boolean check = player1.cardExchange(game, cardIndex);
        Assert.assertEquals(true, check);
    }
}
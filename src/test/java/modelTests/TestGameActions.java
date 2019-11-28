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
     * Tests if number of dice are valid for attacker/defender
     */
    @Test
    public void testDiceValid(){
        game.getMap().getCountries().get("india").setNumberOfArmies(3);
        boolean check = gameActions.diceValid(game, "india", 3, true);
        Assert.assertEquals(false, check);

        game.getMap().getCountries().get("india").setNumberOfArmies(3);
        check = gameActions.diceValid(game, "india", 2, true);
        Assert.assertEquals(true, check);

        game.getMap().getCountries().get("india").setNumberOfArmies(3);
        check = gameActions.diceValid(game, "india", 0, true);
        Assert.assertEquals(false, check);

        game.getMap().getCountries().get("india").setNumberOfArmies(3);
        check = gameActions.diceValid(game, "india", 3, false);
        Assert.assertEquals(false, check);

        game.getMap().getCountries().get("india").setNumberOfArmies(2);
        check = gameActions.diceValid(game, "india", 2, false);
        Assert.assertEquals(true, check);

        game.getMap().getCountries().get("india").setNumberOfArmies(2);
        check = gameActions.diceValid(game, "india", 0, false);
        Assert.assertEquals(false, check);
    }

    /**
     * Tests if valid attack or not by comparing the neighborhood of the countries.
     */
    @Test
    public void testAreNeighbor(){
        gameActions.loadMap(game, "testmapthird.map");
        player1 = new HumanPlayer("Smeet");
        player2 = new HumanPlayer("Tirth");
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);
        game.setGamePhase(Phase.CARDEXCHANGE);

        player1.getOwnedCountries().put("india", game.getMap().getCountries().get("india"));
        player1.getOwnedCountries().put("china", game.getMap().getCountries().get("china"));
        player1.getOwnedCountries().put("canada", game.getMap().getCountries().get("canada"));
        player1.getOwnedCountries().put("aussie", game.getMap().getCountries().get("aussie"));
        player2.getOwnedCountries().put("usa", game.getMap().getCountries().get("usa"));
        player2.getOwnedCountries().put("russia", game.getMap().getCountries().get("russia"));
        player2.getOwnedCountries().put("nz", game.getMap().getCountries().get("nz"));

        c1 = new Card("Infantry", game.getMap().getCountries().get("india"));
        c2 = new Card("Infantry", game.getMap().getCountries().get("russia"));
        c3 = new Card("Artillery", game.getMap().getCountries().get("aussie"));
        c4 = new Card("Infantry", game.getMap().getCountries().get("usa"));
        c5 = new Card("Cavalary", game.getMap().getCountries().get("nz"));
        c6 = new Card("WildCard", null);

        player1.setOwnedCards(c1);
        player1.setOwnedCards(c2);
        player1.setOwnedCards(c3);
        player1.setOwnedCards(c4);
        player1.setOwnedCards(c5);
        player1.setOwnedCards(c6);

        boolean check = gameActions.areNeighbors(game, player1, "india", "china");
        Assert.assertEquals(false, check);

        check = gameActions.areNeighbors(game, player1, "usa", "aussie");
        Assert.assertEquals(false, check);

        check = gameActions.areNeighbors(game, player1, "india", "nz");
        Assert.assertEquals(false, check);

        check = gameActions.areNeighbors(game, player1, "china", "russia");
        Assert.assertEquals(true, check);
    }

    /**
     * Tests if correctly identifies invalid map file or not.
     */
    @Test
    public void testLoadMap1(){
        gameActions.loadMap(game, "invalid.map");
        Assert.assertFalse(game.getMap().getValid());
        Assert.assertEquals(Phase.NULL, game.getGamePhase());
    }

    /**
     * Tests if correctly identifies valid map file or not.
     */
    @Test
    public void testLoadMap2(){
        gameActions.loadMap(game, "testmapthird.map");
        Assert.assertTrue(game.getMap().getValid());
        Assert.assertEquals(Phase.STARTUP, game.getGamePhase());
    }
}
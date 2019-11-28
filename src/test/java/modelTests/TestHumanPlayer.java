package test.java.modelTests;

import main.java.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Tests actions of Human player.
 */
public class TestHumanPlayer {

    /**
     * Represents the state of the game
     */
    GameData game;

    /**
     * To help load the map.
     */
    GameActions gameActions;

    /**
     * Player 1
     */
    Player player1;

    /**
     * Player 2
     */
    Player player2;

    /**
     * List of players in the game.
     */
    ArrayList<Player> players;

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
     * Set up the context
     */
    @Before
    public void before(){
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();

        //load the map
        gameActions.loadMap(game, "testmapthird.map");

        //create players and them to the map
        players = new ArrayList<Player>();
        player1 = new HumanPlayer("Smeet");
        player2 = new HumanPlayer("Tirth");
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);

        //set correct phase for calling fortify method
        game.setGamePhase(Phase.CARDEXCHANGE);

        //assign countries between players
        player1.getOwnedCountries().put("india", game.getMap().getCountries().get("india"));
        player1.getOwnedCountries().put("china", game.getMap().getCountries().get("china"));
        player1.getOwnedCountries().put("canada", game.getMap().getCountries().get("canada"));
        player1.getOwnedCountries().put("aussie", game.getMap().getCountries().get("aussie"));
        player2.getOwnedCountries().put("usa", game.getMap().getCountries().get("usa"));
        player2.getOwnedCountries().put("russia", game.getMap().getCountries().get("russia"));
        player2.getOwnedCountries().put("nz", game.getMap().getCountries().get("nz"));

        //create cards
        c1 = new Card("Infantry", game.getMap().getCountries().get("india"));
        c2 = new Card("Infantry", game.getMap().getCountries().get("russia"));
        c3 = new Card("Artillery", game.getMap().getCountries().get("aussie"));
        c4 = new Card("Infantry", game.getMap().getCountries().get("usa"));
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
     * Tests if valid move after conquering.
     */
    @Test
    public void testMoveArmy(){
        int numberOfDice = 3;
        int numberOfArmies = 4;
        player1.getOwnedCountries().put("usa", game.getMap().getCountries().get("usa"));
        player2.getOwnedCountries().remove("usa");
        game.getMap().getCountries().get("india").setNumberOfArmies(5);
        game.getMap().getCountries().get("usa").setNumberOfArmies(0);
        boolean check = player1.moveArmy(game, "india", "usa", numberOfDice, numberOfArmies);
        Assert.assertTrue(check);

        numberOfDice = 3;
        numberOfArmies = 2;
        game.getMap().getCountries().get("india").setNumberOfArmies(5);
        game.getMap().getCountries().get("usa").setNumberOfArmies(0);
        check = player1.moveArmy(game, "india", "usa", numberOfDice, numberOfArmies);
        Assert.assertFalse(check);

        numberOfDice = 3;
        numberOfArmies = 3;
        game.getMap().getCountries().get("india").setNumberOfArmies(5);
        game.getMap().getCountries().get("usa").setNumberOfArmies(0);
        check = player1.moveArmy(game, "india", "usa", numberOfDice, numberOfArmies);
        Assert.assertTrue(check);
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
        Assert.assertEquals(7, player1.getOwnedArmies());
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

    /**
     * Tests if correctly identifies that player does not own origin country for fortification or not.
     */

    @Test
    public void testFortify1(){
        int numberOfArmies = 1;
        FortificationCheck fortificationCheck = player1.fortify(game, "usa", "china", numberOfArmies);
        Assert.assertEquals(fortificationCheck.FROMCOUNTRYFAIL, fortificationCheck);
    }

    /**
     * Tests if correctly identifies that player does not own destination country for fortification or not.
     */
    @Test
    public void testFortify2(){
        int numberOfArmies = 1;
        FortificationCheck fortificationCheck = player1.fortify(game, "india", "usa", numberOfArmies);
        Assert.assertEquals(fortificationCheck.TOCOUNTRYFAIL, fortificationCheck);
    }

    /**
     * Tests if correctly identifies that player does not own have enough armies for reinforcement or not.
     */
    @Test
    public void testFortify3(){
        int numberOfArmies = 9;
        FortificationCheck fortificationCheck = player1.fortify(game, "india", "china", numberOfArmies);
        Assert.assertEquals(fortificationCheck.ARMYCOUNTFAIL, fortificationCheck);
        numberOfArmies = 1;
        fortificationCheck = player1.fortify(game, "canada", "india", numberOfArmies);
        Assert.assertEquals(fortificationCheck.ARMYCOUNTFAIL, fortificationCheck);
    }

    /**
     * Tests if correctly identifies that there is no path between origin and destination country made of armies owned by player.
     */
    @Test
    public void testFortify4(){
        players.clear();
        player1 = new HumanPlayer("s");
        player2 = new HumanPlayer("t");
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);

        //set correct phase for calling fortify method
        game.setActivePlayer(player1);
        game.setGamePhase(Phase.FORTIFICATION);
        player1.getOwnedCountries().put("india", game.getMap().getCountries().get("india"));
        player1.getOwnedCountries().put("china", game.getMap().getCountries().get("china"));
        player1.getOwnedCountries().put("canada", game.getMap().getCountries().get("canada"));
        player1.getOwnedCountries().put("nz", game.getMap().getCountries().get("nz"));
        player2.getOwnedCountries().put("aussie", game.getMap().getCountries().get("aussie"));

        game.getMap().getCountries().get("india").setNumberOfArmies(9);
        game.getMap().getCountries().get("china").setNumberOfArmies(3);
        game.getMap().getCountries().get("canada").setNumberOfArmies(1);

        int numberOfArmies = 1;
        FortificationCheck fortificationCheck = player1.fortify(game, "india", "nz", numberOfArmies);
        Assert.assertEquals(fortificationCheck.PATHFAIL, fortificationCheck);
    }

    /**
     * Tests if correctly identifies successful fortification move or not.
     */
    @Test
    public void testFortify5(){
        player1.getOwnedCountries().put("india", game.getMap().getCountries().get("india"));
        player1.getOwnedCountries().put("china", game.getMap().getCountries().get("china"));
        player1.getOwnedCountries().put("canada", game.getMap().getCountries().get("canada"));
        player1.getOwnedCountries().put("nz", game.getMap().getCountries().get("nz"));
        player2.getOwnedCountries().put("aussie", game.getMap().getCountries().get("aussie"));

        game.getMap().getCountries().get("india").setNumberOfArmies(9);
        game.getMap().getCountries().get("china").setNumberOfArmies(3);
        game.getMap().getCountries().get("canada").setNumberOfArmies(1);

        int numberOfArmies = 1;
        FortificationCheck fortificationCheck = player1.fortify(game, "india", "canada", numberOfArmies);
        Assert.assertEquals(fortificationCheck.FORTIFICATIONSUCCESS, fortificationCheck);
    }
}
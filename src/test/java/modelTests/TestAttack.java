package test.java.modelTests;

import main.java.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestAttack {

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
     * Card 5
     */
    Card c6;

    /**
     * Set up the context
     */
    /*@Before
    public void before(){
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();

        //load the map
        gameActions.loadMap(game, "testmapthird.map");

        //create players and them to the map
        players = new ArrayList<Player>();
        player1 = new Player("Smeet");
        player2 = new Player("Tirth");
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
    }*/

    /**
     * Tests if number of dice are valid for attacker/defender
     */
    /*@Test
    public void testAttack1(){
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
    }*/

    /**
     * Tests if valid attack or not.
     */
    /*
    @Test
    public void testAttack2(){
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
     * Tests if valid move after conquering.
     */
    /*
    @Test
    public void testAttack3(){
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
    }*/
}

package test.java.controllerTests;

import main.java.controller.Controller;
import main.java.controller.StartUpController;
import main.java.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests if a valid start up phase takes place or not.
 */
public class TestStartUpController {
    /**
     * Represents the state of the game
     */
    GameData game;

    /**
     * To help load the map.
     */
    GameActions gameActions;

    /**
     * Refers to controller
     */
    Controller controller;

    /**
     * List of players in the game.
     */
    ArrayList<Player> players;

    /**
     * Sets up the context for test.
     */
    @Before
    public void before(){
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();
        controller = new StartUpController(game);
        players = new ArrayList<Player>();

        //load the map
        gameActions.loadMap(game, "finaltest.map");
    }

    /**
     * Tests if it addition of more two players with same name is restricted or not.
     */
    @Test
    public void testAddGamePlayer(){
        String message = controller.parseCommand(null, "gameplayer -add S human");
        Assert.assertEquals("Player S  with Strategy human successfully added", message);
        message = controller.parseCommand(null, "gameplayer -add S aggressive");
        Assert.assertEquals("Player with same name already exists. Add player with different name.", message);
    }

    /**
     * Tests if starting game with more than six player is possible or not.
     */
    @Test
    public void testAddGamePlayer2(){
        Player player1 = new HumanPlayer("s");
        Player player2 = new HumanPlayer("t");
        Player player3 = new HumanPlayer("u");
        Player player4 = new HumanPlayer("v");
        Player player5 = new HumanPlayer("w");
        Player player6 = new HumanPlayer("x");

        game.getPlayers().add(player1);
        game.getPlayers().add(player2);
        game.getPlayers().add(player3);
        game.getPlayers().add(player4);
        game.getPlayers().add(player5);
        game.getPlayers().add(player6);

        String message = controller.parseCommand(null, "gameplayer -add y human");
        Assert.assertEquals("Can not add any more player. Maximum 6 players can play.", message);
    }

    /**
     * Tests if correctly identifies right time to call populatecountries or not.
     */
    @Test
    public void testAddGamePlayer3(){
        controller.parseCommand(null, "gameplayer -add s human -add t human");
        controller.parseCommand(null, "populatecountries");
        String message = controller.parseCommand(null, "gameplayer -add q human");
        Assert.assertEquals("Invalid command - use placearmy/placeall/showmap commands to first allocate the assigned armies.", message);
    }

    /**
     * Tests if starting game with a single player is possible or not.
     */
    @Test
    public void testPopulateCountries(){
        Player player1 = new HumanPlayer("s");
        game.getPlayers().add(player1);
        String message = controller.parseCommand(null, "populatecountries");
        Assert.assertEquals("Minimum two players are required to play the game. Maximum six players.", message);
    }

    /**
     * Tests if correctly identifies right time to call populatecountries or not.
     */
    @Test
    public void testPopulateCountries2(){
        String message = controller.parseCommand(null, "populatecountries");
        Assert.assertEquals("Minimum two players are required to play the game. Maximum six players.", message);
    }

    /**
     * Tests if at least one army is placed in each country or not.
     */
    @Test
    public void testPlaceArmy(){
        controller.parseCommand(null, "gameplayer -add s human -add t human");
        controller.parseCommand(null, "populatecountries");
        Player p = game.getPlayers().get(0);
        for(Country country : p.getOwnedCountries().values()){
            Assert.assertEquals(1, country.getNumberOfArmies());
        }
    }

    /**
     * Tests if correctly raises error when trying to assign army to a country that player does not own.
     */
    @Test
    public void testPlaceArmy2(){
        controller.parseCommand(null, "gameplayer -add s human -add t human");
        controller.parseCommand(null, "populatecountries");
        Player p = game.getPlayers().get(0);
        String oppositeCountry = "";
        for(Country country : game.getPlayers().get(1).getOwnedCountries().values()){
            oppositeCountry = country.getCountryName();
        }
        String command = "placearmy " + oppositeCountry;
        System.out.println(command);
        String message = controller.parseCommand(p, command);
        Assert.assertEquals("You don't own this country. Please allocate army in your country.", message);
    }

    /**
     * Tests if correctly identifies right time to call placearmy or not.
     */
    @Test
    public void testPlaceArmy3(){
        String message = controller.parseCommand(null, "placearmy india");
        Assert.assertEquals("Invalid command - use gameplayer/populatecountries/placearmy/placeall/showmap commands in start up phase.", message);

        controller.parseCommand(null, "gameplayer -add s human -add t human");
        message = controller.parseCommand(null, "placearmy india");
        Assert.assertEquals("Invalid command - use gameplayer/populatecountries/placearmy/placeall/showmap commands in start up phase.", message);

        controller.parseCommand(null, "populatecountries");
        message = controller.parseCommand(game.getPlayers().get(0), "placearmy india");
        Assert.assertEquals("", message);
    }
    /**
     * Tests if correctly places all armies and changes the game phase.
     */
    @Test
    public void testPlaceAll(){
        controller.parseCommand(null, "gameplayer -add s human -add t human");
        controller.parseCommand(null, "populatecountries");
        String message = controller.parseCommand(game.getPlayers().get(0), "placeall");
        Assert.assertEquals("Armies placed successfully", message);
        Assert.assertEquals(Phase.CARDEXCHANGE, game.getGamePhase());
    }

    /**
     * Tests if correctly identifies right time to call placeall or not.
     */
    @Test
    public void testPlaceAll2(){
        String message = controller.parseCommand(null, "placeall");
        Assert.assertEquals("Invalid command - use gameplayer/populatecountries/placearmy/placeall/showmap commands in start up phase.", message);

        controller.parseCommand(null, "gameplayer -add s human -add t human");
        message = controller.parseCommand(null, "placeall");
        Assert.assertEquals("Invalid command - use gameplayer/populatecountries/placearmy/placeall/showmap commands in start up phase.", message);

        controller.parseCommand(null, "populatecountries");
        message = controller.parseCommand(game.getPlayers().get(0), "placeall");
        Assert.assertEquals("Armies placed successfully", message);
    }
}

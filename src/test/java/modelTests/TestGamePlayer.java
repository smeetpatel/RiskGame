package test.java.modelTests;

import static  org.junit.Assert.*;

import main.java.model.GameActions;
import main.java.model.GameData;
import main.java.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests if adding game player works correctly or not.
 *
 */
public class TestGamePlayer {

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
     * Set up the context
     */
    /*
    @Before
    public void before(){
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();

        //load the map
        gameActions.loadMap(game, "createdMap.map");

        //create players and them to the map
        players = new ArrayList<Player>();
        player1 = new Player("Smeet");
        players.add(player1);
        game.setPlayers(players);
    }*/

    /**
     * Tests if correctly identifies error in starting game with a single player.
     */
    /*
    @Test
    public void testGameplayer1(){
        boolean check = gameActions.populateCountries(game, players);
        assertFalse(check);
    }*/

    /**
     * Tests if correctly identifies error in starting game with more than six player.
     */
    /*
    @Test
    public void testGamePlayer2(){
        Player player2 = new Player("Patel");
        Player player3 = new Player("Tirth");
        Player player4 = new Player("Patel");
        Player player5 = new Player("soen");
        Player player6 = new Player("app");
        Player player7 = new Player("toc");
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        players.add(player6);
        players.add(player7);
        game.setPlayers(players);
        boolean check = gameActions.populateCountries(game, players);
        assertFalse(check);
    }*/
}

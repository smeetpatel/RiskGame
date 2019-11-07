package test.java;

import main.java.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestArmyAllocation {

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
    @Before
    public void before() {
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

       //populate countries
        gameActions.populateCountries(game, players);

    }

    /**
     * Tests if assigns at least one army to each country of a player or not.
     */
    @Test
    public void testArmyAllocation1(){
        for(Country country : player1.getOwnedCountries().values()){
            Assert.assertEquals(1, country.getNumberOfArmies());
        }
        for(Country country : player2.getOwnedCountries().values()){
            Assert.assertEquals(1, country.getNumberOfArmies());
        }
    }

    /**
     * Tests if correctly raises error when trying to assign army to a country that player does not own.
     */
    @Test
    public void testArmyAllocation2(){
        String countryName = "";
        for(Country country : player2.getOwnedCountries().values()){
            countryName = country.getCountryName();
            break;
        }
        boolean check = gameActions.placeArmy(player1, countryName.toLowerCase());
        Assert.assertFalse(check);
    }

    /**
     * Tests if correctly raises error when trying to assign army to a country that player does not own.
     */
    @Test
    public void testArmyAllocation3(){
        String countryName = "";
        for(Country country : player1.getOwnedCountries().values()){
            countryName = country.getCountryName();
            break;
        }
        player1.setOwnedArmies(0);
        Assert.assertFalse(gameActions.placeArmy(player1, countryName));
    }

    /**
     * Tests if correctly places all armies.
     */
    @Test
    public void testArmyAllocation4(){
        player1.setOwnedArmies(player1.getOwnedArmies()-6);
        player1.setOwnedArmies(player2.getOwnedArmies()-9);
        gameActions.placeAll(game);
        Assert.assertEquals(0, player1.getOwnedArmies());
        Assert.assertEquals(0, player2.getOwnedArmies());
        Assert.assertEquals(Phase.CARDEXCHANGE, game.getGamePhase());
    }
}

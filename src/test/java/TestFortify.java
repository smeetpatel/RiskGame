package test.java;

import main.java.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TestFortify {

    GameData game;
    Player player1;
    Player player2;
    GameMap map;
    ArrayList<Player> players;
    FortificationCheck fortificationCheck;
    String toCountry;
    String fromCountry;
    int numOfArmies;
    GameActions gameActions;
    HashMap<String,Country> nn;


    @Before
    public void before(){
        player1 = new Player("tirth");
        player2 = new Player("smeet");
        map = new GameMap("createdMap.map");
        players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);
        game = new GameData();
        gameActions = new GameActions();
        toCountry  = "china";
        fromCountry = "india";
        numOfArmies = 4;
        Country co1 = new Country("china", "asia");
        Country co2 = new Country("india", "asia");
        nn = new HashMap<String, Country>();
        //nn.put(co1.getCountryName(),co1);
        //nn.put(co2.getCountryName(),co2);
        //player1.setOwnedCountries(nn);
        //player1.getOwnedCountries().get(fromCountry.toLowerCase()).setNumberOfArmies(10);
        //System.out.println(co2.getNumberOfArmies());
        //game.setPlayers(players);

        //Country co3 = new Country("sluci", "azio");
    }

    @Test
    public void testFortify(){

        gameActions.editMap(game,"createdMap.map");
        boolean check = gameActions.populateCountries(game,players);
        //game.setGamePhase(Phase.FORTIFICATION);
        System.out.println(player1.getOwnedArmies());
        gameActions.placeAll(game);
        System.out.println(player1.getOwnedArmies());
        System.out.println(player1.getOwnedCountries().get(fromCountry.toLowerCase()).getCountryName());
        System.out.println(player1.getOwnedCountries().get(fromCountry.toLowerCase()).getNumberOfArmies());
        fortificationCheck = player1.fortify(game,toCountry,fromCountry,numOfArmies);
        System.out.println(fortificationCheck);
    }
}

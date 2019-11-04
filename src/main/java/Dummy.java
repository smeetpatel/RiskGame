package main.java;

import java.util.Scanner;
import java.util.ArrayList;

public class Dummy {

    public static void main(String[] args) {
        GameMap map = new GameMap("ameroki.map");
        GameActions gameActions = new GameActions();
        GameData gameData = new GameData();
        gameActions.loadMap(gameData, "ameroki.map");
        Deck deck = new Deck();
        deck.print();
        Player p1 = new Player("tirth");
        Player p2 = new Player("smeet");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);
        gameActions.populateCountries(gameData, players);
        Country co1 = new Country("china", "azio");
        Country co2 = new Country("japan", "azio");
        Country co3 = new Country("sluci", "azio");
        Card c1 = new Card("Infantry", co1);
        Card c2 = new Card("Cavalary", co2);
        Card c3 = new Card("Artillery", co3);
        p1.setOwnedCards(c1);
        p1.setOwnedCards(c2);
        p1.setOwnedCards(c3);
        MapView mapView = new MapView();
        System.out.println(p1.getOwnedCountries().keySet());
        System.out.println(p1.getOwnedArmies());
        CardExchange ce = new CardExchange();
        ArrayList<Integer> cardIndex = new ArrayList<>();
        cardIndex.add(1);
        cardIndex.add(2);
        cardIndex.add(3);
        System.out.println(p1.getOwnedArmies());
        ce.cardTradeIn(p1, cardIndex);
        System.out.println(p1.getOwnedArmies());

    }

}
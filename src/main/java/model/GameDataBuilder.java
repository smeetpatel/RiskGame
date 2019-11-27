package main.java.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Responsible for building GameData object
 */
public class GameDataBuilder implements Serializable {

    /**
     * Stores the map being edited or the map being played on depending on the user's choice.
     */
    private GameMap map;

    /**
     * Represents the type of the map.
     */
    private String mapType;

    /**
     * Stores the current phase of the game.
     */
    private Phase gamePhase;

    /**
     * Stores the list of players playing the game.
     */
    private ArrayList<Player> players;

    /**
     * Stores currently active player.
     */
    private Player activePlayer;

    /**
     * Stores the deck of cards.
     */
    private Deck deck;

    /**
     * Represents number of cards dealt.
     */
    private int cardsDealt;

    /**
     * Set the game map with the input argument.
     * @param map map to set as the game map.
     * @return GameDataBuilder object
     */
    public GameDataBuilder setMap(GameMap map) {
        this.map = map;
        return this;
    }

    /**
     * Sets the type of the map
     * @param mapType Type of the map, i.e. 'Conquest' format map or 'Domination' format map
     * @return GameDataBuilder object
     */
    public GameDataBuilder setMapType(String mapType) {
        this.mapType = mapType;
        return this;
    }

    /**
     * Set the phase of the game with the input argument.
     * @param gamePhase new phase of the game.
     * @return GameDataBuilder object
     */
    public GameDataBuilder setGamePhase(Phase gamePhase) {
        this.gamePhase = gamePhase;
        return this;
    }

    /**
     * Set the list of players.
     * @param players list of players to play the game.
     * @return GameDataBuilder object
     */
    public GameDataBuilder setPlayers(ArrayList<Player> players) {
        this.players = players;
        return this;
    }

    /**
     * Set the currently active player.
     * @param activePlayer currently active player.
     * @return GameDataBuilder object
     */
    public GameDataBuilder setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
        return this;
    }

    /**
     * Sets the deck of card.
     * @param deck Deck of card for the game.
     * @return GameDataBuilder object
     */
    public GameDataBuilder setDeck(Deck deck) {
        this.deck = deck;
        return this;
    }

    /**
     * set the phase for card trade in phase
     * @param cardsDealt trade in phase number
     * @return GameDataBuilder object
     */
    public GameDataBuilder setCardsDealt(int cardsDealt) {
        this.cardsDealt = cardsDealt;
        return this;
    }

    /**
     * Creates the required GameData object.
     * @return returns the newly created GameData object.
     */
    public GameData buildGameData(){
        return new GameData(map, mapType, gamePhase, players, activePlayer, deck, cardsDealt);
    }
}
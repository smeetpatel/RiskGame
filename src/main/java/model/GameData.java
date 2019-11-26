package main.java.model;

import java.util.ArrayList;

/**
 * Class holds the data required to maintain the state of the game.
 */
public class GameData extends Observable{

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
     * Constructor to initialize the game data.
     */
    public GameData(){
        map = new GameMap();
        gamePhase = Phase.NULL;
        players = new ArrayList<Player>();
        activePlayer = null;
        deck = new Deck();
        cardsDealt = 0;
    }

    public GameData(GameMap map, String mapType, Phase gamePhase, ArrayList<Player> players, Player activePlayer, Deck deck, int cardsDealt){
        this.map = map;
        this.mapType = mapType;
        this.gamePhase = gamePhase;
        this.players = players;
        this.activePlayer = activePlayer;
        this.deck = deck;
        this.cardsDealt = cardsDealt;
    }

    /**
     * Get the map being edited or played on.
     * @return returns the map being edited or played on.
     */
    public GameMap getMap() {
        return this.map;
    }

    /**
     * Set the game map with the input argument.
     * @param map map to set as the game map.
     */
    public void setMap(GameMap map) {
        this.map = map;
    }

    /**
     * Gets the type of the map
     * @return Type of the map, i.e. 'Conquest' format map or 'Domination' format map
     */
    public String getMapType() {
        return mapType;
    }

    /**
     * Sets the type of the map
     * @param mapType Type of the map, i.e. 'Conquest' format map or 'Domination' format map
     */
    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    /**
     * Get the current phase of the game.
     * @return returns the current phase of the game.
     */
    public Phase getGamePhase() {
        return this.gamePhase;
    }

    /**
     * Set the phase of the game with the input argument.
     * @param gamePhase new phase of the game.
     */
    public void setGamePhase(Phase gamePhase) {

        this.gamePhase = gamePhase;
        if(this.gamePhase==Phase.REINFORCEMENT){
            notifyObservers(this);
        } else if (this.gamePhase == Phase.ATTACK) {
            notifyObservers(this);
        } else if (this.gamePhase == Phase.FORTIFICATION) {
            notifyObservers(this);
        }
    }

    /**
     * Get the list of players.
     * @return returns the list of players.
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Set the list of players.
     * @param players list of players to play the game.
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Get currently active player.
     * @return returns currently active player.
     */
    public Player getActivePlayer(){
        return this.activePlayer;
    }

    /**
     * Set the currently active player.
     * @param player currently active player.
     */
   public void setActivePlayer(Player player){
        this.activePlayer = player;
        if(player!=null){
            notifyObservers(this);
        }
   }

    /**
     * Gets the deck of card.
     * @return Returns the deck of card.
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Sets the deck of card.
     * @param deck Deck of card for the game.
     */
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    /**
     * set the trade in phase of card exchange
     * @return trade in phase number
     */
    public int getCardsDealt() {
        return cardsDealt;
    }

    /**
     * set the phase for card trade in phase
     * @param cardsDealt trade in phase number
     */
    public void setCardsDealt(int cardsDealt) {
        this.cardsDealt = cardsDealt;
    }

    /**
     * Remove player
     */
    public void removePlayer(Player p){
        this.players.remove(p);
    }

    /**
     * Resets the game data
     */
    public void resetGameData(){
        map = new GameMap();
        gamePhase = Phase.NULL;
        players = new ArrayList<Player>();
        activePlayer = null;
        deck = new Deck();
        cardsDealt = 0;
    }
}
package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Class holds the data required to maintain the state of the game.
 */
public class GameData extends Observable{

    /**
     * Stores the map being edited or the map being played on depending on the user's choice.
     */
    private GameMap map;

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
     * Constructor to initialize the game data.
     */
    public GameData(){
        map = new GameMap();
        gamePhase = Phase.NULL;
        players = new ArrayList<Player>();
        activePlayer = null;
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
}
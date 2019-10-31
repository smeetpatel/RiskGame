package main.java;

import java.util.ArrayList;

/**
 * Class holds the data required to maintain the state of the game.
 */
public class GameData {

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
     * Constructor to initialize the game data.
     */
    GameData(){
        map = new GameMap();
        gamePhase = Phase.NULL;
        players = new ArrayList<Player>();
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
}
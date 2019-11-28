package main.java.model;

/**
 * Sets up the context for player class.
 */
public class PlayerContext {

    /**
     * Player for which context is to be set.
     */
    private Player player;

    /**
     * Sets the strategy for the player.
     * @param playerName Name of the player.
     * @param playerStrategy Strategy of the player.
     * @return true if successful in creating a player with desired strategy, else false.
     */
    public boolean setPlayerStrategy(String playerName, String playerStrategy){
        switch(playerStrategy){
            case "human":
                player = new HumanPlayer(playerName);
                return true;
            case "aggressive":
                player = new AggressivePlayer(playerName);
                return true;
            case "benevolent":
                player = new BenevolentPlayer(playerName);
                return true;
            case "random":
                player = new RandomPlayer(playerName);
                return true;
            case "cheater":
                player = new CheaterPlayer(playerName);
                return true;
            default:
                return false;
        }
    }

    /**
     * Gets the created player.
     * @return Player object with a defined strategy.
     */
    public Player getPlayer(){
        return this.player;
    }
}

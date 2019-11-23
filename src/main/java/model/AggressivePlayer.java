package main.java.model;

/**
 * Represents the aggressive player.
 */
public class AggressivePlayer extends Player{

    /**
     * Creates an aggressive player.
     * @param playerName Name of the player
     */
    public AggressivePlayer(String playerName){
        super(playerName);
    }

    /**
     * {@inheritDoc}
     * @param game Represents the state of the game
     * @param countryName Reinforce armies here
     * @param num Reinforce this many armies
     * @return
     */
    public boolean reinforce(GameData game, String countryName, int num){
        GameActions gameActions = new GameActions();
        gameActions.assignReinforcementArmies(game, this);

        //check if card exchange is possible, if yes do it.
        addCardExchangeArmies(game);
        return true;
    }

    /**
     * {@inheritDoc}
     * @param game Represents the state of the game.
     * @param countryFrom Country doing the attack
     * @param countryTo Country that is defending
     * @param numberOfDice Number of dice attacker wishes to roll
     * @param defendDice Number of dice defender wishes to roll
     * @param defendingPlayer Player owning the defending country
     * @return
     */
    public boolean attack(GameData game, String countryFrom, String countryTo, int numberOfDice, int defendDice, Player defendingPlayer){
        return true;
    }

    /**
     * {@inheritDoc}
     * @param game represents state of the game
     * @param fromCountry country from armies send
     * @param toCountry country to armies placed
     * @param num total number of armies to send from one country to another country
     * @return
     */
    public FortificationCheck fortify(GameData game, String fromCountry, String toCountry, int num){
        return FortificationCheck.FORTIFICATIONSUCCESS;
    }

}

package main.java.model;

import java.util.Random;

/**
 * Represents the random player.
 */
public class RandomPlayer extends Player{

    //Player player;
    GameActions gameActions;
    /**
     * Creates a player with the argument player name and sets default value for rest of the player fields.
     *
     * @param playerName name of player
     */
    public RandomPlayer(String playerName) {
        super(playerName);
        //player = new RandomPlayer(playerName);
    }

    /**
     * {@inheritDoc}
     * @param game Represents the state of the game
     * @param countryName Reinforce armies here
     * @param num Reinforce this many armies
     * @return
     */
    public boolean reinforce(GameData game, String countryName, int num){

        Random random = new Random();
        Country randomCountry = this.getOwnedCountries().get(random.nextInt(this.getOwnedCountries().size()));

        gameActions.assignReinforcementArmies(game, this);
        this.cardExchange(game, null);

        game.setActivePlayer(this);

        if (this.getOwnedArmies() > 0) {

            int existingArmies = randomCountry.getNumberOfArmies();
            existingArmies += this.getOwnedArmies();
            randomCountry.setNumberOfArmies(existingArmies);
            this.setOwnedArmies(this.getOwnedArmies());
            notifyObservers(Integer.toString(this.getOwnedArmies()) + " armies reinforced at " + randomCountry + ". Remaining reinforcement armies: " + Integer.toString(this.getOwnedArmies()) + "\n");
            game.setGamePhase(Phase.ATTACK);
        } else {
            notifyObservers(this.getPlayerName() + " doesn't have " + this.getOwnedArmies() + " armies to reinforce. Invalid command.");
        }
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

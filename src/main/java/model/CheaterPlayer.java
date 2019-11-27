package main.java.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the cheater player.
 */
public class CheaterPlayer extends Player{

    /**
     * Represents object of GameActions class
     */
    GameActions gameActions;
    /**
     * Creates a player with the argument player name and sets default value for rest of the player fields.
     *
     * @param playerName name of player
     */
    public CheaterPlayer(String playerName) {

        super(playerName);
        gameActions = new GameActions();
    }

    /**
     * Reinforcement phase for cheater player
     * @param game Represents the state of the game
     * @param countryName Reinforce armies here
     * @param num Reinforce this many armies
     * @return true if reinforcement is successful
     */
    public boolean reinforce(GameData game, String countryName, int num){

        ArrayList<Country> countries = (ArrayList<Country>) this.getOwnedCountries().values();
        for(Country c:countries){
            game.getLogger().info(this.getPlayerName() + " reinforced " + c.getCountryName() + " with " + c.getNumberOfArmies());
            c.setNumberOfArmies(c.getNumberOfArmies() * 2);
        }

        return true;
    }

    /**
     * Attack phase for cheater player
     * @param game Represents the state of the game.
     * @param countryFrom Country doing the attack
     * @param countryTo Country that is defending
     * @param numberOfDice Number of dice attacker wishes to roll
     * @param defendDice Number of dice defender wishes to roll
     * @param defendingPlayer Player owning the defending country
     * @return true if attack is successful else false
     */
    public boolean attack(GameData game, String countryFrom, String countryTo, int numberOfDice, int defendDice, Player defendingPlayer){

        for(Country country: this.getOwnedCountries().values()){

            for(Country neighbour: country.getNeighbours().values()){
                if(!this.getOwnedCountries().containsKey(neighbour.getCountryName().toLowerCase())){
                    this.getOwnedCountries().put(neighbour.getCountryName().toLowerCase(),neighbour);
                    neighbour.getOwnerPlayer().getOwnedCountries().remove(neighbour);
                    neighbour.setOwnerPlayer(this);
                    game.getLogger().info(getPlayerName() + " conquered " + neighbour.getCountryName() + ".\n");
                    notifyObservers(getPlayerName() + " conquered " + neighbour.getCountryName() + ".\n");

                    //move armies to conquered territory
                    moveArmy(game, country.getCountryName(), neighbour.getCountryName(), numberOfDice, country.getNumberOfArmies()-1);

                    //check if player owns all the countries on the map
                    if (getOwnedCountries().size() == game.getMap().getCountries().size()) {
                        game.getLogger().info(this.getPlayerName() + " has won the game.");
                        gameActions.endGame(game);
                        return true;
                    }

                    //check if player owns entire continent or not
                    gameActions.checkContinentOwnership(game, this);

                    if (neighbour.getOwnerPlayer().getOwnedCountries().size() == 0) {
                        game.getLogger().info(defendingPlayer.getPlayerName() + " lost his/her last country. Hence, out of the game. " + getPlayerName() + " gets all his/her cards.");
                        notifyObservers(neighbour.getOwnerPlayer().getPlayerName() + " lost his/her last country. Hence, out of the game. " + getPlayerName() + " gets all his/her cards.");
                        gameActions.getAllCards(this, neighbour.getOwnerPlayer());
                        game.removePlayer(neighbour.getOwnerPlayer());
                        if (getOwnedCards().size() >= 6) {
                            addCardExchangeArmies(game);
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Fortification phase for cheater player
     * @param game represents state of the game
     * @param fromCountry country from armies send
     * @param toCountry country to armies placed
     * @param num total number of armies to send from one country to another country
     * @return status of the fortification phase
     */
    public FortificationCheck fortify(GameData game, String fromCountry, String toCountry, int num){

        for(Country country : this.getOwnedCountries().values()){
            for(Country neighbour : country.getNeighbours().values()){
                if(!(this.getOwnedCountries().containsKey(neighbour.getCountryName().toLowerCase()))){
                    game.getLogger().info(this.getPlayerName() + " fortified " + country.getCountryName() + " with " + country.getNumberOfArmies());
                    country.setNumberOfArmies(country.getNumberOfArmies() * 2);
                    break;
                }
            }
        }
        game.getLogger().info(this.getPlayerName() + "'s fortification move complete.");
        return FortificationCheck.FORTIFICATIONSUCCESS;
    }
}

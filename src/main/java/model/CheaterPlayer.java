package main.java.model;

import java.util.ArrayList;

/**
 * Represents the cheater player.
 */
public class CheaterPlayer extends Player{
    /**
     * Creates a player with the argument player name and sets default value for rest of the player fields.
     *
     * @param playerName name of player
     */
    public CheaterPlayer(String playerName) {
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

        ArrayList<Country> countries = (ArrayList<Country>) this.getOwnedCountries().values();
        for(Country c:countries){
            c.setNumberOfArmies(c.getNumberOfArmies() * 2);
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

        ArrayList<Country> countries = (ArrayList<Country>) this.getOwnedCountries().values();
        ArrayList<Country> neighbours;

        for(Country c:countries){
            neighbours = (ArrayList<Country>) c.getNeighbours().values();
            for(Country neighbour : neighbours){
                if(!(neighbour.getOwnerPlayer().equals(this.getPlayerName()))){
                    c.setNumberOfArmies(c.getNumberOfArmies() * 2);
                    break;
                }
            }
        }

        return FortificationCheck.FORTIFICATIONSUCCESS;
    }
}

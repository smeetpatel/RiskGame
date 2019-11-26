package main.java.model;

import java.util.ArrayList;
import java.util.HashMap;

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
     * Reinforcement phase for cheater player
     * @param game Represents the state of the game
     * @param countryName Reinforce armies here
     * @param num Reinforce this many armies
     * @return true if reinforcement is successful
     */
    public boolean reinforce(GameData game, String countryName, int num){

        ArrayList<Country> countries = (ArrayList<Country>) this.getOwnedCountries().values();
        for(Country c:countries){
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

        HashMap<String,Country> conqueredCountries = new HashMap<String, Country>();

        for(Country country: this.getOwnedCountries().values()){
            for(Country neighbour: country.getNeighbours()){
                if(!this.getOwnedCountries().containsKey(neighbour.getCountryName().toLowerCase())){
                    conqueredCountries.put(neighbour.getCountryName(),neighbour);
                    neighbour.getOwnerPlayer().getOwnedCountries().remove(neighbour);
                }
            }
        }
        this.setOwnedCountries(conqueredCountries);
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

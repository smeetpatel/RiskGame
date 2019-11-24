package main.java.model;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents the human player.
 */
public class HumanPlayer extends Player {

    /**
     * Creates a human player.
     * @param playerName Name of the player
     */
    public HumanPlayer(String playerName){
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
        game.setActivePlayer(this);
        if(num<0){
            notifyObservers("You cannot reinforce with negative number of armies.");
        }
        if(getOwnedCountries().containsKey(countryName.toLowerCase()))
        {
            if(getOwnedArmies() >= num)
            {
                Country c = getOwnedCountries().get(countryName.toLowerCase());
                int existingArmies = c.getNumberOfArmies();
                existingArmies += num;
                c.setNumberOfArmies(existingArmies);
                this.setOwnedArmies(getOwnedArmies()-num);
                notifyObservers(num + " armies reinforced at " + countryName + ". Remaining reinforcement armies: " + getOwnedArmies() + "\n");
                if(getOwnedArmies()==0) {
                    game.setGamePhase(Phase.ATTACK);
                }
                return true;
            }
            else
            {
                notifyObservers(getPlayerName() + " doesn't have " + num + " armies to reinforce. Invalid command.");
                return false;
            }
        }
        else
        {
            notifyObservers(countryName + " not owned by " + getPlayerName() +". Invalid command.\n");
            return false;
        }
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
        Country attackingCountry = game.getMap().getCountries().get(countryFrom.toLowerCase());
        Country defendingCountry = game.getMap().getCountries().get(countryTo.toLowerCase());
        int[] attackerDiceRolls = new int[numberOfDice];
        int[] defenderDiceRolls = new int[defendDice];
        int attackerArmiesLost = 0;
        int defenderArmiesLost = 0;
        //roll the dices
        for(int i = 0; i<numberOfDice; i++){
            int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
            attackerDiceRolls[i] = randomNum;
        }
        for(int i = 0; i<defendDice; i++){
            int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
            defenderDiceRolls[i] = randomNum;
        }

        //sort the dice roll result
        Arrays.sort(attackerDiceRolls);
        Arrays.sort(defenderDiceRolls);

        //compare dice results
        for(int i=1; i<=defendDice; i++){
            if(defenderDiceRolls[defenderDiceRolls.length-i]>=attackerDiceRolls[attackerDiceRolls.length-i]){
                attackingCountry.setNumberOfArmies(attackingCountry.getNumberOfArmies()-1);
                attackerArmiesLost++;
            } else {
                defendingCountry.setNumberOfArmies(defendingCountry.getNumberOfArmies()-1);
                defenderArmiesLost++;
            }
            if(defendingCountry.getNumberOfArmies()==0){
                if(attackerArmiesLost>0){
                    notifyObservers(getPlayerName() + " lost " + attackerArmiesLost + " army at " + countryFrom + ".\n");
                }
                if(defenderArmiesLost>0){
                    notifyObservers(defendingPlayer.getPlayerName() + " lost " + defenderArmiesLost + " army at " + countryTo + ".\n");
                }
                getOwnedCountries().put(countryTo.toLowerCase(), defendingCountry);
                defendingCountry.setOwnerPlayer(this);
                defendingPlayer.getOwnedCountries().remove(countryTo.toLowerCase());
                notifyObservers(getPlayerName() + " conquered " + countryTo + ".\n");
                if(defendingPlayer.getOwnedCountries().size()==0){
                    notifyObservers(defendingPlayer.getPlayerName() + " lost his/her last country. Hence, out of the game. " + getPlayerName() + " gets all his/her cards.");
                }
                return true;
            }
            if(i>=numberOfDice){
                break;
            }
        }
        if(attackerArmiesLost>0){
            notifyObservers(getPlayerName() + " lost " + attackerArmiesLost + " army at " + countryFrom + ".\n");
        }
        if(defenderArmiesLost>0){
            notifyObservers(defendingPlayer.getPlayerName() + " lost " + defenderArmiesLost + " army at " + countryTo + ".\n");
        }
        return false;
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
        MapValidation mv = new MapValidation();
        if(num<0){
            return FortificationCheck.ARMYCOUNTFAIL;
        }
        if(getOwnedCountries().containsKey(fromCountry.toLowerCase()))
        {
            if(getOwnedCountries().containsKey(toCountry.toLowerCase()))
            {
                if((getOwnedCountries().get(fromCountry.toLowerCase()).getNumberOfArmies()- num)>=1)
                {
                    if(mv.fortificationConnectivityCheck(this, fromCountry, toCountry))
                    {
                        int fromArmies = getOwnedCountries().get(fromCountry.toLowerCase()).getNumberOfArmies();
                        fromArmies -= num;
                        getOwnedCountries().get(fromCountry.toLowerCase()).setNumberOfArmies(fromArmies);
                        int toArmies = getOwnedCountries().get(toCountry.toLowerCase()).getNumberOfArmies();
                        toArmies += num;
                        getOwnedCountries().get(toCountry.toLowerCase()).setNumberOfArmies(toArmies);
                        notifyObservers(getPlayerName() + " fortified " + toCountry + " with " + num + " armies from " + fromCountry +". " + getPlayerName() + "'s turn ends now.");
                        game.setGamePhase(Phase.TURNEND);
                        return FortificationCheck.FORTIFICATIONSUCCESS;
                    } else {
                        notifyObservers("No path from " + fromCountry + " to " + toCountry + ". Fortification failed.");
                        return FortificationCheck.PATHFAIL;
                    }
                } else {
                    notifyObservers("Not enough armies in " + fromCountry + " to fortify " + toCountry + " with " + num + " armies. Fortification failed.");
                    return FortificationCheck.ARMYCOUNTFAIL;
                }
            } else {
                notifyObservers(getPlayerName() + " does not own " + toCountry + ". Fortification failed.");
                return FortificationCheck.TOCOUNTRYFAIL;
            }
        }
        else
        {
            notifyObservers(getPlayerName() + " does not own  " + fromCountry + ". Fortification failed.");
            return FortificationCheck.FROMCOUNTRYFAIL;
        }
    }
}
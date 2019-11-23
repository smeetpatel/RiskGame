package main.java.model;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
        ArrayList<Country> countries = (ArrayList<Country>) this.getOwnedCountries().values();
        Country randomCountry = countries.get(random.nextInt(this.getOwnedCountries().size()));

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

        boolean check = false;
        Random random = new Random();
        ArrayList<Country> sourceCountries = (ArrayList<Country>) this.getOwnedCountries().values();

        while (!check) {
            countryFrom = sourceCountries.get(random.nextInt(sourceCountries.size())).getCountryName();

            ArrayList<Country> sourceNeighbourCountries = (ArrayList<Country>) this.getOwnedCountries().get(countryFrom).getNeighbours().values();

            for (Country c : sourceNeighbourCountries) {
                if (!this.getOwnedCountries().containsKey(c)) {
                    countryTo = c.getCountryName();
                    check = true;
                }else {
                    check = false;
                }
            }
        }
        numberOfDice = gameActions.getMaxDiceRolls(game, countryFrom, "attacker");
        defendDice = gameActions.getMaxDiceRolls(game, countryTo, "defender");

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

        boolean check = false;
        MapValidation mv = new MapValidation();
        Random random = new Random();
        int randomCounter = 0;

        ArrayList<Country> targetCountries = (ArrayList<Country>) this.getOwnedCountries().values();
        ArrayList<Country> sourceCountries = (ArrayList<Country>) this.getOwnedCountries().values();
        //Collections.shuffle(targetCountries);
        //Collections.shuffle(sourceCountries);

        while (!check || randomCounter<25){
            fromCountry = sourceCountries.get(random.nextInt(this.getOwnedCountries().size())).getCountryName();
            toCountry = targetCountries.get(random.nextInt(this.getOwnedCountries().size())).getCountryName();
            if(fromCountry != toCountry){
                check = mv.fortificationConnectivityCheck(this, fromCountry, toCountry);
            }
            randomCounter++;
        }

        if(!check) {
            this.fortify(game);
            return FortificationCheck.PATHFAIL;
        }else{
            int fromArmies = this.getOwnedCountries().get(fromCountry.toLowerCase()).getNumberOfArmies();
            int toArmies = this.getOwnedCountries().get(toCountry.toLowerCase()).getNumberOfArmies();
            toArmies += (fromArmies - 1);
            fromArmies = 1;
            this.getOwnedCountries().get(fromCountry.toLowerCase()).setNumberOfArmies(fromArmies);
            this.getOwnedCountries().get(toCountry.toLowerCase()).setNumberOfArmies(toArmies);
            notifyObservers(this.getPlayerName() + " fortified " + toCountry + " with " + toArmies + " armies from " + fromCountry + ". " + this.getPlayerName() + "'s turn ends now.");
            game.setGamePhase(Phase.TURNEND);
            return FortificationCheck.FORTIFICATIONSUCCESS;
        }
    }
}

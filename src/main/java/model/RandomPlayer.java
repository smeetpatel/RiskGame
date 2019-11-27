package main.java.model;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents the random player.
 */
public class RandomPlayer extends Player {

    /**
     * Represents the object of GameActions class
     */
    GameActions gameActions;

    /**
     * Creates a player with the argument player name and sets default value for rest of the player fields.
     *
     * @param playerName name of player
     */
    public RandomPlayer(String playerName) {
        super(playerName);
    }

    /**
     * Reinforcement phase for the random player
     * @param game        Represents the state of the game
     * @param countryName Reinforce armies here
     * @param num         Reinforce this many armies
     * @return true if reinforcement successful else false
     */
    public boolean reinforce(GameData game, String countryName, int num) {

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
            notifyObservers(Integer.toString(this.getOwnedArmies()) + " armies reinforced at " + randomCountry.getCountryName() + ". Remaining reinforcement armies: " + Integer.toString(this.getOwnedArmies()) + "\n");
            game.setGamePhase(Phase.ATTACK);
        } else {
            notifyObservers(this.getPlayerName() + " doesn't have " + this.getOwnedArmies() + " armies to reinforce. Invalid command.");
        }
        return true;
    }

    /**
     * Represents the attack phase for random player
     * @param game            Represents the state of the game.
     * @param countryFrom     Country doing the attack
     * @param countryTo       Country that is defending
     * @param numberOfDice    Number of dice attacker wishes to roll
     * @param defendDice      Number of dice defender wishes to roll
     * @param defendingPlayer Player owning the defending country
     * @return true if attack is successful else false
     */
    public boolean attack(GameData game, String countryFrom, String countryTo, int numberOfDice, int defendDice, Player defendingPlayer) {

        int attackerArmiesLost = 0;
        int defenderArmiesLost = 0;
        int[] attackerDiceRolls;
        int[] defenderDiceRolls;

        boolean check = false;
        Random random = new Random();
        Country attackingCountry = null;
        Country defendingCountry = null;
        int randomcounter = 0;

        ArrayList<Country> sourceCountries = (ArrayList<Country>) this.getOwnedCountries().values();

        while (defendingCountry.equals(null) || randomcounter<25) {
            countryFrom = sourceCountries.get(random.nextInt(sourceCountries.size())).getCountryName();
            attackingCountry = game.getMap().getCountries().get(countryFrom.toLowerCase());
            defendingCountry = canAttack(attackingCountry);
            randomcounter++;
        }

        while((defendingCountry = canAttack(attackingCountry))!=null) {

            //get the defending player
            defendingPlayer = defendingCountry.getOwnerPlayer();


            numberOfDice = gameActions.getMaxDiceRolls(game, attackingCountry.getCountryName(), "attacker");
            defendDice = gameActions.getMaxDiceRolls(game, defendingCountry.getCountryName(), "defender");


            attackerDiceRolls = new int[numberOfDice];
            defenderDiceRolls = new int[defendDice];

            //roll the dices
            for (int i = 0; i < numberOfDice; i++) {
                int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
                attackerDiceRolls[i] = randomNum;
            }
            for (int i = 0; i < defendDice; i++) {
                int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
                defenderDiceRolls[i] = randomNum;
            }

            //sort the dice roll result
            Arrays.sort(attackerDiceRolls);
            Arrays.sort(defenderDiceRolls);

            //compare dice results
            for (int i = 1; i <= defendDice; i++) {
                if (defenderDiceRolls[defenderDiceRolls.length - i] >= attackerDiceRolls[attackerDiceRolls.length - i]) {
                    attackingCountry.setNumberOfArmies(attackingCountry.getNumberOfArmies() - 1);
                    attackerArmiesLost++;
                } else {
                    defendingCountry.setNumberOfArmies(defendingCountry.getNumberOfArmies() - 1);
                    defenderArmiesLost++;
                }
                if (defendingCountry.getNumberOfArmies() == 0) {
                    if (attackerArmiesLost > 0) {
                        notifyObservers(getPlayerName() + " lost " + attackerArmiesLost + " army at " + countryFrom + ".\n");
                    }
                    if (defenderArmiesLost > 0) {
                        notifyObservers(defendingPlayer.getPlayerName() + " lost " + defenderArmiesLost + " army at " + countryTo + ".\n");
                    }
                    getOwnedCountries().put(countryTo.toLowerCase(), defendingCountry);
                    defendingCountry.setOwnerPlayer(this);
                    defendingPlayer.getOwnedCountries().remove(countryTo.toLowerCase());
                    notifyObservers(getPlayerName() + " conquered " + countryTo + ".\n");

                    //move armies to conquered territory
                    moveArmy(game, attackingCountry.getCountryName(), defendingCountry.getCountryName(), numberOfDice, numberOfDice);

                    //check if player owns all the countries on the map
                    if (getOwnedCountries().size() == game.getMap().getCountries().size()) {
                        gameActions.endGame(game);
                        return true;
                    }

                    //check if player owns entire continent or not
                    gameActions.checkContinentOwnership(game, this);

                    if (defendingPlayer.getOwnedCountries().size() == 0) {
                        notifyObservers(defendingPlayer.getPlayerName() + " lost his/her last country. Hence, out of the game. " + getPlayerName() + " gets all his/her cards.");
                        gameActions.getAllCards(this, defendingPlayer);
                        game.removePlayer(defendingPlayer);
                        if (getOwnedCards().size() >= 6) {
                            addCardExchangeArmies(game);
                        }
                    }
                    return true;
                }
                if (i >= numberOfDice) {
                    break;
                }
            }
            if (attackerArmiesLost > 0) {
                notifyObservers(getPlayerName() + " lost " + attackerArmiesLost + " army at " + countryFrom + ".\n");
            }
            if (defenderArmiesLost > 0) {
                notifyObservers(defendingPlayer.getPlayerName() + " lost " + defenderArmiesLost + " army at " + countryTo + ".\n");
            }
            attackerArmiesLost = 0;
            defenderArmiesLost = 0;
        }
        return true;
    }

    /**
     * Fortification phase for random player
     * @param game        represents state of the game
     * @param fromCountry country from armies send
     * @param toCountry   country to armies placed
     * @param num         total number of armies to send from one country to another country
     * @return FortificationCheck.FORTIFICATIONSUCCESS if fortification successful
     */
    public FortificationCheck fortify(GameData game, String fromCountry, String toCountry, int num) {

        boolean check = false;
        MapValidation mv = new MapValidation();
        Random random = new Random();
        int randomCounter = 0;

        ArrayList<Country> targetCountries = (ArrayList<Country>) this.getOwnedCountries().values();
        ArrayList<Country> sourceCountries = (ArrayList<Country>) this.getOwnedCountries().values();
        //Collections.shuffle(targetCountries);
        //Collections.shuffle(sourceCountries);

        while (!check || randomCounter < 25) {
            fromCountry = sourceCountries.get(random.nextInt(this.getOwnedCountries().size())).getCountryName();
            toCountry = targetCountries.get(random.nextInt(this.getOwnedCountries().size())).getCountryName();
            if (fromCountry != toCountry) {
                check = mv.fortificationConnectivityCheck(this, fromCountry, toCountry);
            }
            randomCounter++;
        }

        if (!check) {
            this.fortify(game);
            return FortificationCheck.FORTIFICATIONSUCCESS;
        } else {
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

    /**
     * Checks whether attack with argument country is possible or not.
     * @param attackingCountry Country making the attack.
     * @return country to attack
     */
    public Country canAttack(Country attackingCountry){
        if(attackingCountry.getNumberOfArmies()==1){
            return null;
        }
        for(Country neighbor: attackingCountry.getNeighbours().values()){
            if(!getOwnedCountries().containsKey(neighbor.getCountryName().toLowerCase())){
                return neighbor;
            }
        }
        return null;
    }
}


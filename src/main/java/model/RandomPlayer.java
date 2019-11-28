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
        gameActions = new GameActions();
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
        Object[] values = this.getOwnedCountries().values().toArray();
        //Country randomCountry = (Country) values[random.nextInt(values.length)];
        Country randomCountry = (Country) values[0];

        gameActions.assignReinforcementArmies(game, this);
        addCardExchangeArmies(game);

        game.setActivePlayer(this);

        num = this.getOwnedArmies();
        randomCountry.setNumberOfArmies(randomCountry.getNumberOfArmies() + num);
        this.setOwnedArmies(0);
        game.getLogger().info(num + " armies reinforced at " + randomCountry.getCountryName() + ". Remaining reinforcement armies: " + this.getOwnedArmies() + "\n");
        notifyObservers(num + " armies reinforced at " + randomCountry.getCountryName() + ". Remaining reinforcement armies: " + this.getOwnedArmies() + "\n");
        game.setGamePhase(Phase.ATTACK);

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

        Random random = new Random();
        Country attackingCountry = null;
        Country defendingCountry = null;
        int randomCounter = 0;

        //ArrayList<Country> sourceCountries = (ArrayList<Country>) this.getOwnedCountries().values();
        Object[] sourceCountries = this.getOwnedCountries().values().toArray();

        while (defendingCountry == null && randomCounter<25) {
            Country originCountry = (Country) sourceCountries[random.nextInt(sourceCountries.length)];
            countryFrom = originCountry.getCountryName();
            attackingCountry = game.getMap().getCountries().get(countryFrom.toLowerCase());
            defendingCountry = canAttack(attackingCountry);
            randomCounter++;
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
                        game.getLogger().info(getPlayerName() + " lost " + attackerArmiesLost + " army at " + attackingCountry.getCountryName() + ".\n");
                        notifyObservers(getPlayerName() + " lost " + attackerArmiesLost + " army at " + attackingCountry.getCountryName() + ".\n");
                        attackerArmiesLost = 0;
                    }
                    if (defenderArmiesLost > 0) {
                        game.getLogger().info(defendingPlayer.getPlayerName() + " lost " + defenderArmiesLost + " army at " + defendingCountry.getCountryName() + ".\n");
                        notifyObservers(defendingPlayer.getPlayerName() + " lost " + defenderArmiesLost + " army at " + defendingCountry.getCountryName() + ".\n");
                        defenderArmiesLost = 0;
                    }
                    getOwnedCountries().put(defendingCountry.getCountryName().toLowerCase(), defendingCountry);
                    defendingCountry.setOwnerPlayer(this);
                    defendingPlayer.getOwnedCountries().remove(defendingCountry.getCountryName().toLowerCase());
                    game.getLogger().info(getPlayerName() + " conquered " + defendingCountry.getCountryName() + ".\n");
                    notifyObservers(getPlayerName() + " conquered " + defendingCountry.getCountryName() + ".\n");

                    //move armies to conquered territory
                    moveArmy(game, attackingCountry.getCountryName(), defendingCountry.getCountryName(), numberOfDice, numberOfDice);

                    //check if player owns all the countries on the map
                    if (getOwnedCountries().size() == game.getMap().getCountries().size()) {
                        game.getLogger().info(this.getPlayerName() + " has won the game.");
                        gameActions.endGame(game);
                        return true;
                    }

                    //check if player owns entire continent or not
                    gameActions.checkContinentOwnership(game, this);

                    if (defendingPlayer.getOwnedCountries().size() == 0) {
                        game.getLogger().info(defendingPlayer.getPlayerName() + " lost his/her last country. Hence, out of the game. " + getPlayerName() + " gets all his/her cards.");
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
                game.getLogger().info(getPlayerName() + " lost " + attackerArmiesLost + " army at " + attackingCountry.getCountryName() + ".\n");
                notifyObservers(getPlayerName() + " lost " + attackerArmiesLost + " army at " + attackingCountry.getCountryName() + ".\n");
            }
            if (defenderArmiesLost > 0) {
                game.getLogger().info(defendingPlayer.getPlayerName() + " lost " + defenderArmiesLost + " army at " + defendingCountry.getCountryName() + ".\n");
                notifyObservers(defendingPlayer.getPlayerName() + " lost " + defenderArmiesLost + " army at " + defendingCountry.getCountryName() + ".\n");
            }
            attackerArmiesLost = 0;
            defenderArmiesLost = 0;
        }
        game.getLogger().info("Player do no want to attack.");
        gameActions.endAttack(game);
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

        Object[] targetCountries = this.getOwnedCountries().values().toArray();
        Object[] sourceCountries = this.getOwnedCountries().values().toArray();
        //Collections.shuffle(targetCountries);
        //Collections.shuffle(sourceCountries);

        while (!check && randomCounter < 25) {
            Country originCountry = (Country) sourceCountries[random.nextInt(sourceCountries.length)];
            Country destinationCountry = (Country) targetCountries[random.nextInt(sourceCountries.length)];
            fromCountry = originCountry.getCountryName();
            toCountry = destinationCountry.getCountryName();
            if (fromCountry != toCountry) {
                check = mv.fortificationConnectivityCheck(this, fromCountry, toCountry);
            }
            randomCounter++;
        }

        if (!check) {
            game.getLogger().info("No fortification");
            this.fortify(game);
            return FortificationCheck.FORTIFICATIONSUCCESS;
        } else {
            int fromArmies = this.getOwnedCountries().get(fromCountry.toLowerCase()).getNumberOfArmies();
            int toArmies = this.getOwnedCountries().get(toCountry.toLowerCase()).getNumberOfArmies();
            toArmies += (fromArmies - 1);
            fromArmies = 1;
            this.getOwnedCountries().get(fromCountry.toLowerCase()).setNumberOfArmies(fromArmies);
            this.getOwnedCountries().get(toCountry.toLowerCase()).setNumberOfArmies(toArmies);
            game.getLogger().info(this.getPlayerName() + " fortified " + toCountry + " with " + toArmies + " armies from " + fromCountry + ". " + this.getPlayerName() + "'s turn ends now.");
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


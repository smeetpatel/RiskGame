package main.java.model;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

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
        Country country = findStrongestCountry();

        game.setActivePlayer(this);
        num = getOwnedArmies();
        country.setNumberOfArmies(country.getNumberOfArmies() + getOwnedArmies());
        setOwnedArmies(0);
        notifyObservers(num + " armies reinforced at " + country.getCountryName() +". Remaining reinforcement armies: " + getOwnedArmies() + "\n");
        game.setGamePhase(Phase.ATTACK);
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

        int attackerArmiesLost = 0;
        int defenderArmiesLost = 0;
        int[] attackerDiceRolls;
        int[] defenderDiceRolls;

        GameActions gameActions = new GameActions();
        Country attackingCountry = findStrongestCountry();
        Country defendingCountry = canAttack(attackingCountry);

        //attack till attack is possible from the attackingCountry
        while((defendingCountry = canAttack(attackingCountry))!=null){

            //get the defending player
            defendingPlayer = defendingCountry.getOwnerPlayer();

            //get number of dice to roll
            numberOfDice = gameActions.getMaxDiceRolls(game, attackingCountry.getCountryName(), "attacker");
            defendDice = gameActions.getMaxDiceRolls(game, defendingCountry.getCountryName(), "defender");
            attackerDiceRolls = new int[numberOfDice];
            defenderDiceRolls = new int[defendDice];

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
                        notifyObservers(getPlayerName() + " lost " + attackerArmiesLost + " army at " + attackingCountry.getCountryName() + ".\n");
                    }
                    if(defenderArmiesLost>0){
                        notifyObservers(defendingPlayer.getPlayerName() + " lost " + defenderArmiesLost + " army at " + defendingCountry.getCountryName() + ".\n");
                    }
                    getOwnedCountries().put(defendingCountry.getCountryName().toLowerCase(), defendingCountry);
                    defendingCountry.setOwnerPlayer(this);
                    defendingPlayer.getOwnedCountries().remove(defendingCountry.getCountryName().toLowerCase());
                    notifyObservers(getPlayerName() + " conquered " + defendingCountry.getCountryName() + ".\n");

                    //move armies to conquered territory
                    moveArmy(game, attackingCountry.getCountryName(), defendingCountry.getCountryName(), numberOfDice, numberOfDice);

                    //check if player owns all the countries on the map
                    if(getOwnedCountries().size()==game.getMap().getCountries().size()){
                        gameActions.endGame(game);
                    }

                    //check if player owns entire continent or not
                    gameActions.checkContinentOwnership(game, this);

                    //if defending player lost army, get all his cards, perform card exchange if applicable, and removing losing pkayer from the game.
                    if(defendingPlayer.getOwnedCountries().size()==0){
                        notifyObservers(defendingPlayer.getPlayerName() + " lost his/her last country. Hence, out of the game. " + getPlayerName() + " gets all his/her cards.");
                        if(defendingPlayer.getOwnedCountries().size()==0){
                            gameActions.getAllCards(this, defendingPlayer);
                            game.removePlayer(defendingPlayer);
                            if(getOwnedCards().size()>=6){
                                addCardExchangeArmies(game);
                            }
                        }
                    }
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
            attackerArmiesLost = 0;
            defenderArmiesLost = 0;
        }
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
        Country destinationCountry = findCountryToFortify();
        Country originCountry = findOriginCountry(destinationCountry);
        if(originCountry == null){
            fortify(game);
            return FortificationCheck.FORTIFICATIONSUCCESS;
        }
        num = originCountry.getNumberOfArmies()-1;
        destinationCountry.setNumberOfArmies(destinationCountry.getNumberOfArmies()+num);
        originCountry.setNumberOfArmies(originCountry.getNumberOfArmies()-num);
        notifyObservers(getPlayerName() + " fortified " + destinationCountry.getCountryName() + " with " + num + " armies from " + originCountry.getCountryName() +". " + getPlayerName() + "'s turn ends now.");
        game.setGamePhase(Phase.TURNEND);
        return FortificationCheck.FORTIFICATIONSUCCESS;
    }

    /**
     * Finds strongest country to reinforce/attack.
     * @return Strongest country owned by the player.
     */
    public Country findStrongestCountry(){
        int maximumArmies = 0;
        int noNeighborMaximumArmies = 0;
        Country countryToReinforce = null;
        Country noNeighborCountryToReinforce = null;
        for(Country country : getOwnedCountries().values()){
            if(country.getNumberOfArmies()>maximumArmies){
                for(Country neighbor : country.getNeighbours().values()){
                    if(!getOwnedCountries().containsKey(neighbor.getCountryName().toLowerCase())){
                        maximumArmies = country.getNumberOfArmies();
                        countryToReinforce = country;
                        break;
                    }
                }
            }
            if(country.getNumberOfArmies()>noNeighborMaximumArmies){
                noNeighborMaximumArmies = country.getNumberOfArmies();
                noNeighborCountryToReinforce = country;
            }
        }
        if(maximumArmies > 0){
            return countryToReinforce;
        } else {
            return noNeighborCountryToReinforce;
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

    /**
     * Finds the country to fortify.
     * @return Returns the country to fortify.
     */
    public Country findCountryToFortify(){
        int numberOfNeighbors = 0;
        int perCountryNeighbor;
        Country fortifyCountry = null;
        for(Country country : getOwnedCountries().values()){
            perCountryNeighbor = 0;
            for(Country neighbor : country.getNeighbours().values()){
                if(!getOwnedCountries().containsKey(neighbor.getCountryName().toLowerCase())){
                    perCountryNeighbor++;
                }
            }
            if(perCountryNeighbor>numberOfNeighbors){
                numberOfNeighbors = perCountryNeighbor;
                fortifyCountry = country;
            }
        }
        return fortifyCountry;
    }

    /**
     * Finds the country to fortify from.
     * @param destinationCountry Country to fortify.
     * @return Countyr to fortify from.
     */
    public Country findOriginCountry(Country destinationCountry){
        MapValidation mv = new MapValidation();
        int maxArmies = 0;
        Country originCountry = null;
        for(Country neighbor : destinationCountry.getNeighbours().values()){
            if(getOwnedCountries().containsKey(neighbor.getCountryName().toLowerCase())){
                if(neighbor.getNumberOfArmies()>maxArmies && mv.fortificationConnectivityCheck(this, destinationCountry, neighbor)){
                    maxArmies = neighbor.getNumberOfArmies();
                    originCountry = neighbor

                }
            }
        }
        return originCountry;
    }
}

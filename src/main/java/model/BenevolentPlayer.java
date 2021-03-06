package main.java.model;

import java.util.Collection;

public class BenevolentPlayer extends Player {

    /**
     * Represents object of GameActions
     */
    GameActions gameActions;

    /**
     * Creates a player with the argument player name and sets default value for rest of the player fields.
     *
     * @param playerName name of player
     */
    public BenevolentPlayer(String playerName) {

        super(playerName);
        gameActions = new GameActions();
    }

    /**
     * Reinforcement phase for the benevolent player
     * @param game Represents the state of the game
     * @param country Represents country on which armies placed
     * @param num Reinforce this many armies
     * @return true if reinforcement successful else false
     */
    @Override
    public boolean reinforce(GameData game, String country, int num) {

        gameActions.assignReinforcementArmies(game, this);
        addCardExchangeArmies(game);

        game.setActivePlayer(this);
        Country weakestCountry = getWeakestCountry(this);

        num = this.getOwnedArmies();
        weakestCountry.setNumberOfArmies(weakestCountry.getNumberOfArmies() + num);
        this.setOwnedArmies(0);
        game.getLogger().info(num + " armies reinforced at " + weakestCountry.getCountryName() + ". Remaining reinforcement armies: " + this.getOwnedArmies() + "\n");
        notifyObservers(num + " armies reinforced at " + weakestCountry.getCountryName() + ". Remaining reinforcement armies: " + this.getOwnedArmies() + "\n");
        game.setGamePhase(Phase.ATTACK);

        return true;
    }

    /**
     * Attack pahse for the benevolent player
     * @param game Represents the state of the game.
     * @param countryFrom Country doing the attack
     * @param countryTo Country that is defending
     * @param numberOfDice Number of dice attacker wishes to roll
     * @param defendDice Number of dice defender wishes to roll
     * @param defendingPlayer Player owning the defending country
     * @return true if attack is successful else false
     */
    @Override
    public boolean attack(GameData game, String countryFrom, String countryTo, int numberOfDice, int defendDice, Player defendingPlayer) {
        game.getLogger().info(this.getPlayerName() + " decides not to attack.");
        gameActions.endAttack(game);
        return true;
    }

    /**
     * Fortification phase for benevolent player
     * @param game represents state of the game
     * @param fromCountry country from armies send
     * @param toCountry country to armies placed
     * @param num total number of armies to send from one country to another country
     * @return FortificationCheck.FORTIFICATIONSUCCESS if fortification successful, else appropriate error message
     */
    @Override
    public FortificationCheck fortify(GameData game, String fromCountry, String toCountry, int num) {

        String fortifyData = fortifyData(this);

        if(!fortifyData.equals("No connectivity found")) {
            String[] splitted = fortifyData.split("\\s+");
            fromCountry = splitted[0];
            toCountry = splitted[1];

            int fromArmies = this.getOwnedCountries().get(fromCountry.toLowerCase()).getNumberOfArmies();
            int toArmies = this.getOwnedCountries().get(toCountry.toLowerCase()).getNumberOfArmies();
            num = fromArmies - 1;
            toArmies += (fromArmies - 1);
            fromArmies = 1;
            this.getOwnedCountries().get(fromCountry.toLowerCase()).setNumberOfArmies(fromArmies);
            this.getOwnedCountries().get(toCountry.toLowerCase()).setNumberOfArmies(toArmies);
            game.getLogger().info(this.getPlayerName() + " fortified " + toCountry + " with " + num + " armies from " + fromCountry + ". " + this.getPlayerName() + "'s turn ends now.");
            notifyObservers(this.getPlayerName() + " fortified " + toCountry + " with " + num + " armies from " + fromCountry + ". " + this.getPlayerName() + "'s turn ends now.");
            game.setGamePhase(Phase.TURNEND);
            return FortificationCheck.FORTIFICATIONSUCCESS;
        }else{
            game.getLogger().info("No fortification.");
            this.fortify(game);
            return FortificationCheck.FORTIFICATIONSUCCESS;
        }
    }

    /**
     * get the to and from country for fortification phase for benevolent player
     * @param player Represents object of the player
     * @return string contains fromCountry and toCountry separated by blank space
     */
    public String fortifyData(Player player) {

        Collection<Country> countries = player.getOwnedCountries().values();
        Country[] targetCountries = countries.toArray(new Country[0]);

        int length = targetCountries.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (targetCountries[j].getNumberOfArmies() > targetCountries[j + 1].getNumberOfArmies()) {
                    // swap arr[j+1] and arr[i]
                    Country temp = targetCountries[j];
                    targetCountries[j] = targetCountries[j + 1];
                    targetCountries[j + 1] = temp;
                }
            }
        }

        Country[] sourceCountries = countries.toArray(new Country[0]);
        int lengthSources = sourceCountries.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (sourceCountries[j].getNumberOfArmies() < sourceCountries[j + 1].getNumberOfArmies()) {
                    // swap arr[j+1] and arr[i]
                    Country temp = sourceCountries[j];
                    sourceCountries[j] = sourceCountries[j + 1];
                    sourceCountries[j + 1] = temp;
                }
            }
        }

        MapValidation mv = new MapValidation();
        boolean check;
        String fromAndToCountries = "No connectivity found";
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < lengthSources; j++) {
                if(sourceCountries[j]!=targetCountries[i]){
                    check = mv.fortificationConnectivityCheck(player, sourceCountries[j].getCountryName(), targetCountries[i].getCountryName());
                    if (check) {
                        fromAndToCountries = sourceCountries[j].getCountryName() + " " + targetCountries[i].getCountryName();
                        return fromAndToCountries;
                    }else {
                        fromAndToCountries = "No connectivity found";
                    }
                }

            }
        }
        return fromAndToCountries;
    }

    /**
     * Get the weakest country of the player
     * @param player Represents the object of the player
     * @return object of weakest country of player
     */
    public Country getWeakestCountry(Player player) {
        Collection<Country> countries = player.getOwnedCountries().values();
        Country weakest_country = null;
        int min_army = 500;
        for (Country country : countries) {
            int army = country.getNumberOfArmies();
            if (army < min_army) {
                min_army = army;
                weakest_country = country;
            }
        }
        if (weakest_country == null) {
            weakest_country = getOwnedCountries().get(0);
        }
        return weakest_country;
    }
}

package main.java.model;

import java.util.Collection;

public class BenevolentPlayer extends Player {

    GameActions gameActions;
    String weakestCountry;

    /**
     * Creates a player with the argument player name and sets default value for rest of the player fields.
     *
     * @param playerName name of player
     */
    public BenevolentPlayer(String playerName) {
        super(playerName);
    }

    @Override
    public boolean reinforce(GameData game, String country, int num) {

        gameActions.assignReinforcementArmies(game, this);
        this.cardExchange(game, null);

        game.setActivePlayer(this);
        weakestCountry = getWeakestCountry(this).getCountryName();

        if (this.getOwnedArmies() > 0) {
            Country c = this.getOwnedCountries().get(weakestCountry.toLowerCase());
            int existingArmies = c.getNumberOfArmies();
            existingArmies += this.getOwnedArmies();
            c.setNumberOfArmies(existingArmies);
            this.setOwnedArmies(this.getOwnedArmies());
            notifyObservers(Integer.toString(this.getOwnedArmies()) + " armies reinforced at " + weakestCountry + ". Remaining reinforcement armies: " + Integer.toString(this.getOwnedArmies()) + "\n");
            game.setGamePhase(Phase.ATTACK);
        } else {
            notifyObservers(this.getPlayerName() + " doesn't have " + this.getOwnedArmies() + " armies to reinforce. Invalid command.");
        }

        return true;
    }


    @Override
    public boolean attack(GameData game, String countryFrom, String countryTo, int numberOfDice, int defendDice, Player defendingPlayer) {

        gameActions.endAttack(game);
        return true;
    }

    @Override
    public FortificationCheck fortify(GameData game, String fromCountry, String toCountry, int num) {

        String fortifyData = fortifyData(this);

        if(!fortifyData.equals("No connectivity found")) {
            String[] splitted = fortifyData.split("\\s+");
            fromCountry = splitted[0];
            toCountry = splitted[1];

            int fromArmies = this.getOwnedCountries().get(fromCountry.toLowerCase()).getNumberOfArmies();
            int toArmies = this.getOwnedCountries().get(toCountry.toLowerCase()).getNumberOfArmies();
            toArmies += (fromArmies - 1);
            fromArmies = 1;
            this.getOwnedCountries().get(fromCountry.toLowerCase()).setNumberOfArmies(fromArmies);
            this.getOwnedCountries().get(toCountry.toLowerCase()).setNumberOfArmies(toArmies);
            notifyObservers(this.getPlayerName() + " fortified " + toCountry + " with " + toArmies + " armies from " + fromCountry + ". " + this.getPlayerName() + "'s turn ends now.");
            game.setGamePhase(Phase.TURNEND);
            return FortificationCheck.FORTIFICATIONSUCCESS;
        }else{
            this.fortify(game);
            return FortificationCheck.PATHFAIL;
        }
    }

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
                if (sourceCountries[j].getNumberOfArmies() > sourceCountries[j + 1].getNumberOfArmies()) {
                    // swap arr[j+1] and arr[i]
                    Country temp = sourceCountries[j];
                    sourceCountries[j] = sourceCountries[j + 1];
                    sourceCountries[j + 1] = temp;
                }
            }
        }

        MapValidation mv = new MapValidation();
        boolean check;
        String fromAndToCountries = null;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < lengthSources; j++) {
                check = mv.fortificationConnectivityCheck(player, sourceCountries[j].getCountryName(), targetCountries[i].getCountryName());
                if (check) {
                    fromAndToCountries = sourceCountries[j].getCountryName() + " " + targetCountries[i].getCountryName();
                }else {
                    fromAndToCountries = "No connectivity found";
                }
            }
        }
        return fromAndToCountries;
    }

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

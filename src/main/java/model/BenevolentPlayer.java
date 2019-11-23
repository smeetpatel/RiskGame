package main.java.model;

import java.util.Collection;

public class BenevolentPlayer extends Player {

    BenevolentPlayer benevolentPlayer;
    Player player;
    String weakestCountry;

    /**
     * Creates a player with the argument player name and sets default value for rest of the player fields.
     *
     * @param playerName name of player
     */
    public BenevolentPlayer(String playerName) {
        super(playerName);
        player = new BenevolentPlayer(playerName);
    }

    @Override
    public boolean reinforce(GameData game, String country, int num) {
        setOwnedArmies(player.getOwnedArmies());
        game.setActivePlayer(player);
        weakestCountry = getWeakestCountry(player).getCountryName();

        if (player.getOwnedArmies() > 0) {
            Country c = player.getOwnedCountries().get(weakestCountry.toLowerCase());
            int existingArmies = c.getNumberOfArmies();
            existingArmies += player.getOwnedArmies();
            c.setNumberOfArmies(existingArmies);
            player.setOwnedArmies(player.getOwnedArmies());
            notifyObservers(Integer.toString(player.getOwnedArmies()) + " armies reinforced at " + weakestCountry + ". Remaining reinforcement armies: " + Integer.toString(player.getOwnedArmies()) + "\n");
            game.setGamePhase(Phase.ATTACK);
        } else {
            notifyObservers(player.getPlayerName() + " doesn't have " + player.getOwnedArmies() + " armies to reinforce. Invalid command.");
        }
    }


    @Override
    public boolean attack(GameData game, String countryFrom, String countryTo, int numberOfDice, int defendDice, Player defendingPlayer) {

    }

    @Override
    public FortificationCheck fortify(GameData game, String fromCountry, String toCountry, int num){

    }

    public Country getWeakestCountry(Player player){
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

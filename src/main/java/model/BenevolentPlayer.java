package main.java.model;

import java.util.Collection;

public class BenevolentPlayer extends Player {

    @Override
    public void reinforce(GameData game, Country weakestCountry, Player player) {
        ownedArmies = player.getOwnedArmies();

        game.setActivePlayer(player);

        if (this.ownedArmies > 0) {
            int existingArmies = weakestCountry.getNumberOfArmies();
            existingArmies += ownedArmies;
            weakestCountry.setNumberOfArmies(existingArmies);
            player.setOwnedArmies(player.getOwnedArmies() - ownedArmies);
            notifyObservers(Integer.toString(ownedArmies) + " armies reinforced at " + weakestCountry.getCountryName() + ". Remaining reinforcement armies: " + Integer.toString(player.getOwnedArmies()) + "\n");
            if (this.ownedArmies == 0) {
                game.setGamePhase(Phase.ATTACK);
            }
        } else {
            notifyObservers(player.getPlayerName() + " doesn't have " + ownedArmies + " armies to reinforce. Invalid command.");
        }
    }


    @Override
    public void attack() {

    }

    @Override
    public void fortify(GameData game, String fromCountry, String toCountry, Player currentPlayer) {

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

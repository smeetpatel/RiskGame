package main.java.model;

public class BenevolentPlayer extends Observable implements PlayerStrategy {

    private int ownedArmies;

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
}

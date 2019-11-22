package main.java.model;

public interface PlayerStrategy {

    public void reinforce(GameData game, Country weakestCountry, Player currentPlayer);

    public void attack();

    public void fortify(GameData game, String fromCountry, String toCountry, Player currentPlayer);
}

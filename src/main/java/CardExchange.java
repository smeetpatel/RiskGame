package main.java;

import java.util.ArrayList;

public class CardExchange {

    CardExchange ce = new CardExchange();
    int cardExchangeArmies = 5;
    /**
     * This method determine whether player need to do a trade in operation.
     *
     * @param player object of player
     */
    /*public static int checkNoOfCards(Player player) {
            return player.getOwnedCards().size();
        }*/

    /**
     * This method will perform trade in operation of cards.
     *
     * @param player object of player
     */
    public boolean cardTradeIn(Player player, ArrayList<Integer> cardIndex) {
        if (cardIndex.get(0) != cardIndex.get(1) &&  cardIndex.get(1)!= cardIndex.get(2) && cardIndex.get(2)!= cardIndex.get(0)) {
            System.out.println(cardIndex);
            if (player.getOwnedCards().get(cardIndex.get(0) - 1).cardType.equals(player.getOwnedCards().get(cardIndex.get(1) - 1).cardType) &&
                    player.getOwnedCards().get(cardIndex.get(0) - 1).cardType.equals(player.getOwnedCards().get(cardIndex.get(2) - 1).cardType)) {
                checkForOwnedCardCountry(player, cardIndex);
                player.setOwnedArmies(player.getOwnedArmies() + cardExchangeArmies);
                cardExchangeArmies+=5;
                removeCardFromPlayer(player, cardIndex);
                return true;
            } else if (!player.getOwnedCards().get(cardIndex.get(0) - 1).cardType.equals(player.getOwnedCards().get(cardIndex.get(1) - 1).cardType) &&
                    !player.getOwnedCards().get(cardIndex.get(0) - 1).cardType.equals(player.getOwnedCards().get(cardIndex.get(2) - 1).cardType) &&
                    !player.getOwnedCards().get(cardIndex.get(1) - 1).cardType.equals(player.getOwnedCards().get(cardIndex.get(2) - 1).cardType)) {
                checkForOwnedCardCountry(player, cardIndex);
                player.setOwnedArmies(player.getOwnedArmies() + cardExchangeArmies);
                cardExchangeArmies+=5;
                removeCardFromPlayer(player, cardIndex);
                return true;
            } else {
                System.out.println("You do not have cards that could be exchanged.");
                return false;
            }
        }else{
            System.out.println("Selected index numbers are wrong.Enter valid index numbers.");
            return false;
        }
    }

    /**
     * This method prints the cards owned by player.
     */

    public void printPlayersCards(Player player){
        int index = 1;
        System.out.println("Select any three cards from given cards.");
        for (Card c : player.getOwnedCards()) {
            System.out.println(index + ". " + c.cardCountry + " - " + c.cardType);
            index++;
        }
    }
    /**
     * This method checks whether player owns any country from selected cards.
     * @param player object of current player
     * @param cardIndex stores the arraylist of card index
     */
    public void checkForOwnedCardCountry(Player player, ArrayList<Integer> cardIndex){
        for (int i = 0; i < 3; i++) {
            Country country = player.getOwnedCards().get(cardIndex.get(i)-1).cardCountry; // fetch the country object from player's card.
            for (Country c : player.getOwnedCountries().values()) {
                if (c == country) {
                    player.setOwnedArmies(player.getOwnedArmies() + 2);
                    break;
                }
            }
        }
    }

    /**
     * This method removes already exchanged card from the player's ownedCard list.
     * @param player object of current player
     * @param cardIndex list of index of cards
     */
    public void removeCardFromPlayer(Player player, ArrayList<Integer> cardIndex){
        for (int i=cardIndex.size()-1;i>=0;i--){
            System.out.println(cardIndex.get(i)-1);
            player.removeOwnedCards(player.getOwnedCards().get(cardIndex.get(i)-1));
        }
    }
}

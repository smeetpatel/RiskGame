package main.java;

import java.util.ArrayList;

/**
 * It will implement deck of card.
 *
 * @author Tirth
 */
public class Deck {

    /**
     * List of cards in the deck.
     */
    private ArrayList<Card> deck;

    /**
     * Array of type of card.
     */
    String[] cardType;

    /**
     * Object of a Card which stores in the deck.
     */
    Card card;

    /**
     * Card withdrawn by after after conquering country.
     */
    Card withdrawCard;

    /**
     * This default constructor will initialize the deck of cards.
     */
    public Deck() {
        deck = new ArrayList<>();
    }

    /**
     * This constructor will create deck from list of countries.
     *
     * @param countries list of countires
     */
    public Deck(ArrayList<Country> countries) {
        deck = new ArrayList<>();
        cardType = new String[]{"Infantry", "Cavalary", "Artillery"};
        for (int i = 0; i < countries.size(); i++) {
            card = new Card(cardType[i % 3], countries.get(i));
            //System.out.println(countries.get(i).getCountryName() + " " + cardType[i % 3]);
            deck.add(card);
        }
    }

    /**
     * This method will add the card to the existing deck.
     *
     * @param card object of Card, which will added to the deck
     */
    public void addCard(Card card) {
        deck.add(card);
    }

    /**
     * This method will withdraw a card form deck.
     *
     * @return object of card which is withdrawn
     */
    public Card withdrawCard() {
        withdrawCard = deck.get(0);
        deck.remove(withdrawCard);
        return withdrawCard;
    }

    // For testing purpose.
    public void print() {
        for (Card c : deck)
            System.out.println(c.cardCountry.getCountryName()+" "+c.cardType);
    }

}
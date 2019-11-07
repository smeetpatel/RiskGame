package main.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

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
        deck = new ArrayList<Card>();
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

    /**
     * get the size of a deck
     * @return number of cards in the deck
     */
    public int getDeckSize(){
        return deck.size();
    }

    /**
     * method for creation of deck
     * @param countries list of countries
     */
    public void createDeck(Collection<Country> countries) {
        cardType = new String[]{"Infantry", "Cavalary", "Artillery"};
        int i = 0;
        Card wildCard1 = new Card("WildCard", null);
        Card wildCard2 = new Card("WildCard", null);
        deck.add(wildCard1);
        for(Country country : countries){
            card = new Card(cardType[i%3], country);
            deck.add(card);
            i++;
        }
        deck.add(wildCard1);
    }
}
package main.java.model;

import java.io.Serializable;

/** This class will perform operations on cards.
 * @author Tirth
 */

public class Card implements Serializable {

    /**
     * Type of card from Infantry, Cavalary and Artillery.
     */
    private String cardType;

    /**
     * Object of Country of the country which is on the card.
     */
    private Country cardCountry;

    /**
     * Default constructor of Card to access the methods of this class.
     */
    public Card(){

    }
    /** This constructor will assign type ans country of cards.
     *
     * @param cardType card type
     * @param cardCountry country's name
     */
    public Card(String cardType, Country cardCountry){
        this.cardType = cardType;
        this.cardCountry = cardCountry;
    }

    /** The method gives type of a card.
     *
     * @return string card type
     */
    public String getCardType(){
        return cardType;
    }

    /** The method gives country of card.
     *
     * @return object of country
     */
    public Country getCardCountry(){
        return cardCountry;
    }

    /** The method gives type country of card.
     *
     * @return name of country and type of card
     */
    public String getCard(){
        return cardCountry+" - "+ cardType;
    }

}
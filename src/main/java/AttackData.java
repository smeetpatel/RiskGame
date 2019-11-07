package main.java;

/**
 * The following class stores the data of the attack phase.
 */
public class AttackData {

    /**
     * the number of dices
     */
    private int numberOfDice;

    /**
     * attacker country of the player
     */
    private  String fromCountry;

    /**
     * defender country of the map
     */
    private String toCountry;

    /**
     * variable shows that player can attack or not
     */
    private boolean canAttack;

    /**
     * variable shows that possibility of troops to sent on conquered country
     */
    private boolean sendConqueringTroops;

    /**
     * number of countries that has been conquered
     */
    private int territoriesConquered;

    /**
     * boolean variable for card exchange condition
     */
    private boolean cardExchange;

    /**
     * Initializ the variable of this class
     */
    public AttackData(){
        this.numberOfDice = 0;
        this.fromCountry = "";
        this.toCountry = "";
        this.canAttack = false;
        this.sendConqueringTroops = false;
        this.territoriesConquered = 0;
        this.cardExchange = false;
    }

    /**
     * get the number of dice
     * @return total number of dice
     */
    public int getNumberOfDice() {
        return numberOfDice;
    }

    /**
     * Set the number of dice
     * @param numberOfDice total number of dice
     */
    public void setNumberOfDice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }

    /**
     * return the attacker country
     * @return object of country
     */
    public String getFromCountry() {
        return fromCountry;
    }

    /**
     * set the attacker country
     * @param fromCountry object of country
     */
    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    /**
     * get the defender country
     * @return object of country
     */
    public String getToCountry() {
        return toCountry;
    }

    /**
     * set the defender country
     * @param toCountry object of country
     */
    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    /**
     * return that attack is or not
     * @return true if attack possible or false if attack not possible
     */
    public boolean getCanAttack() {
        return canAttack;
    }

    /**
     * set that attack is possible or not
     * @param canAttack set true if attack possible or false if attack not possible
     */
    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    /**
     * get that possible to sent troops to conquered country
     * @return true if possible else false
     */
    public boolean getSendConqueringTroops() {
        return sendConqueringTroops;
    }

    /**
     * set true if possible to sent troops to conquered country else false
     * @param sendConqueringTroops true if possible to sent troops to conquered country else false
     */
    public void setSendConqueringTroops(boolean sendConqueringTroops) {
        this.sendConqueringTroops = sendConqueringTroops;
    }

    /**
     * get the number of territories that has been conquered
     * @return the number of territories
     */
    public int getTerritoriesConquered() {
        return territoriesConquered;
    }

    /**
     * set the number of territories that has been conquered
     * @param territoriesConquered number of territories
     */
    public void setTerritoriesConquered(int territoriesConquered) {
        this.territoriesConquered = territoriesConquered;
    }

    /**
     * get whether cardexchange is possible or not
     * @return return true if cardexchange is possible else false
     */
    public boolean isCardExchange() {
        return cardExchange;
    }

    /**
     * set that cardexchange is possible or not
     * @param cardExchange set true if cardexchange is possible else false
     */
    public void setCardExchange(boolean cardExchange) {
        this.cardExchange = cardExchange;
    }

    /**
     * set the variables at the end of the attack phase
     */
    public void resetAttack(){
        this.numberOfDice = 0;
        this.fromCountry = "";
        this.toCountry = "";
        this.canAttack = false;
        this.sendConqueringTroops = false;
        this.territoriesConquered = 0;
    }
}

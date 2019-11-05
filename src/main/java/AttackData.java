package main.java;

public class AttackData {
    private int numberOfDice;
    private  String fromCountry;
    private String toCountry;
    private boolean canAttack;
    private boolean sendConqueringTroops;
    private int territoriesConquered;
    private boolean cardExchange;

    public AttackData(){
        this.numberOfDice = 0;
        this.fromCountry = "";
        this.toCountry = "";
        this.canAttack = false;
        this.sendConqueringTroops = false;
        this.territoriesConquered = 0;
        this.cardExchange = false;
    }

    public int getNumberOfDice() {
        return numberOfDice;
    }

    public void setNumberOfDice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public boolean getCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public boolean getSendConqueringTroops() {
        return sendConqueringTroops;
    }

    public void setSendConqueringTroops(boolean sendConqueringTroops) {
        this.sendConqueringTroops = sendConqueringTroops;
    }

    public int getTerritoriesConquered() {
        return territoriesConquered;
    }

    public void setTerritoriesConquered(int territoriesConquered) {
        this.territoriesConquered = territoriesConquered;
    }

    public boolean isCardExchange() {
        return cardExchange;
    }

    public void setCardExchange(boolean cardExchange) {
        this.cardExchange = cardExchange;
    }

    public void resetAttack(){
        this.numberOfDice = 0;
        this.fromCountry = "";
        this.toCountry = "";
        this.canAttack = false;
        this.sendConqueringTroops = false;
        this.territoriesConquered = 0;
    }
}

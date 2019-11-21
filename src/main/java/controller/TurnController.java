package main.java.controller;

import main.java.model.*;
import main.java.view.AttackView;
import main.java.view.CardExchangeView;
import main.java.view.MapView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages commands related to three phases of each turn of the game.
 */
public class TurnController extends Controller{

    /**
     * Helps access methods to view map.
     */
    public MapView mapView;

    /**
     * Represents the 'Card exchange view'
     */
    public CardExchangeView cardExchangeView;

    /**
     * Represents possible attack options for a player.
     */
    public AttackView attackView;

    /**
     * Represents the state of the game when an attack is declared or carried out.
     */
    public AttackData attackData;

    /**
     * Intializes required class variables.
     */
    public TurnController(GameData game){
        this.game = game;
        gameAction = new GameActions();
        mapView = new MapView();
        attackView = new AttackView();
        attackData = new AttackData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String parseCommand(Player player, String newCommand) {
        int numberOfArmies;
        int armiesToFortify;
        String message = "";
        String countryName;
        String fromCountry;
        String toCountry;
        String[] data = newCommand.split("\\s+");
        String commandName = data[0];
        if (game.getGamePhase().equals(Phase.CARDEXCHANGE)) {
            switch (commandName) {
                case "exchangecards":
                    try {
                        if (data[1].equals("-none")) {
                            if(gameAction.noCardExchange(game, player)){
                                message = "Player do not want to perform card exchange operation or player do " +
                                        "not have enough cards to exchange.";
                                player.detach(cardExchangeView);
                                cardExchangeView.setVisible(false);
                                cardExchangeView.dispose();
                            } else {
                                message = "Player has to exchange the cards.";
                            }
                        } else {
                            if (!(data[1] == null) && !(data[2] == null) && !(data[3] == null)) {
                                if (data[1].matches("[1-9]+") && data[2].matches("[1-9]+") && data[3].matches("[1-9]+")) {
                                    int firstCard = Integer.parseInt(data[1]);
                                    int secondCard = Integer.parseInt(data[2]);
                                    int thirdCard = Integer.parseInt(data[3]);
                                    int totalCards = player.getOwnedCards().size();
                                    if (firstCard <= totalCards && secondCard <= totalCards && thirdCard <= totalCards) {
                                        ArrayList<Integer> cardIndex = new ArrayList<Integer>();
                                        cardIndex.add(firstCard);
                                        cardIndex.add(secondCard);
                                        cardIndex.add(thirdCard);
                                        Collections.sort(cardIndex);
                                        if(player.cardExchange(game, cardIndex)){
                                            message = "Card Exchange successfully occurred";
                                            if(player.getOwnedCards().size()<=2){
                                                if(gameAction.noCardExchange(game, player)){
                                                    message = "Player do not want to perform card exchange operation or player do " +
                                                            "not have enough cards to exchange.";
                                                    player.detach(cardExchangeView);
                                                    cardExchangeView.setVisible(false);
                                                    cardExchangeView.dispose();
                                                }
                                            }

                                        } else {
                                            message = "Invalid exchange command.";
                                        }
                                    } else {
                                        message = "Index number of card is wrong";
                                    }
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'exchangecards num num num -none'";
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'exchangecards num num num -none'";
                    }
                    break;
                default:
                    message = "Invalid command - use exchangecards command.";
                    break;
            }
        } else if (game.getGamePhase().equals(Phase.REINFORCEMENT)) {
            switch (commandName) {
                case "reinforce":
                    try {
                        if (!(data[1] == null) && !(data[2] == null)) {
                            if (this.isAlpha(data[1]) && data[2].matches("[0-9]+")) {
                                countryName = data[1];
                                numberOfArmies = Integer.parseInt(data[2]);
                                boolean check = player.reinforce(game, countryName, numberOfArmies);
                                if (check) {
                                    if (player.getOwnedArmies() == 0) {
                                        message = "Reinforcement phase successfully ended. Begin attack now.";
                                        if (player.isAttackPossible()) {
                                            attackView.canAttack(player);
                                        } else {
                                            gameAction.endAttack(game);
                                        }
                                    }
                                } else {
                                    if (player.getOwnedArmies() < numberOfArmies) {
                                        message = "You don't have enough armies";
                                    } else {
                                        message = "You don't own this country";
                                    }
                                }
                            } else {
                                message = "Invalid command - invalid characters in command";
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'reinforce countryName num'";
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'reinforce countryName num'";
                    }
                    break;

                case "showmap":
                    mapView.showMap(game.getMap(), game.getPlayers());
                    break;

                default:
                    message = "Invalid command - either use reinforce or showmap commands in reinforcement phase.";
                    break;
            }
        } else if (game.getGamePhase().equals(Phase.ATTACK)) {
            switch (commandName){
                case "attack":
                    try{
                        if(data[1].equals("-noattack")){
                            if(!attackData.getSendConqueringTroops()){
                                gameAction.endAttack(game);
                                if(attackData.getTerritoriesConquered()>0){
                                    player.setOwnedCards(game.getDeck().withdrawCard());
                                }
                                attackData.resetAttack();
                                message = "Player do not want to perform attack";
                            } else {
                                message = "Must move army to just conquered country first. Use 'attackmove num' command.";
                            }
                        }else if(data.length == 4){
                            if(!attackData.getSendConqueringTroops()){
                                if(!(data[1] == null) && !(data[2] == null) && !(data[3] == null)){
                                    if(this.isAlpha(data[1]) && this.isAlpha(data[2]) && data[3].matches("[1-3]")){
                                        attackData.setFromCountry(data[1]);
                                        attackData.setToCountry(data[2]);
                                        attackData.setNumberOfDice(Integer.parseInt(data[3]));
                                        if(gameAction.areNeighbors(game, player, attackData.getFromCountry(), attackData.getToCountry())){
                                            if(gameAction.hasEnoughArmies(game, attackData.getFromCountry())){
                                                if(gameAction.diceValid(game, attackData.getFromCountry(), attackData.getNumberOfDice(), true)){
                                                    attackData.setCanAttack(true);
                                                } else {
                                                    message = attackData.getNumberOfDice() + " dice rolls not possible for attack from " + attackData.getFromCountry();
                                                }
                                            } else {
                                                message = attackData.getFromCountry() + " does not have enough armies to attack. Attack not possible.";
                                            }
                                        } else {
                                            message = attackData.getFromCountry() + " and " + attackData.getToCountry() + " are not neighbors or belong to the same player. Attack not possible.";
                                        }
                                    }
                                } else {
                                    message = "Invalid command  it should be of the form 'attack countrynamefrom countynameto numdice allout'" +
                                            " or 'attack noattack'";
                                }
                            } else {
                                message = "Must move army to just conquered country first. Use 'attackmove num' command.";
                            }

                        }else if(data.length == 5){
                            if(!attackData.getSendConqueringTroops()){
                                if(!(data[1] == null) && !(data[2] == null) && !(data[3] == null) && !(data[4] == null)){
                                    if(this.isAlpha(data[1]) && this.isAlpha(data[2]) && data[3].matches("[1-3]") && data[4].equals("-allout")){
                                        attackData.setFromCountry(data[1]);
                                        attackData.setToCountry(data[2]);
                                        attackData.setNumberOfDice(Integer.parseInt(data[3]));
                                        if(gameAction.areNeighbors(game, player, attackData.getFromCountry(), attackData.getToCountry())){
                                            if(gameAction.hasEnoughArmies(game, attackData.getFromCountry())){
                                                if(gameAction.diceValid(game, attackData.getFromCountry(), attackData.getNumberOfDice(), true)){
                                                    Player p = gameAction.getOwner(game, attackData.getToCountry());
                                                    do{
                                                        attackData.setNumberOfDice(gameAction.getMaxDiceRolls(game, attackData.getFromCountry(), "attacker"));
                                                        int defendDice = gameAction.getMaxDiceRolls(game, attackData.getFromCountry(), "defender");
                                                        if(player.attack(game, attackData.getFromCountry(), attackData.getToCountry(), attackData.getNumberOfDice(), defendDice, p)){
                                                            message = player.getPlayerName() + " has successfully conquered " + attackData.getToCountry();
                                                            if(player.getOwnedCountries().size()==game.getMap().getCountries().size()){
                                                                gameAction.endGame(game);
                                                                return message;
                                                            }
                                                            gameAction.checkContinentOwnership(game, player);
                                                            //move troops to newly conquered country
                                                            attackData.setSendConqueringTroops(true);
                                                            attackData.setTerritoriesConquered(attackData.getTerritoriesConquered()+1);
                                                            //get cards
                                                            if(p.getOwnedCountries().size()==0){
                                                                gameAction.getAllCards(player, p);
                                                                game.removePlayer(p);
                                                                if(player.getOwnedCards().size()>=6){
                                                                    gameAction.setAttackCardExchange(game);
                                                                    cardExchangeView = new CardExchangeView();
                                                                    cardExchangeView.setVisible(true);
                                                                    cardExchangeView.setSize(600, 600);
                                                                    player.attach(cardExchangeView);
                                                                    gameAction.initializeCEV(player);
                                                                }
                                                            }
                                                        } else {
                                                            if(player.isAttackPossible()){
                                                                attackView.canAttack(player);
                                                            } else {
                                                                if(attackData.getTerritoriesConquered()>0){
                                                                    player.setOwnedCards(game.getDeck().withdrawCard());
                                                                }
                                                                attackData.resetAttack();
                                                                gameAction.endAttack(game);
                                                            }
                                                        }
                                                    } while(!attackData.getSendConqueringTroops() && gameAction.getMaxDiceRolls(game, attackData.getFromCountry(), "attacker")!=0);
                                                    if(attackData.getSendConqueringTroops()){
                                                        gameAction.calculateMapControlled(game, player);
                                                        gameAction.calculateMapControlled(game, p);
                                                    }
                                                } else {
                                                    message = attackData.getNumberOfDice() + " dice rolls not possible for attack from " + attackData.getFromCountry();
                                                }
                                            } else {
                                                message = attackData.getFromCountry() + " does not have enough armies to attack. Attack not possible.";
                                            }
                                        } else {
                                            message = attackData.getFromCountry() + " and " + attackData.getToCountry() + " are not neighbors or belong to the same player. Attack not possible.";
                                        }
                                    } else{
                                        message = "Invalid command  it should be of the form 'attack countrynamefrom countynameto numdice allout'" +
                                                " or 'attack noattack'";
                                    }
                                }
                                else{
                                    message = "Invalid command  it should be of the form 'attack countrynamefrom countynameto numdice allout'" +
                                            " or 'attack noattack'";
                                }
                            } else {
                                message = "Must move army to just conquered country first. Use 'attackmove num' command.";
                            }
                        } else {
                            message = "Invalid command  it should be of the form 'attack countrynamefrom countynameto numdice allout'" +
                                    " or 'attack noattack'";
                        }
                    }catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command  it should be of the form 'attack countrynamefrom countynameto numdice allout'" +
                                " or 'attack noattack'";
                    } catch (NumberFormatException e) {
                        message = "Invalid command  it should be of the form 'attack countrynamefrom countynameto numdice allout'" +
                                " or 'attack noattack'";
                    } catch (Exception e) {
                        message = "Invalid command. It should be of the form 'attack countrynamefrom countynameto numdice allout'" +
                                " or 'attack noattack'";
                    }
                    break;
                case "defend":
                    try {
                        if(!attackData.getSendConqueringTroops()){
                            if(attackData.getCanAttack()) {
                                if (!(data[1] == null)) {
                                    if (data[1].matches("[1-2]")) {
                                        Player p = gameAction.getOwner(game, attackData.getToCountry());
                                        int defendDice = Integer.parseInt(data[1]);
                                        if(gameAction.diceValid(game, attackData.getToCountry(), defendDice, false)) {
                                            if(player.attack(game, attackData.getFromCountry(), attackData.getToCountry(), attackData.getNumberOfDice(), defendDice, p)){
                                                message = player.getPlayerName() + " has successfully conquered " + attackData.getToCountry();
                                                if(player.getOwnedCountries().size()==game.getMap().getCountries().size()){
                                                    gameAction.endGame(game);
                                                    return message;
                                                }
                                                gameAction.calculateMapControlled(game, player);
                                                gameAction.calculateMapControlled(game, p);
                                                gameAction.checkContinentOwnership(game, player);
                                                attackData.setTerritoriesConquered(attackData.getTerritoriesConquered()+1);
                                                attackData.setSendConqueringTroops(true);
                                                if(p.getOwnedCountries().size()==0){
                                                    gameAction.getAllCards(player, p);
                                                    game.removePlayer(p);
                                                    if(player.getOwnedCards().size()>=6){
                                                        gameAction.setAttackCardExchange(game);
                                                        cardExchangeView = new CardExchangeView();
                                                        cardExchangeView.setVisible(true);
                                                        cardExchangeView.setSize(600, 600);
                                                        player.attach(cardExchangeView);
                                                        gameAction.initializeCEV(player);
                                                    }
                                                }
                                            } else {
                                                if(player.isAttackPossible()){
                                                    attackView.canAttack(player);
                                                } else {
                                                    if(attackData.getTerritoriesConquered()>0){
                                                        player.setOwnedCards(game.getDeck().withdrawCard());
                                                    }
                                                    attackData.resetAttack();
                                                    gameAction.endAttack(game);
                                                }
                                            }
                                            attackData.setCanAttack(false);
                                        } else {
                                            message = p.getPlayerName() + " does not have enough armies to roll dice " + defendDice + " times.";
                                            if(player.isAttackPossible()){
                                                attackView.canAttack(player);
                                            }
                                        }
                                    }else {
                                        message = "Enter valid number of dice. Enter either 1 or 2.";
                                    }
                                }else{
                                    message = "Invalid command - it should be of the form 'defend numdice'.";
                                }
                            }else{
                                message = "Before defend command, enter 'attack countrynamefrom countynameto numdice allout'.";
                            }
                        } else {
                            message = "Must move army to just conquered country first. Use 'attackmove num' command.";
                        }
                    }catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'defend numdice'. ";
                    } catch (NumberFormatException e) {
                        message = "Invalid command - it should be of the form 'defend numdice'.";
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'defend numdice'.";
                    }
                    break;

                case "attackmove":
                    try{
                        if(attackData.getSendConqueringTroops()){
                            if(!(data[1] == null)){
                                if(data[1].matches("[0-9]+")){
                                    numberOfArmies = Integer.parseInt(data[1]);
                                    if(player.moveArmy(game, attackData.getFromCountry(), attackData.getToCountry(), attackData.getNumberOfDice(), numberOfArmies)){
                                        attackData.setSendConqueringTroops(false);
                                        if(player.isAttackPossible()){
                                            attackView.canAttack(player);
                                        } else {
                                            if(attackData.getTerritoriesConquered()>0){
                                                player.setOwnedCards(game.getDeck().withdrawCard());
                                            }
                                            attackData.resetAttack();
                                            gameAction.endAttack(game);
                                        }
                                    } else {
                                        message = "Move at least " + attackData.getNumberOfDice() + " armies to " + attackData.getToCountry();
                                    }
                                }else {
                                    message = "Enter valid number of armies";
                                }
                            }else {
                                message = "Invalid command - it should be of the form 'attackmove num'.";
                            }
                        }else{
                            message = "Player did not counquered any country, so invalid command";
                        }
                    }catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'attackmove num'.";
                    } catch (NumberFormatException e) {
                        message = "Invalid command - it should be of the form 'attackmove num'.";
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'attackmove num'.";
                    }
                    break;

                case "showmap":
                    mapView.showMap(game.getMap(), game.getPlayers());
                    break;

                default:
                    message = "Invalid command - either use attack/defend/attackmove/showmap command.";
                    break;
            }
        } else if (game.getGamePhase().equals(Phase.ATTACKCARDEXCHANGE)){
            switch (commandName) {
                case "exchangecards":
                    try{
                        if (!(data[1] == null) && !(data[2] == null) && !(data[3] == null)) {
                            if (data[1].matches("[1-9]+") && data[2].matches("[1-9]+") && data[3].matches("[1-9]+")) {
                                int firstCard = Integer.parseInt(data[1]);
                                int secondCard = Integer.parseInt(data[2]);
                                int thirdCard = Integer.parseInt(data[3]);
                                int totalCards = player.getOwnedCards().size();
                                if (firstCard <= totalCards && secondCard <= totalCards && thirdCard <= totalCards) {
                                    ArrayList<Integer> cardIndex = new ArrayList<Integer>();
                                    cardIndex.add(firstCard);
                                    cardIndex.add(secondCard);
                                    cardIndex.add(thirdCard);
                                    Collections.sort(cardIndex);
                                    if(player.cardExchange(game, cardIndex)){
                                        message = "Card Exchange successfully occurred";
                                        if(!gameAction.continueCardExchange(game, player)) {
                                            message = "Required card exchange completed. Continue with attack phase. First, move armies to conquered armies.";
                                            player.detach(cardExchangeView);
                                            cardExchangeView.setVisible(false);
                                            cardExchangeView.dispose();
                                        } else {
                                            message = "You still need to exchange cards till you have four or lesser number of cards.";
                                        }
                                    } else {
                                        message = "Invalid exchange command.";
                                    }
                                }else {
                                    message = "Index number of card is wrong";
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'exchangecards num num num -none'";
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'exchangecards num num num -none'";
                    }
                    break;

                default:
                    message = "Invalid command - use 'exchangecards num num num' command only.";
                    break;
            }
        } else if (game.getGamePhase().equals(Phase.FORTIFICATION)) {
            switch (commandName) {
                case "fortify":
                    try {
                        if (data[1].equals("none")) {
                            player.fortify(game);
                            //player.detach(phaseView);
                            message = "Turn ends.";
                        } else if (!(data[1] == null) && !(data[2] == null) && !(data[3] == null)) {
                            if (this.isAlpha(data[1]) || this.isAlpha(data[2]) || data[3].matches("[0-9]+")) {
                                fromCountry = data[1];
                                toCountry = data[2];
                                armiesToFortify = Integer.parseInt(data[3]);
                                FortificationCheck check = player.fortify(game, fromCountry, toCountry, armiesToFortify);
                                if (check == FortificationCheck.FORTIFICATIONSUCCESS) {
                                    //player.detach(phaseView);
                                    message = "Successful fortification";
                                } else if (check == FortificationCheck.PATHFAIL) {
                                    message = fromCountry + " and " + toCountry + " do not have path of player owned countries.";
                                } else if (check == FortificationCheck.ARMYCOUNTFAIL) {
                                    message = "You don't have enough armies.";
                                } else if (check == FortificationCheck.TOCOUNTRYFAIL) {
                                    message = toCountry + " does not exist.";
                                } else {
                                    message = fromCountry + " does not exist.";
                                }
                            } else {
                                message = "Invalid command - invalid characters in command";
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'fortify fromCountry toCountry num' or 'foritfy none'";
                    } catch (NumberFormatException e) {
                        message = "Invalid command - it should be of the form 'fortify fromCountry toCountry num' or 'foritfy none'";
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'fortify fromCountry toCountry num' or 'foritfy none'";
                    }
                    break;

                case "showmap":
                    mapView.showMap(game.getMap(), game.getPlayers());
                    break;

                default:
                    message = "Invalid command - either use fortify/showmap command.";
                    break;
            }
        }
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameData getGame() {
        return game;
    }

    /**
     * event occur when player's turn changed
     * @param player object of player
     */
    public void playerChangeEvent(Player player) {
        gameAction.assignReinforcementArmies(game, player);
        cardExchangeView = new CardExchangeView();
        cardExchangeView.setVisible(true);
        cardExchangeView.setSize(600, 600);
        player.attach(cardExchangeView);
        gameAction.initializeCEV(player);
    }

    /**
     * method for turn end of player
     */
    public void turnEndEvent() {
        gameAction.turnEnd(game);
    }

    /**
     * Ensures string matches the defined criteria.
     *
     * @param s input string
     * @return true if valid match, else false
     */
    public boolean isAlpha(String s) {

        return s != null && s.matches("^[a-zA-Z-_]*$");
    }
}
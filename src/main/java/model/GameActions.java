package main.java.model;


import java.io.*;
import java.util.*;

/**
 * Class performs background operations for playing Risk.
 */
public class GameActions extends Observable{
    /**
     * Loads map as GameMap object for editing.
     * If the map file does not exist, creates a new GameMap object to add information.
     *
     * @param game    Representing game state
     * @param mapName Name of the map to be searched/created
     */
    public void editMap(GameData game, String mapName) {
        //Check if file exists
        String filePath = "src/main/resources/maps/" + mapName;
        GameMap map;
        File f = new File(filePath);
        if (f.exists()) {
            if(determineMapType("src/main/resources/maps/" + mapName)){
                //Domination map
                game.setMapType("domination");
                DominationMap lm = new LoadDominationMap();
                map = lm.readMap(filePath);
                map.setMapName(mapName);
            } else {
                //Conquest map
                game.setMapType("conquest");
                game.setUpLogger();
                DominationMap lm = new MapAdapter(new LoadConquestMap());
                map = lm.readMap(filePath);
                map.setMapName(mapName);
            }
        } else {
            game.setMapType("domination");
            map = new GameMap(mapName);
        }
        game.setMap(map);
        game.setGamePhase(Phase.EDITMAP);
    }

    /**
     * Determines the type of the map.
     * @param s map file path
     * @return true if Domination style map, else false indicating Conquest style map
     */
    private boolean determineMapType(String s) {
        try{
            BufferedReader reader = new BufferedReader((new FileReader(s)));
            String result = reader.readLine();
            if(result.equals("[Map]")){
                return false;
            } else {
                return true;
            }
        } catch(FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            System.out.println(e.getMessage());
        }
        catch(IOException e) {
            System.out.println("IOException");
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Loads map as GameMap object for playing the game.
     * If map file does not exist, it reflects the command as invalid command.
     *
     * @param game    Representing game state
     * @param mapName name of the map to be used for playing the game
     * @return true if map exists, else false
     */
    public boolean loadMap(GameData game, String mapName) {
        //check if file exists
        String filePath = "src/main/resources/maps/" + mapName;
        GameMap map;
        File f = new File(filePath);
        if (f.exists()) {
            if(determineMapType("src/main/resources/maps/" + mapName)){
                //Domination map
                game.setMapType("domination");
                DominationMap lm = new LoadDominationMap();
                map = lm.readMap(filePath);
                map.setMapName(mapName);
            } else {
                //Conquest map
                game.setMapType("conquest");
                DominationMap lm = new MapAdapter(new LoadConquestMap());
                map = lm.readMap(filePath);
                map.setMapName(mapName);
            }
            game.setMap(map);
            game.setUpLogger();
            if (validateMap(map) == MapValidityStatus.VALIDMAP) {
                map.setValid(true);
                game.setGamePhase(Phase.STARTUP);
                game.getDeck().createDeck(game.getMap().getCountries().values());
            } else {
                map.setValid(false);
                game.setGamePhase(Phase.NULL);
            }
        } else {
            game.setGamePhase(Phase.NULL);
            return false;
        }
        return true;
    }

    /**
     * Runs various validity checks to ensure that map is suitable for playing the game.
     * Checks:
     * 1) If any empty continent is present, i.e. continent without any country
     * 2) If each continent is a connected sub-graph
     * 3) If map for the game is connected graph or not
     *
     * @param map GameMap representing the map
     * @return returns MapValidityStatus value as VALIDMAP if it is a valid map, else appropriate error value of MapValidityStatus
     */
    public MapValidityStatus validateMap(GameMap map) {
        MapValidation mv = new MapValidation();
        if (!mv.notEmptyContinent(map)) {
            return MapValidityStatus.EMPTYCONTINENT;
        } else if (!mv.isGraphConnected(mv.createGraph(map))) {
            return MapValidityStatus.UNCONNECTEDGRAPH;
        } else if (!mv.continentConnectivityCheck(map)) {
            return MapValidityStatus.UNCONNECTEDCONTINENT;
        }
        return MapValidityStatus.VALIDMAP;
    }

    /**
     * Adds player to the game.
     * Restricts number of players to 6.
     *
     * @param players    List of players in the game
     * @param playerName Name of the player
     * @param playerStrategy Strategy used by the player
     * @return true if successful in adding the player, else false
     */
    public boolean addPlayer(ArrayList<Player> players, String playerName, String playerStrategy) {
        if (players.size() == 6) {
            return false;
        }
        Iterator<Player> itr = players.listIterator();
        while (itr.hasNext()) {
            if (itr.next().getPlayerName().equalsIgnoreCase(playerName)) {
                return false;
            }
        }

        PlayerContext playerContext = new PlayerContext();
        if(playerContext.setPlayerStrategy(playerName, playerStrategy)){
            players.add(playerContext.getPlayer());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes player from the game.
     *
     * @param players    List of players in the game
     * @param playerName Name of the player
     * @return true if successful in removing the player, else false
     */
    public boolean removePlayer(ArrayList<Player> players, String playerName) {
        Iterator<Player> itr = players.listIterator();
        while (itr.hasNext()) {
            Player p = itr.next();
            if (p.getPlayerName().equals(playerName)) {
                players.remove(p);
                return true;
            }
        }
        return false;
    }

    /**
     * Responsible for distributing countries amongst players and assigning initial armies.
     *
     * @param game    Represents the state of the game
     * @param players List of players in the game
     * @return true if successful, else false
     */
    public boolean populateCountries(GameData game, ArrayList<Player> players) {
        int numberOfPlayers = players.size();
        if (players.size() < 2 || players.size() > 6) {
            return false;
        }
        int counter = 0;
        for (Country c : game.getMap().getCountries().values()) {
            Player p = players.get(counter);
            c.setOwnerPlayer(p);
            p.getOwnedCountries().put(c.getCountryName().toLowerCase(), c);
            if (counter >= numberOfPlayers - 1)
                counter = 0;
            else
                counter++;
        }
        assignInitialArmies(players);
        placeInitialArmies(players);
        game.setGamePhase(Phase.ARMYALLOCATION);
        return true;
    }

    /**
     * This method places initially one army to each country owned by the player
     * @param players List of player
     */
    public void placeInitialArmies(ArrayList<Player> players) {
        for (Player player : players) {
            if (player.getOwnedArmies() > 0) {
                for (Country country : player.getOwnedCountries().values()) {

                    int existingArmy = country.getNumberOfArmies();
                    existingArmy++;
                    country.setNumberOfArmies(existingArmy);
                    player.setOwnedArmies(player.getOwnedArmies() - 1);
                }
            }else{
                System.out.println("Player do not own enough armies");
            }
        }
    }

    /**
     * Assigns initial armies to each player depending on the number of players.
     *
     * @param players List of players in the game
     */
    public void assignInitialArmies(ArrayList<Player> players) {
        int numberOfPlayers = players.size();
        int numberOfArmies = 0;
        if (numberOfPlayers == 2) {
            numberOfArmies = 40;
        } else if (numberOfPlayers == 3) {
            numberOfArmies = 35;
        } else if (numberOfPlayers == 4) {
            numberOfArmies = 30;
        } else if (numberOfPlayers == 5) {
            numberOfArmies = 25;
        } else if (numberOfPlayers == 6) {
            numberOfArmies = 20;
        }
        Iterator<Player> itr = players.listIterator();
        while (itr.hasNext()) {
            Player p = itr.next();
            p.setOwnedArmies(numberOfArmies);
        }
    }

    /**
     * Place army at the argument country if it is in compliance with game rules.
     *
     * @param player      Player assigning the army
     * @param countryName Country where army is to be placed
     * @return true if successful, else false
     */
    public boolean placeArmy(Player player, String countryName) {
        if (player.getOwnedArmies() > 0) {
            if (player.getOwnedCountries().containsKey(countryName.toLowerCase())) {
                int existingArmy = player.getOwnedCountries().get(countryName.toLowerCase()).getNumberOfArmies();
                existingArmy++;
                player.getOwnedCountries().get(countryName.toLowerCase()).setNumberOfArmies(existingArmy);
                player.setOwnedArmies(player.getOwnedArmies() - 1);
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Place army of all players randomly.
     *
     * @param game Represents the state of the game.
     * @return true if successful, else false
     */
    public boolean placeAll(GameData game) {
        Iterator<Player> itr = game.getPlayers().listIterator();
        while (itr.hasNext()) {
            Player p = itr.next();
            while (p.getOwnedArmies() > 0) {
                for (Country country : p.getOwnedCountries().values()) {
                    int newArmy = country.getNumberOfArmies();
                    newArmy += 1;
                    country.setNumberOfArmies(newArmy);
                    p.setOwnedArmies(p.getOwnedArmies() - 1);
                    if (p.getOwnedArmies() <= 0)
                        break;
                }
            }
        }
        game.setGamePhase(Phase.CARDEXCHANGE);
        return true;
    }

    /**
     * Checks if all armies of every player is allocated or not.
     *
     * @param game Represents the state of the game.
     * @return true if successful, else false
     */
    public boolean isAllArmyPlaced(GameData game) {
        Iterator<Player> itr = game.getPlayers().listIterator();
        while (itr.hasNext()) {
            Player p = itr.next();
            if (p.getOwnedArmies() > 0) {
                return false;
            }
        }
        game.setGamePhase(Phase.CARDEXCHANGE);
        return true;
    }

    /**
     * Checks if no card exchange is valid move or not.
     * @param game Represents the state of the game.
     * @param player Represents the player calling the function.
     * @return true if player owns less than five cards, else false.
     */
    public boolean noCardExchange(GameData game, Player player){ ;
        if (player.getOwnedCards().size() < 5){
            game.setGamePhase(Phase.REINFORCEMENT);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This function is to assign armies to player for reinforcement.
     * @param game Represents the state of the game.
     * @param player Player playing the move.
     * @return true if successful, else false.
     */
    public boolean assignReinforcementArmies(GameData game, Player player) {
        int totalControlValue = 0;
        int totalReinforcementArmies;
        if (player.getOwnedCountries().size() >= 9) {
            if (player.getOwnedContinents().size() > 0) {
                for (Continent c : player.getOwnedContinents().values()) {
                    totalControlValue += c.getControlValue();
                }
                totalReinforcementArmies = (int) (player.getOwnedCountries().size() / 3) + totalControlValue;
            } else {
                totalReinforcementArmies = (int) (player.getOwnedCountries().size() / 3);
            }
        } else if(player.getOwnedContinents().size() > 0){
            for (Continent c : player.getOwnedContinents().values()) {
                totalControlValue += c.getControlValue();
            }
            totalReinforcementArmies = 3 + totalControlValue;
        } else {
            totalReinforcementArmies = 3;
        }
        player.setOwnedArmies(player.getOwnedArmies() + totalReinforcementArmies);
        game.setActivePlayer(player);
        return true;
    }

    /**
     * Calculates the percentage of the map controlled by the argument player.
     * @param game Represents the state of the game.
     * @param player Calculates percentage of the map controlled for this player.
     */
    public void calculateMapControlled(GameData game, Player player){
        player.setMapControlled(((double)player.getOwnedCountries().size()/(double)game.getMap().getCountries().size())*100);
    }

    /**
     * Calculates the percentage of the map controlled by all the player at the beginning of the
     * @param game Represents the state of the game.
     */
    public void initalizaMapContolValue(GameData game){
        for(int i = 0; i<game.getPlayers().size(); i++){
            this.calculateMapControlled(game, game.getPlayers().get(i));
        }
    }

    /**
     * Sets gamephase to CARDEXCHANGE for the next player and sets active player as null when the turn ends for one player.
     * @param game Represents the state of the game.
     */
    public void turnEnd(GameData game) {
        game.setGamePhase(Phase.CARDEXCHANGE);
        game.setActivePlayer(null);
    }

    /**
     * Displays player's cards at the beginning of the card exchange phase.
     * @param player currently active player
     */
    public void initializeCEV(Player player) {
        notifyObservers(player);
    }

    /**
     * Checks if two countries are neighbors or not.
     * @param game Represents the state of the game
     * @param player Attacking player
     * @param fromCountry First country
     * @param toCountry Second country
     * @return true if both countries are neighbors, else false.
     */
    public boolean areNeighbors(GameData game, Player player, String fromCountry, String toCountry) {
        Country country = game.getMap().getCountries().get(fromCountry.toLowerCase());
        if(country.getNeighbours().containsKey(toCountry.toLowerCase()) && !player.getOwnedCountries().containsKey(toCountry.toString())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if attack from argument country is possible or not.
     * @param game Represents the state of the game
     * @param fromCountry Attacking country
     * @return true if attack is possible, else false.
     */
    public boolean hasEnoughArmies(GameData game, String fromCountry) {
        Country country = game.getMap().getCountries().get(fromCountry.toLowerCase());
        if(country.getNumberOfArmies()>1){
            return true;
        } else {
             return false;
        }
    }

    /**
     * Checks if the argument number of dice can be rolled for attack from argument country.
     * @param game Represents the state of the game
     * @param countryName Attacking country
     * @param numberOfDice Proposed number of dice rolls
     * @param attack true if attacker calling the function, else false
     * @return true if attack is possible, else false.
     */
    public boolean diceValid(GameData game, String countryName, int numberOfDice, boolean attack) {
        Country country = game.getMap().getCountries().get(countryName.toLowerCase());
        if(attack){
            if(country.getNumberOfArmies()>numberOfDice && numberOfDice > 0){
                return true;
            } else {
                return false;
            }
        } else {
            if(country.getNumberOfArmies()>=numberOfDice && numberOfDice < 3 && numberOfDice > 0){
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Returns the owner of the country.
     * @param game Represents the state of the game
     * @param countryName Country whose owner is to be found
     * @return Player owning the argument country, null if no player owns the country.
     */
    public Player getOwner(GameData game, String countryName) {
        for(Player p : game.getPlayers()){
            if(p.getOwnedCountries().containsKey(countryName.toLowerCase())){
                return p;
            }
        }
        return null;
    }

    /**
     * Marks the end of the attack phase.
     * @param game Represents the state of the game.
     */
    public void endAttack(GameData game) {
        System.out.println("Cannot attack anymore. Begin fortification now.");
        game.setGamePhase(Phase.FORTIFICATION);
    }

    /**
     * Gets maimum possible dice rolls for given argument.
     * @param game Represents the state of the game.
     * @param fromCountry Principal country in question.
     * @param role Attacker/defender
     * @return maximum number of valid dices
     */
    public int getMaxDiceRolls(GameData game, String fromCountry, String role){
        Country country = game.getMap().getCountries().get(fromCountry.toLowerCase());
        if(role.equals("attacker")){
            if(country.getNumberOfArmies()>=4){
                return 3;
            } else if (country.getNumberOfArmies()>=3) {
                return 2;
            } else  if(country.getNumberOfArmies() >= 2){
                return 1;
            } else {
                return 0;
            }
        } else {
            if(country.getNumberOfArmies()>=2) {
                return 2;
            } else {
                return 1;
            }
        }

    }

    /**
     * Argument player gets all the cards of the player p when player p leaves the game.
     * @param player winning player
     * @param p player getting out of the game
     */
    public void getAllCards(Player player, Player p) {
        for(Card card : p.getOwnedCards()) {
            player.setOwnedCards(card);
        }
    }

    /**
     * Sets attack card exchange mode for when the attacking player gets all the cards of the player getting out the game.
     * @param game Represents the state of the game
     */
    public void setAttackCardExchange(GameData game) {
        game.setGamePhase(Phase.ATTACKCARDEXCHANGE);
    }

    /**
     * method for cards exchange when player has more then 4 cards
     * @param game object of GameData
     * @param player object of player
     * @return true if continue card exchange else false
     */
    public boolean continueCardExchange(GameData game, Player player) {
        if(player.getOwnedCards().size()>4) {
            return true;
        }
        game.setGamePhase(Phase.ATTACK);
        return false;
    }

    /**
     * set the phase for end of the game
     * @param game object os GameData
     */
    public void endGame(GameData game) {
        game.setGamePhase(Phase.QUIT);
    }

    /**
     * Checks if player owns entire continent or not.
     * @param game Represents the state of the game.
     * @param player Argument player
     */
    public void checkContinentOwnership(GameData game, Player player) {
        boolean addContinent = true;
        for(Continent continent : game.getMap().getContinents().values()){
            for(Country country : continent.getCountries().values()){
                if(!player.getOwnedCountries().containsKey(country.getCountryName().toLowerCase())) {
                    addContinent = false;
                    break;
                }
            }
            if(addContinent){
                player.getOwnedContinents().put(continent.getContinentName().toLowerCase(), continent);
            }
            addContinent = true;
        }
    }

    /**
     * Checks if the map with argument name exists or not.
     * If it exists, it also checks if its valid or not.
     * @param mapName Name of the map file to be checked.
     * @return true if file exists and is a valid file, otherwise false.
     */
    public boolean isMapExists(String mapName){
        String filePath = "src/main/resources/maps/" + mapName;
        File f = new File(filePath);
        if (f.exists()){
            GameMap map = new GameMap();
            if(determineMapType("src/main/resources/maps/" + mapName)){
                //Domination map
                LoadDominationMap lm = new LoadDominationMap();
                map = lm.readMap(filePath);
            } else {
                //Conquest map
                LoadConquestMap lm = new LoadConquestMap();
                map = lm.readMap(filePath);
            }
            if (validateMap(map) == MapValidityStatus.VALIDMAP){
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * Saves risk game.
     * @param game Represents the state of the game.
     * @param fileName Name of the file saving the game.
     */
    public void saveGame(GameData game, String fileName){
        GameDataBuilder gameDataBuilder = new GameDataBuilder();
        gameDataBuilder.setMap(game.getMap());
        gameDataBuilder.setMapType(game.getMapType());
        gameDataBuilder.setGamePhase(game.getGamePhase());
        gameDataBuilder.setPlayers(game.getPlayers());
        gameDataBuilder.setActivePlayer(game.getActivePlayer());
        gameDataBuilder.setDeck(game.getDeck());
        gameDataBuilder.setCardsDealt((game.getCardsDealt()));

        try{
            FileOutputStream f = new FileOutputStream(new File("src/main/resources/game/" + fileName));
            ObjectOutputStream o = new ObjectOutputStream((f));
            o.writeObject(gameDataBuilder);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads risk game.
     * @param fileName Name of the file to be loaded.
     * @return GameDataBuilder object to build GameData object.
     */
    public GameDataBuilder loadGame(String fileName){
        GameDataBuilder gameDataBuilder;
        try{
            FileInputStream f = new FileInputStream((new File("src/main/resources/game/" + fileName)));
            ObjectInputStream o = new ObjectInputStream((f));
            gameDataBuilder = (GameDataBuilder) o.readObject();
        } catch(FileNotFoundException e){
            return null;
        } catch(IOException e) {
            return null;
        } catch (ClassNotFoundException e){
            return null;
        }
        return gameDataBuilder;
    }
}
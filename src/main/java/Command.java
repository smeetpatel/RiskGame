package main.java;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class responsible for:
 * 1) Interpreting user commands
 * 2) Call appropriate methods to act on user commands
 * @author Tirth
 */
public class Command {

    /**
     * Holds the data related to the game.
     */
    public GameData game;

    /**
     * Helps access methods to view map.
     */
    public MapView mapView;

    /**
     * Helps access methods related to background operations of the game such as loading/editing map, calculating the number of reinforcement armies, etc.
     */
    public GameActions gameAction;

    /**
     * Make it true when player conquered the attacked country.
     */
    public static boolean attackSuccess = false;

    /**
     * Initializes the variables and objects required to play the game and act on user commands.
     */
    public Command() {
        game = new GameData();
        mapView = new MapView();
        gameAction = new GameActions();
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

    /**
     * Ensures map name is valid.
     *
     * @param s input string
     * @return true if valid match, else false
     */
    public boolean isMapNameValid(String s) {

        return s != null && s.matches("^[a-zA-Z.]*$");
    }

    /**
     * Function responsible for parsing user command and calling appropriate method.
     *
     * @param player     Player playing the move
     * @param newCommand Command to be interpreted
     * @return next game phase
     */
    public Phase parseCommand(Player player, String newCommand) {

        int controlValue = 0;
        int numberOfArmies = 0;
        int armiesToFortify = 0;
        int numberOfDice = 0;

        String mapName = null;
        String continentName = null;
        String countryName = null;
        String neighborCountryName = null;
        String playerName = null;
        String fromCountry = null;
        String toCountry = null;
        String[] data = newCommand.split("\\s+");
        String commandName = data[0];

        boolean isAttackAllOut = false;
        boolean attackCommandExecuted = false;

        if (game.getGamePhase().equals(Phase.NULL)) {
            switch (commandName) {
                case "editmap":
                    try {
                        if (data.length > 2) {
                            System.out.println("Invalid command - it should be of the form 'editmap mapname.map'");
                            break;
                        }
                        if (!(data[1] == "")) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                gameAction.editMap(game, mapName);
                                if (game.getMap().getCountries().size() == 0) {
                                    System.out.println(mapName + " does not exist.");
                                    System.out.println("Creating a new map named " + mapName);
                                }
                                System.out.println("You can now edit " + mapName);
                            } else {
                                System.out.println("Invalid map name.");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'editmap mapname.map'");
                    }
                    break;

                case "loadmap":
                    try {
                        if (data.length > 2) {
                            System.out.println("Invalid command - it should be of the form 'loadmap mapname.map'");
                            break;
                        }
                        if (data[1] != null) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                if (gameAction.loadMap(game, mapName)) {
                                    if (!game.getMap().getValid()) {
                                        System.out.println("Map not suitable for game play. Correct the map to continue with this map or load/edit another map.");
                                    } else {
                                        System.out.println("Map is valid. Add players now.");
                                    }
                                } else {
                                    System.out.println("Map " + mapName + " does not exist. Try again or use 'editmap' if you want to create new map.");
                                }
                            } else {
                                System.out.println("Map name not valid.");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'loadmap mapname.map'");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'loadmap mapname.map'");
                    }
                    break;
                default:
                    System.out.println("Load or edit map first using commands: 'loadmap mapname.map' or 'editmap.mapname.map'");
                    break;
            }
        } else if (game.getGamePhase().equals(Phase.EDITMAP)) {
            switch (commandName) {
                case "editcontinent":
                    try {
                        for (int i = 1; i < data.length; i++) {
                            if (data[i].equals("-add")) {
                                if (this.isAlpha(data[i + 1])) {
                                    continentName = data[i + 1];
                                    controlValue = Integer.parseInt(data[i + 2]);
                                    boolean check = game.getMap().addContinent(continentName, controlValue);
                                    if (check) {
                                        System.out.println(continentName + " added to the map");
                                    } else {
                                        System.out.println("Continent already exists - Please add valid Continent Name");
                                    }
                                } else {
                                    System.out.println("Invalid continent name");
                                }
                            } else if (data[i].equals("-remove")) {
                                if (this.isAlpha(data[i + 1])) {
                                    continentName = data[i + 1];
                                    boolean check = game.getMap().removeContinent(continentName);
                                    if (check) {
                                        System.out.println(continentName + " removed from the map");
                                    } else {
                                        System.out.println("Continent does not exist - Please enter valid continent name");
                                    }
                                } else {
                                    System.out.println("Invalid continent name");
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'editcontinent -add continentName controlValue -remove continentName'");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid command - it should be of the form 'editcontinent -add continentName controlValue -remove continentName'");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'editcontinent -add continentName controlValue -remove continentName'");
                    }
                    break;

                case "editcountry":
                    try {
                        for (int i = 1; i < data.length; i++) {
                            if (data[i].equals("-add")) {
                                if (this.isAlpha(data[i + 1]) && this.isAlpha(data[i + 2])) {
                                    countryName = data[i + 1];
                                    continentName = data[i + 2];
                                    boolean check = game.getMap().addCountry(countryName, continentName);
                                    if (check) {
                                        System.out.println(countryName + " added to the map");
                                    } else {
                                        if (!game.getMap().getContinents().containsKey(continentName.toLowerCase())) {
                                            System.out.println(continentName + " does not exist.");
                                        } else {
                                            System.out.println("Country already exists - Please add valid Continent Name");
                                        }
                                    }
                                } else {
                                    System.out.println("Invalid country/continent name");
                                }
                            } else if (data[i].equals("-remove")) {
                                if (this.isAlpha(data[i + 1])) {
                                    countryName = data[i + 1];
                                    boolean check = game.getMap().removeCountry(countryName);
                                    if (check) {
                                        System.out.println(countryName + " removed from the map");
                                    } else {
                                        System.out.println("Country does not exist - Please enter valid country name");
                                    }
                                } else {
                                    System.out.println("Invalid country name");
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'editcountry -add countryName continentName -remove countryName'");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'editcountry -add countryName continentName -remove countryName'");
                    }
                    break;

                case "editneighbor":
                    try {
                        for (int i = 1; i < data.length; i++) {
                            if (data[i].equals("-add")) {
                                if (this.isAlpha(data[i + 1]) && this.isAlpha(data[i + 2])) {
                                    countryName = data[i + 1];
                                    neighborCountryName = data[i + 2];
                                    boolean check = game.getMap().addNeighbor(countryName, neighborCountryName);
                                    if (check) {
                                        System.out.println(neighborCountryName + " added as neighbor of " + countryName);
                                    } else {
                                        if (!game.getMap().getCountries().containsKey(countryName.toLowerCase()) && !game.getMap().getCountries().containsKey(neighborCountryName.toLowerCase()))
                                            System.out.println(countryName + " and " + neighborCountryName + "  does not exist. Create country first and then set their neighbors.");
                                        else if (!game.getMap().getCountries().containsKey(countryName.toLowerCase())) {
                                            System.out.println(countryName + " does not exist. Create country first and then set its neighbors.");
                                        } else {
                                            System.out.println(neighborCountryName + " does not exist. Create country first and then set its neighbors.");
                                        }
                                    }
                                } else {
                                    System.out.println("Invalid country name");
                                }
                            } else if (data[i].equals("-remove")) {
                                if (this.isAlpha(data[i + 1]) || this.isAlpha(data[i + 2])) {
                                    countryName = data[i + 1];
                                    neighborCountryName = data[i + 2];
                                    boolean check = game.getMap().removeNeighbor(countryName, neighborCountryName);
                                    if (check) {
                                        System.out.println(neighborCountryName + " removed as neighbor of " + countryName);
                                    } else {
                                        if (!game.getMap().getCountries().containsKey(countryName.toLowerCase()) && !game.getMap().getCountries().containsKey(neighborCountryName.toLowerCase()))
                                            System.out.println(countryName + " and " + neighborCountryName + "  does not exist.");
                                        else if (!game.getMap().getCountries().containsKey(countryName.toLowerCase())) {
                                            System.out.println(countryName + " does not exist.");
                                        } else {
                                            System.out.println(neighborCountryName + " does not exist.");
                                        }
                                    }
                                } else {
                                    System.out.println("Invalid country name");
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'editneighbor -add countryName neighborCountryName -remove countryName neighborCountryName'");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'editneighbor -add countryName neighborCountryName -remove countryName neighborCountryName'");
                    }
                    break;

                case "savemap":
                    try {
                        if (!(data[1] == "")) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                boolean check = gameAction.saveMap(game.getMap(), mapName);
                                if (check) {
                                    System.out.println("Map file saved successfully as " + mapName + ".map");
                                } else {
                                    System.out.println("Error in map saving. Map not suitable for game play. Correct the map to continue with the new map or load a map from the existing maps.");
                                }
                            } else
                                System.out.println("Map name not valid.");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'savemap filename'");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'savemap filename'");
                    }
                    break;

                case "showmap":
                    mapView.showMap(game.getMap());
                    break;

                case "editmap":
                    try {
                        if (data[1] != null) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                gameAction.editMap(game, mapName);
                                if (game.getMap().getCountries().size() == 0) {
                                    System.out.println(mapName + " does not exist.");
                                    System.out.println("Creating a new map named " + mapName);
                                }
                                System.out.println("You can now edit " + mapName);
                            } else
                                System.out.println("Map name not valid.");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'editmap mapname.map'");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'editmap mapname.map'");
                    }
                    break;

                case "loadmap":
                    try {
                        if (data.length > 2) {
                            System.out.println("Invalid command - it should be of the form 'loadmap mapname.map'");
                            break;
                        }
                        if (data[1] != null) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                if (gameAction.loadMap(game, mapName)) {
                                    if (!game.getMap().getValid()) {
                                        System.out.println("Map not suitable for game play. Correct the map to continue with this map or load/edit another map.");
                                    } else {
                                        System.out.println("Map is valid. Add players now.");
                                    }
                                } else {
                                    System.out.println("Map " + mapName + " does not exist. Try again or use 'editmap' if you want to create new map.");
                                }
                            } else {
                                System.out.println("Map name not valid.");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'loadmap mapname.map'");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'loadmap mapname.map'");
                    }
                    break;

                case "validatemap":
                    MapValidityStatus mvs = gameAction.validateMap(game.getMap());
                    if (mvs == MapValidityStatus.VALIDMAP) {
                        System.out.println("Valid map");
                    } else if (mvs == MapValidityStatus.EMPTYCONTINENT) {
                        System.out.println("Invalid map - empty continent present.");
                    } else if (mvs == MapValidityStatus.UNCONNECTEDGRAPH) {
                        System.out.println("Invalid map - not a connected graph");
                    } else {
                        System.out.println("Invalid map - one of the continent is not a connected sub-graph");
                    }
                    break;

                default:
                    System.out.println("Invalid command - either use edit commands(editcontinent/editcountry/editneighbor) or savemap/validatemap/editmap/loadmap/showmap command");
                    break;

            }
        } else if (game.getGamePhase().equals(Phase.STARTUP)) {
            switch (commandName) {
                case "gameplayer":
                    try {
                        for (int i = 1; i < data.length; i++) {
                            if (data[i].equals("-add")) {
                                if (data[i + 1].matches("[a-zA-Z0-9]+")) {
                                    playerName = data[i + 1];
                                    boolean check = gameAction.addPlayer(game.getPlayers(), playerName);
                                    if (check) {
                                        System.out.println("Player " + playerName + " successfully added");
                                    } else {
                                        if (game.getPlayers().size() == 6) {
                                            System.out.println("Can not add any more player. Maximum 6 players can play.");
                                        } else {
                                            System.out.println("Player with same name already exists. Add player with different name.");
                                        }

                                    }
                                } else {
                                    System.out.println("Invalid player name");
                                }
                            } else if (data[i].equals("-remove")) {
                                if (data[i + 1].matches("[a-zA-Z0-9]+")) {
                                    playerName = data[i + 1];
                                    boolean check = gameAction.removePlayer(game.getPlayers(), playerName);
                                    if (check)
                                        System.out.println("Player " + playerName + " successfully removed");
                                    else
                                        System.out.println("Player " + playerName + " does not exist");
                                } else
                                    System.out.println("Invalid player name");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'gameplayer -add playername -remove playername'");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'gameplayer -add playername -remove playername'");
                    }
                    break;

                case "populatecountries":
                    boolean check = gameAction.populateCountries(game, game.getPlayers());
                    if (check) {
                        System.out.println("Countries allocated amongst the players");
                        //gameAction.armyDistribution(game, this);
                    } else {
                        System.out.println("Minimum two players are required to play the game.");
                    }
                    break;

                case "showmap":
                    mapView.showMap(game.getMap(), game.getPlayers());
                    break;

                default:
                    System.out.println("Invalid command - use gameplayer/populatecountries/placearmy/placeall/showmap commands in start up phase.");
                    break;
            }
        } else if (game.getGamePhase().equals(Phase.ARMYALLOCATION)) {
            switch (commandName) {
                case "placearmy":
                    try {
                        if (!(data[1] == "")) {
                            if (this.isAlpha(data[1])) {
                                countryName = data[1];
                                if (!gameAction.placeArmy(player, countryName)) {
                                    if (player.getOwnedArmies() <= 0) {
                                        System.out.println("Invalid command - player does not own armies to assign.");
                                    } else {
                                        System.out.println("You don't own this country. Please allocate army in your country.");
                                    }
                                }
                                gameAction.isAllArmyPlaced(game);
                            } else
                                System.out.println("Invalid country name");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'placearmy countryname'");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'placearmy countryname'");
                    }
                    break;

                case "placeall":
                    if (gameAction.placeAll(game)) {
                        System.out.println("Armies placed successfully");
                    }

                    break;

                case "showmap":
                    mapView.showMap(game.getMap(), game.getPlayers());
                    break;

                default:
                    System.out.println("Invalid command - use placearmy/placeall/showmap commands to first allocate the assigned armies.");
                    break;
            }
        } else if (game.getGamePhase().equals(Phase.CARDEXCHANGE)) {
            switch (commandName) {
                case "exchangecards":
                    try {
                        if (data[1].equals("none")) {
                            if (player.getOwnedCards().size() < 5) {
                                System.out.println("Player do not want to perform card exchange operation or player do " +
                                        "not have enough cards to exchange.");

                                game.setGamePhase(Phase.REINFORCEMENT);
                            } else {
                                System.out.println("Player has to exchange the cards.");
                            }
                        } else {
                            if (!(data[1] == null) && !(data[2] == null) && !(data[3] == null)) {
                                if (data[1].matches("[0-9]+") && data[2].matches("[0-9]+") && data[3].matches("[0-9]+")) {
                                    ArrayList<Integer> cardIndex = new ArrayList<Integer>();
                                    cardIndex.add(Integer.parseInt(data[1]));
                                    cardIndex.add(Integer.parseInt(data[2]));
                                    cardIndex.add(Integer.parseInt(data[3]));
                                    Collections.sort(cardIndex);
                                    CardExchange ce = new CardExchange();
                                    boolean check = ce.cardTradeIn(player, cardIndex);
                                    if (check) {
                                        System.out.println("Card Exchange successfully occured");
                                        game.setGamePhase(Phase.REINFORCEMENT);
                                    } else {
                                        System.out.println("Failure of Card Exchange");
                                    }
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'exchangecards num num num -none'");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'exchangecards num num num -none'");
                    }
                    break;

                default:
                    System.out.println("Invalid command - use exchangecards command.");
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
                                        System.out.println("Reinforcement phase successfully ended. Begin fortification now.");
                                    }
                                } else {
                                    if (player.getOwnedArmies() < numberOfArmies) {
                                        System.out.println("You don't have enough armies");
                                    } else {
                                        System.out.println("You don't own this country");
                                    }
                                }
                            } else
                                System.out.println("Invalid command - invalid characters in command");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'reinforce countryName num'");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'reinforce countryName num'");
                    }
                    break;

                case "showmap":
                    mapView.showMap(game.getMap(), game.getPlayers());
                    break;

                default:
                    System.out.println("Invalid command - either use reinforce or showmap commands in reinforcement phase.");
                    break;
            }
        } else if (game.getGamePhase().equals(Phase.ATTACK)) {
            switch (commandName){
                case "attack":
                    try{
                        if(data[1].equals("-noattack")){
                            System.out.println("Player do not want to perform attack");
                            game.setGamePhase(Phase.FORTIFICATION);
                        }else if(data.length == 4){
                            if(!(data[1] == null) && !(data[2] == null) && !(data[3] == null)){
                                if(this.isAlpha(data[1]) && this.isAlpha(data[2]) && data[3].matches("[1-3]")){
                                    fromCountry = data[1];
                                    toCountry = data[2];
                                    numberOfDice = Integer.parseInt(data[3]);
                                    attackCommandExecuted = true;
                                }
                            }
                        }else if(data.length == 5){
                            if(!(data[1] == null) && !(data[2] == null) && !(data[3] == null) && !(data[4] == null)){
                                if(this.isAlpha(data[1]) && this.isAlpha(data[2]) && data[3].matches("[1-3]") && data[4].equals("-allout")){
                                    fromCountry = data[1];
                                    toCountry = data[2];
                                    numberOfDice = Integer.parseInt(data[3]);
                                    isAttackAllOut = true;
                                    attackCommandExecuted = true;
                                }
                            }

                        }
                    }catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'attack countrynamefrom countynameto numdice –allout'" +
                                " or 'attack -noattack'");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid command - it should be of the form 'attack countrynamefrom countynameto numdice –allout'" +
                                " or 'attack -noattack'");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'attack countrynamefrom countynameto numdice –allout'" +
                                " or 'attack -noattack'");
                    }
                    break;

                case "defend":
                    try {
                        if(attackCommandExecuted) {
                            if (!(data[1] == null)) {
                                if (data[1].matches("[1-2]")) {
                                /*boolean check = player.attack(fromCountry,toCountry,numberOfDice,isAttackAllOut);
                                    if(check){

                                    }else{

                                    }*/
                                attackCommandExecuted = false;
                                }else {
                                    System.out.println("Enter valid number of dice. Enter either 1 or 2.");
                                }
                            }else{
                                System.out.println("Invalid command - it should be of the form 'defend numdice'. ");
                            }
                        }else{
                            System.out.println("Before defend command, enter 'attack countrynamefrom countynameto numdice –allout'. ");
                        }
                    }catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'defend numdice'. ");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid command - it should be of the form 'defend numdice'.");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'defend numdice'.");
                    }
                    break;

                case "attackmove":
                    try{
                        if(attackSuccess){
                            if(!(data[1] == null)){
                                if(data[1].matches("[0-9]+")){
                                    numberOfArmies = Integer.parseInt(data[1]);
                                    /*boolean check = attackmove(numberOfArmies);
                                    if(check){

                                    }else{

                                    }*/
                                }else {
                                    System.out.println("Enter valid number");
                                }
                            }else {
                                System.out.println("Invalid command - it should be of the form 'attackmove num'. ");
                            }
                        }else{
                            System.out.println("Player did not counquered any country, so invalid command");
                        }
                    }catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'attackmove num'. ");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid command - it should be of the form 'attackmove num'.");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'attackmove num'.");
                    }

                case "showmap":
                    mapView.showMap(game.getMap(), game.getPlayers());
                    break;

                default:
                    System.out.println("Invalid command - either use attack/defend/showmap command.");
                    break;
            }
        } else if (game.getGamePhase().equals(Phase.FORTIFICATION)) {
            switch (commandName) {
                case "fortify":
                    try {
                        if (data[1].equals("none")) {
                            player.fortify(game);
                            System.out.println("Successfull fortification");
                        } else if (!(data[1] == null) && !(data[2] == null) && !(data[3] == null)) {
                            if (this.isAlpha(data[1]) || this.isAlpha(data[2]) || data[3].matches("[0-9]+")) {
                                fromCountry = data[1];
                                toCountry = data[2];
                                armiesToFortify = Integer.parseInt(data[3]);
                                FortificationCheck check = player.fortify(game, fromCountry, toCountry, armiesToFortify);
                                if (check == FortificationCheck.FORTIFICATIONSUCCESS) {
                                    System.out.println("Successfull fortification");
                                } else if (check == FortificationCheck.PATHFAIL) {
                                    System.out.println(fromCountry + " and " + toCountry + " do not have path of player owned countries.");
                                } else if (check == FortificationCheck.ARMYCOUNTFAIL) {
                                    System.out.println("You don't have enough armies.");
                                } else if (check == FortificationCheck.TOCOUNTRYFAIL) {
                                    System.out.println(toCountry + " does not exist.");
                                } else {
                                    System.out.println(fromCountry + " does not exist.");
                                }
                            } else
                                System.out.println("Invalid command - invalid characters in command");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form 'fortify fromCountry toCountry num' or 'foritfy none'");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid command - it should be of the form 'fortify fromCountry toCountry num' or 'foritfy none'");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form 'fortify fromCountry toCountry num' or 'foritfy none'");
                    }
                    break;

                case "showmap":
                    mapView.showMap(game.getMap(), game.getPlayers());
                    break;

                default:
                    System.out.println("Invalid command - either use fortify/showmap command.");
                    break;
            }
        }
        return game.getGamePhase();
    }
}
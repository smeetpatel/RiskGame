package main.java;

import java.util.ArrayList;
import java.util.List;

public class Command {

    public GameMap map;
    public RunCommand runCmd;
    public StartUp startUp;
    //public Player player;
    public Reinforcement rfc;
    public Fortification ftf;
    public static boolean allArmiesPlaced = false;

    /**create a function of type
     * @author Tirth
     *  public Phase parseCommand(String cmd)()
     *  This function will return appropriate phase value as per the command.
     *  This value can be one of the following: {NULL, EDITMAP, STARTUP, REINFORCEMENT, FORTIFICATION, QUIT}
     *  You will return it as, for example: return PlayRisk.Phase.EDITMAP;
     */

    public ArrayList<Player> players = new ArrayList<Player>();

    public Command(){
        map = new GameMap();
        runCmd = new RunCommand();
        startUp = new StartUp();
        //player = new Player();
    }
    public enum Phase {NULL, EDITMAP, STARTUP, ARMYALLOCATION, REINFORCEMENT, FORTIFICATION, TURNEND, QUIT}


    //public GameMap map;
    //public RunCommand runCmd;
    Phase gamePhase = Phase.NULL;


    public boolean isAlpha(String s) {
        return s != null && s.matches("^[a-zA-Z]*$");
    }

    public Phase parseCommand(Player player, String newCommand) {

        String mapName = null;
        String continentName = null;
        String countryName = null;
        String neighborCountryName = null;
        String playerName = null;
        String fromCountry = null;
        String toCountry = null;

        int controlValue = 0;
        int numberOfArmies = 0;
        int armiesToFortify = 0;

        String[] data = newCommand.split("\\s+");

        Command ob1 = new Command();
        System.out.println(gamePhase);

        String commandName = data[0];
        System.out.println(commandName);
        if (gamePhase.equals(Phase.NULL) || gamePhase.equals(Phase.EDITMAP)) {
            System.out.println(gamePhase);
            switch (commandName) {
                case "editmap":
                    if (!(data[1] == "")) {
                        if (ob1.isAlpha(data[1])) {
                            mapName = data[1];
                            map = runCmd.editMap(mapName);
                            gamePhase = Phase.EDITMAP;
                        } else
                            System.out.println("invalid command");
                    } else {
                        System.out.println("Empty Name");
                    }

                    break;

                case "loadmap":
                    if (!(data[1] == "")) {
                        if (ob1.isAlpha(data[1])) {
                            //System.out.println(data[1]);
                            mapName = data[1];
                            map = runCmd.loadMap(mapName);
                            System.out.println(map);

                            if(map!=null) {
                                if (!map.getValid()) {
                                    System.out.println("Map is not valid for game play");
                                    gamePhase = Phase.NULL; // map is not valid for game play. so return to NULL Phase
                                } else {
                                    gamePhase = Phase.STARTUP; // startup phase of game started from here
                                }
                            }
                            else{
                                gamePhase = Phase.NULL;
                            }
                        } else
                        	System.out.println("invalid command");
                    } else {
                        System.out.println("Empty Name");
                    }
                    break;

                default:
                    System.out.println("Please enter valid command");
            }
        }

        if (gamePhase.equals("EDITMAP")) {
            switch (commandName) {
                case "editcontinent":

                    for (int i = 1; i < data.length; i++) {
                        if (data[i].equals("-add")) {
                            if (ob1.isAlpha(data[i + 1]))
                                continentName = data[i + 1];
                            else
                                System.out.println("invalid command");
                            controlValue = Integer.parseInt(data[i + 2]);

                            boolean check = runCmd.addContinent(map, continentName,controlValue);
                            if(check) {
                                System.out.println("Continent added to the map");
                                gamePhase = Phase.EDITMAP;
                            }
                            else
                                System.out.println("Continent Exist - Please add valid Continent Name");

                            System.out.println(continentName + "  " + controlValue);
                        } else if (data[i].equals("-remove")) {
                            if (ob1.isAlpha(data[i + 1]))
                                continentName = data[i + 1];
                            else
                                System.out.println("invalid command");

                            boolean check = runCmd.removeContinent(map, continentName);
                            if(check) {
                                System.out.println("Continent removed from the map");
                                gamePhase = Phase.EDITMAP;
                            }
                            else
                                System.out.println("Continent does not exist - Please enter valid continent name");
                            //System.out.println(continentName);
                        }
                    }

                    break;

                case "editcountry":

                    for (int i = 1; i < data.length; i++) {
                        if (data[i].equals("-add")) {
                            if (ob1.isAlpha(data[i + 1]) || ob1.isAlpha(data[i + 2])) {
                                countryName = data[i + 1];
                                continentName = data[i + 2];
                            } else
                                System.out.println("invalid command");

                            boolean check = runCmd.addCountry(map, countryName, continentName);
                            if(check) {
                                System.out.println("Country added to the map");
                                gamePhase = Phase.EDITMAP;
                            }
                            else
                                System.out.println("Country Exist - Please add valid Country Name");
                            //System.out.println(countryName + "  " + continentName);
                        } else if (data[i].equals("-remove")) {
                            if (ob1.isAlpha(data[i + 1]))
                                countryName = data[i + 1];
                            else
                                System.out.println("invalid command");

                            boolean check = runCmd.removeCountry(map, countryName);
                            if(check) {
                                System.out.println("Country removed from the map");
                                gamePhase = Phase.EDITMAP;
                            }
                            else
                                System.out.println("Country doed not exist - Please enter valid Country Name");
                            //System.out.println(countryName);
                        }
                    }
                    break;

                case "editneighbor":

                    for (int i = 1; i < data.length; i++) {
                        if (data[i].equals("-add")) {
                            if (ob1.isAlpha(data[i + 1]) || ob1.isAlpha(data[i + 2])) {
                                countryName = data[i + 1];
                                neighborCountryName = data[i + 2];
                            } else
                                System.out.println("invalid command");

                            boolean check = runCmd.addNeighbor(map, countryName, neighborCountryName);
                            if(check) {
                                System.out.println("Neighbor added to the map");
                                gamePhase = Phase.EDITMAP;
                            }
                            else
                                System.out.println("Neighbor Exist or Country does not exist - Please enter valid neighbor or country name");
                            //System.out.println(countryName + "  " + neighborCountryName);
                        } else if (data[i].equals("-remove")) {
                            if (ob1.isAlpha(data[i + 1]) || ob1.isAlpha(data[i + 2])) {
                                countryName = data[i + 1];
                                neighborCountryName = data[i + 2];
                            } else
                                System.out.println("invalid command");

                            boolean check = runCmd.addNeighbor(map, countryName, neighborCountryName);
                            if(check) {
                                System.out.println("Neighbor removed from the map");
                                gamePhase = Phase.EDITMAP;
                            }
                            else
                                System.out.println("Neighbor does not exist or Country does not exist - Please enter valid neighbor or country name");
                            //System.out.println(countryName + "  " + neighborCountryName);
                        }
                    }

                    break;

                case  "savemap":
                    if (!(data[1] == "")) {
                        if (ob1.isAlpha(data[1])) {
                            mapName = data[1];
                            boolean check = runCmd.saveMap(map,mapName);
                            if(check) {
                                System.out.println("Map file saved successfully");
                                gamePhase = Phase.EDITMAP;
                            }
                            else
                                System.out.println("Error in map saving");
                        } else
                            System.out.println("invalid command");
                    } else {
                        System.out.println("Empty Name");
                    }
                    break;

                case "showmap":
                    runCmd.showMap(map);
                    gamePhase = Phase.EDITMAP;
                    break;

                default:
                    System.out.println("Please enter valid command");

            }
        }

        if (gamePhase.equals("STARTUP")) {
            switch (commandName) {
                case "gameplayer":

                    for (int i = 1; i < data.length; i++) {
                        if (data[i].equals("-add")) {
                            if (data[i + 1].matches("[a-zA-Z0-9]+")) {
                                playerName = data[i + 1];
                                boolean check = startUp.addPlayer(players,playerName);
                                if(check)
                                    System.out.println("Player successfully added");
                                else
                                    System.out.println("Can not add any player");
                                gamePhase = Phase.STARTUP;
                            } else
                                System.out.println("invalid command");

                            //System.out.println(playerName);
                            // parse the playerName to class
                        } else if (data[i].equals("-remove")) {
                            if (data[i + 1].matches("[a-zA-Z0-9]+")) {
                                playerName = data[i + 1];
                                boolean check = startUp.removePlayer(players,playerName);
                                if(check)
                                    System.out.println("Player successfully removed");
                                else
                                    System.out.println("Player does not exist");
                                gamePhase = Phase.STARTUP;
                            } else
                                System.out.println("invalid command");
                            System.out.println(playerName);

                            // parse playerName to class
                        }
                    }

                    break;

                case "populatecountries":

                    // call class method which will assign initial armies
                    boolean check = startUp.populateCountries(map, players);
                    if(check)
                        System.out.println("Countries allocated among players");
                    else
                        System.out.println("Operation failed");
                    startUp.armyDistribution(players, gamePhase);
                    gamePhase = Phase.REINFORCEMENT;
                    break;

                case "placearmy":
                    if (!(data[1] == "")) {
                        if (ob1.isAlpha(data[1])) {
                            countryName = data[1];
                            startUp.placeArmy(player, countryName);
                            boolean checkstatus = startUp.isAllArmyPlaced(players);
                            if(checkstatus)
                                gamePhase = Phase.REINFORCEMENT;
                            else
                                gamePhase = Phase.STARTUP;
                        } else
                            System.out.println("Invalid command");
                    } else {
                        System.out.println("Invalid command");
                    }
                    break;

                case "placeall":
                    startUp.placeAll(players);
                    gamePhase = Phase.REINFORCEMENT;

                default:
                    System.out.println("Please enter valid command");

            }
        }

        if (gamePhase.equals("REINFORCEMENT")) {
            switch (commandName) {
                case "reinforce":

                    if (!(data[1] == null) || !(data[2] == null)) {
                        if (ob1.isAlpha(data[1]) || data[2].matches("[0-9]+")) {
                            countryName = data[1];
                            numberOfArmies = Integer.parseInt(data[2]);

                            boolean check = rfc.reinforce(player,countryName, numberOfArmies);
                            if(check)
                                System.out.println("Reinforcement phase successfully ended");
                            else
                                System.out.println("Invalid command");
                            //System.out.println(countryName + "  " + numberOfArmies);
                        } else
                            System.out.println("invlid command");

                        // parse countryName and numberOfArmies
                    }
                    gamePhase = Phase.FORTIFICATION;
                    break;

                default:
                    System.out.println("Please enter valid command");
            }
        }

        if (gamePhase.equals("FORTIFICATION")) {
            switch (commandName) {
                case "fortify":

                    if (!(data[1] == null) || !(data[2] == null) || !(data[3] == null) || !(data[1].equals("none"))) {
                        if (ob1.isAlpha(data[1]) || ob1.isAlpha(data[2]) || data[3].matches("[0-9]+")) {
                            fromCountry = data[1];
                            toCountry = data[2];
                            armiesToFortify = Integer.parseInt(data[2]);

                            boolean check = ftf.fortify(player,fromCountry,toCountry,armiesToFortify);
                            if(check) {
                                System.out.println("Successfull fortification");
                                gamePhase = Phase.TURNEND;
                            }
                            else
                                System.out.println("Invalid command");
                            //System.out.println(fromCountry + "  " + toCountry + " " + armiesToFortify);
                        } else
                            System.out.println("invlid command");

                        // parse countryName and numberOfArmies
                    }
                    break;

                default:
                    System.out.println("Please enter valid command");

            }
        }
        return gamePhase;
    }
}

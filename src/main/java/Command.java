package main.java;

public class Command {

    public enum Phase {NULL, EDITMAP, STARTUP, REINFORCEMENT, FORTIFICATION, QUIT}


    public GameMap map;
    public RunCommand runCmd;

    Phase gamePhase = Phase.NULL;

    public boolean isAlpha(String s) {
        return s != null && s.matches("^[a-zA-Z]*$");
    }

    public Phase parseCommand(String newCommand) {

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
        if (gamePhase.equals(Phase.NULL)) {
            System.out.println(gamePhase);
            switch (commandName) {
                case "editmap":
                    if (!(data[1] == "")) {
                        if (ob1.isAlpha(data[1])) {
                            mapName = data[1];
                            runCmd = new RunCommand();
                            map = new GameMap(mapName);
                            map = runCmd.editMap(mapName);
                        } else
                            System.out.println("invalid command");
                    } else {
                        System.out.println("Empty Name");
                    }
                    gamePhase = Phase.EDITMAP;
                    break;

                case "loadmap":
                    if (!(data[1] == "")) {
                        if (ob1.isAlpha(data[1])) {
                            //System.out.println(data[1]);
                            mapName = data[1];
                            runCmd = new RunCommand();
                            map = new GameMap(mapName);
                            map = runCmd.loadMap(mapName);
                            if(map.getValid())
                                System.out.println("Enter valid map name");
                        } else
                            System.out.println("invalid command");
                    } else {
                        System.out.println("Empty Name");
                    }
                    gamePhase = Phase.STARTUP; // startup phase of game started from here
                    break;
            }
        }

        if (gamePhase.equals("EDITMAP")) {
            runCmd = new RunCommand();
            map = new GameMap(mapName);

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
                            if(check)
                                System.out.println("Continent added to the map");
                            else
                                System.out.println("Continent Exist - Please add valid Continent Name");

                            //System.out.println(continentName + "  " + controlValue);
                        } else if (data[i].equals("-remove")) {
                            if (ob1.isAlpha(data[i + 1]))
                                continentName = data[i + 1];
                            else
                                System.out.println("invalid command");

                            boolean check = runCmd.removeContinent(map, continentName);
                            if(check)
                                System.out.println("Continent removed from the map");
                            else
                                System.out.println("Continent does not exist - Please enter valid continent name");
                            //System.out.println(continentName);
                        }
                    }
                    gamePhase = Phase.EDITMAP;
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
                            if(check)
                                System.out.println("Country added to the map");
                            else
                                System.out.println("Country Exist - Please add valid Country Name");
                            //System.out.println(countryName + "  " + continentName);
                        } else if (data[i].equals("-remove")) {
                            if (ob1.isAlpha(data[i + 1]))
                                countryName = data[i + 1];
                            else
                                System.out.println("invalid command");

                            boolean check = runCmd.removeCountry(map, countryName);
                            if(check)
                                System.out.println("Country removed from the map");
                            else
                                System.out.println("Country doed not exist - Please enter valid Country Name");
                            //System.out.println(countryName);
                        }
                    }
                    gamePhase = Phase.EDITMAP;
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
                            if(check)
                                System.out.println("Neighbor added to the map");
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
                            if(check)
                                System.out.println("Neighbor removed from the map");
                            else
                                System.out.println("Neighbor does not exist or Country does not exist - Please enter valid neighbor or country name");
                            //System.out.println(countryName + "  " + neighborCountryName);
                        }
                    }
                    gamePhase = Phase.EDITMAP;
                    break;

                case  "savemap":
                    if (!(data[1] == "")) {
                        if (ob1.isAlpha(data[1])) {
                            mapName = data[1];
                        } else
                            System.out.println("invalid command");
                    } else {
                        System.out.println("Empty Name");
                    }
                    boolean check = runCmd.saveMap(map,mapName);
                    if(check)
                        System.out.println("Map file saved successfully");
                    else
                        System.out.println("Error in map saving");
                    gamePhase = Phase.EDITMAP;
                    break;
                case "showmap":
                    runCmd.showMap(map);
                    gamePhase = Phase.EDITMAP;
                    break;
            }
        }

        if (gamePhase.equals("STARTUP")) {
            switch (commandName) {
                case "gameplayer":

                    for (int i = 1; i < data.length; i++) {
                        if (data[i].equals("-add")) {
                            if (data[i + 1].matches("[a-zA-Z0-9]+")) {
                                playerName = data[i + 1];
                            } else
                                System.out.println("invalid command");
                            System.out.println(playerName);

                            // parse the playerName to class
                        } else if (data[i].equals("-remove")) {
                            if (data[i + 1].matches("[a-zA-Z0-9]+")) {
                                playerName = data[i + 1];
                            } else
                                System.out.println("invalid command");
                            System.out.println(playerName);

                            // parse playerName to class
                        }
                    }
                    gamePhase = Phase.REINFORCEMENT;
                    break;

                case "populatecountries":

                    // call class method which will assign initial armies
                    gamePhase = Phase.STARTUP;
                    break;
            }
        }

        if (gamePhase.equals("REINFORCEMENT")) {
            switch (commandName) {
                case "reinforce":

                    if (!(data[1] == null) || !(data[2] == null)) {
                        if (ob1.isAlpha(data[1]) || data[2].matches("[0-9]+")) {
                            countryName = data[1];
                            numberOfArmies = Integer.parseInt(data[2]);
                            System.out.println(countryName + "  " + numberOfArmies);
                        } else
                            System.out.println("invlid command");

                        // parse countryName and numberOfArmies
                    }
                    gamePhase = Phase.FORTIFICATION;
                    break;
            }
        }

        if (gamePhase.equals("FORTIFICATION")) {
            switch (commandName) {
                case "fortify":

                    if (!(data[1] == null) || !(data[2] == null) || !(data[3] == null)) {
                        if (ob1.isAlpha(data[1]) || ob1.isAlpha(data[2]) || data[3].matches("[0-9]+")) {
                            fromCountry = data[1];
                            toCountry = data[2];
                            armiesToFortify = Integer.parseInt(data[2]);
                            System.out.println(fromCountry + "  " + toCountry + " " + armiesToFortify);
                        } else
                            System.out.println("invlid command");

                        // parse countryName and numberOfArmies
                    }
                    break;
            }
        }
        return gamePhase;
    }
}

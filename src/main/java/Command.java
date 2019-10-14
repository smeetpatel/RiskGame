package main.java;

public class Command {

    /*TODO: @Tirth create a function of type
     *  public PlayRisk.Phase parseCommand(PlayRisk.phase phaseValue, String cmd)()
     *  This function will return appropriate phase value as per the command.
     *  This value can be one of the following: {NULL, EDITMAP, STARTUP, REINFORCEMENT, FORTIFICATION, QUIT}
     *  You will return it as, for example: return PlayRisk.Phase.EDITMAP;
     */

    public enum Phase {NULL, EDITMAP, STARTUP, REINFORCEMENT, FORTIFICATION, QUIT}
    public GameMap map;
    Phase gamePhase = Phase.NULL;
    public PlayRisk play;


    public boolean isAlpha(String s) {
        return s != null && s.matches("^[a-zA-Z]*$");
    }

    public Phase parseCommand(String newCommand) {

        System.out.println(newCommand);
        String continentName = null;
        String countryName = null;
        String neighborCountryName = null;
        String playerName = null;
        String fromCountry = null;
        String toCountry = null;

        int continentValue = 0;
        int numberOfArmies = 0;
        int armiesToFortify = 0;

        String[] data = newCommand.split("\\s+");

        Command ob1 = new Command();
        Command checkPhase;

        String commandName = data[0];
        //System.out.println(gamePhase);
        //System.out.println(commandName);
        if (gamePhase.equals(Phase.NULL)) {
            switch (commandName) {
                case "editmap":
                    if (!(data[1] == "")) {
                        if (ob1.isAlpha(data[1])) {
                            //EditMap lm = new EditMap();
                            //map = RunCommand.editmap(data[1]);
                            System.out.println(data[1]);
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
                            //EditMap lm = new EditMap();
                            System.out.println(data[1]);
                        } else
                            System.out.println("invalid command");
                    } else {
                        System.out.println("Empty Name");
                    }
                    gamePhase = Phase.STARTUP; // startup phase of game started from here
                    break;
            }
        }
        System.out.println(gamePhase);
        if (gamePhase.equals(Phase.EDITMAP)) {
            System.out.println(commandName);
            switch (commandName) {
                case "editcontinent":

                    System.out.println(commandName);
                    System.out.println(commandName);
                    for (int i = 1; i < data.length; i++) {
                        if (data[i].equals("-add")) {
                            if (ob1.isAlpha(data[i + 1]))
                                continentName = data[i + 1];
                            else
                                System.out.println("invalid command");

                            continentValue = Integer.parseInt(data[i + 2]);

                            System.out.println(continentName + "  " + continentValue);
                        } else if (data[i].equals("-remove")) {
                            if (ob1.isAlpha(data[i + 1]))
                                continentName = data[i + 1];
                                //boolean check = RunCommand.removeContinent(map, continentName);
                            else
                                System.out.println("invalid command");
                            System.out.println(continentName);
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

                            System.out.println(countryName + "  " + continentName);
                        } else if (data[i].equals("-remove")) {
                            if (ob1.isAlpha(data[i + 1]))
                                countryName = data[i + 1];
                            else
                                System.out.println("invalid command");
                            System.out.println(countryName);
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
                            System.out.println(countryName + "  " + neighborCountryName);
                        } else if (data[i].equals("-remove")) {
                            if (ob1.isAlpha(data[i + 1]) || ob1.isAlpha(data[i + 2])) {
                                countryName = data[i + 1];
                                neighborCountryName = data[i + 2];
                            } else
                                System.out.println("invalid command");
                            System.out.println(countryName + "  " + neighborCountryName);
                        }
                    }
                    gamePhase = Phase.EDITMAP;
                    break;

                case "showmap":
                    if (!(data[1] == "")) {
                        //LoadMap lm = new LoadMap();
                    } else {
                        System.out.println("Empty Name");
                    }
                    gamePhase = Phase.EDITMAP;
                    break;
            }
        }

        if (gamePhase.equals(Phase.STARTUP)) {
            switch (commandName) {
                case "gameplayer":

                    for (
                            int i = 1;
                            i < data.length; i++) {
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

        if (gamePhase.equals(Phase.REINFORCEMENT)) {
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

        if (gamePhase.equals(Phase.FORTIFICATION)) {
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

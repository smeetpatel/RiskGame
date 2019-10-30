package main.java;

/**
 * Class responsible for:
 * 1) Interpreting user commands
 * 2) Call appropriate methods to act on user commands
 */
public class Command {

    /**
     * Holds the data related to the game.
     */
	public GameData game;

    /**
     * Helps access methods related to editing or loading a game map.
     */
    public MapEditor mapCmd;

    /**
     * Helps access methods related to start up phase of the game.
     */
    public StartUp startUp;

    /**
     * Helps access methods related to reinforcement phase of the game.
     */
    public Reinforcement rfc;

    /**
     * Helps access methods related to fortification phase of the game.
     */
    public Fortification ftf;

    /**
     * Initializes the variables and objects required to play the game and act on user commands.
     */
    Command() {
        game = new GameData();
        mapCmd = new MapEditor();
        startUp = new StartUp();
        rfc = new Reinforcement();
        ftf = new Fortification();
    }

    /**
     * Ensures string matches the defined criteria.
     * @param s input string
     * @return true if valid match, else false
     */
    public boolean isAlpha(String s) {

        return s != null && s.matches("^[a-zA-Z-_]*$");
    }

    /**
     * Ensures map name is valid.
     * @param s input string
     * @return true if valid match, else false
     */
    public boolean isMapNameValid(String s) {

        return s != null && s.matches("^[a-zA-Z.]*$");
    }

    /**
     * Function responsible for parsing user command and calling appropriate method.
     * @param player Player playing the move
     * @param newCommand Command to be interpreted
     * @return next game phase
     */
    public Phase parseCommand(Player player, String newCommand) {

    	int controlValue = 0;
        int numberOfArmies = 0;
        int armiesToFortify = 0;
        
        String mapName = null;
        String continentName = null;
        String countryName = null;
        String neighborCountryName = null;
        String playerName = null;
        String fromCountry = null;
        String toCountry = null;
        String[] data = newCommand.split("\\s+");
        String commandName = data[0];
        
        if (game.getGamePhase().equals(Phase.NULL)) {
            switch (commandName) {
                case "editmap":
                	try {
                	    if(data.length>2){
                            System.out.println("Invalid command - it should be of the form 'editmap mapname.map'");
                            break;
                        }
                		if (!(data[1] == "")) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                game.setMap(mapCmd.editMap(mapName));
                                game.getGamePhase(Phase.EDITMAP);
                                System.out.println("You can now edit " + mapName);
                            } else {
                                System.out.println("Invalid map name.");
                            }
                        } 
                	}
                	catch(ArrayIndexOutOfBoundsException e) {
                		System.out.println("Invalid command - it should be of the form 'editmap mapname.map'");
                	}
                    break;
                    
                case "loadmap":
                	try {
                        if(data.length>2){
                            System.out.println("Invalid command - it should be of the form 'loadmap mapname.map'");
                            break;
                        }
                		if (data[1] != null) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                game.setMap(mapCmd.loadMap(mapName));
                                if (game.getMap() != null) {
                                    if (!game.getMap().getValid()) {
                                        System.out.println("Map is not valid for game play");
                                        game.setGamePhase(Phase.NULL);
                                    } else {
                                    	System.out.println("Map is valid. Add players now.");
                                    	game.setGamePhase(Phase.STARTUP);
                                    }
                                } else {
                                    game.setGamePhase(Phase.NULL);
                                }
                            } else {
                                System.out.println("Map name not valid.");
                            }
                        } 
                	}
                	catch(ArrayIndexOutOfBoundsException e) {
                		System.out.println("Invalid command - it should be of the form 'loadmap mapname.map'");
                	}
                	catch(Exception e) {
                		System.out.println("Invalid command - it should be of the form 'loadmap mapname.map'");
                	}
                    break;
                default:
                    System.out.println("Load or edit map first using commands: 'loadmap mapname.map' or 'editmap.mapname.map'");
                    break;
            }
        }
        else if (game.getGamePhase().equals(Phase.EDITMAP)) {
            switch (commandName) {
                case "editcontinent":
                	try {
                		for (int i = 1; i < data.length; i++) {
                            if (data[i].equals("-add")) {
                                if (this.isAlpha(data[i + 1])) {
                                	continentName = data[i + 1];
                                }
                                else {
                                	System.out.println("Invalid continent name");
                                }
                                controlValue = Integer.parseInt(data[i + 2]);

                                boolean check = mapCmd.addContinent(game.getMap(), continentName, controlValue);
                                if (check) {
                                    System.out.println(continentName + " continent added to the map");
                                    game.setGamePhase(Phase.EDITMAP);
                                } else {
                                	System.out.println("Continent already exists - Please add valid Continent Name");
                                }
                            } else if (data[i].equals("-remove")) {
                                if (this.isAlpha(data[i + 1])) {
                                	continentName = data[i + 1];
                                }
                                else
                                    System.out.println("Invalid continent name");

                                boolean check = mapCmd.removeContinent(game.getMap(), continentName);
                                if (check) {
                                    System.out.println("Continent removed from the map");
                                    game.setGamePhase(Phase.EDITMAP);
                                } else
                                    System.out.println("Continent does not exist - Please enter valid continent name");
                            }
                        }
                	}
                	catch(ArrayIndexOutOfBoundsException e) {
                		System.out.println("Invalid command - it should be of the form 'editcontinent -add continentName controlValue -remove continentName'");
                	}
                	catch(NumberFormatException e) {
                		System.out.println("Invalid command - it should be of the form 'editcontinent -add continentName controlValue -remove continentName'");
                	}
                	catch(Exception e) {
                		System.out.println("Invalid command - it should be of the form 'editcontinent -add continentName controlValue -remove continentName'");
                	}
                    break;

                case "editcountry":
                	try {
                		for (int i = 1; i < data.length; i++) {
                            if (data[i].equals("-add")) {
                                if (this.isAlpha(data[i + 1]) || this.isAlpha(data[i + 2])) {
                                    countryName = data[i + 1];
                                    continentName = data[i + 2];
                                } else {
                                	System.out.println("Invalid country name");
                                }
                                boolean check = mapCmd.addCountry(game.getMap(), countryName, continentName);
                                if (check) {
                                    System.out.println("Country added to the map");
                                    game.setGamePhase(Phase.EDITMAP);
                                } else {
                                	System.out.println("Country already exists - Please add valid Continent Name");
                                }
                            } else if (data[i].equals("-remove")) {
                                if (this.isAlpha(data[i + 1])) {
                                	countryName = data[i + 1];
                                }
                                else {
                                	System.out.println("Invalid country name");
                                }
                                boolean check = mapCmd.removeCountry(game.getMap(), countryName);
                                if (check) {
                                    System.out.println("Country removed from the map");
                                    game.setGamePhase(Phase.EDITMAP);
                                } else {
                                	System.out.println("Country does not exist - Please enter valid country name");
                                }
                            }
                        }
                	}
                	catch(ArrayIndexOutOfBoundsException e) {
                		System.out.println("Invalid command - it should be of the form 'editcountry -add countryName continentName -remove countryName'");
                	}
                	catch(Exception e) {
                		System.out.println("Invalid command - it should be of the form 'editcountry -add countryName continentName -remove countryName'");
                	}
                    break;

                case "editneighbor":
                	try {
                		for (int i = 1; i < data.length; i++) {
                            if (data[i].equals("-add")) {
                                if (this.isAlpha(data[i + 1]) || this.isAlpha(data[i + 2])) {
                                    countryName = data[i + 1];
                                    neighborCountryName = data[i + 2];
                                } else {
                                	System.out.println("Invalid country name");
                                }

                                boolean check = mapCmd.addNeighbor(game.getMap(), countryName, neighborCountryName);
                                if (check) {
                                    System.out.println("Neighbor added to the map");
                                    game.setGamePhase(Phase.EDITMAP);
                                } else {
                                	System.out.println("Country does not exist - Please enter valid neighbor or country name");
                                }
                            } else if (data[i].equals("-remove")) {
                                if (this.isAlpha(data[i + 1]) || this.isAlpha(data[i + 2])) {
                                    countryName = data[i + 1];
                                    neighborCountryName = data[i + 2];
                                } else {
                                	System.out.println("Invalid country name");
                                }

                                boolean check = mapCmd.removeNeighbor(game.getMap(), countryName, neighborCountryName);
                                if (check) {
                                    System.out.println("Neighbor removed from the map");
                                    game.setGamePhase(Phase.EDITMAP);
                                } else
                                    System.out.println("Country does not exist - Please enter valid neighbor or country name");
                            } 
                        }
                	}
                	catch(ArrayIndexOutOfBoundsException e) {
                		System.out.println("Invalid command - it should be of the form 'editneighbor -add countryName neighborCountryName -remove countryName neighborCountryName'");
                	}
                	catch(Exception e) {
                		System.out.println("Invalid command - it should be of the form 'editneighbor -add countryName neighborCountryName -remove countryName neighborCountryName'");
                	}
                    break;

                case "savemap":
                	try {
                		if (!(data[1] == "")) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                boolean check = mapCmd.saveMap(game.getMap(), mapName);
                                if (check) {
                                    System.out.println("Map file saved successfully");
                                    game.setGamePhase(Phase.EDITMAP);
                                } else
                                    System.out.println("Error in map saving - invalid map");
                            } else
                                System.out.println("Map name not valid.");
                        } 
                	}
                	catch(ArrayIndexOutOfBoundsException e) {
                		System.out.println("Invalid command - it should be of the form 'savemap filename'");
                	}
                	catch(Exception e) {
                		System.out.println("Invalid command - it should be of the form 'savemap filename'");
                	}
                    break;

                case "showmap":
                    mapCmd.showMap(game.getMap());
                    game.setGamePhase(Phase.EDITMAP);
                    break;

                case "editmap":
                	try {
                		if (data[1] != null) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                game.setMap(mapCmd.editMap(mapName));
                                System.out.println("You can now edit " + mapName);
                                game.setGamePhase(Phase.EDITMAP);
                            } else
                                System.out.println("Map name not valid.");
                        } 
                	}
                	catch(ArrayIndexOutOfBoundsException e) {
                		System.out.println("Invalid command - it should be of the form 'editmap mapname.map'");
                	}
                	catch(Exception e) {
                		System.out.println("Invalid command - it should be of the form 'editmap mapname.map'");
                	}
                    break;

                case "loadmap":
                	try {
                		if (!(data[1] == "")) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                game.setMap(mapCmd.loadMap(mapName));
                                if (game.getMap() != null) {
                                    if (!game.getMap().getValid()) {
                                        System.out.println("Map is not valid for game play");
                                        game.setGamePhase(Phase.NULL);
                                    } else {
                                    	System.out.println("Map is valid. Add players now.");
                                        game.setGamePhase(Phase.STARTUP);
                                    }
                                } else {
                                    game.setGamePhase(Phase.NULL);
                                }
                            } else
                                System.out.println("Map name not valid.");
                        } 
                	}
                	catch(ArrayIndexOutOfBoundsException e) {
                		System.out.println("Invalid command - it should be of the form 'loadmap mapname.map'");
                	}
                	catch(Exception e) {
                		System.out.println("Invalid command - it should be of the form 'loadmap mapname.map'");
                	}
                    break;
                    
                case "validatemap":
                	if(mapCmd.validateMap(game.getMap())) {
                		System.out.println("Valid map");
                	}
                	else {
                		System.out.println("Invalid map");
                	}
                	break;
                	
                default:
                    System.out.println("Invalid command - either use edit commands(editcontinent/editcountry/editneighbor) or savemap/validatemap/editmap/loadmap/showmap command");
                    break;

            }
        }
        else if (game.getGamePhase().equals(Phase.STARTUP)) {
            switch (commandName) {
                case "gameplayer":
                	try {
                		for (int i = 1; i < data.length; i++) {
                            if (data[i].equals("-add")) {
                                if (data[i + 1].matches("[a-zA-Z0-9]+")) {
                                    playerName = data[i + 1];
                                    boolean check = startUp.addPlayer(game.getPlayers(), playerName);
                                    if (check) {
                                        System.out.println("Player successfully added");
                                    } else {
                                        System.out.println("Can not add any more player. Maximum 6 players can play.");
                                    }
                                    game.setGamePhase(Phase.STARTUP);
                                } else {
                                	System.out.println("Invalid player name");
                                }     
                            } else if (data[i].equals("-remove")) {
                                if (data[i + 1].matches("[a-zA-Z0-9]+")) {
                                    playerName = data[i + 1];
                                    boolean check = startUp.removePlayer(game.getPlayers(), playerName);
                                    if (check)
                                        System.out.println("Player successfully removed");
                                    else
                                        System.out.println("Player does not exist");
                                    game.setGamePhase(Phase.STARTUP);
                                } else
                                    System.out.println("Invalid player name");
                            }
                        }
                	}
                	catch(ArrayIndexOutOfBoundsException e) {
                		System.out.println("Invalid command - it should be of the form 'gameplayer -add playername -remove playername'");
                	}
                	catch(Exception e) {
                		System.out.println("Invalid command - it should be of the form 'gameplayer -add playername -remove playername'");
                	}
                    break;

                case "populatecountries":
                    boolean check = startUp.populateCountries(game.getMap(), game.getPlayers());
                    if (check) {
                    	System.out.println("Countries allocated amongst the players");
                    }
                    game.setGamePhase(Phase.ARMYALLOCATION);
                    startUp.armyDistribution(game.getPlayers(), this, game.getGamePhase());
                    game.setGamePhase(Phase.REINFORCEMENT);
                    break;
                
                case "showmap":
		        	startUp.showMap(game.getPlayers(), game.getMap());
		        	break;
                	
                default:
                    System.out.println("Invalid command - use gameplayer/populatecountries/placearmy/placeall/showmap commands in start up phase.");
                    break;
            }
        }
        else if(game.getGamePhase().equals(Phase.ARMYALLOCATION)) {
        	switch (commandName) {
	        	case "placearmy":
		        	try {
		        		if (!(data[1] == "")) {
		                    if (this.isAlpha(data[1])) {
		                        countryName = data[1];
		                        startUp.placeArmy(player, countryName);
		                        boolean checkstatus = startUp.isAllArmyPlaced(game.getPlayers());
		                        if (checkstatus)
                                    game.setGamePhase(Phase.REINFORCEMENT);
		                        else
                                    game.setGamePhase(Phase.ARMYALLOCATION);
		                    } else
		                        System.out.println("Invalid country name");
		                } 
		        	}
		        	catch(ArrayIndexOutOfBoundsException e) {
		        		System.out.println("Invalid command - it should be of the form 'placearmy countryname'");
		        	}
		        	catch(Exception e) {
		        		System.out.println("Invalid command - it should be of the form 'placearmy countryname'");
                	}
		            break;
	
		        case "placeall":
		            if(startUp.placeAll(game.getPlayers())) {
		            	System.out.println("Armies placed successfully");
		            }
                    game.setGamePhase(Phase.REINFORCEMENT);
		            break;
	        
		        case "showmap":
		        	startUp.showMap(game.getPlayers(), game.getMap());
		        	break;
	        	
		        default:
		        	System.out.println("Invalid command - use placearmy/placeall/showmap commands to first allocate the assigned armies.");
		            break;
        	}		
        }
        else if (game.getGamePhase().equals(Phase.REINFORCEMENT)) {
            switch (commandName) {
                case "reinforce":
                	try {
                		if (!(data[1] == null) || !(data[2] == null)) {
                            if (this.isAlpha(data[1]) || data[2].matches("[0-9]+")) {
                                countryName = data[1];
                                numberOfArmies = Integer.parseInt(data[2]);
                                boolean check = rfc.reinforce(player, countryName, numberOfArmies);
                                if (check) {
                                	if(player.getOwnedArmies()==0) {
                                		System.out.println("Reinforcement phase successfully ended. Begin fortification now.");
                                        game.setGamePhase(Phase.FORTIFICATION);
                                	}
                                }
                            } else
                                System.out.println("Invalid command - invalid characters in command");
                        } 
                	}
                	catch(ArrayIndexOutOfBoundsException e) {
                		System.out.println("Invalid command - it should be of the form 'reinforce countryName num'");
                	}
                	catch(Exception e) {
                		System.out.println("Invalid command - it should be of the form 'reinforce countryName num'");
                	}
                    break;
                    
                case "showmap":
                	startUp.showMap(game.getPlayers(), game.getMap());
                	break;
                	
                default:
                    System.out.println("Invalid command - either use reinforce or showmap commands in reinforcement phase.");
                    break;
            }
        }

        else if (game.getGamePhase().equals(Phase.FORTIFICATION)) {
            switch (commandName) {
                case "fortify":
                	try {
                		if(data[1].equals("none")) {
                    		System.out.println("Successfull fortification");
                            game.setGamePhase(Phase.TURNEND);
                    	}
                    	else if (!(data[1] == null) && !(data[2] == null) && !(data[3] == null)) {
                            if (this.isAlpha(data[1]) || this.isAlpha(data[2]) || data[3].matches("[0-9]+")) {
                                fromCountry = data[1];
                                toCountry = data[2];
                                armiesToFortify = Integer.parseInt(data[3]);

                                boolean check = ftf.fortify(player, fromCountry, toCountry, armiesToFortify);
                                if (check) {
                                    System.out.println("Successfull fortification");
                                    game.setGamePhase(Phase.TURNEND);
                                } 
                            } else
                                System.out.println("Invalid command - invalid characters in command");
                        }
                	}
                	catch(ArrayIndexOutOfBoundsException e) {
                		System.out.println("Invalid command - it should be of the form 'fortify fromCountry toCountry num' or 'foritfy none'");
                	}
                	catch(NumberFormatException e) {
                        System.out.println("Invalid command - it should be of the form 'fortify fromCountry toCountry num' or 'foritfy none'");
                	}
                	catch(Exception e) {
                        System.out.println("Invalid command - it should be of the form 'fortify fromCountry toCountry num' or 'foritfy none'");
                	}
                    break;
                    
                case "showmap":
                	startUp.showMap(game.getPlayers(), game.getMap());
                	break;
                	
                default:
                    System.out.println("Invalid command - either use fortify/showmap command.");
                    break;
            }
        }
        return game.getGamePhase();
    }
}
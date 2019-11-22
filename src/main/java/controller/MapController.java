package main.java.controller;

import main.java.model.*;
import main.java.view.MapView;

/**
 * Responsible for handling user commands related to editing/loading a map.
 */
public class MapController extends Controller{

    /**
     * Helps access methods to view map.
     */
    public MapView mapView;

    /**
     * Intializes required class variables.
     */
    public MapController(){
        game = new GameData();
        gameAction = new GameActions();
        mapView = new MapView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String parseCommand(Player player, String newCommand){
        int controlValue;

        String message = "";
        String mapName;
        String continentName;
        String countryName;
        String neighborCountryName;
        String[] data = newCommand.split("\\s+");
        String commandName = data[0];

        if (game.getGamePhase().equals(Phase.NULL)) {
            switch (commandName) {
                case "editmap":
                    try {
                        if (data.length > 2) {
                            message = "Invalid command - it should be of the form 'editmap mapname.map'";
                            return message;
                        }
                        if (!(data[1] == "")) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                gameAction.editMap(game, mapName);
                                if (game.getMap().getCountries().size() == 0) {
                                    message = mapName + " does not exist." + "\nCreating a new map named " + mapName;
                                    return message;
                                }
                                message = "You can now edit " + mapName;
                                return message;
                            } else {
                                message = "Invalid map name.";
                                return message;
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'editmap mapname.map'";
                        return message;
                    }
                    break;

                case "loadmap":
                    try {
                        if (data.length > 2) {
                            message = "Invalid command - it should be of the form 'loadmap mapname.map'";
                            return message;
                        }
                        if (data[1] != null) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                if (gameAction.loadMap(game, mapName)) {
                                    if (!game.getMap().getValid()) {
                                        message = "Map not suitable for game play. Correct the map to continue with this map or load/edit another map.";
                                    } else {
                                        message = "Map is valid. Add players now.";
                                    }
                                } else {
                                    message = "Map " + mapName + " does not exist. Try again or use 'editmap' if you want to create new map.";
                                }
                            } else {
                                message = "Map name not valid.";
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'loadmap mapname.map'";
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'loadmap mapname.map'";
                    }
                    break;

                default:
                    message = "Load or edit map first using commands: 'loadmap mapname.map' or 'editmap.mapname.map'";
                    break;
            }
        } else if(game.getGamePhase().equals(Phase.EDITMAP)){
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
                                        message = continentName + " added to the map";
                                    } else {
                                        message = "Continent already exists - Please add valid Continent Name";
                                    }
                                } else {
                                    message = "Invalid continent name";
                                }
                            } else if (data[i].equals("-remove")) {
                                if (this.isAlpha(data[i + 1])) {
                                    continentName = data[i + 1];
                                    boolean check = game.getMap().removeContinent(continentName);
                                    if (check) {
                                        message = continentName + " removed from the map";
                                    } else {
                                        message = "Continent does not exist - Please enter valid continent name";
                                    }
                                } else {
                                    message = "Invalid continent name";
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'editcontinent -add continentName controlValue -remove continentName'";
                    } catch (NumberFormatException e) {
                        message = "Invalid command - it should be of the form 'editcontinent -add continentName controlValue -remove continentName'";
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'editcontinent -add continentName controlValue -remove continentName'";
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
                                        message = countryName + " added to the map";
                                    } else {
                                        if (!game.getMap().getContinents().containsKey(continentName.toLowerCase())) {
                                            message = continentName + " does not exist.";
                                        } else {
                                            message = "Country already exists - Please add valid Continent Name";
                                        }
                                    }
                                } else {
                                    message = "Invalid country/continent name";
                                }
                            } else if (data[i].equals("-remove")) {
                                if (this.isAlpha(data[i + 1])) {
                                    countryName = data[i + 1];
                                    boolean check = game.getMap().removeCountry(countryName);
                                    if (check) {
                                        message = countryName + " removed from the map";
                                    } else {
                                        message = "Country does not exist - Please enter valid country name";
                                    }
                                } else {
                                    message = "Invalid country name";
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'editcountry -add countryName continentName -remove countryName'";
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'editcountry -add countryName continentName -remove countryName'";
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
                                        message = neighborCountryName + " added as neighbor of " + countryName;
                                    } else {
                                        if (!game.getMap().getCountries().containsKey(countryName.toLowerCase()) && !game.getMap().getCountries().containsKey(neighborCountryName.toLowerCase())){
                                            message = countryName + " and " + neighborCountryName + "  does not exist. Create country first and then set their neighbors.";
                                        } else if (!game.getMap().getCountries().containsKey(countryName.toLowerCase())) {
                                            message = countryName + " does not exist. Create country first and then set its neighbors.";
                                        } else {
                                            message = neighborCountryName + " does not exist. Create country first and then set its neighbors.";
                                        }
                                    }
                                } else {
                                    message = "Invalid country name.";
                                }
                            } else if (data[i].equals("-remove")) {
                                if (this.isAlpha(data[i + 1]) || this.isAlpha(data[i + 2])) {
                                    countryName = data[i + 1];
                                    neighborCountryName = data[i + 2];
                                    boolean check = game.getMap().removeNeighbor(countryName, neighborCountryName);
                                    if (check) {
                                        message = neighborCountryName + " removed as neighbor of " + countryName;
                                        System.out.println(neighborCountryName + " removed as neighbor of " + countryName);
                                    } else {
                                        if (!game.getMap().getCountries().containsKey(countryName.toLowerCase()) && !game.getMap().getCountries().containsKey(neighborCountryName.toLowerCase())){
                                            message = countryName + " and " + neighborCountryName + "  does not exist.";
                                        } else if (!game.getMap().getCountries().containsKey(countryName.toLowerCase())) {
                                            message = countryName + " does not exist.";
                                        } else {
                                            message = neighborCountryName + " does not exist.";
                                        }
                                    }
                                } else {
                                     message = "Invalid country name";
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'editneighbor -add countryName neighborCountryName -remove countryName neighborCountryName'";
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'editneighbor -add countryName neighborCountryName -remove countryName neighborCountryName'";
                    }
                    break;

                case "savemap":
                    try {
                        if (!(data[1] == "")) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                boolean check = false;
                                if(game.getMapType().equals("domination")){
                                    check = (new LoadMap()).saveMap(game.getMap(), mapName);
                                } else {
                                    check = (new MapAdapter(new LoadConquestMap())).saveMap(game.getMap(), mapName);
                                }
                                if (check) {
                                    message = "Map file saved successfully as " + mapName + ".map";
                                } else {
                                    message = "Error in map saving. Map not suitable for game play. Correct the map to continue with the new map or load a map from the existing maps.";
                                }
                            } else {
                                message = "Map name is not valid.";
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'savemap filename'";
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'savemap filename'";
                    }
                    break;

                case "showmap":
                    mapView.showMap(game.getMap());
                    break;

                case "editmap":
                    try {
                        if (data.length > 2) {
                            message = "Invalid command - it should be of the form 'editmap mapname.map'";
                            return message;
                        }
                        if (!(data[1] == "")) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                gameAction.editMap(game, mapName);
                                if (game.getMap().getCountries().size() == 0) {
                                    message = mapName + " does not exist." + "\nCreating a new map named " + mapName;
                                    return message;
                                }
                                message = "You can now edit " + mapName;
                                return message;
                            } else {
                                message = "Invalid map name.";
                                return message;
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'editmap mapname.map'";
                        return message;
                    }
                    break;

                case "loadmap":
                    try {
                        if (data.length > 2) {
                            message = "Invalid command - it should be of the form 'loadmap mapname.map'";
                            return message;
                        }
                        if (data[1] != null) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                if (gameAction.loadMap(game, mapName)) {
                                    if (!game.getMap().getValid()) {
                                        message = "Map not suitable for game play. Correct the map to continue with this map or load/edit another map.";
                                    } else {
                                        message = "Map is valid. Add players now.";
                                    }
                                } else {
                                    message = "Map " + mapName + " does not exist. Try again or use 'editmap' if you want to create new map.";
                                }
                            } else {
                                message = "Map name not valid.";
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'loadmap mapname.map'";
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'loadmap mapname.map'";
                    }
                    break;

                case "validatemap":
                    MapValidityStatus mvs = gameAction.validateMap(game.getMap());
                    if (mvs == MapValidityStatus.VALIDMAP) {
                        message = "Valid map.";
                    } else if (mvs == MapValidityStatus.EMPTYCONTINENT) {
                        message = "Invalid map - empty continent present.";
                    } else if (mvs == MapValidityStatus.UNCONNECTEDGRAPH) {
                        message = "Invalid map - not a connected graph";
                    } else {
                        message = "Invalid map - one of the continent is not a connected sub-graph";
                    }
                    break;

                default:
                    message = "Invalid command - either use edit commands(editcontinent/editcountry/editneighbor) or savemap/validatemap/editmap/loadmap/showmap command";
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
     * Ensures map name is valid.
     *
     * @param s input string
     * @return true if valid match, else false
     */
    public boolean isMapNameValid(String s) {

        return s != null && s.matches("^[a-zA-Z.]*$");
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
     * Registers the type of map to be used to play the game.
     * @param mapType Type of the map to be used for the game.
     */
    public void registerMapType(String mapType){
        game.setMapType(mapType);
    }
}

package main.java.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * Interface to read domination map format.
 */
public interface DominationMap {
    /**
     * Tracks the index value of continents, new or existing, to later facilitate writing them to
     * map files following domaination's conventions.
     */
    private int inMapIndex = 1;

    /**
     * Represents the game map.
     */
    private GameMap map;

    /**
     * Stores list of countries in the map.
     * Facilitates reading neighbors of a country written following the rules of ".map" files of domination file format
     * HashMap has
     * 1) Key: integer value representing the index of the country in ".map" file of domination file format
     * 2) Value: corresponding Country object
     */
    private HashMap<Integer, Country> listOfCountries; //temporary HashMap to facilitate reading .map files

    /**
     * Getter method for getting the game map.
     * @return GameMap object that is created.
     */
    public GameMap getMap();

    /**
     * Setter method for setting the game map.
     * @param map GameMap object to be set.
     */
    public void setMap(GameMap map);

    /**
     * Reads the ".map" file and creates a GameMap object accordingly.
     * Performs basic validation checks too.
     * @param mapName Name of the map file to be read
     * @return GameMap object representing the map just read
     * @throws FileNotFoundException, IOException
     */
    public GameMap readMap(String mapName);

    /**
     * Reads the continents from the ".map" files.
     * Exits the program if error of duplicate continents is found.
     * @param reader Stream starting from continents section of ".map" file
     * @return BufferedReader stream at the point where it has finished reading continents
     * @throws IOException
     */
    private BufferedReader readContinents(BufferedReader reader);

    /**
     * Reads the countries from the ".map" files.
     * Exits the program if duplicate country or in non-existent country.
     * @param reader Stream starting from countries section of ".map" file
     * @return BufferedReader stream at the point where it has finished reading countries
     * @throws IOException
     */
    private BufferedReader readCountries(BufferedReader reader);

    /**
     * Reads the borders from the ".map" files.
     * Exits the programming error if attempted to add invalid neighbor or to an invalid country.
     * @param reader Stream starting from borders section of ".map" file
     * @return BufferedReader stream at the end of file
     * @throws IOException
     */
    private BufferedReader readBorders(BufferedReader reader);

    /**
     * Registers this new country as part of its continent.
     * If duplicate country, exits the program throwing error.
     * @param newCountry Country to be registered with the corresponding continent
     */
    private void addToContinentMap(Country newCountry);

    /**
     * Registers the country at argument 'stringIndex' with the argumentCountry.
     * Exits the programming throwing error if invalid neighbor is found.
     * @param argumentCountry Country to which neighbor is to be registered.
     * @param stringIndex Index of the country to be added as a neighbor to the argument country
     */
    private void addNeighbour(Country argumentCountry, String stringIndex);

    /**
     * Saves GameMap object as ".map" file following Domination game format
     *
     * @param map      GameMap object representing the map to be saved
     * @param fileName Name with which map file is to be saved
     * @return true if successful, else false indicating invalid command
     * @throws IOException
     */
    public boolean saveMap(GameMap map, String fileName);
}
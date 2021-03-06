package main.java.model;

/**
 * Interface to read/write domination map format.
 */
public interface DominationMap {

    /**
     * Reads the ".map" file and creates a GameMap object accordingly.
     * Performs basic validation checks too.
     * @param mapName Name of the map file to be read
     * @return GameMap object representing the map just read
     */
    public GameMap readMap(String mapName);

    /**
     * Saves GameMap object as ".map" file following Domination game format
     *
     * @param map      GameMap object representing the map to be saved
     * @param fileName Name with which map file is to be saved
     * @return true if successful, else false indicating invalid command
     */
    boolean saveMap(GameMap map, String fileName);
}
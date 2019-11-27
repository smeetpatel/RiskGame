package main.java.model;

/**
 * Adapter class for maps of domination and conquest map formats.
 */
public class MapAdapter implements DominationMap{

    /**
     * Represents the map of Conquest type maps.
     */
    ConquestMap conquestMap;

    /**
     * Creates the MapAdapter object and in turn creates a ConquestMap object.
     * @param conquestMap
     */
    public MapAdapter(ConquestMap conquestMap){
        super();
        this.conquestMap = conquestMap;
    }

    /**
     * Reads conquest map.
     * @param mapName Name of the map file to be read.
     * @return Returns game object after reading the argument file.
     */
    @Override
    public GameMap readMap(String mapName) {
        return conquestMap.readMap(mapName);
    }

    /**
     * Saves map as Conquest map type.
     * @param map      GameMap object representing the map to be saved
     * @param fileName Name with which map file is to be saved
     * @return true if successful in saving the
     */
    @Override
    public boolean saveMap(GameMap map, String fileName) {
        return conquestMap.saveMap(map, fileName);
    }
}

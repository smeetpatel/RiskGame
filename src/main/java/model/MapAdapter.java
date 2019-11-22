package main.java.model;

/**
 * Adapter class responsible to make ConquestMap compatible with DominationMap.
 */
public class MapAdapter implements  DominationMap{

    /**
     * Helps access conquest map.
     */
    ConquestMap conquestMap;

    /**
     * Creates map adapter object.
     * @param conquestMap Conquest map object to be used.
     */
    public MapAdapter(ConquestMap conquestMap){
        super();
        this.conquestMap = conquestMap;
    }

    /**
     * Reads conquest map.
     * @param mapName Name of the map file to be read
     * @return Map representing the game
     */
    @Override
    public GameMap readMap(String mapName){
        GameMap map = conquestMap.readMap(mapName);
        return map;
    }

    /**
     * Saves map as per Conquest format.
     * @param map      GameMap object representing the map to be saved
     * @param fileName Name with which map file is to be saved
     * @return true if successful, else false
     */
    @Override
    public boolean saveMap(GameMap map, String fileName){
        boolean check = conquestMap.saveMap(map, fileName);
        return check;
    }
}
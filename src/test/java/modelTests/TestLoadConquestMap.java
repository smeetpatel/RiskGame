package test.java.modelTests;

import main.java.model.ConquestMap;
import main.java.model.GameActions;
import main.java.model.GameMap;
import main.java.model.LoadConquestMap;
import main.java.view.MapView;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests load conquest map
 */
public class TestLoadConquestMap {

    /**
     * Refers to the game map
     */
    ConquestMap map;

    /**
     * Refers to the game actions variable.
     */
    GameActions gameActions;

    /**
     * Helps view map.
     */
    MapView mapView;

    @Before
    public void before(){
        map = new LoadConquestMap();
        gameActions = new GameActions();
        mapView = new MapView();
    }

    @Test
    public void testLoadConquestMap1(){
        GameMap gameMap = map.readMap("src/main/resources/maps/3D Cliff.map");
        if(map!=null){
            System.out.println(gameActions.validateMap(gameMap));
            mapView.showMap(gameMap);
        }
    }
}

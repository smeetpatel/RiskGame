package test.java;

import main.java.GameMap;
import main.java.MapEditor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests if neighbors are being removed properly or not
 *
 */
public class TestNeighbor {

    GameMap map;
    String countryName;
    String neighborCountrynName;
    MapEditor rcmd;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        map = new GameMap("ameroki.map");
        rcmd = new MapEditor();
        countryName = "siberia";
        neighborCountrynName = "egypt";
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testNeighbor(){

        /*map = rcmd.editMap("ameroki.map");
        System.out.println(map.getMapName());
        boolean check = rcmd.removeNeighbor(map, countryName, neighborCountrynName);
        assertEquals(true,check);*/
    }
}

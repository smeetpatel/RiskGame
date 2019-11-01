package test.java;

import main.java.GameMap;
import main.java.MapValidation;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if connected maps are being rightly identified or not.
 *
 */
public class TestConnectedMap {

    MapValidation mvr;
    GameMap map;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        map = new GameMap("world.map");
        mvr = new MapValidation();
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testConnectedMap(){
        /*map = rcmd.editMap("world.map");
        //boolean check = rcmd.saveMap(map,"naee.map");
        boolean check = mvr.isGraphConnected(mvr.createGraph(map));
        assertEquals(true,check);*/
    }
}

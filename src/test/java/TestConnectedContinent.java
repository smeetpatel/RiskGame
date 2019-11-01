package test.java;

import main.java.GameMap;
import main.java.MapValidation;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if connected continent are being rightly identified or not.
 *
 */
public class TestConnectedContinent {
    MapValidation mvr;
    GameMap map;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        mvr = new MapValidation();
        map = new GameMap("world.map");
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testConnectedContinent(){
        /*map = rcmd.editMap("world.map");
        boolean check = mvr.continentConnectivityCheck(map);
        assertEquals(true,check);*/
    }
}
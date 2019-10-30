package test.java;

import main.java.GameMap;
import main.java.MapEditor;
import main.java.MapValidator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests if connected continent are being rightly identified or not.
 *
 */
public class TestConnectedContinent {
    MapValidator mvr;
    MapEditor rcmd;
    GameMap map;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        mvr = new MapValidator();
        rcmd = new MapEditor();
        map = new GameMap("world.map");
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testConnectedContinent(){
        map = rcmd.editMap("world.map");
        boolean check = mvr.continentConnectivityCheck(map);
        assertEquals(true,check);
    }
}
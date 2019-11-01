package test.java;

import static org.junit.Assert.*;

import main.java.GameMap;
import main.java.MapEditor;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if edit map works correctly or not.
 *
 */
public class TestEditMap {

    MapEditor rcmd;
    String mapName;
    GameMap map;
    
    /**
     * Set up the context
     */
    @Before
    public void before(){
        rcmd = new MapEditor();
        mapName = "newcall.map";
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testEditMap(){
        //map = rcmd.editMap(mapName);
        //assertEquals(map.getMapName(), mapName);
    }
}
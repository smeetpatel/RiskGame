package test.java.modelTests;

import main.java.model.GameMap;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if edit map works correctly or not.
 *
 */
public class TestEditMap {

    String mapName;
    GameMap map;
    
    /**
     * Set up the context
     */
    @Before
    public void before(){
        mapName = "newcall.map";
    }

    /**
     * Test if tests are rightly identified or not
     * This test method checks that edit map method
     */
    @Test
    public void testEditMap(){
        //map = rcmd.editMap(mapName);
        //assertEquals(map.getMapName(), mapName);
    }
}

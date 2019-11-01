package test.java;

import main.java.GameMap;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if continents are being removed properly or not
 *
 */
public class TestContinent {

    GameMap map;
    String continentName;
    int controlValue;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        map = new GameMap("ameroki.map");
        continentName = "azio";
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testContinent(){

        /*map = rcmd.editMap("ameroki.map");
        System.out.println(map.getMapName());
        System.out.println(map.getContinents().size());
        boolean check = rcmd.removeContinent(map, continentName);
        assertEquals(true,check);*/
    }
}

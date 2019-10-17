package test.java;

import static org.junit.Assert.*;

import main.java.GameMap;
import main.java.RunCommand;
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
    RunCommand rcmd;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        map = new GameMap("ameroki.map");
        rcmd = new RunCommand();
        continentName = "azio";
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testContinent(){

        map = rcmd.editMap("ameroki.map");
        System.out.println(map.getMapName());
        System.out.println(map.getContinents().size());
        boolean check = rcmd.removeContinent(map, continentName);
        assertEquals(true,check);
    }
}

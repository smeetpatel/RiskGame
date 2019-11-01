package test.java;

import main.java.GameMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests if country are being removed properly or not
 *
 */
public class TestCountry {

    GameMap map;
    String countryName;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        map = new GameMap("ameroki.map");
        countryName = "siberia";
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testCountry(){

        /*map = rcmd.editMap("ameroki.map");
        System.out.println(map.getMapName());
        System.out.println(map.getCountries().size());
        boolean check = rcmd.removeCountry(map, countryName);
        assertEquals(true,check);*/
    }
}
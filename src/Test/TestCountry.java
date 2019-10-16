package Test;

import static org.junit.Assert.*;

import main.java.GameMap;
import main.java.RunCommand;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCountry {

    GameMap map;
    String countryName;
    RunCommand rcmd;

    @Before
    public void before(){
        map = new GameMap("ameroki.map");
        rcmd = new RunCommand();
        countryName = "siberia";
    }

    @Test
    public void testCountry(){

        map = rcmd.editMap("ameroki.map");
        System.out.println(map.getMapName());
        System.out.println(map.getCountries().size());
        boolean check = rcmd.removeCountry(map, countryName);
        assertEquals(true,check);
    }
}
package test.java;

import static org.junit.Assert.*;

import main.java.GameMap;
import main.java.RunCommand;
import org.junit.Before;
import org.junit.Test;

public class TestContinent {

    GameMap map;
    String continentName;
    int controlValue;
    RunCommand rcmd;

    @Before
    public void before(){
        map = new GameMap("ameroki.map");
        rcmd = new RunCommand();
        continentName = "azio";
    }

    @Test
    public void testContinent(){

        map = rcmd.editMap("ameroki.map");
        System.out.println(map.getMapName());
        System.out.println(map.getContinents().size());
        boolean check = rcmd.removeContinent(map, continentName);
        assertEquals(true,check);
    }
}

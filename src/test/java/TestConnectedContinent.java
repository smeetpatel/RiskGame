package test.java;

import main.java.GameMap;
import main.java.MapValidator;
import main.java.RunCommand;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestConnectedContinent {
    MapValidator mvr;
    RunCommand rcmd;
    GameMap map;

    @Before
    public void before(){
        mvr = new MapValidator();
        rcmd = new RunCommand();
        map = new GameMap("world.map");
    }

    @Test
    public void testConnectedContinent(){
        map = rcmd.editMap("world.map");
        boolean check = mvr.continentConnectivityCheck(map);
        assertEquals(true,check);
    }
}

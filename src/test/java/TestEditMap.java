package test.java;

import static org.junit.Assert.*;

import main.java.GameMap;
import main.java.RunCommand;
import org.junit.Before;
import org.junit.Test;

public class TestEditMap {

    RunCommand rcmd;
    String mapName;
    GameMap map;
    @Before
    public void before(){
        rcmd = new RunCommand();
        mapName = "newcall.map";
    }

    @Test
    public void testEditMap(){
        map = rcmd.editMap(mapName);
        assertEquals(map.getMapName(), mapName);
    }
}

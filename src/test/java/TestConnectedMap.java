package test.java;

import static org.junit.Assert.*;
import main.java.GameMap;
import main.java.MapValidator;
import main.java.RunCommand;
import org.junit.Before;
import org.junit.Test;
import org.junit.validator.ValidateWith;

public class TestConnectedMap {

    MapValidator mvr;
    GameMap map;
    RunCommand rcmd;

    @Before
    public void before(){
        map = new GameMap("world.map");
        mvr = new MapValidator();
        rcmd = new RunCommand();
    }

    @Test
    public void testConnectedMap(){
        map = rcmd.editMap("world.map");
        //boolean check = rcmd.saveMap(map,"naee.map");
        boolean check = mvr.isGraphConnected(mvr.createGraph(map));
        assertEquals(true,check);
    }
}

package Test;

import main.java.GameMap;
import main.java.RunCommand;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestValidateMap {
    RunCommand rcmd;
    GameMap map;

    @Before
    public void before(){

        rcmd = new RunCommand();
        map = new GameMap("ameroki.map");
    }

    @Test
    public void testValidateMap(){
        map = rcmd.editMap("ameroki.map");
        boolean check = rcmd.validateMap(map);
        assertEquals(true,check);
    }
}

package test.java;

import main.java.GameMap;
import main.java.RunCommand;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if map gets validated correctly or not
 *
 */
public class TestValidateMap {
    RunCommand rcmd;
    GameMap map;

    /**
     * Set up the context
     */
    @Before
    public void before(){

        rcmd = new RunCommand();
        map = new GameMap("ameroki.map");
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testValidateMap(){
        map = rcmd.editMap("ameroki.map");
        boolean check = rcmd.validateMap(map);
        assertEquals(true,check);
    }
}

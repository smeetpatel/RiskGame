package Test;

import main.java.GameMap;
import main.java.RunCommand;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestNeighbor {

    GameMap map;
    String countryName;
    String neighborCountrynName;
    RunCommand rcmd;

    @Before
    public void before(){
        map = new GameMap("ameroki.map");
        rcmd = new RunCommand();
        countryName = "siberia";
        neighborCountrynName = "egypt";
    }

    @Test
    public void testNeighbor(){

        map = rcmd.editMap("ameroki.map");
        System.out.println(map.getMapName());
        boolean check = rcmd.removeNeighbor(map, countryName, neighborCountrynName);
        assertEquals(true,check);
    }
}

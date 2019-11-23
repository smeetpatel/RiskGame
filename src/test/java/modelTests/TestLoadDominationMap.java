package test.java.modelTests;

import main.java.model.GameActions;
import main.java.model.GameData;
import main.java.model.Phase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestLoadDominationMap {
    /**
     * Represents the state of the game
     */
    GameData game;

    /**
     * To help load the map.
     */
    GameActions gameActions;

    /**
     * Set up the context
     * Initialize the objects and parameters
     */
    @Before
    public void before() {
        //initialize required references
        gameActions = new GameActions();
        game = new GameData();
    }

    /**
     * Tests if correctly identifies invalid map file or not.
     */
    @Test
    public void testLoadMap1(){
        gameActions.loadMap(game, "invalid.map");
        Assert.assertFalse(game.getMap().getValid());
        Assert.assertEquals(Phase.NULL, game.getGamePhase());
    }

    /**
     * Tests if correctly identifies invalid map file or not.
     */
    @Test
    public void testLoadMap2(){
        gameActions.loadMap(game, "emptyMap.map");
        Assert.assertFalse(game.getMap().getValid());
        Assert.assertEquals(Phase.NULL, game.getGamePhase());
    }

    /**
     * Tests if correctly identifies valid map file or not.
     */
    @Test
    public void testLoadMap3(){
        gameActions.loadMap(game, "testmapthird.map");
        Assert.assertTrue(game.getMap().getValid());
        Assert.assertEquals(Phase.STARTUP, game.getGamePhase());
    }
}

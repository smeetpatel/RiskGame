package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import test.java.modelTests.*;

/**
 * run all the test cases present in the system
 */
@RunWith(Suite.class)
@SuiteClasses({TestArmyAllocation.class,
        TestAttack.class,
        TestConnectedContinent.class,
        TestConnectedMap.class,
        TestContinent.class,
        TestCountry.class,
        TestDeckCreation.class,
        TestEditMap.class,
        TestEmptyContinent.class,
        TestFortify.class,
        TestGamePlayer.class,
        TestLoadDominationMap.class,
        TestPopulatecountries.class,
        TestNeighbor.class,
        TestReinforceArmies.class,
        TestReinforcement.class,
        })

public class TestSuite {}
package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * run all the test cases present in the system
 */
@RunWith(Suite.class)
@SuiteClasses({TestConnectedContinent.class,
        TestConnectedMap.class,
        TestContinent.class,
        TestCountry.class,
        TestDeckCreation.class,
        TestEditMap.class,
        TestGameplayerAdd.class,
        TestPopulatecountries.class,
        TestNeighbor.class,
        TestReinforceArmies.class,
        TestReinforcement.class,
        TestValidateMap.class})

public class TestSuite {}
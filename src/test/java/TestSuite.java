package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestConnectedContinent.class,
        TestConnectedMap.class,
        TestContinent.class,
        TestCountry.class,
        TestEditMap.class,
        TestGameplayerAdd.class,
        TestPopulatecountries.class,
        TestNeighbor.class,
        TestReinforceArmies.class,
        TestValidateMap.class})

public class TestSuite {}
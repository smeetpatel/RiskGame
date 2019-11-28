package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.java.controllerTests.TestLoadGameController;
import test.java.controllerTests.TestStartUpController;
import test.java.controllerTests.TestTournamentController;
import test.java.controllerTests.TestTurnController;
import test.java.modelTests.*;

/**
 * run all the test cases for testing the models
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestContinent.class,
        TestDeckCreation.class,
        TestEditMap.class,
        TestGameActions.class,
        TestHumanPlayer.class,
        TestBenevolentPlayer.class,
        TestAggressivePlayer.class,
        TestCheaterPlayer.class,
        TestMapValidation.class,
})

/**
 * Runs all test cases for testing the models
 */
public class ModelsTestSuite {
}

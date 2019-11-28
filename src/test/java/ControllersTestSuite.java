package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.java.controllerTests.TestLoadGameController;
import test.java.controllerTests.TestStartUpController;
import test.java.controllerTests.TestTournamentController;
import test.java.controllerTests.TestTurnController;

/**
 * run all the test cases for testing the controllers
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestStartUpController.class,
        TestTurnController.class,
        TestLoadGameController.class,
        TestTournamentController.class
})

/**
 * Runs all test cases for testing the controllers
 */
public class ControllersTestSuite {
}

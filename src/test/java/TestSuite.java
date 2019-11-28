package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import test.java.controllerTests.TestLoadGameController;
import test.java.controllerTests.TestStartUpController;
import test.java.controllerTests.TestTournamentController;
import test.java.controllerTests.TestTurnController;
import test.java.modelTests.*;

/**
 * run all the test cases present in the system
 */
@RunWith(Suite.class)
@SuiteClasses({
        TestContinent.class,
        TestDeckCreation.class,
        TestEditMap.class,
        TestGameActions.class,
        TestHumanPlayer.class,
        TestMapValidation.class,
        TestStartUpController.class,
        TestTurnController.class,
        TestLoadGameController.class,
        TestTournamentController.class
        })

public class TestSuite {}
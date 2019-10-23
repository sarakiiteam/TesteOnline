package service.interfaces;

import database.models.Test;
import database.models.enums.Difficulty;
import utils.exceptions.ErrorMessageException;

import java.util.List;
import java.util.function.Predicate;

public interface ITestService {

    /**
     * Adds a new test into database
     * @param username: the user's username
     * @param testName: the test's name
     * @param testDifficulty: test's difficulty
     * @throws ErrorMessageException if something is wrong
     */
    void addTest(
            final String username,
            final String testName, final Difficulty testDifficulty) throws ErrorMessageException;

    /**
     * @return a list with all tests from database
     */
    List<Test> getAllTests();

    /**
     * @param predicate: lambda function that represents the condition
     * @return a list with all tests that respects a certain condition
     */
    List<Test> getFilteredTests(final Predicate<Test> predicate);
}



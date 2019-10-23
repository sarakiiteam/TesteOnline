package database.store.interfaces;

import database.models.Test;
import database.models.TestResult;
import database.models.enums.Difficulty;
import database.store.IPersistence;
import utils.exceptions.ErrorMessageException;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface ITestRepository extends IPersistence<Test> {

    /**
     *
     * @param name: the test's name
     * @return: optional.of(test) if in database exists a test with name eq to @param name or optional.isEmpty() otherwise
     */
    Optional<Test> getTestByName(final String name);

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
     * Add test result into database
     * @param testName: the name of the test
     * @param guestName: the name of the person that answered to test
     * @param points: test points
     * @param corrects: the number of correct answers
     * @throws ErrorMessageException if something is wrong
     */
    void addTestResult(
            final String testName,
            final String guestName, final int points, final int corrects) throws ErrorMessageException;

    /**
     *
     * @param predicate: a filter function
     * @return a list with all test results that respects a given condition
     */
    List<TestResult> getTestResultsByCondition(Predicate<TestResult> predicate);
}

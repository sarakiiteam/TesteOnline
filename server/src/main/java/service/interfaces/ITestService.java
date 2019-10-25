package service.interfaces;

import database.models.Question;
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
     * Add a new question to a specific test
     * @param testName: the name of test
     * @param question: the text of the question
     * @param answer: the text of the answer
     * @param points: the number of points
     */
    void addQuestion(
            final String testName,
            final String question, final String answer, final int points) throws ErrorMessageException;

    /**
     * Returns a list of test questions
     * @param testName: the name of test
     * @return a list of questions
     */
    List<Question> getQuestionsForTest(final String testName);

    /**
     * @return a list with all tests from database
     */
    List<Test> getAllTests();

    /**
     * @param predicate: lambda function that represents the condition
     * @return a list with all tests that respects a certain condition
     */
    List<Test> getFilteredTests(final Predicate<Test> predicate);

    /**
     * @return a list with all available answers
     */
    List<String> getAllAvailableAnswers();
}



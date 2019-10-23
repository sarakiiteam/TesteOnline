package service.interfaces;

import database.models.Question;
import database.models.TestResult;
import messages.Answer;
import utils.exceptions.ErrorMessageException;

import java.util.List;
import java.util.function.Predicate;

public interface IResultService {

    /**
     * Add test result into database
     * @param testName: the name of the test
     * @param guestName: the name of the person that answered to test
     * @param answers: the list of answers
     * @throws ErrorMessageException if something is wrong
     */
    void addTestResult(
            final String testName,
            final String guestName, final List<Answer> answers) throws ErrorMessageException;

    /**
     * @return a list with all available answers
     */
    List<String> getAllAvailableAnswers();

    /**
     *
     * @param predicate: a filter function
     * @return a list with all test results that respects a given condition
     */
    List<TestResult> getTestResultsByCondition(final Predicate<TestResult> predicate);
}

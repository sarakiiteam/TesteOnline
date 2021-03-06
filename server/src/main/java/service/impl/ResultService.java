package service.impl;

import cache.ICacheResolver;
import cache.annotations.*;
import cache.proxies.ProxyCacher;
import database.models.Question;
import database.models.Test;
import database.models.TestResult;
import database.models.enums.Difficulty;
import database.store.interfaces.ITestRepository;
import domain.TestOverview;
import messages.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import service.interfaces.IResultService;
import utils.exceptions.ErrorMessageException;
import utils.service.IQuestionComparer;

import java.util.*;
import java.util.function.Predicate;

@Component
@ComponentScan(
        basePackages = {"config"}
)
public class ResultService implements IResultService {

    private final ITestRepository testRepository;
    private final IQuestionComparer questionComparer;

    @Autowired
    public ResultService(
            final ITestRepository testRepository,
            final IQuestionComparer questionComparer) {

        this.testRepository = testRepository;
        this.questionComparer = questionComparer;
    }

    @Override
    public synchronized TestOverview addTestAndGetResult
            (final String testName,
             final String guestName, final List<Answer> answers) throws ErrorMessageException {

        // get the test
        final Optional<Test> testOptional = testRepository.getTestByName(testName);
        if (!testOptional.isPresent()) {
            throw new ErrorMessageException(
                    "Test not found", HttpStatus.NOT_FOUND);
        }

        // map the questions by id's so that it will be easy to check the answer's correctness
        final Map<Integer, Question> questionMap = mapQuestionsById(testName);
        // get the test
        final Test test = testOptional.get();

        //all get all the correct answers for the incorrect answers
        final List<Question> incorrect = new ArrayList<>();

        //calculate the number of points
        int totalScore = 0, correctAnswers = 0;
        for (final Answer answer : answers) {

            final Question question = questionMap.get(
                    answer.getQuestionId()
            );
            if (!questionComparer.equal(
                    answer.getQuestionAnswer(), question.getCorrectAnswer())) {
                incorrect.add(question);
                continue;
            }

            totalScore += question.getPoints() + getBonusByTestDifficulty(
                    test.getTestDifficulty()
            );
            ++correctAnswers;
        }

        testRepository.addTestResult(
                testName, guestName, totalScore, correctAnswers
        );

        return new TestOverview(correctAnswers, totalScore, incorrect);
    }


    @Override
    public synchronized List<TestResult> getTestResultsByCondition(
            final Predicate<TestResult> predicate) {

        return testRepository.getTestResultsByCondition(predicate);
    }


    private Map<Integer, Question> mapQuestionsById(final String testName) {

        final Map<Integer, Question> map = new TreeMap<>();

        final Optional<Test> testOptional = testRepository.getTestByName(testName);
        if (!testOptional.isPresent()) {
            return map;
        }

        testOptional.get().getQuestions().forEach(
                q -> map.put(q.getQuestionId(), q)
        );

        return map;
    }

    private int getBonusByTestDifficulty(final Difficulty difficulty) {

        switch (difficulty) {
            case EASY:
                return 1;
            case MEDIUM:
                return 2;
            case HARD:
                return 10;
        }

        return 0;
    }
}

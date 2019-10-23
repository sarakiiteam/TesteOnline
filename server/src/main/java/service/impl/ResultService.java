package service.impl;

import database.models.Question;
import database.models.Test;
import database.models.TestResult;
import database.models.enums.Difficulty;
import database.store.interfaces.ITestRepository;
import messages.Answer;
import service.interfaces.IResultService;
import utils.exceptions.ErrorMessageException;
import utils.service.IQuestionComparer;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ResultService implements IResultService {

    private final ITestRepository testRepository;
    private final IQuestionComparer questionComparer;

    public ResultService(
            final ITestRepository testRepository, final IQuestionComparer questionComparer) {
        this.testRepository = testRepository;
        this.questionComparer = questionComparer;
    }

    @Override
    public void addTestResult
            (final String testName,
             final String guestName, final List<Answer> answers) throws ErrorMessageException {

        // get the test
        final Optional<Test> testOptional = testRepository.getTestByName(testName);
        if (!testOptional.isPresent()) {
            return;
        }

        // map the questions by id's so that it will be easy to check the answer's correctness
        final Map<Integer, Question> questionMap = mapQuestionsById(testName);
        // get the test
        final Test test = testOptional.get();

        //calculate the number of points
        int totalScore = 0, correctAnswers = 0;
        for (final Answer answer : answers) {

            final Question question = questionMap.get(
                    answer.getQuestionId()
            );
            if (!questionComparer.equal(
                    answer.getQuestionAnswer(), question.getAnswer())) {
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
    }

    @Override
    public List<String> getAllAvailableAnswers() {

        final Set<String> answers = testRepository
                .getAll()
                .stream()
                .map(Test::getQuestions)
                .flatMap(Set::stream)
                .map(Question::getAnswer)
                .collect(Collectors.toSet());

        if(answers.isEmpty()){
            return new ArrayList<>();
        }

        return new ArrayList<>(answers);
    }

    @Override
    public List<TestResult> getTestResultsByCondition(
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

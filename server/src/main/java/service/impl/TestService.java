package service.impl;

import cache.ICacheResolver;
import cache.annotations.*;
import cache.proxies.ProxyCacher;
import database.models.Question;
import database.models.Test;
import database.models.enums.Difficulty;
import database.store.AbstractRepository;
import database.store.interfaces.ITestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import service.interfaces.ITestService;
import utils.exceptions.ErrorMessageException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@Cacheable
@ComponentScan(
        basePackages = {"config"}
)
public class TestService extends ProxyCacher<ITestService> implements ITestService {

    private final ITestRepository testRepository;

    @Autowired
    public TestService(
            final ITestRepository testRepository, final ICacheResolver<ITestService> cacheResolver) {
        super(cacheResolver);
        this.testRepository = testRepository;
    }

    @Override
    @VolatileCaches(
            value = {
                    @VolatileCache(cacheUpdate = "getAllTests")
            }
    )
    public synchronized void addTest(
            final String username,
            final String testName, final Difficulty testDifficulty) throws ErrorMessageException {

        testRepository.addTest(
                username, testName, testDifficulty
        );
    }

    @Override
    @VolatileCaches(
            value = {
                    @VolatileCache(cacheUpdate = "getAllAvailableAnswers")
            }
    )
    public synchronized void addQuestion(
            final String testName,
            final String question, final String answer, final int points) throws ErrorMessageException {

        final Optional<Test> testOptional = testRepository.getTestByName(testName);
        if (!testOptional.isPresent()) {
            throw new ErrorMessageException(
                    "Test not found", HttpStatus.NOT_FOUND
            );
        }

        final Test test = testOptional.get();
        test.addQuestion(
                new Question(
                        question, answer, points
                )
        );
        asAbstractRepository(testRepository).update(test);
    }

    @Override
    public List<Question> getQuestionsForTest(final String testName) {
        final Optional<Test> testOptional = testRepository.getTestByName(testName);
        return testOptional
                .map(
                        test -> new ArrayList<>(test.getQuestions()))
                .orElseGet(ArrayList::new);
    }

    @Override
    @Cached(cacheName = "getAllTests", cacheTime = 3600 * 24, timeUnit = TTL.SECONDS)
    public synchronized List<Test> getAllTests() {
        return testRepository.getAll();
    }

    @Override
    @Cached(cacheName = "getAllAvailableAnswers", cacheTime = 3600 * 24, timeUnit = TTL.SECONDS)
    public synchronized List<String> getAllAvailableAnswers() {

        final Set<String> answers = testRepository
                .getAll()
                .stream()
                .map(Test::getQuestions)
                .flatMap(Set::stream)
                .map(Question::getAnswer)
                .collect(Collectors.toSet());

        if (answers.isEmpty()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(answers);
    }

    @Override
    public synchronized List<Test> getFilteredTests(final Predicate<Test> predicate) {
        return this
                .asAbstractRepository(testRepository)
                .getByCondition(predicate);
    }

    @Override
    protected ITestService getProxySource() {
        return this;
    }

    @SuppressWarnings("unchecked")
    private AbstractRepository<Test> asAbstractRepository(
            final ITestRepository testRepository) {

        return (AbstractRepository<Test>) testRepository;
    }
}

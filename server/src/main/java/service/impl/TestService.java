package service.impl;

import database.models.Test;
import database.models.enums.Difficulty;
import database.store.AbstractRepository;
import database.store.interfaces.ITestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import service.interfaces.ITestService;
import utils.exceptions.ErrorMessageException;

import java.util.List;
import java.util.function.Predicate;

@Component
@ComponentScan(
        basePackages = {"config"}
)
public class TestService implements ITestService {

    private final ITestRepository testRepository;

    @Autowired
    public TestService(final ITestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public void addTest(
            final String username,
            final String testName, final Difficulty testDifficulty) throws ErrorMessageException {

        testRepository.addTest(
                username, testName, testDifficulty
        );
    }

    @Override
    public List<Test> getAllTests() {
        return testRepository.getAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Test> getFilteredTests(final Predicate<Test> predicate) {
        return (
                (AbstractRepository<Test>)testRepository
        ).getByCondition(predicate);
    }
}

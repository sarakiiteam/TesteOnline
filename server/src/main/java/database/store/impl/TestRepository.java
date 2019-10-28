package database.store.impl;

import database.models.Test;
import database.models.TestResult;
import database.models.User;
import database.models.enums.Difficulty;
import database.store.AbstractRepository;
import database.store.interfaces.ITestRepository;
import database.store.interfaces.IUserRepository;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import utils.exceptions.ErrorMessageException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestRepository extends AbstractRepository<Test> implements ITestRepository {

    private final IUserRepository userRepository;

    public TestRepository(final IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Test> getByCondition(final Predicate<Test> predicate) {
        return getAll()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Test> getTestByName(final String name) {

        try (Session session = persistenceUtils.getSessionFactory().openSession()) {

            //build query
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Test> userQuery = builder.createQuery(Test.class);
            Root<Test> table = userQuery.from(Test.class);

            //construct query
            userQuery.select(table).where(
                    builder.equal(
                            table.get("testName"),
                            name
                    )
            );

            //return the user
            return Optional.ofNullable(session.createQuery(userQuery).getSingleResult());
        } catch (Exception ignored) {
        }

        return Optional.empty();
    }

    @Override
    public void addTest(
            final String username,
            final String description,
            final String testName, final Difficulty testDifficulty) throws ErrorMessageException {

        final Optional<User> userOptional = userRepository.getByUsername(username);
        if (!userOptional.isPresent()) {
            throw new ErrorMessageException(
                    "Username not found", HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        final User user = userOptional.get();
        user.addTest(new Test(
                testName, testDifficulty, description
        ));

        userRepository.update(user);
    }

    @Override
    public void addTestResult(
            final String testName,
            final String guestName, final int points, final int corrects) throws ErrorMessageException {


        final Optional<Test> testOptional = getTestByName(testName);
        if (!testOptional.isPresent()) {
            throw new ErrorMessageException(
                    "Test not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        final Test test = testOptional.get();
        test.addTestResult(new TestResult(
                guestName, points, corrects
        ));

        update(test);
    }

    @Override
    public List<TestResult> getTestResultsByCondition(final Predicate<TestResult> predicate) {

        try (Session session = persistenceUtils.getSessionFactory().openSession()) {

            //create query
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TestResult> usersQuery = builder.createQuery(TestResult.class);
            Root<TestResult> table = usersQuery.from(TestResult.class);

            //build the query
            usersQuery.select(table);

            //return the results filtered by predicate
            return session
                    .createQuery(usersQuery)
                    .getResultList()
                    .stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Test> getAll() {

        try (Session session = persistenceUtils.getSessionFactory().openSession()) {

            //create query
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Test> usersQuery = builder.createQuery(Test.class);
            Root<Test> table = usersQuery.from(Test.class);

            //build the query
            usersQuery.select(table);

            //return the results
            return session.createQuery(usersQuery).getResultList();
        }
    }
}

package database.store.impl;

import database.models.User;
import database.store.AbstractRepository;
import database.store.interfaces.IUserRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import utils.exceptions.ErrorMessageException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserRepository extends AbstractRepository<User> implements IUserRepository {

    @Override
    public List<User> getByCondition(final Predicate<User> predicate) {

        return getAll()
                .stream()
                .filter(predicate)
                .collect(
                        Collectors.toList()
                );
    }

    @Override
    public Optional<User> getByUsername(final String username) {

        try (Session session = persistenceUtils.getSessionFactory().openSession()) {

            //build query
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> userQuery = builder.createQuery(User.class);
            Root<User> table = userQuery.from(User.class);

            //construct query
            userQuery.select(table).where(
                    builder.equal(
                            table.get("username"),
                            username
                    )
            );

            //return the user
            return Optional.ofNullable(session.createQuery(userQuery).getSingleResult());
        } catch (Exception ignored) {
        }

        return Optional.empty();
    }

    @Override
    public void register(final String username, final String password) throws ErrorMessageException {

        try (Session session = persistenceUtils.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            try {
                session.save(new User(password, username));
                transaction.commit();
            } catch (Exception ex) {
                transaction.rollback();
                throw new ErrorMessageException(
                        ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }

    @Override
    public boolean canLogin(final String username, final String password) {

        try (Session session = persistenceUtils.getSessionFactory().openSession()) {

            //create criteria for user query
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> userQuery = builder.createQuery(User.class);
            Root<User> table = userQuery.from(User.class);

            //build the query
            userQuery.select(table).where(
                    builder.and(
                            builder.equal(
                                    table.get("username"),
                                    username
                            ),
                            builder.equal(
                                    table.get("password"),
                                    password
                            )
                    )
            );

            //check if the list has at least one element
            return !session.createQuery(userQuery).getResultList().isEmpty();
        }
    }

    @Override
    public void changePassword(final String username, final String newPassword) throws ErrorMessageException {

        final Optional<User> userOptional = getByUsername(username);
        if (!userOptional.isPresent()) {
            throw new ErrorMessageException("User not found", HttpStatus.NOT_FOUND);
        }

        final User user = userOptional.get();
        user.setPassword(newPassword);
        update(user);
    }

    @Override
    public List<User> getAll() {

        try (Session session = persistenceUtils.getSessionFactory().openSession()) {

            //create query
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> usersQuery = builder.createQuery(User.class);
            Root<User> table = usersQuery.from(User.class);

            //build the query
            usersQuery.select(table);

            //return the results
            return session.createQuery(usersQuery).getResultList();
        }
    }
}

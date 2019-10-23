package database.store;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import utils.database.HibernateUtils;
import utils.exceptions.ErrorMessageException;

import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractRepository<T> implements IPersistence<T> {

    protected HibernateUtils persistenceUtils;

    protected AbstractRepository() {
        persistenceUtils = HibernateUtils.getInstance();
    }

    /**
     * Method used when update is needed
     *
     * @param object: the object that will be updated
     * @throws ErrorMessageException: if something went wrong in saving the object
     */
    @Override
    public void update(final T object) throws ErrorMessageException {
        try (Session session = persistenceUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.saveOrUpdate(object);
                transaction.commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                transaction.rollback();
                throw new ErrorMessageException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * @param object: the object that will be deleted
     * @throws ErrorMessageException: if something went wrong in saving the object
     */
    @Override
    public void delete(final T object) throws ErrorMessageException {
        try (Session session = persistenceUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.delete(object);
                transaction.commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                transaction.rollback();
                throw new ErrorMessageException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * @param predicate: a function used for filtering
     * @return a filtered list by predicate @param predicate
     */
    public abstract List<T> getByCondition(Predicate<T> predicate);
}
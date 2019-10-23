package database.store;

import utils.exceptions.ErrorMessageException;

import java.util.List;

public interface IPersistence<T> {

    /**
     * Method for updating fields of an object
     * @param object: the object that will be updated
     * @throws ErrorMessageException if something is wrong
     */
    void update(final T object) throws ErrorMessageException;

    /**
     * Delete the object from database
     * @param object: the object that will be deleted
     * @throws ErrorMessageException if something is wrong
     */
    void delete(final T object) throws ErrorMessageException;

    /**
     * @return a list with all elements
     */
    List<T> getAll();

}

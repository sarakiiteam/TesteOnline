package database.store.interfaces;

import database.models.User;
import database.store.IPersistence;
import utils.exceptions.ErrorMessageException;

import java.util.Optional;

public interface IUserRepository  extends IPersistence<User> {

    /**
     * Returns a user by its username
     * @param username: the user's username
     * @return Optional.of(user) if in database exist such a user or Optional.empty() otherwise
     */
    Optional<User> getByUsername(final String username);

    /**
     * Adds a new user into database
     * @param username: the user's username
     * @param password: the user's password
     * @throws ErrorMessageException if in database exist such an user
     */
    void register(final String username, final String password) throws ErrorMessageException;

    /**
     * @param username: the user's username
     * @param password: the user's password
     * @return true if user has an account or false otherwise
     */
    boolean canLogin(final String username, final String password);

    /**
     * Update the user's password
     * @param username: user's username
     * @param newPassword: user's password
     * @throws ErrorMessageException: if in data base does not exist an user with given username
     */
    void changePassword(
            final String username, final String newPassword) throws ErrorMessageException;
}

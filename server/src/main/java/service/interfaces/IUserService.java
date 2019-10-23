package service.interfaces;

import utils.exceptions.ErrorMessageException;

public interface IUserService {
    /**
     * Create a new account for user
     * @param username: the user's username
     * @param password: the user's password
     * @throws ErrorMessageException if something is wrong
     */
    void registerAccount(
            final String username, final String password) throws ErrorMessageException;

    /**
     * Changes the password for a specific user
     * @param username: the user's username
     * @param password: the user's new password
     * @throws ErrorMessageException if something went wrong
     */
    void changePassword(
            final String username, final String password) throws ErrorMessageException;

}

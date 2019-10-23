package service.interfaces;

public interface ILoginService {
    /**
     * Used to check if a user has an account
     * @param username: the user's username
     * @param password: the users's password
     * @return true if the user can login or false otherwise
     */
    boolean hasAccount(
            final String username, final String password);

}

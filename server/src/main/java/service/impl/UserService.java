package service.impl;

import database.store.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import service.interfaces.IUserService;
import utils.exceptions.ErrorMessageException;

import java.util.Base64;

@Component
@ComponentScan(
        basePackages = {"config"}
)
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(final IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void registerAccount(
            final String username, final String password) throws ErrorMessageException {

        //encode the password
        final String encodedPassword = Base64.getEncoder().encodeToString(
                password.getBytes()
        );

        //register the account
        userRepository.register(
                username, encodedPassword
        );
    }

    @Override
    public void changePassword(
            final String username, final String password) throws ErrorMessageException {

        //encode the password
        final String encodedPassword = Base64.getEncoder().encodeToString(
                password.getBytes()
        );

        //change the old password with the new one
        userRepository.changePassword(
                username, encodedPassword
        );
    }
}

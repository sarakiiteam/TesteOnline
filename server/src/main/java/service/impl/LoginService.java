package service.impl;

import database.store.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import service.interfaces.ILoginService;

import java.util.Base64;


@Component
@ComponentScan(
        basePackages = {"config"}
)
public class LoginService implements ILoginService {

    private final IUserRepository userRepository;

    @Autowired
    public LoginService(final IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public boolean hasAccount(final String username, final String password) {

        //decode the password for comparing the results
        final String encodedPassword = Base64.getEncoder().encodeToString(
                password.getBytes()
        );

        return userRepository.canLogin(
                username, password
        );
    }

}

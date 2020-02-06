package service.impl;

import database.store.interfaces.IUserRepository;
import mocks.repository.MockUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;
import service.interfaces.ILoginService;
import service.interfaces.IUserService;
import utils.exceptions.ErrorMessageException;

import static org.junit.Assert.*;

public class UserServiceTest {

    private  IUserService userService;
    private  ILoginService loginService;

    @Before
    public void setUp() throws Exception {

        final IUserRepository userRepository = new MockUserRepository(){{
            register("eduard", "onu");
            register("ionut", "mihoci");
            register("sorana", "ciobanita");
            register("daniel", "moldovan");
        }};

        userService = new UserService(userRepository);
        loginService = new LoginService(userRepository);
    }

    @Test(expected = ErrorMessageException.class)
    public void registerAlreadyHaveAccount() throws Exception {
        userService.registerAccount("eduard","uno");
    }

    @Test
    public void registerTest() throws Exception {
        userService.registerAccount("a", "b");
        assert loginService.hasAccount("a", "b");
    }

    @Test
    public void changePassword() throws Exception {
        userService.changePassword("eduard","new");
        assert !loginService.hasAccount("eduard", "onu");
    }
}
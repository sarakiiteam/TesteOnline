package server;
import database.store.impl.TestRepository;
import database.store.impl.UserRepository;
import database.store.interfaces.ITestRepository;
import database.store.interfaces.IUserRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.impl.LoginService;
import service.impl.UserService;

@SpringBootApplication
public class Main {

    public static void main(String... args) throws Exception {
        IUserRepository userRepository = new UserRepository();

        LoginService loginService = new LoginService(userRepository);

        System.out.println(loginService.hasAccount("eduard", "onu"));
    }
}

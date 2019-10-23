package server;
import database.models.enums.Difficulty;
import database.store.impl.TestRepository;
import database.store.impl.UserRepository;
import database.store.interfaces.ITestRepository;
import database.store.interfaces.IUserRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.impl.LoginService;
import service.impl.TestService;
import service.impl.UserService;

@SpringBootApplication
public class Main {

    public static void main(String... args) throws Exception {
        IUserRepository userRepository = new UserRepository();
        ITestRepository testRepository = new TestRepository(userRepository);

        TestService testService = new TestService(testRepository);

    }
}

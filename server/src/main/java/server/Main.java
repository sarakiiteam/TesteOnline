package server;
import database.models.enums.Difficulty;
import database.store.impl.TestRepository;
import database.store.impl.UserRepository;
import database.store.interfaces.ITestRepository;
import database.store.interfaces.IUserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import service.impl.LoginService;
import service.impl.TestService;
import service.impl.UserService;

@SpringBootApplication
@ComponentScan(
        basePackages = "controllers"
)
public class Main {

    public static void main(String... args) throws Exception {
        SpringApplication.run(Main.class);
    }
}

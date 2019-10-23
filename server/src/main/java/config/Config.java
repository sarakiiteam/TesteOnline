package config;

import database.store.impl.TestRepository;
import database.store.impl.UserRepository;
import database.store.interfaces.ITestRepository;
import database.store.interfaces.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import utils.service.IQuestionComparer;
import utils.service.LowerCaseQuestionComparer;

@Configuration
public class Config {

    @Bean
    public IUserRepository userRepository() {
        return new UserRepository();
    }

    @Bean
    public ITestRepository testRepository() {
        return new TestRepository(
                userRepository()
        );
    }

    @Bean
    public IQuestionComparer questionComparer(){
        return new LowerCaseQuestionComparer();
    }

}

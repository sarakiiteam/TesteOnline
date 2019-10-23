package config;

import database.store.impl.TestRepository;
import database.store.impl.UserRepository;
import database.store.interfaces.ITestRepository;
import database.store.interfaces.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import utils.controllers.checkers.IModelChecker;
import utils.controllers.checkers.RestrictionModelChecker;
import utils.controllers.restrictions.DifficultyEnumRestriction;
import utils.controllers.restrictions.IRestriction;
import utils.controllers.restrictions.NumberRestriction;
import utils.controllers.restrictions.StringRestriction;
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
        return new TestRepository(userRepository());
    }

    @Bean
    public IQuestionComparer questionComparer() {
        return new LowerCaseQuestionComparer();
    }


    @Bean
    public IModelChecker modelChecker() {

        final RestrictionModelChecker restrictionModelChecker = new RestrictionModelChecker();

        //config
        restrictionModelChecker.addRestriction(stringRestriction());
        restrictionModelChecker.addRestriction(nullDifficultyRestriction());
        restrictionModelChecker.addRestriction(integerRestriction());


        return restrictionModelChecker;
    }

    @Bean
    IRestriction<Object> stringRestriction() {
        return new StringRestriction();
    }

    @Bean
    IRestriction<Object> nullDifficultyRestriction() {
        return new DifficultyEnumRestriction();
    }

    @Bean
    IRestriction<Object> integerRestriction() {
        return new NumberRestriction();
    }
}

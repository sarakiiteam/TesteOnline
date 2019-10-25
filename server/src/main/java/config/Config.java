package config;

import cache.CacheResolver;
import cache.ICacheResolver;
import database.store.impl.TestRepository;
import database.store.impl.UserRepository;
import database.store.interfaces.ITestRepository;
import database.store.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.interfaces.IResultService;
import service.interfaces.ITestService;
import utils.controllers.IModelChecker;
import utils.controllers.checkers.RestrictionModelChecker;
import utils.controllers.restrictions.DifficultyEnumRestriction;
import utils.controllers.IRestriction;
import utils.controllers.restrictions.NumberRestriction;
import utils.controllers.restrictions.StringRestriction;
import utils.service.IQuestionComparer;
import utils.service.LowerCaseQuestionComparer;

import java.util.Date;

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
    public ICacheResolver<IResultService> cacheResolverResult(){
        return  new CacheResolver<>((k, v)->{
            System.out.println();
            System.out.println("===================================");
            System.out.println("Cached expired....\nRefreshed cache");
        });
    }

    @Bean
    public ICacheResolver<ITestService> cacheResolverTest(){
        return  new CacheResolver<>((k, v)->{
            System.out.println();
            System.out.println("===================================");
            System.out.println("Cached expired....\nRefreshed cache");
        });
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

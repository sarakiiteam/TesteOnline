package service.cached;

import cache.ICacheResolver;
import cache.annotations.Cacheable;
import cache.annotations.Cached;
import cache.proxies.ProxyCacheBuilder;
import database.models.TestResult;
import messages.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import service.interfaces.IResultService;
import utils.exceptions.ErrorMessageException;

import java.util.List;
import java.util.function.Predicate;

@Component
@Cacheable
@Qualifier("resultServiceCached")
@ComponentScan(
        basePackages = {"services", "config"}
)
public class TestResultServiceCached extends ProxyCacheBuilder<IResultService> implements IResultService {

    private IResultService resultService;

    @Autowired
    public TestResultServiceCached(
            final ICacheResolver<IResultService> cacheResolver,
            @Qualifier("resultServiceNonCached") final IResultService resultService) {
        super(cacheResolver);
        setCacheable(this);
        this.resultService = resultService;
    }

    @Override
    public void addTestResult(
            final String testName, final String guestName, final List<Answer> answers) throws ErrorMessageException {

        resultService.addTestResult(
                testName, guestName, answers
        );
    }

    @Override
    @Cached(cacheTime = 1000)
    public List<String> getAllAvailableAnswers() {
        return resultService.getAllAvailableAnswers();
    }

    @Override
    public List<TestResult> getTestResultsByCondition(final Predicate<TestResult> predicate) {
        return resultService.getTestResultsByCondition(predicate);
    }
}

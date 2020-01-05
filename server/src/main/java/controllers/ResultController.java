package controllers;

import cache.proxies.ProxyCacher;
import database.models.TestResult;
import domain.TestOverview;
import messages.Message;
import messages.Requests.TestResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.interfaces.IResultService;
import service.interfaces.ITestService;
import utils.controllers.IModelChecker;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/results")
@ComponentScan(
        basePackages = {"service", "config"}
)
public class ResultController {

    private final IResultService resultService;
    private final ITestService testService;
    private final IModelChecker modelChecker;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ResultController(
            final IModelChecker modelChecker, final IResultService resultService, final ITestService testService) {

        this.modelChecker = modelChecker;
        this.resultService = ProxyCacher.getCacheable(resultService);
        this.testService = ProxyCacher.getCacheable(testService);
    }

    /**
     * This method is responsible for getting all the results for a specific test
     * method type: GET
     * base call: http://localhost:8080/api/results/da-test
     *
     * @return a message like this
     * {
     * "results": [
     * {
     * "guestName": "eduard",
     * "guestPoints": 1752,
     * "correctAnswers": 2
     * },
     * {
     * "guestName": "mariano",
     * "guestPoints": 1755,
     * "correctAnswers": 5
     * }
     * ]
     * }
     */
    @GetMapping(value = "/{testName}")
    public ResponseEntity<?> getResultsTo(@PathVariable String testName) {

        //get all test results for a specific test
        final List<TestResult> testResults = resultService
                .getTestResultsByCondition(
                        x -> x.getTest().getTestName().equals(testName));

        return new ResponseEntity<>(
                new TreeMap<String, List<TestResult>>() {{
                    put("results",
                            testResults
                                    .stream()
                                    .peek(test -> test.setTest(null))
                                    .collect(Collectors.toList()));
                }},
                HttpStatus.OK);
    }


    /**
     * This method is responsible for adding a test result into database
     * method type: POST
     * base call: http://localhost:8080/api/results/test-results/add
     *
     * @param message: the input data that is necessary for the call (it looks like this)
     *                 {
     *                 "testName": "test1",
     *                 "guestName":"edi",
     *                 "answers" : [
     *                 {
     *                 "questionId" : 1,
     *                 "questionAnswer":"mere"
     *                 },
     *                 {
     *                 "questionId" : 2,
     *                 "questionAnswer":"mere"
     *                 }
     *                 ]
     *                 }
     * @return a message like this
     * {
     * "code": "CREATED",
     * "msg": "Result successfully added"
     * }
     */
    @PostMapping(value = "/test-results/add")
    public ResponseEntity<?> addTestResult(@RequestBody TestResultMessage message) {

        final Map.Entry<Boolean, String> validationResult = modelChecker.isModelValid(message);
        if (!validationResult.getKey()) {
            return new ResponseEntity<>(
                    new Message(
                            HttpStatus.BAD_REQUEST,
                            validationResult.getValue() + (message.getAnswers().size() == 0 ? "Field 'answers' missing" : "")
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }

        final TestOverview overview;
        try {
            overview = resultService.addTestAndGetResult(
                    message.getTestName(),
                    message.getGuestName(), message.getAnswers()
            );
        } catch (final Exception ex) {
            return new ResponseEntity<>(
                    new Message(
                            HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return new ResponseEntity<>(
                overview, HttpStatus.CREATED
        );
    }


    /**
     * This method is responsible for getting results for a specific guest name
     * method type: GET
     * base call: http://localhost:8080/api/results/test-results/guestName/all
     *
     * @param guestName: the guest name
     * @return a message like this
     * {
     * "results": [
     * {
     * "guestName": "edi",
     * "guestPoints": 102,
     * "correctAnswers": 1,
     * "test": {
     * "testName": "test1",
     * "testDifficulty": "MEDIUM",
     * "proposedBy": {
     * "usern": "eduard"
     * }
     * }
     * }
     * ]
     * }
     */
    @GetMapping(value = "/test-results/{guestName}/all")
    public ResponseEntity<?> getResultsForUser(@PathVariable String guestName) {

        final Map<String, List<TestResult>> results = new TreeMap<>();
        results.put(
                "results",
                resultService.getTestResultsByCondition(
                        testResult -> testResult.getGuestName().equals(guestName)
                )
        );

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * This method is responsible for getting results for all users
     * method type: GET
     * base call: http://localhost:8080/api/results/test-results/all
     *
     * @return a message like this
     * {
     * "results": [
     * {
     * "guestName": "edi",
     * "guestPoints": 102,
     * "correctAnswers": 1,
     * "test": {
     * "testName": "test1",
     * "testDifficulty": "MEDIUM",
     * "proposedBy": {
     * "usern": "eduard"
     * }
     * }
     * }
     * ]
     * }
     */
    @GetMapping(value = "/test-results/all")
    public ResponseEntity<?> getAllResults() {
        final Map<String, List<TestResult>> results = new TreeMap<>();
        results.put(
                "results",
                resultService.getTestResultsByCondition(x -> true)
        );

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}

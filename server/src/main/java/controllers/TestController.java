package controllers;

import cache.proxies.ProxyCacher;
import database.models.Test;
import messages.Message;
import messages.Requests.QuestionMessage;
import messages.Requests.TestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.interfaces.ITestService;
import utils.controllers.IModelChecker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tests")
@ComponentScan(
        basePackages = {"service", "config"}
)
public class TestController {

    private final ITestService testService;
    private final IModelChecker modelChecker;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public TestController(
            final ITestService testService, final IModelChecker modelChecker) {

        this.testService = ProxyCacher.getCacheable(testService);
        this.modelChecker = modelChecker;
    }

    /**
     * This method is responsible for adding a test into database
     * method type: POST
     * base call: http://localhost:8080/api/tests/add
     *
     * @param message: the input data that is necessary for the call (it looks like this)
     *                 {
     *                 "username": "username",
     *                 "testName":"test",
     *                 "difficulty": "MEDIUM" or "EASY" or "HARD"
     *                 }
     * @return a message like this
     * {
     * "code": "status code",
     * "msg": "message"
     * }
     */
    @PostMapping(value = "/add")
    public ResponseEntity<?> addTest(@RequestBody TestMessage message) {

        //check the model validity
        final Map.Entry<Boolean, String> validationResult = modelChecker.isModelValid(message);
        if (!validationResult.getKey()) {
            return new ResponseEntity<>(
                    new Message(
                            HttpStatus.BAD_REQUEST, validationResult.getValue()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }

        // try to add the account into database
        try {

            testService.addTest(
                    message.getUsername(),
                    message.getDescription(),
                    message.getTestName(), message.getDifficulty()
            );

        } catch (Exception ex) {
            return new ResponseEntity<>(
                    new Message(
                            HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        // return the response
        return new ResponseEntity<>(
                new Message(
                        HttpStatus.CREATED, "Test successfully added"
                ),
                HttpStatus.CREATED
        );
    }


    /**
     * This method is responsible for getting all tests from database
     * method type: GET
     * base call: http://localhost:8080/api/tests/users
     *
     * @return a message like this
     * {
     * "tests": [
     * {
     * "testName": "test1",
     * "testDifficulty": "MEDIUM",
     * "proposedBy": {
     * "usern": "eduard"
     * }
     * },
     * {
     * "testName": "test1",
     * "testDifficulty": "MEDIUM",
     * "proposedBy": {
     * "usern": "eduard"
     * }
     * },
     * ]
     * }
     */
    @GetMapping(value = "/users")
    public ResponseEntity<?> getAllTests() {

        final Map<String, List<Test>> tests = new TreeMap<>();
        tests.put("tests", testService.getAllTests());

        return new ResponseEntity<>(tests, HttpStatus.OK);
    }

    /**
     * This method is responsible for getting all tests posted by a specific user
     * method type: GET
     * base call: http://localhost:8080/api/tests/users/username
     *
     * @param username: the user's username
     * @return a object like this
     * {
     * "tests": [
     * {
     * "testName": "test",
     * "testDifficulty": "MEDIUM",
     * "proposedBy": {
     * "usern": "usern"
     * }
     * }
     * ]
     * }
     */
    @GetMapping(value = "/users/{username}")
    public ResponseEntity<?> getAllTestsPostedByUser(@PathVariable String username) {

        // filter the result
        final Map<String, List<Test>> userTests = new HashMap<>();
        final List<Test> listx = testService.getFilteredTests(
                test -> test
                        .getUser()
                        .getUsername()
                        .equals(
                                username.toLowerCase()
                        )
        ).stream().peek(x -> x.setUser(null)).collect(Collectors.toList());

        userTests.put(
                "tests",
                testService.getFilteredTests(
                        test -> test
                                .getUser()
                                .getUsername()
                                .equals(
                                        username.toLowerCase()
                                )
                ).stream().peek(x -> x.setUser(null)).collect(Collectors.toList())
        );

        return new ResponseEntity<>(userTests, HttpStatus.OK);
    }

    /**
     * This method is responsible for getting all questions for a specific test
     * method type: GET
     * base call: http://localhost:8080/api/tests/testName/questions
     *
     * @param testName: the name of the test
     * @return a message like this
     * {
     * "questions": [
     * {
     * "question": "ce are ana?",
     * "points": 100
     * },
     * {
     * "question": "ce avea ana?",
     * "points": 140
     * }
     * ]
     * }
     */
    @GetMapping(value = "/{testName}/questions")
    public ResponseEntity<?> getTestQuestions(@PathVariable String testName) {
        return new ResponseEntity<>(
                new TreeMap<String, Object>() {{
                    put("questions",  testService.getQuestionsForTest(testName));
                }},
                HttpStatus.OK
        );
    }


    /**
     * This method is responsible for adding a test into database
     * method type: POST
     * base call: http://localhost:8080/api/tests/testName/questions/add
     *
     * @param testName: the name of test we want to add the question
     * @param message:  the input data that is necessary for the call (it looks like this)
     *                  {
     *                  "username": "username",
     *                  "question":"cati ani ai daca nu te-ai nascut?",
     *                  "answer": "0",
     *                  "points": 100
     *                  }
     * @return a message like this
     * {
     * "code": "CREATED",
     * "msg": "Question added successfully"
     * }
     */
    @PostMapping(value = "/{testName}/questions/add")
    public ResponseEntity<?> addQuestionToTest(@PathVariable String testName,
                                               @RequestBody final QuestionMessage message) {

        // validate
        final Map.Entry<Boolean, String> validationResult = modelChecker.isModelValid(message);
        if (!validationResult.getKey()) {
            return new ResponseEntity<>(
                    new Message(
                            HttpStatus.BAD_REQUEST, validationResult.getValue()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }

        // try to add
        try {

            testService.addQuestion(
                    testName,
                    message.getQuestion(),
                    message.getCorrectAnswer(),
                    message.getFirstWrongAnswer(),
                    message.getSecondWrongAnswer(), message.getPoints()
            );
        } catch (final Exception ex) {
            return new ResponseEntity<>(
                    new Message(
                            HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        //return the response
        return new ResponseEntity<>(
                new Message(
                        HttpStatus.CREATED, "Question added successfully"
                ),
                HttpStatus.CREATED
        );
    }
}

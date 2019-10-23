package controllers;

import database.models.Question;
import database.models.Test;
import javafx.util.Pair;
import messages.Message;
import messages.Requests.TestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.interfaces.ITestService;
import utils.controllers.checkers.IModelChecker;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        this.testService = testService;
        this.modelChecker = modelChecker;
    }

    /**
     * This method is responsible for adding a test into database
     * method type: POST
     * base call: http://localhost:8080/api/tests/add
     * @param message: the input data that is necessary for the call (it looks like this)
            {
                "username": "username",
                "testName":"test",
                "difficulty": "MEDIUM" or "EASY" or "HARD"
            }
     * @return a message like this
            {
              "code": "status code",
              "msg": "message"
            }
     */
    @PostMapping(value = "/add")
    public ResponseEntity<?> registerUser(@RequestBody TestMessage message) {

        //check the model validity
        final Pair<Boolean, String> validationResult = modelChecker.isModelValid(message);
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
     * base call: http://localhost:8080/api/tests/all
     * @return a message like this
        {
            "tests": [
                {
                    "testName": "test1",
                    "testDifficulty": "MEDIUM",
                    "proposedBy": {
                        "usern": "eduard"
                    }
                },
                {
                    "testName": "test1",
                    "testDifficulty": "MEDIUM",
                    "proposedBy": {
                        "usern": "eduard"
                    }
                },
            ]
        }
     */
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllTests() {

        final Map<String, List<Test>> tests = new TreeMap<>();
        tests.put("tests", testService.getAllTests());

        return new ResponseEntity<>(tests, HttpStatus.OK);
    }

    /**
     * This method is responsible for getting all questions for a specific test
     * method type: GET
     * base call: http://localhost:8080/api/tests/testName/questions
     * @param testName: the name of the test
     * @return a message like this
     * {
     *     "questions": [
     *         {
     *             "question": "ce are ana?",
     *             "points": 100
     *         },
     *         {
     *             "question": "ce avea ana?",
     *             "points": 140
     *         }
     *     ]
     * }
     */
    @GetMapping(value = "/{testName}/questions")
    public ResponseEntity<?> getTestQuestions(@PathVariable String testName) {

        final Map<String, List<Question>> questions = new TreeMap<>();
        questions.put("questions", testService.getQuestionsForTest(testName));

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }


}

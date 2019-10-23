package controllers;

import database.models.TestResult;
import javafx.util.Pair;
import messages.Message;
import messages.Requests.TestResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.interfaces.IResultService;
import utils.controllers.IModelChecker;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/results")
@ComponentScan(
        basePackages = {"service", "config"}
)
public class ResultController {

    private final IResultService resultService;
    private final IModelChecker modelChecker;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ResultController(
            final IResultService resultService, final IModelChecker modelChecker) {

        this.resultService = resultService;
        this.modelChecker = modelChecker;
    }

    /**
     * This method is responsible for getting all answers existent in database
     * method type: GET
     * base call: http://localhost:8080/api/results/available
     * @return a message like this
     * {
     *     "answers": [
     *         "0",
     *         "mere",
     *         "pere"
     *     ]
     * }
     */
    @GetMapping(value = "/available")
    public ResponseEntity<?> getAllAvailableAnswers() {

        final Map<String, List<String>> answers = new TreeMap<>();
        answers.put("answers", resultService.getAllAvailableAnswers());

        return new ResponseEntity<>(answers, HttpStatus.OK);
    }


    /**
     * This method is responsible for adding a test result into database
     * method type: POST
     * base call: http://localhost:8080/api/results/test-results/add
     * @param message: the input data that is necessary for the call (it looks like this)
            {
                "testName": "test1",
                "guestName":"edi",
                "answers" : [
                        {
                            "questionId" : 1,
                            "questionAnswer":"mere"
                        },
                        {
                            "questionId" : 2,
                            "questionAnswer":"mere"
                        }
                ]
            }
     * @return a message like this
            {
            "code": "CREATED",
            "msg": "Result successfully added"
            }
     */
    @PostMapping(value = "/test-results/add")
    public ResponseEntity<?> addTestResult(@RequestBody TestResultMessage message) {

        final Pair<Boolean, String> validationResult = modelChecker.isModelValid(message);
        if (!validationResult.getKey()) {
            return new ResponseEntity<>(
                    new Message(
                            HttpStatus.BAD_REQUEST,
                            validationResult.getValue() + (message.getAnswers().size() == 0 ? "Field 'answers' missing" : "")
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }

        try {
            resultService.addTestResult(
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
                new Message(
                        HttpStatus.CREATED, "Result successfully added"
                ),
                HttpStatus.CREATED
        );
    }



    /**
     * This method is responsible for getting results for a specific guest name
     * method type: GET
     * base call: http://localhost:8080/api/results/test-results/guestName/all
     * @param guestName: the guest name
     * @return a message like this
     * {
     *     "results": [
     *         {
     *             "guestName": "edi",
     *             "guestPoints": 102,
     *             "correctAnswers": 1,
     *             "test": {
     *                 "testName": "test1",
     *                 "testDifficulty": "MEDIUM",
     *                 "proposedBy": {
     *                     "usern": "eduard"
     *                 }
     *             }
     *         }
     *     ]
     * }
     */
    @GetMapping(value = "/test-results/{guestName}/all")
    public ResponseEntity<?> getResultsForUser(@PathVariable String guestName){

        final Map<String, List<TestResult>> results = new TreeMap<>();
        results.put(
                "results",
                resultService.getTestResultsByCondition(
                        testResult -> testResult.getGuestName().equals(guestName)
                )
        );

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}

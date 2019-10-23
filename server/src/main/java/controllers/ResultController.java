package controllers;

import messages.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.interfaces.IResultService;
import utils.controllers.checkers.IModelChecker;

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

    
}

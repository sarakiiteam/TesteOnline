package controllers;

import javafx.util.Pair;
import messages.Message;
import messages.Requests.AuthenticationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.interfaces.ILoginService;
import utils.controllers.checkers.IModelChecker;

@RestController
@RequestMapping("/api/user")
@ComponentScan(basePackages = {
        "service", "config"
})
public class LoginController {

    private final ILoginService loginService;
    private final IModelChecker modelChecker;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public LoginController(final ILoginService loginService, final IModelChecker modelChecker) {
        this.loginService = loginService;
        this.modelChecker = modelChecker;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticateStudent(@RequestBody final AuthenticationMessage message) {

        final Pair<Boolean, String> validationResult = modelChecker.isModelValid(message);

        if (!validationResult.getKey()) {
            return new ResponseEntity<>(
                    new Message(
                            HttpStatus.BAD_REQUEST, validationResult.getValue()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }

        if(!loginService.hasAccount(message.getUsername(), message.getPassword())){
            return new ResponseEntity<>(
                    new Message(
                            HttpStatus.NOT_FOUND,
                            "Incorrect username or password"),
                    HttpStatus.NOT_FOUND
            );
        }



        return new ResponseEntity<>(
                new Message(
                        HttpStatus.OK, "User will be logged in"),
                HttpStatus.OK);
    }

}

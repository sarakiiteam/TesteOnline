package controllers;

import messages.Message;
import messages.Requests.AuthenticationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.interfaces.ILoginService;
import utils.controllers.IModelChecker;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
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

    /**
     * This method is responsible for checking if a user has an account
     * method type: POST
     * base call: http://localhost:8080/api/user/login
     * @param message: the input data that is necessary for the call (looks like this)
         * {
         *     "username": "user1"
         *     "password": "password1"
         * }
     * @return a message like this
         * {
         *     "code": "status code",
         *     "msg": "message"
         * }
     */
    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticateStudent(@RequestBody final AuthenticationMessage message) {

        final Map.Entry<Boolean, String> validationResult = modelChecker.isModelValid(message);

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

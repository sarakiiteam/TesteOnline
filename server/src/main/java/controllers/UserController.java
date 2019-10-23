package controllers;

import database.store.interfaces.IUserRepository;
import javafx.util.Pair;
import messages.Message;
import messages.Requests.AuthenticationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.interfaces.IUserService;
import utils.controllers.checkers.IModelChecker;

@RestController
@RequestMapping("/api/user")
@ComponentScan(
        basePackages = {"config", "service"}
)
public class UserController {

    private final IUserService userService;
    private final IModelChecker modelChecker;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public UserController(
            final IUserService userService, final IModelChecker modelChecker) {
        this.userService = userService;
        this.modelChecker = modelChecker;
    }


    /**
     * This method is responsible for creating a user account
     * method type: POST
     * base call: http://localhost:8080/api/user/register
     * @param message: the input data that is necessary for the call (it looks like this)
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
    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthenticationMessage message) {

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

            userService.registerAccount(
                    message.getUsername(), message.getPassword()
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
                        HttpStatus.CREATED, "User successfully registered"
                ),
                HttpStatus.CREATED
        );
    }


    /**
     * This method is responsible for updating user's profile
     * method type: PATCH
     * base call: http://localhost:8080/api/user/profile/update
     * @param message: the input data that is necessary for the call (it looks like this)
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
    @PatchMapping(value = "/profile/update")
    public ResponseEntity<?> updateProfile(@RequestBody AuthenticationMessage message) {

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

        // try to update the password
        try {

            userService.changePassword(
                    message.getUsername(), message.getPassword()
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
                        HttpStatus.OK, "User's password successfully updated"
                ),
                HttpStatus.OK
        );
    }
}


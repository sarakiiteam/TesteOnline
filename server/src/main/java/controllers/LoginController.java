package controllers;

import messages.ErrorMessage;
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

@RestController
@RequestMapping("/api/user")
@ComponentScan(basePackages = {
        "service"
})
public class LoginController {

    private final ILoginService loginService;

    @Autowired
    public LoginController(final ILoginService loginService){
        this.loginService = loginService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticateStudent(@RequestBody final AuthenticationMessage message){

        if(message == null || message.getPassword() == null || message.getUsername() == null
                || message.getUsername().isEmpty()
                || message.getPassword().isEmpty()){
            return new ResponseEntity<>(
                    new ErrorMessage(
                            HttpStatus.BAD_REQUEST,
                            "Required filed missing"
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

}

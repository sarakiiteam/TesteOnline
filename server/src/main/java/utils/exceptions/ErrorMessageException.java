package utils.exceptions;

import messages.Message;
import org.springframework.http.HttpStatus;

public class ErrorMessageException extends Exception {

    public ErrorMessageException(final String message, final HttpStatus status){
        super(message);
        this.message = new Message(status, message);
    }

    public String getMessage(){
        return message.getMessage();
    }

    private Message message;
}

package messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

    @JsonProperty("code")
    private HttpStatus code;

    @JsonProperty("msg")
    private String message;

    public ErrorMessage(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorMessage() {
    }

    public HttpStatus getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
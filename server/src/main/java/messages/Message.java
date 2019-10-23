package messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class Message implements Serializable {

    @JsonProperty("code")
    private HttpStatus code;

    @JsonProperty("msg")
    private String message;

    public Message(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    public Message() {
    }

    public HttpStatus getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
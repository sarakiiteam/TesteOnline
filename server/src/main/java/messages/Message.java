package messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class Message implements Serializable {

    @JsonProperty("code")
    private HttpStatus code;

    @JsonProperty("msg")
    private String message;

    public Message(final HttpStatus code, final String message) {
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

    public void setCode(final HttpStatus code) {
        this.code = code;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
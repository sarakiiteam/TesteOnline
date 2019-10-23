package messages.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AuthenticationMessage  implements Serializable {

    @JsonProperty("usern")
    private String username;

    @JsonProperty("passwd")
    private String password;

    public AuthenticationMessage() {
    }

    public AuthenticationMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}

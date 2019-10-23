package messages.Requests;

import database.models.enums.Difficulty;

import java.io.Serializable;

public class TestMessage implements Serializable {

    private String username;
    private String testName;

    private Difficulty difficulty;

    public TestMessage(
            final String username,
            final String testName, final Difficulty difficulty) {
        this.username = username;
        this.testName = testName;
        this.difficulty = difficulty;
    }

    public TestMessage() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}

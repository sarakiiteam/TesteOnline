package messages.Requests;

import messages.Answer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestResultMessage implements Serializable {

    private String testName;
    private String guestName;

    private List<Answer> answers = new ArrayList<>();

    public TestResultMessage(
            final String testName, final String guestName, final List<Answer> answers) {

        this.testName = testName;
        this.guestName = guestName;
        this.answers = answers;
    }

    public TestResultMessage() {
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}

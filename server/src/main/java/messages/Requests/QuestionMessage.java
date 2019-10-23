package messages.Requests;

import java.io.Serializable;

public class QuestionMessage implements Serializable {

    private String question;
    private String answer;

    private int points = -1;


    public QuestionMessage(
            final String question, final String answer, final int points) {
        this.question = question;
        this.answer = answer;
        this.points = points;
    }

    public QuestionMessage() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

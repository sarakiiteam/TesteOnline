package messages.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class QuestionMessage implements Serializable {

    private String question;
    private int points = -1;

    @JsonProperty("answer")
    private String correctAnswer;

    @JsonProperty("wrong1")
    private String firstWrongAnswer;

    @JsonProperty("wrong2")
    private String secondWrongAnswer;

    public QuestionMessage() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getFirstWrongAnswer() {
        return firstWrongAnswer;
    }

    public void setFirstWrongAnswer(String firstWrongAnswer) {
        this.firstWrongAnswer = firstWrongAnswer;
    }

    public String getSecondWrongAnswer() {
        return secondWrongAnswer;
    }

    public void setSecondWrongAnswer(String secondWrongAnswer) {
        this.secondWrongAnswer = secondWrongAnswer;
    }
}

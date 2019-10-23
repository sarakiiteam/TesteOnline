package messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Answer implements Serializable {

    private String questionAnswer;

    private int questionId;

    public Answer() {
    }

    public Answer(String questionAnswer, int questionId) {
        this.questionAnswer = questionAnswer;
        this.questionId = questionId;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}

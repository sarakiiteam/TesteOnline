package domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import database.models.Question;

import java.io.Serializable;
import java.util.List;

public class TestOverview implements Serializable {

    private int correctAnswers;
    private double score;

    private List<Question> quizMistakes;

    public TestOverview() {
    }

    public TestOverview(final int correctAnswers,
                        final double score,
                        final List<Question> quizMistakes) {
        this.correctAnswers = correctAnswers;
        this.score = score;
        this.quizMistakes = quizMistakes;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }
    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public List<Question> getQuizMistakes() {
        return quizMistakes;
    }
    public void setQuizMistakes(List<Question> quizMistakes) {
        this.quizMistakes = quizMistakes;
    }

    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
}

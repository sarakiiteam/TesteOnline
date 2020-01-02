package database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Question {

    private final static int MAX_LENGTH = 256 * 256 * 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int questionId;

    @Column(length = MAX_LENGTH)
    private String question;

    @Column(length = MAX_LENGTH)
    private String correctAnswer;

    @Column(length = MAX_LENGTH)
    private String firstWrongAnswer;

    @Column(length = MAX_LENGTH)
    private String secondWrongAnswer;

    @Column
    private int points;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "testId")
    @JsonIgnore
    private Test test;

    public Question(final String question,
                    final String correctAnswer,
                    final String firstWrongAnswer,
                    final String secondWrongAnswer, final int points) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.firstWrongAnswer = firstWrongAnswer;
        this.secondWrongAnswer = secondWrongAnswer;
        this.points = points;
    }

    public Question() {
    }

    public int getQuestionId() {
        return questionId;
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

    public Test getTest() {
        return test;
    }

    public void setTest(final Test test) {
        this.test = test;
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

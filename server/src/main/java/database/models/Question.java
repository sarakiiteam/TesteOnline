package database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Question {

    private final static int MAX_LENGTH = 256 * 256 * 4;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int questionId;

    @Column(length = MAX_LENGTH)
    private String question;

    @Column(length = MAX_LENGTH)
    @JsonIgnore
    private String answer;

    @Column
    private int points;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "testId")
    @JsonIgnore
    private Test test;

    public Question(String question, String answer, int points) {
        this.question = question;
        this.answer = answer;
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

    public Test getTest() {
        return test;
    }

    public void setTest(final Test test) {
        this.test = test;
    }

}

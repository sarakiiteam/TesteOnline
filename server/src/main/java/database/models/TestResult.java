package database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class TestResult {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int testResultId;

    @Column
    private String guestName;

    @Column
    private int guestPoints;

    @Column
    private int correctAnswers;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "testId")
    private Test test;

    public TestResult(String guestName, int guestPoints, int correctAnswers) {
        this.guestName = guestName;
        this.guestPoints = guestPoints;
        this.correctAnswers = correctAnswers;
    }

    public TestResult() {
    }

    public int getTestResultId() {
        return testResultId;
    }

    public String getGuestName() {
        return guestName;
    }
    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getGuestPoints() {
        return guestPoints;
    }
    public void setGuestPoints(int guestPoints) {
        this.guestPoints = guestPoints;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }
    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}

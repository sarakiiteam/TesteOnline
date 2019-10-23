package database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import database.models.enums.Difficulty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "test")
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
                        "name"
                }
        )
)
public class Test {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int testId;

    @Column(name = "name")
    private String testName;

    @Column(name = "difficulty")
    private Difficulty testDifficulty;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId")
    @JsonProperty("proposedBy")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "test")
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Set<Question> questions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "test")
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Set<TestResult> results = new HashSet<>();

    public Test() {
    }

    public Test(String testName, Difficulty testDifficulty) {
        this.testName = testName;
        this.testDifficulty = testDifficulty;
    }

    public void addQuestion(final Question question) {
        question.setTest(this);
        questions.add(question);
    }

    public void addTestResult(final TestResult testResult){
        testResult.setTest(this);
        results.add(testResult);
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Set<TestResult> getResults() {
        return results;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Difficulty getTestDifficulty() {
        return testDifficulty;
    }

    public void setTestDifficulty(Difficulty testDifficulty) {
        this.testDifficulty = testDifficulty;
    }

    public int getTestId() {
        return testId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

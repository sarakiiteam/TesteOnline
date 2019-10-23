package database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
@Table(uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"username"}
        )
})
public class User {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column
    @JsonProperty("passwd")
    private String password;

    @Column
    @JsonProperty("usern")
    private String username;

    public User() {
    }

    public User(final String password, final String username) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void addTest(final Test test) {
        test.setUser(this);
        tests.add(test);
    }

    public List<Test> getTests() {
        return tests;
    }

    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Test> tests = new ArrayList<>();
}



package edu.virginia.sde.reviews;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @Column (name = "USERNAME", unique = true, nullable = false, length = 24)
    private String username;

    @Column(name="PASSWORD", nullable = false, length = 32)
    private String password;

    @OneToMany(mappedBy = "id")
    private List<Review> reviews;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
        // a zero-argument constructor is required for hibernate to work correctly
        // You may have additional constructors, but hibernate will only use the
        // zero argument constructor
        reviews = new ArrayList<>();
    }

    // all fields must have public getters and setters
    // the names must be get[VariableName] and set[VariableName] exactly
    // Just use the IntelliJ generate for Getters and setters, it will create the correct behavior


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}

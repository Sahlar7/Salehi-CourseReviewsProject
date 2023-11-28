package edu.virginia.sde.reviews;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "REVIEWS")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "RATING", nullable = false)
    private int rating;

    @Column(name = "COMMENT", length = 250)
    private String comment;

    @Column(name = "TIMESTAMP",nullable = false)
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "USER", referencedColumnName = "USERNAME")
    private User user;

    @ManyToOne
    @JoinColumn(name = "COURSE", referencedColumnName = "ID")
    private Course course;


    public Review() {
        this.timestamp =  new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

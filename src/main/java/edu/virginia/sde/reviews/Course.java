package edu.virginia.sde.reviews;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

@Entity
@Table(name = "COURSES")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int courseID;

    @Column(name = "MNEMONIC", length = 4, nullable = false)
    private String mnemonic;

    @Column(name = "COURSE_NUMBER", nullable = false)
    private int courseNumber;

    @Column(name = "TITLE", nullable = false, length = 50)
    private String title;

    @OneToMany(mappedBy = "id")
    private List<Review> reviews;

    private double avgRating;


    public Course(int courseID, String mnemonic, int courseNumber, String title) {
        this.courseID = courseID;
        this.mnemonic = mnemonic;
        this.courseNumber = courseNumber;
        this.title = title;
    }

    public Course() {
        reviews = new ArrayList<>();
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review){
        this.reviews.add(review);
        avgRating = getAverageRating();
    }

    public Double getAverageRating(){
        if(reviews == null){
            return null;
        }
        int sum = 0;
        for(Review r : reviews){
            sum += r.getRating();
        }
        double avg = (double) sum / reviews.size();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(avg));
    }
}

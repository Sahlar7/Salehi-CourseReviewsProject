package edu.virginia.sde.reviews;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.hibernate.*;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.ArrayList;



public class ReviewListController {

    @FXML
    private TableView<Review> reviewTable;
    @FXML
    private TableView<Review> myReviewTable;
    @FXML
    private TextField courseName;
    @FXML
    private TextField avgRating;
    @FXML
    private Button backToSearch;
    @FXML
    private Button addReview;
    @FXML
    private Button editReview;
    @FXML
    private Button deleteReview;
    private final ArrayList<Review> reviews = new ArrayList<>();
    private static Course reviewedCourse;

    public void initialize(){
        getReviews();
        courseName.setText(reviewedCourse.getMnemonic() + " " + reviewedCourse.getCourseNumber() + ": " + reviewedCourse.getTitle());
        reviewedCourse.calculateAvgRating(reviews);
        avgRating.setText(reviewedCourse.getAvgRating());

    }
    private void getReviews(){
        String hql = "from Review where course = :reviewedCourse";
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query<Review> query = (session.createQuery(hql, Review.class));
        query.setParameter("reviewedCourse", reviewedCourse);
        reviews.addAll(query.getResultList());
        session.getTransaction().commit();
        session.close();
        updateTable();
    }
    private void updateTable(){
        ObservableList<Review> obsList = FXCollections.observableList(reviews);
        reviewTable.getItems().clear();
        myReviewTable.getItems().clear();
        for (Review review : obsList) {
            if (review.getUser().getUsername().equals(CourseReviewsApplication.getThisUser().getUsername()))
                myReviewTable.getItems().add(review);
            else
                reviewTable.getItems().add(review);
        }
        if (myReviewTable.getItems().isEmpty()) {
            editReview.setVisible(false);
            deleteReview.setVisible(false);
            addReview.setVisible(true);
        }
        else {
            addReview.setVisible(false);
            editReview.setVisible(true);
            deleteReview.setVisible(true);
        }
    }
    public void backToCourseSearch() throws IOException {
        CourseReviewsApplication.switchScene("course-search.fxml", "Course Search");
    }
    public void addReview() throws IOException {
        ReviewEditorController.setNewReview(true);
        ReviewEditorController.setReview(new Review(true));
        CourseReviewsApplication.switchScene("review-editor.fxml", "add review");
    }

    public void editReview() throws IOException {
        Review myReview = myReviewTable.getItems().get(0);
        ReviewEditorController.setNewReview(false);
        ReviewEditorController.setReview(myReview);
        CourseReviewsApplication.switchScene("review-editor.fxml", "edit review");
    }
    public void deleteReview(){
        Review myReview = myReviewTable.getItems().get(0);
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(myReview);
        session.getTransaction().commit();
        session.close();
        reviews.clear();
        getReviews();
        reviewedCourse.calculateAvgRating(reviews);
        avgRating.setText(reviewedCourse.getAvgRating());
    }
    public static void setReviewedCourse(Course course){
        reviewedCourse = course;
    }
    public static Course getReviewedCourse(){
        return reviewedCourse;
    }
}

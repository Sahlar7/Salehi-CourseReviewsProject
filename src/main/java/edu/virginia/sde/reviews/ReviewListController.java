package edu.virginia.sde.reviews;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.hibernate.*;

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
    private ArrayList<Review> reviews = new ArrayList<Review>();
    private static Course reviewedCourse = null;

    public void initialize(){
        getReviews();
        updateTable();
    }
    private void getReviews(){
        String hql = "SELECT r from Review R WHERE R.course = :reviewedCourse";
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        reviews.addAll(session.createQuery(hql).list());
        session.close();
        HibernateUtil.shutdown();
    }
    private void updateTable(){
        ObservableList<Review> obsList = FXCollections.observableList(new ArrayList<>(reviews));
        if (!reviewTable.getItems().isEmpty())
            reviewTable.getItems().clear();
        for(int i = 0; i<obsList.size(); i++){
            if(obsList.get(i).getUser().equals(CourseReviewsApplication.getThisUser()))
                myReviewTable.getItems().add(obsList.get(i));
            else
                reviewTable.getItems().add(obsList.get(i));
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
        ReviewEditorController.setReview(new Review());
        CourseReviewsApplication.switchScene("review-editor.fxml", "add review");
    }

    public void editReview() throws IOException {
        Review myReview = myReviewTable.getItems().get(0);
        ReviewEditorController.setNewReview(false);
        ReviewEditorController.setReview(myReview);
        CourseReviewsApplication.switchScene("review-editor.fxml", "edit review");
    }
    public void deleteReview(){

    }
    public static void setReviewedCourse(Course course){
        reviewedCourse = course;
    }
    public static Course getReviewedCourse(){
        return reviewedCourse;
    }
}

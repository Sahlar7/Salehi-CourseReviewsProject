package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.ArrayList;


public class MyReviewsController {

    @FXML
    private TextField heading;
    @FXML
    private TableView<Review> myReviews;
    @FXML
    private Button backToSearch;

    private ArrayList<Review> reviews = new ArrayList<>();


    public void initialize(){
        heading.setText(CourseReviewsApplication.getThisUser().getUsername() + "'s Reviews");
        myReviews.setOnMouseClicked(this::handleCourseSelect);
        getReviews();
    }

    private void getReviews(){
        String hql = "from Review where user=:user";
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query<Review> query = session.createQuery(hql, Review.class);
        query.setParameter("user", CourseReviewsApplication.getThisUser());
        reviews.addAll(query.getResultList());
        session.getTransaction().commit();
        session.close();
        updateTable();
    }

    private void updateTable() {
        ObservableList<Review> obsList = FXCollections.observableList(reviews);
        myReviews.getItems().clear();
        for (Review r : obsList) {
            r.setCourseMnemonic(r.getCourse().getMnemonic());
            r.setCourseNumber(r.getCourse().getCourseNumber());
            myReviews.getItems().add(r);
        }
    }
    private void handleCourseSelect(MouseEvent mouseEvent) {
        try {
            if (mouseEvent.getClickCount() == 2 && !myReviews.getSelectionModel().isEmpty()) {
                Course selected = myReviews.getSelectionModel().getSelectedItem().getCourse();
                ReviewListController.setReviewedCourse(selected);
                CourseReviewsApplication.switchScene("review-list.fxml", selected.getMnemonic() + " " + selected.getCourseNumber());
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    public void backToCourseSearch() throws IOException {
        CourseReviewsApplication.switchScene("course-search.fxml", "Course Search");
    }
}

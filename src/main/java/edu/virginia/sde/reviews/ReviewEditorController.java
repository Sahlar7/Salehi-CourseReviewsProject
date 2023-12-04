package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import org.hibernate.Session;

import java.io.IOException;


public class ReviewEditorController {
    @FXML
    private TextArea comment;
    @FXML
    private ToggleGroup rating;
    @FXML
    private Text ratingCheck;
    @FXML
    private Button submit;

    private static Review review;
    private static boolean newReview;


    public void handleReviewSubmit() throws IOException {
        if(rating.getSelectedToggle()==null) {
            ratingCheck.setVisible(true);
        }
        else {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            if (newReview) {
                review.setRating(Integer.parseInt((rating.getSelectedToggle().getUserData().toString())));
                review.setComment(comment.getText());
                review.setUser(CourseReviewsApplication.getThisUser());
                review.setCourse(ReviewListController.getReviewedCourse());
                session.persist(review);
                session.getTransaction().commit();
            }
            else {
                review.setRating(Integer.parseInt((rating.getSelectedToggle().getUserData().toString())));
                review.setComment(comment.getText());
                session.update(review);
                session.getTransaction().commit();
            }
            session.close();
            HibernateUtil.shutdown();
            ratingCheck.setVisible(false);
            CourseReviewsApplication.switchScene("review-list.fxml", "review list");
        }
    }

    public static void setNewReview(boolean n) {
        newReview = n;
    }
    public static void setReview(Review r) {
        review = r;
    }
}

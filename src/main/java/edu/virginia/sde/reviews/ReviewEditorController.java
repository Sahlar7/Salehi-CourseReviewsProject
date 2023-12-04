package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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
    @FXML
    private Text commentCheck;
    @FXML
    private RadioButton rating1;
    @FXML
    private RadioButton rating2;
    @FXML
    private RadioButton rating3;
    @FXML
    private RadioButton rating4;
    @FXML
    private RadioButton rating5;


    private static Review review;
    private static boolean newReview;


    public void initialize(){
        if(!newReview){
            switch(review.getRating()){
                case 1:
                    rating.selectToggle(rating1);
                case 2:
                    rating.selectToggle(rating2);
                case 3:
                    rating.selectToggle(rating3);
                case 4:
                    rating.selectToggle(rating4);
                case 5:
                    rating.selectToggle(rating5);
                default:

            }
            comment.setText(review.getComment());
        }
    }
    public void handleReviewSubmit() throws IOException {
        if(rating.getSelectedToggle()==null) {
            ratingCheck.setVisible(true);
            return;
        }
        if(comment.getText().length() > 250) {
            commentCheck.setVisible(true);
            return;
        }
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            if (newReview) {
                if(rating.getSelectedToggle().equals(rating1))
                    review.setRating(1);
                else if(rating.getSelectedToggle().equals(rating2))
                    review.setRating(2);
                else if(rating.getSelectedToggle().equals(rating3))
                    review.setRating(3);
                else if(rating.getSelectedToggle().equals(rating4))
                    review.setRating(4);
                else if(rating.getSelectedToggle().equals(rating5))
                    review.setRating(5);
                review.setComment(comment.getText());
                review.setUser(CourseReviewsApplication.getThisUser());
                review.setCourse(ReviewListController.getReviewedCourse());
                session.persist(review);
                session.getTransaction().commit();
            }
            else {
                if(rating.getSelectedToggle().equals(rating1))
                    review.setRating(1);
                else if(rating.getSelectedToggle().equals(rating2))
                    review.setRating(2);
                else if(rating.getSelectedToggle().equals(rating3))
                    review.setRating(3);
                else if(rating.getSelectedToggle().equals(rating4))
                    review.setRating(4);
                else if(rating.getSelectedToggle().equals(rating5))
                    review.setRating(5);
                review.setComment(comment.getText());
                session.update(review);
                session.getTransaction().commit();
            }
            session.close();
            ratingCheck.setVisible(false);
            commentCheck.setVisible(false);
            CourseReviewsApplication.switchScene("review-list.fxml", review.getCourse().getMnemonic() + " " + review.getCourse().getCourseNumber());
        }

    public static void setNewReview(boolean n) {
        newReview = n;
    }
    public static void setReview(Review r) {
        review = r;
    }
}

package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

import java.io.IOException;
import java.util.logging.Level;

public class AddClassController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button addButton;

    @FXML
    private TextField mnemonic;

    @FXML
    private TextField courseNumber;

    @FXML
    private TextField title;

    @FXML
    private DialogPane errorPopup;

    public void handleAdd() throws IOException {
        String subject = mnemonic.getText().trim();
        Integer courseNum = Integer.parseInt(courseNumber.getText().trim());
        String courseTitle = title.getText().trim();

        if (subject.length() > 4 || subject.length() < 2) {
            handleError("Invalid subject mnemonic length (Must be 2-4 characters)");
        } else if (courseNum < 1000 || courseNum > 9999) {
            handleError("Invalid course number (Must be within 1000-9999");
        } else if (courseTitle.length() < 1 || courseTitle.length() > 50) {
            handleError("Invalid course title length (Must be 1-50 characters)");
        } else {
            for(int i = 0; i < subject.length(); i++){
                if(Character.isDigit(subject.charAt(i))){
                    handleError("Invalid subject mnemonic. Use only letters.");
                    return;
                }
            }
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query<Course> query = session.createQuery("FROM Course WHERE mnemonic = :subject " +
                    "AND courseNumber = :courseNum AND title = :courseTitle");
            query.setParameter("subject", subject.toUpperCase());
            query.setParameter("courseNum", courseNum);
            query.setParameter("courseTitle", courseTitle);
            if(query.getSingleResultOrNull() != null){
                handleError("Course already exists.");
                session.close();
                return;
            }
            session.close();
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Course added = new Course(subject.toUpperCase(), courseNum, courseTitle);
            session.persist(added);
            session.getTransaction().commit();
            session.close();
            CourseReviewsApplication.switchScene("course-search.fxml", "Course Search");
        }
    }

    public void handleCancel() throws IOException {
        CourseReviewsApplication.switchScene("course-search.fxml", "Course Search");
    }

    private void handleError(String message) {
        errorPopup.setContentText(message);
        errorPopup.setVisible(true);
    }
}

package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import org.hibernate.Session;

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
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String subject = null;
        Integer courseNum = null;
        String courseTitle = null;
        try {
            subject = mnemonic.getText();
            courseNum = Integer.parseInt(courseNumber.getText());
            courseTitle = title.getText();
        } catch (Exception e) {
            handleError("Invalid inputs. Use only letters for the Subject and numbers for the Course Number.");
            session.close();
            return;
        }
        if (subject.length() > 4 || subject.length() < 2) {
            handleError("Invalid subject mnemonic length (Must be 2-4 characters)");
        } else if (courseNum < 1000 || courseNum > 9999) {
            handleError("Invalid course number (Must be within 1000-9999");
        } else if (courseTitle.length() < 1 || courseTitle.length() > 50) {
            handleError("Invalid course title length (Must be 1-50 characters)");
        } else {
            for (Course c : session.createQuery("FROM Course", Course.class).list()) {
                if (c.getMnemonic().equalsIgnoreCase(subject) && c.getCourseNumber() == courseNum) {
                    handleError(subject.toUpperCase() + " " + courseNum + " already exists.");
                    session.close();
                    return;
                }
            }
            Course added = new Course(subject, courseNum, courseTitle);
            session.persist(added);
            session.getTransaction().commit();
            session.close();
            CourseReviewsApplication.switchScene("course-search.fxml", "Course Search");
            return;
        }
        session.close();
    }

    public void handleCancel() throws IOException {
        CourseReviewsApplication.switchScene("course-search.fxml", "Course Search");
    }

    private void handleError(String message) {
        errorPopup.setContentText(message);
        errorPopup.setVisible(true);
    }
}

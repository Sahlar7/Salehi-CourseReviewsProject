package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class CourseSearchController {

    @FXML
    private TableView<Course> tableView;

    @FXML
    private Button searchButton;

    @FXML
    private Button logOutButton;

    @FXML
    private TextField mnemonic;

    @FXML
    private TextField courseNumber;

    @FXML
    private TextField title;

    private Catalog catalog = new Catalog();

    private Course selected;

    public void initialize() {
        showAllCourses();
        mnemonic.clear();
        courseNumber.clear();
        title.clear();
        tableView.setOnMouseClicked(this::handleCourseSelect);
        tableView.setOnMouseEntered(this::handleHoverEnter);
        tableView.setOnMouseExited(this::handleHoverExit);

    }

    private void handleHoverExit(MouseEvent mouseEvent) {
        if (mouseEvent.getTarget() instanceof TableRow) {
            TableRow<Course> row = (TableRow<Course>) mouseEvent.getTarget();
            row.setStyle("-fx-background-color: transparent;");
        }
    }

    private void handleHoverEnter(MouseEvent mouseEvent) {
        if (mouseEvent.getTarget() instanceof TableRow) {
            TableRow<Course> row = (TableRow<Course>) mouseEvent.getTarget();
            row.setStyle("-fx-background-color: lightgray;");
        }
    }

    private void handleCourseSelect(MouseEvent mouseEvent) {
        try {
            if (mouseEvent.getClickCount() == 2 && !tableView.getSelectionModel().isEmpty()) {
                selected = tableView.getSelectionModel().getSelectedItem();
                ReviewListController.setReviewedCourse(selected);
                CourseReviewsApplication.switchScene("review-list.fxml", selected.getMnemonic() + " " + selected.getCourseNumber());
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public Course getSelectedCourse() {
        return selected;
    }

    private void updateTable() {
        if (tableView != null) {
            ObservableList<Course> obsList = FXCollections.observableList(catalog.getCoursesInAlphabetMnemonicOrder());
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (Course c : obsList) {
                Query<Review> query = session.createQuery("FROM Review WHERE course = :course");
                query.setParameter("course", c);
                c.calculateAvgRating(query.list());
            }
            session.close();
            tableView.getItems().clear();
            tableView.getItems().addAll(obsList);
        }
    }

    /*private String calculateAvgRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return "";
        }
        int sum = 0;
        for (Review r : reviews) {
            sum += r.getRating();
        }
        double avg = (double) sum / reviews.size();
        return String.format("%.2f", avg);
    }*/

    public void handleSearchButton() {
        if (tableView.getItems() != null) {
            catalog.setCourses(tableView.getItems());
        } else {
            return;
        }
        if (!this.mnemonic.getText().isEmpty() || !this.courseNumber.getText().isEmpty() || !this.title.getText().isEmpty()) {
            if (!this.mnemonic.getText().isEmpty()) {
                catalog.setCourses(catalog.getCoursesByMnemonic(mnemonic.getText()));
            }
            if (!this.courseNumber.getText().isEmpty()) {
                catalog.setCourses(catalog.getCoursesByNumber(Integer.parseInt(courseNumber.getText())));
            }
            if (!this.title.getText().isEmpty()) {
                catalog.setCourses(catalog.getCoursesByTitle(title.getText()));
            }
        } else {
            showAllCourses();
        }
        updateTable();
    }

    public void showAllCourses() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        catalog.setCourses(session.createQuery("FROM Course", Course.class).list());
        session.getTransaction().commit();
        session.close();
        updateTable();
    }

    public void addCourse() throws IOException {
        CourseReviewsApplication.switchScene("add-class.fxml", "Add Course");
    }

    public void logOut() throws IOException {
        CourseReviewsApplication.setThisUser(null);
        CourseReviewsApplication.switchScene("login.fxml", "Log In");
    }

    public void myReviews() throws IOException {
        CourseReviewsApplication.switchScene("my-reviews.fxml", "My Reviews");
    }

}

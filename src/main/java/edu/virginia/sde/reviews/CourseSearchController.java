package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.logging.Level;

public class CourseSearchController {

    @FXML
    private TableView<Course> tableView;

    @FXML
    private TextField mnemonic;

    @FXML
    private TextField courseNumber;

    @FXML
    private TextField title;

    @FXML
    private Button okButton;

    @FXML
    private DialogPane errorPopup;

    private Catalog allCourses = new Catalog();

    private Catalog searchCourses = new Catalog();

    private Course selected;

    public void initialize() {
        createAllCourses();
        showAllCourses();
        tableView.setOnMouseClicked(this::handleCourseSelect);
        tableView.setOnMouseEntered(this::handleHoverEnter);
        tableView.setOnMouseExited(this::handleHoverExit);
    }

    public void clearSearch(){
        showAllCourses();
        mnemonic.clear();
        courseNumber.clear();
        title.clear();
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
            ObservableList<Course> obsList = FXCollections.observableList(searchCourses.getCoursesInAlphabetMnemonicOrder());
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

    public void handleSearchButton() {
        if (!validInputs()) {
            handleError();
            return;
        }
        if (!this.mnemonic.getText().isEmpty() || !this.courseNumber.getText().isEmpty() || !this.title.getText().isEmpty()) {
            searchCourses.setCourses(allCourses.getCoursesInAlphabetMnemonicOrder());
            if (!this.mnemonic.getText().isEmpty()) {
                searchCourses.setCourses(searchCourses.getCoursesByMnemonic(mnemonic.getText().trim()));
            }
            if (!this.courseNumber.getText().isEmpty()) {
                searchCourses.setCourses(searchCourses.getCoursesByNumber(Integer.parseInt(courseNumber.getText().trim())));
            }
            if (!this.title.getText().isEmpty()) {
                searchCourses.setCourses(searchCourses.getCoursesByTitle(title.getText()));
            }
            updateTable();
        }
    }

    private boolean validInputs(){
        if (!mnemonic.getText().trim().isEmpty()) {
            if(mnemonic.getText().trim().length() > 4 || mnemonic.getText().trim().length() < 2) {
                return false;
            }
            for(int i = 0; i < mnemonic.getText().trim().length(); i++){
                if(mnemonic.getText().trim().toUpperCase().charAt(i) > 90 || mnemonic.getText().trim().toUpperCase().charAt(i) < 65){
                    return false;
                }
            }
        }
        if (!courseNumber.getText().trim().isEmpty()) {
            for(int i = 0; i < courseNumber.getText().length(); i++){
                if(!Character.isDigit(courseNumber.getText().charAt(i))){
                    return false;
                }
            }
            if(Integer.parseInt(courseNumber.getText().trim()) < 1000 || Integer.parseInt(courseNumber.getText().trim()) > 9999) {
                return false;
            }
        }
        if (!title.getText().isEmpty()) {
            if(title.getText().trim().isEmpty() || title.getText().trim().length() > 50){
                return false;
            }
        }
        return true;
    }

    public void createAllCourses() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        allCourses.setCourses(session.createQuery("FROM Course", Course.class).list());
        session.getTransaction().commit();
        session.close();
    }

    public void showAllCourses() {
        searchCourses.setCourses(allCourses.getCoursesInAlphabetMnemonicOrder());
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

    private void handleError() {
        errorPopup.setContentText("Use 2-4 letters for Subject, 4 letters for Number, and 1-50 characters for Title.");
        errorPopup.setVisible(true);
        okButton.setVisible(true);
        clearSearch();
    }

    public void handleErrorClose(){
        errorPopup.setVisible(false);
        okButton.setVisible(false);
    }

}

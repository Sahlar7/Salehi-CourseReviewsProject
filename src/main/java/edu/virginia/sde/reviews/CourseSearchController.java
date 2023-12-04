package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.hibernate.*;

import java.io.IOException;
import java.util.List;

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

    public void initialize(){
        showAllCourses();
        tableView.setOnMouseClicked(this::handleCourseSelect);
        tableView.setOnMouseEntered(this::handleHoverEnter);
        tableView.setOnMouseExited(this::handleHoverExit);

    }

    private void handleHoverExit(MouseEvent mouseEvent) {
        if (mouseEvent.getTarget() instanceof TableRow) {
            TableRow<Course> row = (TableRow<Course>)mouseEvent.getTarget();
            row.setStyle("-fx-background-color: transparent;");
        }
    }

    private void handleHoverEnter(MouseEvent mouseEvent) {
        if (mouseEvent.getTarget() instanceof TableRow) {
            TableRow<Course> row = (TableRow<Course>)mouseEvent.getTarget();
            row.setStyle("-fx-background-color: lightgray;");
        }
    }

    private void handleCourseSelect(MouseEvent mouseEvent){
        try {
            if (mouseEvent.getClickCount() == 2 && !tableView.getSelectionModel().isEmpty()) {
                selected = tableView.getSelectionModel().getSelectedItem();
                ReviewListController.setReviewedCourse(selected);
                CourseReviewsApplication.switchScene("review-list.fxml", selected.getMnemonic() + " " + selected.getCourseNumber());
            }
        } catch(IOException e){
            throw new RuntimeException();
        }
    }
    public Course getSelectedCourse(){
        return selected;
    }

    private void updateTable(){
        if(tableView != null){
            ObservableList<Course> obsList = FXCollections.observableList(catalog.getCoursesInAlphabetMnemonicOrder());
            tableView.getItems().clear();
            tableView.getItems().addAll(obsList);
        }
    }

    public void handleSearchButton(){
        if(tableView.getItems() != null){
            catalog.setCourses(tableView.getItems());
        }
        else{
            return;
        }
        if(this.mnemonic.hasProperties() || this.courseNumber.hasProperties() || this.title.hasProperties()){
            if(this.mnemonic.hasProperties()){
                catalog.setCourses(catalog.getCoursesByMnemonic(mnemonic.getText()));
            }
            if(this.courseNumber.hasProperties()){
                catalog.setCourses(catalog.getCoursesByNumber(Integer.parseInt(courseNumber.getText())));
            }
            if(this.title.hasProperties()){
                catalog.setCourses(catalog.getCoursesByTitle(title.getText()));
            }
        }
        else{
            showAllCourses();
        }
        updateTable();
    }

    public void showAllCourses(){
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

package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    public void initialize(){
        showAllCourses();
        updateTable();
    }
    private void updateTable(){
        tableView.getItems().clear();
        tableView.getItems().addAll(catalog.getCourses());
    }

    public void handleSearchButton(){
        catalog.setCourses(tableView.getItems());
        if(mnemonic.hasProperties() || courseNumber.hasProperties() || title.hasProperties()){
            if(mnemonic.hasProperties()){
                catalog.setCourses(catalog.getCoursesByMnemonic(mnemonic.getText()));
            }
            if(courseNumber.hasProperties()){
                catalog.setCourses(catalog.getCoursesByNumber(Integer.parseInt(courseNumber.getText())));
            }
            if(title.hasProperties()){
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
        HibernateUtil.shutdown();
        updateTable();
    }

    public void addCourse(){
        // TODO:
        // CourseReviewsApplication.switchScene("add-course", "Add Course");
    }

    public void selectCourse(){
        // TODO:
        // CourseReviewsApplication.switchScene(/*COURSE VIEW FILE*/, "Course");
    }

    public void logOut() throws IOException {
        CourseReviewsApplication.setThisUser(null);
        CourseReviewsApplication.switchScene("login.fxml", "Log In");
    }

    public void myReviews(){
        // TODO:
        // CourseReviewsApplication.switchScene(/*MY REVIEWS FILE*/, "My Reviews");
    }
}

package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseReviewsApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static Stage stage;

    private static User thisUser = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void switchScene(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(CourseReviewsApplication.class
                .getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void setThisUser(User user) {
        thisUser = user;
    }

    public static User getThisUser() {
        return thisUser;
    }
}

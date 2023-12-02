package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.hibernate.Session;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;

public class LoginController {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField passwordVisible;

    @FXML
    private CheckBox togglePassword;

    @FXML
    private DialogPane errorPopup;

    @FXML
    private Button login;

    @FXML
    private Button createAccount;

    @FXML
    private Button okButton;

    @FXML
    private Label loading;


    public void createAccount() throws IOException {
        CourseReviewsApplication.switchScene("create-account.fxml", "Create Account");
    }

    public void handleLogin() throws IOException {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        User thisUser = session.get(User.class, username.getCharacters().toString());
        if (thisUser == null) {
            handleError("Username Not Found.");
            session.close();
            return;
        }
        var curPassword = togglePassword.isSelected() ? passwordVisible.getText() : password.getText();
        if (!thisUser.getPassword().equals(curPassword)) {
            handleError("Incorrect Password.");
            session.close();
            return;
        }
        session.close();
        CourseReviewsApplication.setThisUser(thisUser);
        CourseReviewsApplication.switchScene("course-search.fxml", thisUser.getUsername());


    }

    public void togglePassword() {
        if (togglePassword.isSelected()) {
            passwordVisible.setText(password.getText());
            passwordVisible.setVisible(true);
            password.setVisible(false);
            return;
        }
        password.setText(passwordVisible.getText());
        password.setVisible(true);
        passwordVisible.setVisible(false);
    }

    private void handleError(String errorMessage) {

        username.setDisable(true);
        password.setDisable(true);
        login.setDisable(true);
        createAccount.setDisable(true);
        togglePassword.setDisable(true);

        errorPopup.setContentText(errorMessage);
        errorPopup.setVisible(true);
        okButton.setVisible(true);
    }

    public void handleErrorClose() {
        username.setDisable(false);
        username.setText("");
        password.setDisable(false);
        password.setText("");
        login.setDisable(false);
        createAccount.setDisable(false);
        togglePassword.setDisable(false);

        errorPopup.setVisible(false);
        okButton.setVisible(false);
    }

}

package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.logging.Level;

public class CreateAccountController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Button createAccount;

    @FXML
    private Button backButton;

    @FXML
    private DialogPane errorPopup;

    @FXML
    private Button okButton;

    @FXML
    private Button popupBackButton;

    public void returnToLogin() throws IOException {
        CourseReviewsApplication.switchScene("login.fxml", "Login");
    }

    public void CreateAccount() throws IOException {

        if (username.getText().isEmpty()) {
            handleError("Please Enter A Username.");
            return;
        }
        if (password.getText().isEmpty()) {
            handleError("Please Enter A Password");
            return;
        }
        if(confirmPassword.getText().isEmpty()) {
            handleError("Please Confirm Your Password.");
            return;
        }


        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query<User> query = session.createQuery("from User where username = '"+username.getText()+"'");
        if (query.getSingleResultOrNull() != null) {
            handleError("Username Taken.");
            session.close();
            return;
        }

        if (password.getText().length() < 8) {
            handleError("Password must be at least 8 characters.");
            session.close();
            return;
        }

        if (!password.getText().equals(confirmPassword.getText()) ) {
            handleError("Passwords do not match.");
            session.close();
            return;
        }

        User newUser = new User(username.getText(), password.getText());
        session.persist(newUser);
        session.getTransaction().commit();
        session.close();

        errorPopup.setContentText("Account Created.");
        username.setDisable(true);
        password.setDisable(true);
        confirmPassword.setDisable(true);
        backButton.setDisable(true);
        createAccount.setDisable(true);
        errorPopup.setVisible(true);
        popupBackButton.setVisible(true);
    }

    private void handleError(String errorMessage) {

        username.setDisable(true);
        password.setDisable(true);
        confirmPassword.setDisable(true);
        backButton.setDisable(true);
        createAccount.setDisable(true);

        errorPopup.setContentText(errorMessage);
        errorPopup.setVisible(true);
        okButton.setVisible(true);
    }

    public void handleErrorClose() {
        username.setDisable(false);
        username.setText("");
        password.setDisable(false);
        password.setText("");
        confirmPassword.setText("");
        confirmPassword.setDisable(false);
        backButton.setDisable(false);
        createAccount.setDisable(false);

        errorPopup.setVisible(false);
        okButton.setVisible(false);
    }


}

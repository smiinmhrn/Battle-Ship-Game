package MyApp.controller;

import MyApp.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class RegisterPageController implements Initializable {
    @FXML
    private Button cancelBTN;

    @FXML
    private Button registerBTN;

    @FXML
    private TextField confField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label errorLable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mouseHover();
        mouseOutOfHover();
        errorLable.setText("");
        cancelBTN.setOnAction(event -> cancelBTNAction());
        registerBTN.setOnAction(event -> {
            try {
                register();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void mouseHover() {
        cancelBTN.setOnMouseEntered(mouseEvent -> {
            cancelBTN.setStyle("-fx-background-color: blue; -fx-background-radius: 50");
        });
        registerBTN.setOnMouseEntered(mouseEvent -> {
            registerBTN.setStyle("-fx-background-color: blue; -fx-background-radius: 50");
        });
    }
    private void mouseOutOfHover(){
        cancelBTN.setOnMouseExited(mouseEvent -> {
            cancelBTN.setStyle("-fx-background-color: black; -fx-background-radius: 50");
        });
        registerBTN.setOnMouseExited(mouseEvent -> {
            registerBTN.setStyle("-fx-background-color: black; -fx-background-radius: 50");
        });
    }
    private void cancelBTNAction() {
        ((Stage)cancelBTN.getScene().getWindow()).close();
        LoginPageController.stage = null;
    }
    private void register() throws SQLException {
        if (checkEmptyFields() && checkUsernameUniq() && checkPasswords() ){

            User user = new User(usernameField.getText(), nameField.getText(),
                    lastNameField.getText(), 0, passwordField.getText());
            user.addOrUpdate();
            cleanPage();
        }
    }
    private boolean checkEmptyFields() {

        if (usernameField.getText().isEmpty() || nameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() || passwordField.getText().isEmpty()
                || confField.getText().isEmpty()) {
            errorLable.setTextFill(Color.RED);
            errorLable.setText("Please fill in all the fields");
            return false;
        }
        return true;
    }
    private boolean checkUsernameUniq() throws SQLException {
        ArrayList<User> users = User.getAllUsers();
        for (User user: users) {
            if (user.getUserName().equals(usernameField.getText())) {
                errorLable.setTextFill(Color.RED);
                errorLable.setText("This is username is already taken. try another one");
                return false;
            }
        }
        return true;
    }
    private boolean checkPasswords() {
        if (!(passwordField.getText().equals(confField.getText()))){
            errorLable.setTextFill(Color.RED);
            errorLable.setText("passwords arent the same");
            return false;
        }
        return true;
    }
    private void cleanPage() {
        usernameField.setText("");
        nameField.setText("");
        lastNameField.setText("");
        passwordField.setText("");
        confField.setText("");
        errorLable.setTextFill(Color.GREEN);
        errorLable.setText("""
                registered successfully!
                register another account or\s
                press cancel and enter the game""");
    }
}

package MyApp.controller;

import MyApp.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
        errorLable.setText("");
        cancelBTN.setOnAction(event -> cancelBTNAction());
        registerBTN.setOnAction(event -> register());
    }
    private void cancelBTNAction() {
        ((Stage)cancelBTN.getScene().getWindow()).close();
        LoginPageController.stage = null;
    }
    private void register(){
        if (checkEmptyFields()){

        }
    }
    private boolean checkEmptyFields() {

        if (usernameField.getText().isEmpty() || nameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() || passwordField.getText().isEmpty()
                || confField.getText().isEmpty()) {
            errorLable.setText("Please fill in all the fields");
            return false;
        }
        return true;
    }
//    private boolean checkUsernameUniq() {
//        for (:) {
//
//        }
//    }
}

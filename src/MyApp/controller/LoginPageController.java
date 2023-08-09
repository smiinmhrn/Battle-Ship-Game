package MyApp.controller;

import MyApp.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    @FXML
    private Button startBTN;

    @FXML
    private Button exitBTN;

    @FXML
    private Button registerBTN;

    @FXML
    private TextField passWordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label errorLBL;

    static Stage stage = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLBL.setText("");
        exitBTN.setOnAction(event -> cancelBTNAction());
        startBTN.setOnAction(event -> {
            try {
                startGame();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        registerBTN.setOnAction(event -> {
            try {
                openRegisterWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void cancelBTNAction() {
        ((Stage)exitBTN.getScene().getWindow()).close();
    }
    private void openRegisterWindow() throws IOException {

        if (stage == null) {
            BorderPane root = FXMLLoader.load(this.getClass().getResource("/MyApp/view/RegisterPage.fxml"));
            stage = new Stage();
            stage.setTitle("register window");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
    private void startGame() throws SQLException, IOException {
        if (checkEmptyFields() && checkUserInfo()) {
            AnchorPane root = FXMLLoader.load(this.getClass().getResource("/MyApp/view/GameMainPage.fxml"));
            Stage mianStage = (Stage)registerBTN.getScene().getWindow();
            mianStage.setScene(new Scene(root));
            mianStage.setFullScreen(true);
        }
    }
    private boolean checkEmptyFields() {

        if (usernameField.getText().isEmpty() || passWordField.getText().isEmpty()) {
            errorLBL.setText("Please fill in all the fields");
            return false;
        }
        return true;
    }
    private boolean checkUserInfo () throws SQLException {
        User user = checkExistsUsername();
        if (user != null) {
            return checkPassword(user);
        }else errorLBL.setText("username not found");
        return false;
    }

    private User checkExistsUsername() throws SQLException {
        ArrayList<User> users = User.getAllUsers();
        for (User user: users) {
            if (user.getUserName().equals(usernameField.getText())) {
                return user;
            }
        }
        return null;
    }
    private boolean checkPassword(User user) {
        if (!(user.getPassword().equals(passWordField.getText()))) {
            errorLBL.setText("wrong password try again");
            return false;
        }
        return true;
    }
}

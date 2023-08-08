package MyApp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    @FXML
    private Button enterBTN;

    @FXML
    private Button exitBTN;

    @FXML
    private Button registerBTN;

    @FXML
    private TextField passWordField;

    @FXML
    private TextField usernameField;

    static Stage stage = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitBTN.setOnAction(event -> cancelBTNAction());
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
}

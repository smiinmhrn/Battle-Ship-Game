package MyApp.controller;

import MyApp.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameMainPageController implements Initializable {

    @FXML
    private Label pointsLBL;

    @FXML
    private Label userNameLBL;
    private User loggedUser;

    @FXML
    private VBox enemyFiled;

    @FXML
    private VBox userFiled;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        for (int i = 0; i < 10; i++) {
//            HBox hBox = new HBox();
//            for (int j = 0; j < 10; j++) {
//                Button button = new Button("");
//                hBox.getChildren().add(button);
//            }
//            userFiled.getChildren().add(hBox);
//        }
    }

    public void initUser(User loggedUser) {
        this.loggedUser = loggedUser;
        userNameLBL.setText(loggedUser.getUserName());
        pointsLBL.setText(String.valueOf(loggedUser.getPoint()));
    }
}

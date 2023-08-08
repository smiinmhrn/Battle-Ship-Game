package MyApp;

import MyApp.controller.DataBase;
import MyApp.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main  {
    public static void main(String[] args) throws SQLException {
//        launch(args);
        int id = DataBase.creatUser(new User("sam", "samin", "mehran", 20));
    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        BorderPane root = FXMLLoader.load(this.getClass().getResource("view/LoginPage.fxml"));
//        stage.setTitle("Battle ship");
//        stage.setScene(new Scene(root));
//        stage.show();
//
//    }
}
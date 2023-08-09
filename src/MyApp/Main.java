package MyApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(this.getClass().getResource("view/LoginPage.fxml"));
        stage.setTitle("Battle ship");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
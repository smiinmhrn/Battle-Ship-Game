package MyApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {
    public static MediaPlayer mainMediaPlayer;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Thread audioPlayer = new Thread(() -> {

            String audioFilePath = "src/MyApp/view/audio/extreme-metal-57400-PREVIEW_musicte.mp3";
            Media media = new Media(new File(audioFilePath).toURI().toString());
            mainMediaPlayer = new MediaPlayer(media);
            mainMediaPlayer.play();

            mainMediaPlayer.setOnEndOfMedia(() -> {
                mainMediaPlayer.seek(mainMediaPlayer.getStartTime());
                mainMediaPlayer.play();
            });
        });



        BorderPane root = FXMLLoader.load(this.getClass().getResource("view/LoginPage.fxml"));
        stage.setTitle("Battle ship");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
        audioPlayer.start();
    }
}
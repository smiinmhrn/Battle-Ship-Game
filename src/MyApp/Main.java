package MyApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class Main extends Application {
    private MediaPlayer mediaPlayer;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        String audioFilePath = "src/MyApp/view/audio/extreme-metal-57400-PREVIEW_musicte.mp3";
        Media media = new Media(new File(audioFilePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        stage.setOnCloseRequest(event -> {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        });


        BorderPane root = FXMLLoader.load(Objects.requireNonNull(
                this.getClass().getResource("view/LoginPage.fxml")));

        stage.setTitle("Battle ship");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
//        audioPlayer.start();
    }
}
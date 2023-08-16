package MyApp.controller;

import MyApp.model.Cell;
import MyApp.model.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameMainPageController implements Initializable {

    @FXML
    private Label pointsLBL;

    @FXML
    private Label enemyPointLBL;

    @FXML
    private Label userNameLBL;
    private User loggedUser;
    private int enemyPoint = 0;

    @FXML
    private VBox enemyFiled;

    @FXML
    private VBox userFiled;

    @FXML
    private Button randomBTN;

    @FXML
    private Button existBTN;

    @FXML
    private Button startGameBTN;

    @FXML
    private Button retryBTN;
    private boolean gameStarted;

    private ImageView imageView1 = null;

    private ImageView imageView = null;

    private Cell[][] userCell;
    private MediaPlayer buttonMediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String buttonAudioFilePath = "src/MyApp/view/audio/bombSoundEffect.mp3";
        Media buttonMedia = new Media(new File(buttonAudioFilePath).toURI().toString());
        buttonMediaPlayer = new MediaPlayer(buttonMedia);

        mouseHover();
        mouseOutOfHover();

        prepareGameBoard();

        retryBTN.setDisable(true);
        randomBTN.setOnAction(event -> prepareGameBoard());

        retryBTN.setOnAction(event -> {
            prepareGameBoard();

            retryBTN.setDisable(true);
            randomBTN.setDisable(false);
            startGameBTN.setDisable(false);

            existBTN.setText("Exit");

        });

        existBTN.setOnAction(event -> Platform.exit());

        startGameBTN.setOnAction(event -> {
            gameStarted = true;

            randomBTN.setDisable(true);
            retryBTN.setDisable(false);
            startGameBTN.setDisable(true);

            existBTN.setText("Pause");

            existBTN.setOnAction(event1 -> {
                startGameBTN.setText("Resume");
                startGameBTN.setDisable(false);

                existBTN.setText("Exit");
                existBTN.setOnAction(event2 -> Platform.exit());

                gameStarted = false;
            });

        });

    }
    private void mouseHover() {
        randomBTN.setOnMouseEntered(
                mouseEvent -> randomBTN.setStyle("-fx-background-color: blue; -fx-background-radius: 50"));
        retryBTN.setOnMouseEntered(
                mouseEvent -> retryBTN.setStyle("-fx-background-color: blue; -fx-background-radius: 50"));
        existBTN.setOnMouseEntered(
                mouseEvent -> existBTN.setStyle("-fx-background-color: red; -fx-background-radius: 50"));
        startGameBTN.setOnMouseEntered(
                mouseEvent -> startGameBTN.setStyle("-fx-background-color: green; -fx-background-radius: 50"));
    }
    private void mouseOutOfHover(){
        randomBTN.setOnMouseExited(
                mouseEvent -> randomBTN.setStyle("-fx-background-color: black; -fx-background-radius: 50"));
        retryBTN.setOnMouseExited(
                mouseEvent -> retryBTN.setStyle("-fx-background-color: black; -fx-background-radius: 50"));
        existBTN.setOnMouseExited(
                mouseEvent -> existBTN.setStyle("-fx-background-color: black; -fx-background-radius: 50"));
        startGameBTN.setOnMouseExited(
                mouseEvent -> startGameBTN.setStyle("-fx-background-color: black; -fx-background-radius: 50"));
    }
    public void initUser(User loggedUser) {
        this.loggedUser = loggedUser;
        userNameLBL.setText(loggedUser.getUserName());
        pointsLBL.setText("Points: " + loggedUser.getPoint());
        enemyPointLBL.setText("Points: " + "0");
    }
    private void prepareGameBoard() {

        resetPoints();
        clearButton();
        clearGameBoard();

        randomBTN.setText("Change Zone");
        startGameBTN.setText("Start Game");

        userCell = getRandomField(false);
        Cell[][] enemyCell = getRandomField(true);

        for (int i = 0; i < 10; i++) {
            HBox userHbox = new HBox();
            HBox enemyHbox = new HBox();

            for (int j = 0; j < 10; j++) {

                userHbox.getChildren().add(userCell[i][j]);
                enemyHbox.getChildren().add(enemyCell[i][j]);
            }
            userFiled.getChildren().add(userHbox);
            enemyFiled.getChildren().add(enemyHbox);
        }
    }
    private void clearGameBoard() {
        userFiled.getChildren().clear();
        enemyFiled.getChildren().clear();
    }
    private void clearButton() {
        if (imageView1 != null) {
            this.imageView1.setImage(null);
            this.imageView1 = null;
        }
        if (imageView != null) {
            this.imageView.setImage(null);
            this.imageView = null;
        }
    }
    private void resetPoints() {
        if (enemyPoint != 0 && loggedUser != null) {
            this.enemyPoint = 0;
            enemyPointLBL.setText("Points: " + "0");

            loggedUser.setPoint(0);
            pointsLBL.setText("Points: " + loggedUser.getPoint());
        }
    }
    private void playAudio() {

        if (buttonMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            buttonMediaPlayer.stop();
            buttonMediaPlayer.dispose();
        }

        buttonMediaPlayer = new MediaPlayer(buttonMediaPlayer.getMedia());

        buttonMediaPlayer.seek(buttonMediaPlayer.getStartTime());
        buttonMediaPlayer.play();

    }
    private void cellOnAction(Cell cell) {

        cell.setOnAction(event -> {

            if (cell.isEnemyCell() && !cell.isSelected() && gameStarted) {
                if (cell.getPoint() != 0) {
                    setBackground(cell);

                    loggedUser.setPoint(loggedUser.getPoint() + cell.getPoint());
                    pointsLBL.setText("Points: " + loggedUser.getPoint());

                    if (loggedUser.getPoint() == 50) {
                        winnerController("/MyApp/view/WinnerWindow.fxml");
                    }

                } else {
                    cell.setStyle("-fx-background-color: blue");
                }
                cell.setSelected(true);

                while (true) {

                    int x = (int) (Math.random() * 10);
                    int y = (int) (Math.random() * 10);

                    if (!userCell[x][y].isSelected() && gameStarted) {
                        if (userCell[x][y].getPoint() != 0) {

                            setBackground(userCell[x][y]);
                            playAudio();

                            userCell[x][y].setSelected(true);

                            enemyPoint = enemyPoint + userCell[x][y].getPoint();
                            enemyPointLBL.setText("Points: " + enemyPoint);

                            if (enemyPoint == 50 && loggedUser.getPoint() != 50) {
                                winnerController("/MyApp/view/GameOverWindow.fxml");
                            }
                        }else {
                            userCell[x][y].setStyle("-fx-background-color: blue");
                            userCell[x][y].setSelected(true);
                        }
                        break;
                    }

                }
            }
        });
    }
    private void winnerController(String path) {
        result(path);
        startGameBTN.setDisable(true);
        randomBTN.setDisable(true);
        existBTN.setText("Exit");
        existBTN.setOnAction(event1 -> Platform.exit());
    }
    private void setBackground(Cell cell) {

        cell.setStyle("-fx-background-color: red");
        Image image = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/MyApp/view/image/pic8.png")));

        imageView = new ImageView(image);
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);

        cell.setGraphic(imageView);
    }
    private Cell[][] getRandomField(boolean isEnemy) {
        Cell[][] cells = new Cell[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new Cell(i, j, isEnemy);
                cellOnAction(cells[i][j]);
            }
        }

        int ship4 = 1, ship3 = 2, ship2 = 3, ship1 = 4;

        while (true){

            int x = (int) (Math.random() * 10);
            int y = (int) (Math.random() * 10);

            if (ship4 > 0) {
                if (assertShip(cells, x, y, 4)) ship4--;
            }
            else if (ship3 > 0) {
                if (assertShip(cells, x, y, 3)) ship3--;
            }
            else if (ship2 > 0) {
                if (assertShip(cells, x, y, 2)) ship2--;
            }
            else if (ship1 > 0) {
                if (assertShip(cells, x, y, 1)) ship1--;
            }else break;
        }
        return cells;
    }
    private boolean assertShip(Cell[][] cells, int x, int y, int len) {
        return checkUp(cells, x, y, len) || checkRight(cells, x, y, len) ||
                checkDown(cells, x, y, len) || checkLeft(cells, x, y, len);
    }

    private boolean checkRectangle(Cell[][] cells, int xStart, int xEnd,
                                   int yStart, int yEnd, int len, boolean assertShip) {

        for (int i = xStart; i <= xEnd; i++) {
            for (int j = yStart; j <= yEnd ; j++) {

                try {
                    if (!assertShip) {
                        if (isCoreOfRectangle(xStart, xEnd, yStart, yEnd, i, j)
                                && !cells[i][j].isEditable()) {
                            return false;
                        }
                    }
                    else {
                        cells[i][j].setEditable(false);
                        if (isCoreOfRectangle(xStart, xEnd, yStart, yEnd, i, j)) {
                            Image image = new Image(Objects.requireNonNull(
                                    getClass().getResourceAsStream("/MyApp/view/image/pic9.png")));

                            this.imageView1 = new ImageView(image);
                            cells[i][j].setPoint(getPoint(len));
                        }
                    }
                } catch (Exception ignored){
                    if (isCoreOfRectangle(xStart, xEnd, yStart, yEnd, i, j)) return false;
                }
            }
        }
        return true;
    }
    private int getPoint(int len) {
        if (len == 1)
            return 4;
        else if (len == 2)
            return 3;
        else if (len == 3)
            return 2;
        else return 1;
    }
    private boolean checkUp(Cell[][] cells, int x, int y, int len) {
        if (checkRectangle(cells, x - 1, x + 1, y - len, y + 1, len, false))
            return checkRectangle(cells, x - 1, x + 1, y - len, y + 1, len, true);
        else return false;
    }
    private boolean checkRight(Cell[][] cells, int x, int y, int len) {
        if (checkRectangle(cells, x - 1, x + len, y - 1, y + 1, len, false))
            return checkRectangle(cells, x - 1, x + len, y - 1, y + 1, len, true);
        else return false;
    }
    private boolean checkLeft(Cell[][] cells, int x, int y, int len) {
        if (checkRectangle(cells, x - len, x + 1, y - 1, y + 1, len, false))
            return checkRectangle(cells, x - len, x + 1, y - 1, y + 1, len, true);
        else return false;
    }
    private boolean checkDown(Cell[][] cells, int x, int y, int len) {
        if (checkRectangle(cells, x - 1, x + 1, y - 1, y + len, len, false))
            return checkRectangle(cells, x - 1, x + 1, y - 1, y + len, len, true);
        else return false;
    }
    private boolean isCoreOfRectangle(int xStart, int xEnd, int yStart, int yEnd, int i, int j) {
        return i != xStart && i != xEnd && j != yStart && j != yEnd;
    }
    private void result(String path) {
        Thread secondaryThread = new Thread(() -> {

            gameStarted = false;
            Platform.runLater(() -> {
                Stage secondaryStage = new Stage();
                AnchorPane root;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource(path)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene secondaryScene = new Scene(root);
                secondaryStage.setScene(secondaryScene);
                secondaryStage.setResizable(false);
                secondaryStage.setTitle("Final Result");
                secondaryStage.show();
            });
        });

        secondaryThread.start();
    }
}



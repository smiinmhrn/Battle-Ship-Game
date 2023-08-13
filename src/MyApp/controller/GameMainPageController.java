package MyApp.controller;

import MyApp.model.Cell;
import MyApp.model.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameMainPageController implements Initializable {

    @FXML
    private Label pointsLBL;
    @FXML
    private Label enemyPointLBL;

    @FXML
    private Label userNameLBL;
    private User loggedUser;

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
    private boolean gameStarted;
    private Cell[][] userCell;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepareGameBoard();
        randomBTN.setOnAction(event -> prepareGameBoard());
        existBTN.setOnAction(event -> Platform.exit());
        startGameBTN.setOnAction(event -> {
            gameStarted = true;
            randomBTN.setDisable(true);
            startGameBTN.setDisable(true);

            existBTN.setText("Pause");
            existBTN.setOnAction(event1 -> {
                startGameBTN.setText("Resume");
                randomBTN.setDisable(false);
                startGameBTN.setDisable(false);
                existBTN.setText("Exit");
                existBTN.setOnAction(event2 -> Platform.exit());
                gameStarted = false;
            });

        });

    }
    public void initUser(User loggedUser) {
        this.loggedUser = loggedUser;
        userNameLBL.setText(loggedUser.getUserName());
        pointsLBL.setText("Points: " + loggedUser.getPoint());
        enemyPointLBL.setText("Points: " + "0");
    }
    private void prepareGameBoard() {
        clearGameBoard();
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

    private void cellOnAction(Cell cell) {
        cell.setOnAction(event -> {

            int enemyPoint = 0;
            if (cell.isEnemyCell() && !cell.isSelected() && gameStarted) {
                if (cell.getPoint() != 0) {

                    setBackground(cell);
                    loggedUser.setPoint(loggedUser.getPoint() + cell.getPoint());
                    pointsLBL.setText("Points: " + loggedUser.getPoint());

                    if (loggedUser.getPoint() == 50) {
                        // cong window
                    }

                } else {
                    cell.setStyle("-fx-background-color: blue");
                }
                cell.setSelected(true);

                while (true) {
                    int x = (int) (Math.random() * 10);
                    int y = (int) (Math.random() * 10);

                    if (!userCell[x][y].isSelected()) {
                        if (userCell[x][y].getPoint() != 0) {
                            setBackground(userCell[x][y]);
                            userCell[x][y].setSelected(true);
                            enemyPoint = enemyPoint + userCell[x][y].getPoint();

                            enemyPointLBL.setText("Points: " + enemyPoint);

                            if (enemyPoint == 50) {
                                // game over
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
    private void setBackground(Cell cell) {
        cell.setStyle("-fx-background-color: red");
        Image image = new Image(getClass().getResourceAsStream("/MyApp/view/image/p11.png"));

        ImageView imageView = new ImageView(image);
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
}

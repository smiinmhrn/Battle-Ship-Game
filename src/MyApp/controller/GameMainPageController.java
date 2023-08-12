package MyApp.controller;

import MyApp.model.Cell;
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

    @FXML
    private Button randomBTN;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepareGameBoard();
        randomBTN.setOnAction(event -> prepareGameBoard());
    }
    public void initUser(User loggedUser) {
        this.loggedUser = loggedUser;
        userNameLBL.setText(loggedUser.getUserName());
        pointsLBL.setText(String.valueOf(loggedUser.getPoint()));
    }
    private void prepareGameBoard() {
        clearGameBoard();

        Cell[][] userCell = getRandomField();
        Cell[][] enemyCell = getRandomField();

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
    private Cell[][] getRandomField() {
        Cell[][] cells = new Cell[10][10];

        for (int i = 0; i < 10; i++)
            for (int j = 0; j <10 ;j++)
                cells [i][j] = new Cell(i, j);

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

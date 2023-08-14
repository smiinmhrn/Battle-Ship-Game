package MyApp.model;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cell extends Button {
    private int point = 0;
    private int xPosition;
    private int yPosition;

    private boolean isSelected;
    private boolean isEditable = true;
    private boolean isEnemyCell;
    private ImageView imageView;

    public Cell(int xPosition, int yPosition, boolean isEnemyCell) {
        super(" ");
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        setEnemyCell(isEnemyCell);
        setPrefHeight(500.0);
        setPrefWidth(500.0);
    }

    public boolean isEnemyCell() {
        return isEnemyCell;
    }

    public void setEnemyCell(boolean enemyCell) {
        isEnemyCell = enemyCell;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point, ImageView imageView) {
//        clearButt();
        this.point = point;
        if (!isEnemyCell)
            setBackground(imageView);
    }
    private void setBackground(ImageView imageView) {
        setStyle("-fx-background-color: gray");
        Image image = new Image(getClass().getResourceAsStream("/MyApp/view/image/p12.png"));

        imageView = new ImageView(image);
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);

        setGraphic(imageView);
    }


    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

}

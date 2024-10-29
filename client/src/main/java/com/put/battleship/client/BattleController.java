package com.put.battleship.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class BattleController {

    @FXML
    private HBox hBoxVirtual;

    private static final int RECTANGLE_WIDTH = 50;
    private static final int RECTANGLE_HEIGHT = 50;
    private static final int SPACING_BETWEEN_GRIDS = 100;

    @FXML
    public void initialize() {
        GridPane gridPane1 = createGrid(Color.YELLOW);
        GridPane gridPane2 = createGrid(Color.LIGHTGREEN);

        hBoxVirtual.getChildren().addAll(gridPane1, gridPane2);
    }

    private GridPane createGrid(Color color) {
        GridPane gridPane = new GridPane();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT);

                rectangle.setFill(color.darker().interpolate(color.brighter(), Math.random()));

                gridPane.add(rectangle, col, row);
            }
        }
        return gridPane;
    }

    SceneController sceneController = new SceneController();

    public void switchToEndScreen(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "end_screen.fxml");

    }
}

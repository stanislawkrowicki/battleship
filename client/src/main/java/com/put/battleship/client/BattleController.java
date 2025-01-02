package com.put.battleship.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;


import java.io.IOException;

public class BattleController extends GridController {

    private static final int RECTANGLE_WIDTH = 50;
    private static final int RECTANGLE_HEIGHT = 50;
    private final Map<String, Object> carrier = new HashMap<>();
    private final boolean canSetShips = false;
    SceneController sceneController = new SceneController();
    GridPane yourBoard, enemyBoard;
    @FXML
    private HBox hBoxVirtual;

    @FXML
    public void initialize() {
        yourBoard = createGrid(Color.YELLOW, false);
        enemyBoard = createGrid(Color.LIGHTGREEN, true);
        fillYourGrid(yourBoard);
        hBoxVirtual.getChildren().addAll(yourBoard, enemyBoard);
    }

    private GridPane createGrid(Color color, boolean isEnemy) {
        GridPane gridPane = new GridPane();
        for (int row = 0; row < BattleShipsApp.model.getSizey(); row++) {
            for (int col = 0; col < BattleShipsApp.model.getSizex(); col++) {
                Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
                rectangle.setFill(color.darker().interpolate(color.brighter(), BattleShipsApp.model.color(row, col)));
                if (isEnemy) {
                    rectangle.setOnMouseClicked(event -> {
                        if (BattleShipsApp.model.getCanAttack()) {
                            try {
                                handleAttack(rectangle);
                            } catch (BattleShips.AttackNotPermitted e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
                gridPane.add(rectangle, col, row);
            }
        }

        return gridPane;
    }

    private void handleAttack(Rectangle rectangle) throws BattleShips.AttackNotPermitted {
        if (BattleShipsApp.model.handleAttack(GridPane.getRowIndex(rectangle), GridPane.getColumnIndex(rectangle)))
            rectangle.setFill(Color.RED);
        else {
            rectangle.setFill(Color.BLUE);
        }
    }

    public void switchToEndScreen(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "end_screen.fxml");
    }
}

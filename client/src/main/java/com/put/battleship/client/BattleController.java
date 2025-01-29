package com.put.battleship.client;

import javafx.event.ActionEvent;
import javafx.event.Event;
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
    SceneController sceneController = new SceneController();
    GridPane yourBoard, enemyBoard;

    Color hoverColor = Color.DARKGREEN;
    @FXML
    private HBox hBoxVirtual;

    @FXML
    public void initialize() {
        yourBoard = createGrid(false);
        enemyBoard = createGrid(true);
        fillYourGrid(yourBoard);
        hBoxVirtual.getChildren().addAll(yourBoard, enemyBoard);
    }

    private GridPane createGrid(boolean isEnemy) {
        GridPane gridPane = new GridPane();
        for (int row = 0; row < BattleShipsApp.model.getSizey(); row++) {
            for (int col = 0; col < BattleShipsApp.model.getSizex(); col++) {
                Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
                if (!isEnemy)
                    rectangle.setFill(BattleShipsApp.model.getYourBackgroundColor());
                else {
                    rectangle.setFill(BattleShipsApp.model.getEnemyBackgroundColor());
                    final int finalrow = row;
                    final int finalcol = col;
                    rectangle.setOnMouseExited(event -> {
                        if (BattleShipsApp.model.enemyCellNotYetShot(finalrow, finalcol))
                            rectangle.setFill(BattleShipsApp.model.getEnemyBackgroundColor());
                    });
                    rectangle.setOnMouseEntered(event -> {
                        if (BattleShipsApp.model.enemyCellNotYetShot(finalrow, finalcol))
                            rectangle.setFill(hoverColor);
                    });
                    rectangle.setOnMouseClicked(event -> {
                        if (BattleShipsApp.model.enemyCellNotYetShot(finalrow, finalcol)) {
                            if (BattleShipsApp.model.getCanAttack()) {
                                try {
                                    handleAttack(rectangle);
                                    if (BattleShipsApp.model.youWin())
                                        switchToVictoryScreen(event);
                                } catch (BattleShips.AttackNotPermitted | IOException e) {
                                    throw new RuntimeException(e);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                }
                gridPane.add(rectangle, col, row);
            }
        }

        return gridPane;
    }

    private void handleAttack(Rectangle rectangle) throws BattleShips.AttackNotPermitted, InterruptedException {
        if (BattleShipsApp.model.handleAttack(GridPane.getRowIndex(rectangle), GridPane.getColumnIndex(rectangle)))
            rectangle.setFill(Color.RED);
        else {
            rectangle.setFill(Color.BLUE);
        }
    }

    public void switchToEndScreen(Event event) throws IOException {
        sceneController.switchScene(event, "end_screen.fxml");
    }

    public void switchToVictoryScreen(Event event) throws IOException {
        //sceneController.switchScene(event, "victory_screen.fxml");
    }
}

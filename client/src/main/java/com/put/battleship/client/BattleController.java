package com.put.battleship.client;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;


import java.io.IOException;

public class BattleController extends GridController implements HitHandler {

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
        BattleShipsApp.model.hitHandler = this;
        fillYourGrid(yourBoard);
        hBoxVirtual.getChildren().addAll(yourBoard, enemyBoard);
    }

    public void handleHit(boolean hit, int rowIndex, int colIndex, boolean yours) {
        ObservableList<Node> nodes = (yours ? yourBoard : enemyBoard).getChildren();
        ((Rectangle) nodes.get(rowIndex * BattleShipsApp.model.getSizex() + colIndex)).setFill(hit ? Color.RED : Color.BLUE);
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
                        if (BattleShipsApp.model.getCanAttack() && BattleShipsApp.model.enemyCellNotYetShot(finalrow, finalcol))
                            rectangle.setFill(BattleShipsApp.model.getEnemyBackgroundColor());
                    });
                    rectangle.setOnMouseEntered(event -> {
                        if (BattleShipsApp.model.getCanAttack() && BattleShipsApp.model.enemyCellNotYetShot(finalrow, finalcol))
                            rectangle.setFill(hoverColor);
                    });
                    rectangle.setOnMouseClicked(event -> {
                        if (BattleShipsApp.model.getCanAttack() && BattleShipsApp.model.enemyCellNotYetShot(finalrow, finalcol)) {
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
                    });
                }
                gridPane.add(rectangle, col, row);
            }
        }

        return gridPane;
    }

    private void handleAttack(Rectangle rectangle) throws BattleShips.AttackNotPermitted, InterruptedException {
        BattleShipsApp.model.handleAttack(GridPane.getRowIndex(rectangle), GridPane.getColumnIndex(rectangle));
    }

    public void switchToEndScreen(Event event) throws IOException {
        sceneController.switchScene(event, "defeat_screen.fxml");
    }

    public void switchToVictoryScreen(Event event) throws IOException {
        //sceneController.switchScene(event, "victory_screen.fxml");
    }
}
